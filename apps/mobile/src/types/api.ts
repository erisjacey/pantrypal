/**
 * API response types matching Edge Function return shapes.
 *
 * These mirror the JSON responses from supabase/functions/.
 */

import type { Item } from './database';

export interface InventoryItem {
  barcode: string | null;
  name: string;
  totalQuantity: number;
  unit: string;
  batchCount: number;
  oldestExpiry: string | null;
  category: string | null;
}

export interface AddItemResponse {
  success: boolean;
  item: Item;
}

export interface ConsumeItemResponse {
  success: boolean;
}

export interface GetInventoryResponse {
  success: boolean;
  inventory: InventoryItem[];
}

export interface UpdateItemResponse {
  success: boolean;
  item: Item;
}
