import { createClient } from 'npm:@supabase/supabase-js@2';
import { Quantity } from '../_shared/domain/Quantity.ts';
import { Item } from '../_shared/domain/Item.ts';
import { ItemAggregate } from '../_shared/domain/ItemAggregate.ts';

const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Headers': 'authorization, x-client-info, apikey, content-type',
};

Deno.serve(async (req) => {
  if (req.method === 'OPTIONS') {
    return new Response('ok', { headers: corsHeaders });
  }

  try {
    const { pantryId } = await req.json();

    const supabase = createClient(
      Deno.env.get('SUPABASE_URL')!,
      Deno.env.get('SUPABASE_ANON_KEY')!,
      { global: { headers: { Authorization: req.headers.get('Authorization')! } } }
    );

    // Load all active items
    const { data: items, error } = await supabase
      .from('items')
      .select('*, categories(name)')
      .eq('pantry_id', pantryId)
      .eq('is_consumed', false)
      .order('expiry_date', { ascending: true });

    if (error) {
      throw error;
    }

    // Group by barcode and aggregate with unit conversion
    const grouped = new Map<string, any[]>();
    for (const item of items || []) {
      const key = item.barcode || item.id;
      if (!grouped.has(key)) {
        grouped.set(key, []);
      }
      grouped.get(key)!.push(item);
    }

    const inventory = [];
    for (const [key, batches] of grouped) {
      const domainItems = batches.map(b => Item.create({
        id: b.id,
        name: b.name,
        currentQuantity: Quantity.create(b.current_quantity, b.unit),
        initialQuantity: Quantity.create(b.initial_quantity, b.unit),
        expiryDate: b.expiry_date ? new Date(b.expiry_date) : null,
        purchaseDate: new Date(b.purchase_date),
      }));

      const aggregate = new ItemAggregate(key, batches[0].name, domainItems);
      const firstUnit = batches[0].unit;

      inventory.push({
        barcode: batches[0].barcode,
        name: batches[0].name,
        totalQuantity: aggregate.getTotalQuantity(firstUnit).amount,
        unit: firstUnit,
        batchCount: aggregate.getBatchCount(),
        oldestExpiry: batches[0].expiry_date,
        category: batches[0].categories?.name || null,
      });
    }

    return new Response(
      JSON.stringify({ success: true, inventory }),
      { headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  } catch (error) {
    return new Response(
      JSON.stringify({ error: error.message }),
      { status: 400, headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  }
});
