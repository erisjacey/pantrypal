import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { supabase } from '@/lib/supabase'
import { householdsApi } from '@/lib/api'
import { useSnackbar } from '@/contexts/SnackbarContext'
import { HOUSEHOLDS_QUERY_KEY } from './useHouseholds'

const membersQueryKey = (householdId: string) => ['household-members', householdId]

export type MemberWithProfile = {
  id: string
  household_id: string
  user_id: string
  role: string
  joined_at: string
  profile: { display_name: string; avatar_url: string | null } | null
}

export const useHouseholdMembers = (householdId: string | null) => {
  return useQuery({
    queryKey: membersQueryKey(householdId!),
    queryFn: async () => {
      const { data: members, error } = await supabase
        .from('household_members')
        .select('*')
        .eq('household_id', householdId!)
        .order('joined_at')
      if (error) {
        throw error
      }

      const userIds = members.map((m) => m.user_id)
      const { data: profiles, error: profilesError } = await supabase
        .from('profiles')
        .select('id, display_name, avatar_url')
        .in('id', userIds)
      if (profilesError) {
        throw profilesError
      }

      return members.map((m) => ({
        ...m,
        profile: profiles?.find((p) => p.id === m.user_id) ?? null,
      })) as MemberWithProfile[]
    },
    enabled: !!householdId,
  })
}

export const useRemoveMember = () => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: async ({ memberId, householdId }: { memberId: string; householdId: string }) => {
      const { error } = await supabase
        .from('household_members')
        .delete()
        .eq('id', memberId)
        .eq('household_id', householdId)
      if (error) {
        throw error
      }
    },
    onSuccess: (_data, variables) => {
      queryClient.invalidateQueries({ queryKey: membersQueryKey(variables.householdId) })
      showSnackbar('Member removed')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}

export const useRegenerateInviteCode = () => {
  const queryClient = useQueryClient()
  const { showSnackbar } = useSnackbar()
  return useMutation({
    mutationFn: householdsApi.regenerateInviteCode,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: HOUSEHOLDS_QUERY_KEY })
      showSnackbar('Invite code regenerated')
    },
    onError: (error) => {
      showSnackbar(error.message, 'error')
    },
  })
}
