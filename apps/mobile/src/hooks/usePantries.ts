import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { pantriesApi } from '@/lib/api'
import { useHousehold } from '@/contexts/HouseholdContext'
import { useSnackbar } from '@/contexts/SnackbarContext'

export const pantriesQueryKey = (householdId: string) => ['pantries', householdId]

export const usePantries = () => {
  const { householdId } = useHousehold()
  return useQuery({
    queryKey: pantriesQueryKey(householdId!),
    queryFn: () => pantriesApi.listByHousehold(householdId!),
    enabled: !!householdId,
  })
}

export const usePantry = (id: string | undefined) => {
  return useQuery({
    queryKey: ['pantry', id],
    queryFn: async () => {
      const { data, error } = await (await import('@/lib/supabase')).supabase
        .from('pantries')
        .select('*')
        .eq('id', id!)
        .single()
      if (error) {
        throw error
      }
      return data
    },
    enabled: !!id,
  })
}

export const useCreatePantry = () => {
  const queryClient = useQueryClient()
  const { householdId } = useHousehold()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: (params: { name: string; location?: string }) =>
      pantriesApi.create({ householdId: householdId!, ...params }),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: pantriesQueryKey(householdId!) })
      showSnackbar('Pantry created')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useUpdatePantry = () => {
  const queryClient = useQueryClient()
  const { householdId } = useHousehold()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: (params: { id: string; name?: string; location?: string | null }) => {
      const { id, ...rest } = params
      return pantriesApi.update(id, rest)
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: pantriesQueryKey(householdId!) })
      showSnackbar('Pantry updated')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useDeletePantry = () => {
  const queryClient = useQueryClient()
  const { householdId } = useHousehold()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: pantriesApi.delete,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: pantriesQueryKey(householdId!) })
      showSnackbar('Pantry deleted')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}
