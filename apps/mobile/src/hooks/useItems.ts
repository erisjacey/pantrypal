import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { supabase } from '@/lib/supabase'
import { itemsApi } from '@/lib/api'
import { useSnackbar } from '@/contexts/SnackbarContext'

export const itemsQueryKey = (pantryId: string) => ['items', pantryId]

export const useItems = (pantryId: string | undefined) => {
  return useQuery({
    queryKey: itemsQueryKey(pantryId!),
    queryFn: async () => {
      const { data, error } = await supabase
        .from('items')
        .select('*')
        .eq('pantry_id', pantryId!)
        .eq('is_consumed', false)
        .order('expiry_date', { ascending: true, nullsFirst: false })
      if (error) {
        throw error
      }
      return data
    },
    enabled: !!pantryId,
  })
}

export const useItem = (id: string | undefined) => {
  return useQuery({
    queryKey: ['item', id],
    queryFn: async () => {
      const { data, error } = await supabase.from('items').select('*').eq('id', id!).single()
      if (error) {
        throw error
      }
      return data
    },
    enabled: !!id,
  })
}

export const useAddItem = (pantryId: string) => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: itemsApi.add,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: itemsQueryKey(pantryId) })
      showSnackbar('Item added')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useUpdateItem = (pantryId: string) => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: itemsApi.update,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: itemsQueryKey(pantryId) })
      showSnackbar('Item updated')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useDeleteItem = (pantryId: string) => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: async (id: string) => {
      const { error } = await supabase.from('items').delete().eq('id', id)
      if (error) {
        throw error
      }
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: itemsQueryKey(pantryId) })
      showSnackbar('Item deleted')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}
