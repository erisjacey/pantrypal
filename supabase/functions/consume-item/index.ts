import { createClient } from 'npm:@supabase/supabase-js@2'
import { Quantity } from '../_shared/domain/Quantity.ts'
import { Item } from '../_shared/domain/Item.ts'
import { ItemAggregate } from '../_shared/domain/ItemAggregate.ts'

const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Headers': 'authorization, x-client-info, apikey, content-type',
}

Deno.serve(async (req) => {
  if (req.method === 'OPTIONS') {
    return new Response('ok', { headers: corsHeaders })
  }

  try {
    const { pantryId, barcode, amount, unit } = await req.json()

    const supabase = createClient(
      Deno.env.get('SUPABASE_URL')!,
      Deno.env.get('SUPABASE_ANON_KEY')!,
      { global: { headers: { Authorization: req.headers.get('Authorization')! } } },
    )

    // Load batches
    const { data: batches, error: fetchError } = await supabase
      .from('items')
      .select('*')
      .eq('pantry_id', pantryId)
      .eq('barcode', barcode)
      .eq('is_consumed', false)
      .order('expiry_date', { ascending: true })

    if (fetchError) {
      throw fetchError
    }
    if (!batches || batches.length === 0) {
      throw new Error('Product not found')
    }

    // Reconstitute domain objects
    const items = batches.map((b) =>
      Item.create({
        id: b.id,
        name: b.name,
        currentQuantity: Quantity.create(b.current_quantity, b.unit),
        initialQuantity: Quantity.create(b.initial_quantity, b.unit),
        expiryDate: b.expiry_date ? new Date(b.expiry_date) : null,
        purchaseDate: new Date(b.purchase_date),
      })
    )

    const aggregate = new ItemAggregate(barcode, batches[0].name, items)

    // Execute domain logic
    aggregate.consume(Quantity.create(amount, unit))

    // Persist remaining batches (partially consumed)
    for (const batch of aggregate.getBatches()) {
      const qty = batch.getCurrentQuantity()
      await supabase
        .from('items')
        .update({
          current_quantity: qty.amount,
          unit: qty.unit,
        })
        .eq('id', batch.id)
    }

    // Persist fully consumed batches
    for (const batch of aggregate.getConsumedBatches()) {
      await supabase
        .from('items')
        .update({
          current_quantity: 0,
          is_consumed: true,
          consumed_at: new Date().toISOString(),
        })
        .eq('id', batch.id)
    }

    return new Response(JSON.stringify({ success: true }), {
      headers: { ...corsHeaders, 'Content-Type': 'application/json' },
    })
  } catch (error) {
    return new Response(JSON.stringify({ error: (error as Error).message }), {
      status: 400,
      headers: { ...corsHeaders, 'Content-Type': 'application/json' },
    })
  }
})
