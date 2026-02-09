/**
 * Database types matching the Supabase schema.
 *
 * In production, generate with:
 *   npx supabase gen types typescript --local > src/types/database.ts
 *
 * These manual types mirror the migration files in supabase/migrations/.
 */

export type Json =
  | string
  | number
  | boolean
  | null
  | { [key: string]: Json | undefined }
  | Json[];

export type UnitValue =
  | 'ml' | 'L' | 'fl_oz' | 'cup' | 'tbsp' | 'tsp' | 'gal' | 'pt' | 'qt'
  | 'mg' | 'g' | 'kg' | 'oz' | 'lb'
  | 'piece' | 'dozen' | 'package';

export type MemberRole = 'owner' | 'member';

export interface Database {
  public: {
    Tables: {
      households: {
        Row: {
          id: string;
          name: string;
          created_by: string | null;
          created_at: string;
          updated_at: string;
        };
        Insert: {
          id?: string;
          name: string;
          created_by?: string;
          created_at?: string;
          updated_at?: string;
        };
        Update: {
          name?: string;
          updated_at?: string;
        };
        Relationships: [
          {
            foreignKeyName: 'households_created_by_fkey';
            columns: ['created_by'];
            isOneToOne: false;
            referencedRelation: 'users';
            referencedColumns: ['id'];
          },
        ];
      };
      household_members: {
        Row: {
          id: string;
          household_id: string;
          user_id: string;
          role: MemberRole;
          joined_at: string;
        };
        Insert: {
          id?: string;
          household_id: string;
          user_id: string;
          role?: MemberRole;
          joined_at?: string;
        };
        Update: {
          role?: MemberRole;
        };
        Relationships: [
          {
            foreignKeyName: 'household_members_household_id_fkey';
            columns: ['household_id'];
            isOneToOne: false;
            referencedRelation: 'households';
            referencedColumns: ['id'];
          },
          {
            foreignKeyName: 'household_members_user_id_fkey';
            columns: ['user_id'];
            isOneToOne: false;
            referencedRelation: 'users';
            referencedColumns: ['id'];
          },
        ];
      };
      pantries: {
        Row: {
          id: string;
          household_id: string;
          name: string;
          location: string | null;
          created_at: string;
          updated_at: string;
        };
        Insert: {
          id?: string;
          household_id: string;
          name: string;
          location?: string | null;
          created_at?: string;
          updated_at?: string;
        };
        Update: {
          name?: string;
          location?: string | null;
          updated_at?: string;
        };
        Relationships: [
          {
            foreignKeyName: 'pantries_household_id_fkey';
            columns: ['household_id'];
            isOneToOne: false;
            referencedRelation: 'households';
            referencedColumns: ['id'];
          },
        ];
      };
      categories: {
        Row: {
          id: string;
          name: string;
          icon: string | null;
          color: string | null;
          created_at: string;
        };
        Insert: {
          id?: string;
          name: string;
          icon?: string | null;
          color?: string | null;
        };
        Update: {
          name?: string;
          icon?: string | null;
          color?: string | null;
        };
        Relationships: [];
      };
      items: {
        Row: {
          id: string;
          pantry_id: string;
          category_id: string | null;
          name: string;
          barcode: string | null;
          description: string | null;
          initial_quantity: number;
          current_quantity: number;
          unit: UnitValue;
          purchase_date: string;
          expiry_date: string | null;
          opened_date: string | null;
          unit_price: number | null;
          total_price: number | null;
          currency: string;
          image_url: string | null;
          notes: string | null;
          added_by: string | null;
          is_consumed: boolean;
          consumed_at: string | null;
          created_at: string;
          updated_at: string;
        };
        Insert: {
          id?: string;
          pantry_id: string;
          category_id?: string | null;
          name: string;
          barcode?: string | null;
          description?: string | null;
          initial_quantity: number;
          current_quantity: number;
          unit: UnitValue;
          purchase_date: string;
          expiry_date?: string | null;
          opened_date?: string | null;
          unit_price?: number | null;
          total_price?: number | null;
          currency?: string;
          image_url?: string | null;
          notes?: string | null;
          added_by?: string | null;
          is_consumed?: boolean;
          consumed_at?: string | null;
        };
        Update: {
          name?: string;
          barcode?: string | null;
          description?: string | null;
          current_quantity?: number;
          unit?: UnitValue;
          expiry_date?: string | null;
          opened_date?: string | null;
          unit_price?: number | null;
          total_price?: number | null;
          category_id?: string | null;
          notes?: string | null;
          image_url?: string | null;
          is_consumed?: boolean;
          consumed_at?: string | null;
        };
        Relationships: [
          {
            foreignKeyName: 'items_pantry_id_fkey';
            columns: ['pantry_id'];
            isOneToOne: false;
            referencedRelation: 'pantries';
            referencedColumns: ['id'];
          },
          {
            foreignKeyName: 'items_category_id_fkey';
            columns: ['category_id'];
            isOneToOne: false;
            referencedRelation: 'categories';
            referencedColumns: ['id'];
          },
          {
            foreignKeyName: 'items_added_by_fkey';
            columns: ['added_by'];
            isOneToOne: false;
            referencedRelation: 'users';
            referencedColumns: ['id'];
          },
        ];
      };
    };
    Views: {
      [_ in never]: never;
    };
    Functions: {
      [_ in never]: never;
    };
    Enums: {
      [_ in never]: never;
    };
    CompositeTypes: {
      [_ in never]: never;
    };
  };
}

// Convenience type aliases
export type Tables = Database['public']['Tables'];
export type Household = Tables['households']['Row'];
export type HouseholdInsert = Tables['households']['Insert'];
export type HouseholdMember = Tables['household_members']['Row'];
export type Pantry = Tables['pantries']['Row'];
export type PantryInsert = Tables['pantries']['Insert'];
export type Category = Tables['categories']['Row'];
export type Item = Tables['items']['Row'];
export type ItemInsert = Tables['items']['Insert'];
