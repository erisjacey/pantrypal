import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { householdsApi } from '@/lib/api'
import { useSnackbar } from '@/contexts/SnackbarContext'

export const HOUSEHOLDS_QUERY_KEY = ['households']

export const useHouseholds = () => {
  return useQuery({
    queryKey: HOUSEHOLDS_QUERY_KEY,
    queryFn: householdsApi.list,
  })
}

export const useCreateHousehold = () => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: householdsApi.create,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: HOUSEHOLDS_QUERY_KEY })
      showSnackbar('Household created')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useJoinHousehold = () => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: householdsApi.joinWithCode,
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: HOUSEHOLDS_QUERY_KEY })
      showSnackbar('Joined household')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}
