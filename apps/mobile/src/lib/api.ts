import { supabase } from './supabase';
import type {
  AddItemResponse,
  ConsumeItemResponse,
  GetInventoryResponse,
  UpdateItemResponse,
} from '@/types/api';

// Edge Function calls (business logic: unit conversion, FIFO, validation)

export const itemsApi = {
  add: async (params: {
    pantryId: string;
    name: string;
    barcode?: string;
    description?: string;
    quantity: number;
    unit: string;
    purchaseDate: string;
    expiryDate?: string;
    unitPrice?: number;
    totalPrice?: number;
    categoryId?: string;
    notes?: string;
  }) => {
    const { data, error } = await supabase.functions.invoke('add-item', {
      body: params,
    });
    if (error) {
      throw error;
    }
    return data as AddItemResponse;
  },

  consume: async (params: {
    pantryId: string;
    barcode: string;
    amount: number;
    unit: string;
  }) => {
    const { data, error } = await supabase.functions.invoke('consume-item', {
      body: params,
    });
    if (error) {
      throw error;
    }
    return data as ConsumeItemResponse;
  },

  getInventory: async (pantryId: string) => {
    const { data, error } = await supabase.functions.invoke('get-pantry-inventory', {
      body: { pantryId },
    });
    if (error) {
      throw error;
    }
    return data as GetInventoryResponse;
  },

  update: async (params: {
    itemId: string;
    name?: string;
    barcode?: string;
    description?: string;
    expiryDate?: string;
    openedDate?: string;
    unitPrice?: number;
    totalPrice?: number;
    categoryId?: string;
    notes?: string;
    imageUrl?: string;
  }) => {
    const { data, error } = await supabase.functions.invoke('update-item', {
      body: params,
    });
    if (error) {
      throw error;
    }
    return data as UpdateItemResponse;
  },
};

// Direct Supabase calls (simple CRUD, no business logic)

export const householdsApi = {
  list: async () => {
    const { data, error } = await supabase
      .from('households')
      .select('*, household_members(count)')
      .order('created_at', { ascending: false });
    if (error) {
      throw error;
    }
    return data;
  },

  create: async (name: string) => {
    const { data: { user } } = await supabase.auth.getUser();
    if (!user) {
      throw new Error('Not authenticated');
    }

    const { data: household, error: createError } = await supabase
      .from('households')
      .insert({ name, created_by: user.id })
      .select()
      .single();
    if (createError) {
      throw createError;
    }

    const { error: memberError } = await supabase
      .from('household_members')
      .insert({
        household_id: household.id,
        user_id: user.id,
        role: 'owner' as const,
      });
    if (memberError) {
      throw memberError;
    }

    return household;
  },

  getById: async (id: string) => {
    const { data, error } = await supabase
      .from('households')
      .select('*, household_members(*)')
      .eq('id', id)
      .single();
    if (error) {
      throw error;
    }
    return data;
  },
};

export const pantriesApi = {
  listByHousehold: async (householdId: string) => {
    const { data, error } = await supabase
      .from('pantries')
      .select('*')
      .eq('household_id', householdId)
      .order('name');
    if (error) {
      throw error;
    }
    return data;
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
      .single();
    if (error) {
      throw error;
    }
    return data;
  },
};

export const categoriesApi = {
  list: async () => {
    const { data, error } = await supabase
      .from('categories')
      .select('*')
      .order('name');
    if (error) {
      throw error;
    }
    return data;
  },
};
