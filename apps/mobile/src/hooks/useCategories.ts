import { useQuery } from '@tanstack/react-query'
import { categoriesApi } from '@/lib/api'

export const CATEGORIES_QUERY_KEY = ['categories']

export const useCategories = () => {
  return useQuery({
    queryKey: CATEGORIES_QUERY_KEY,
    queryFn: categoriesApi.list,
    staleTime: 1000 * 60 * 60, // 1 hour — rarely changes
  })
}
