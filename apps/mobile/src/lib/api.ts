import type {
  AddItemResponse,
  ConsumeItemResponse,
  GetInventoryResponse,
  UpdateItemResponse,
} from '@/types/api'
import { supabase } from './supabase'

// Edge Function calls (business logic: unit conversion, FIFO, validation)

export const itemsApi = {
  add: async (params: {
    pantryId: string
    name: string
    barcode?: string
    description?: string
    quantity: number
    unit: string
    purchaseDate: string
    expiryDate?: string
    unitPrice?: number
    totalPrice?: number
    categoryId?: string
    notes?: string
  }) => {
    const { data, error } = await supabase.functions.invoke('add-item', {
      body: params,
    })
    if (error) {
      throw error
    }
    return data as AddItemResponse
  },

  consume: async (params: { pantryId: string; barcode: string; amount: number; unit: string }) => {
    const { data, error } = await supabase.functions.invoke('consume-item', {
      body: params,
    })
    if (error) {
      throw error
    }
    return data as ConsumeItemResponse
  },

  getInventory: async (pantryId: string) => {
    const { data, error } = await supabase.functions.invoke('get-pantry-inventory', {
      body: { pantryId },
    })
    if (error) {
      throw error
    }
    return data as GetInventoryResponse
  },

  update: async (params: {
    itemId: string
    name?: string
    barcode?: string
    description?: string
    expiryDate?: string
    openedDate?: string
    unitPrice?: number
    totalPrice?: number
    categoryId?: string
    notes?: string
    imageUrl?: string
  }) => {
    const { data, error } = await supabase.functions.invoke('update-item', {
      body: params,
    })
    if (error) {
      throw error
    }
    return data as UpdateItemResponse
  },
}

// Direct Supabase calls (simple CRUD, no business logic)

export const householdsApi = {
  list: async () => {
    const { data, error } = await supabase
      .from('households')
      .select('*, household_members(count)')
      .order('created_at', { ascending: false })
    if (error) {
      throw error
    }
    return data
  },

  create: async (name: string) => {
    const { data, error } = await supabase.rpc('create_household_with_owner', {
      p_name: name,
    })
    if (error) {
      throw error
    }
    return data
  },

  joinWithCode: async (inviteCode: string) => {
    const { data, error } = await supabase.rpc('join_household_with_code', {
      p_invite_code: inviteCode,
    })
    if (error) {
      throw error
    }
    return data
  },

  regenerateInviteCode: async (householdId: string) => {
    const { data, error } = await supabase.rpc('regenerate_invite_code', {
      p_household_id: householdId,
    })
    if (error) {
      throw error
    }
    return data as string
  },

  getById: async (id: string) => {
    const { data, error } = await supabase
      .from('households')
      .select('*, household_members(*)')
      .eq('id', id)
      .single()
    if (error) {
      throw error
    }
    return data
  },

  update: async (id: string, params: { name: string }) => {
    const { data, error } = await supabase
      .from('households')
      .update({ name: params.name })
      .eq('id', id)
      .select()
      .single()
    if (error) {
      throw error
    }
    return data
  },

  delete: async (id: string) => {
    const { error } = await supabase.from('households').delete().eq('id', id)
    if (error) {
      throw error
    }
  },
}

export const pantriesApi = {
  listByHousehold: async (householdId: string) => {
    const { data, error } = await supabase
      .from('pantries')
      .select('*')
      .eq('household_id', householdId)
      .order('name')
    if (error) {
      throw error
    }
    return data
  },

  create: async (params: { householdId: string; name: string; location?: string }) => {
    const { data, error } = await supabase
      .from('pantries')
      .insert({
        household_id: params.householdId,
        name: params.name,
        location: params.location ?? null,
      })
      .select()
      .single()
    if (error) {
      throw error
    }
    return data
  },

  update: async (id: string, params: { name?: string; location?: string | null }) => {
    const { data, error } = await supabase
      .from('pantries')
      .update(params)
      .eq('id', id)
      .select()
      .single()
    if (error) {
      throw error
    }
    return data
  },

  delete: async (id: string) => {
    const { error } = await supabase.from('pantries').delete().eq('id', id)
    if (error) {
      throw error
    }
  },
}

export const categoriesApi = {
  list: async () => {
    const { data, error } = await supabase.from('categories').select('*').order('name')
    if (error) {
      throw error
    }
    return data
  },
}
