import { createClient } from 'npm:@supabase/supabase-js@2';

const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Headers': 'authorization, x-client-info, apikey, content-type',
};

Deno.serve(async (req) => {
  if (req.method === 'OPTIONS') {
    return new Response('ok', { headers: corsHeaders });
  }

  try {
    const { itemId, ...updates } = await req.json();

    if (!itemId) {
      throw new Error('itemId is required');
    }

    const supabase = createClient(
      Deno.env.get('SUPABASE_URL')!,
      Deno.env.get('SUPABASE_ANON_KEY')!,
      { global: { headers: { Authorization: req.headers.get('Authorization')! } } }
    );

    // Build update payload from allowed fields
    const allowedFields: Record<string, string> = {
      name: 'name',
      barcode: 'barcode',
      description: 'description',
      expiryDate: 'expiry_date',
      openedDate: 'opened_date',
      unitPrice: 'unit_price',
      totalPrice: 'total_price',
      categoryId: 'category_id',
      notes: 'notes',
      imageUrl: 'image_url',
    };

    const payload: Record<string, any> = {};
    for (const [key, column] of Object.entries(allowedFields)) {
      if (updates[key] !== undefined) {
        payload[column] = updates[key];
      }
    }

    if (Object.keys(payload).length === 0) {
      throw new Error('No valid fields to update');
    }

    const { data, error } = await supabase
      .from('items')
      .update(payload)
      .eq('id', itemId)
      .select()
      .single();

    if (error) {
      throw error;
    }

    return new Response(
      JSON.stringify({ success: true, item: data }),
      { headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  } catch (error) {
    return new Response(
      JSON.stringify({ error: error.message }),
      { status: 400, headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  }
});
