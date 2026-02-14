import { useQuery } from '@tanstack/react-query'
import { supabase } from '@/lib/supabase'

export const PROFILE_QUERY_KEY = ['profile']

export const useProfile = (userId: string | undefined) => {
  return useQuery({
    queryKey: [...PROFILE_QUERY_KEY, userId],
    queryFn: async () => {
      const { data, error } = await supabase.from('profiles').select('*').eq('id', userId!).single()
      if (error) {
        throw error
      }
      return data
    },
    enabled: !!userId,
  })
}
