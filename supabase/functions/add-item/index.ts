import { createClient } from 'npm:@supabase/supabase-js@2'

const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Headers': 'authorization, x-client-info, apikey, content-type',
}

Deno.serve(async (req) => {
  if (req.method === 'OPTIONS') {
    return new Response('ok', { headers: corsHeaders })
  }

  try {
    const {
      pantryId,
      name,
      barcode,
      description,
      quantity,
      unit,
      purchaseDate,
      expiryDate,
      unitPrice,
      totalPrice,
      categoryId,
      notes,
    } = await req.json()

    const supabase = createClient(
      Deno.env.get('SUPABASE_URL')!,
      Deno.env.get('SUPABASE_ANON_KEY')!,
      { global: { headers: { Authorization: req.headers.get('Authorization')! } } },
    )

    // Get current user
    const { data: { user }, error: authError } = await supabase.auth.getUser()
    if (authError || !user) {
      throw new Error('Unauthorized')
    }

    const { data, error } = await supabase
      .from('items')
      .insert({
        pantry_id: pantryId,
        name,
        barcode: barcode || null,
        description: description || null,
        initial_quantity: quantity,
        current_quantity: quantity,
        unit,
        purchase_date: purchaseDate,
        expiry_date: expiryDate || null,
        unit_price: unitPrice || null,
        total_price: totalPrice || null,
        category_id: categoryId || null,
        notes: notes || null,
        added_by: user.id,
      })
      .select()
      .single()

    if (error) {
      throw error
    }

    return new Response(
      JSON.stringify({ success: true, item: data }),
      { headers: { ...corsHeaders, 'Content-Type': 'application/json' } },
    )
  } catch (error) {
    return new Response(
      JSON.stringify({ error: (error as Error).message }),
      { status: 400, headers: { ...corsHeaders, 'Content-Type': 'application/json' } },
    )
  }
})
