import { createContext, useContext, useMemo, type ReactNode } from 'react'
import { useHouseholds } from '@/hooks/useHouseholds'
import { useAuth } from '@/contexts/AuthContext'
import type { Household } from '@/types/database'

interface HouseholdContextValue {
  activeHousehold: Household | null
  householdId: string | null
  isOwner: boolean
  isLoading: boolean
  hasHousehold: boolean
}

const HouseholdContext = createContext<HouseholdContextValue | null>(null)

export const useHousehold = () => {
  const context = useContext(HouseholdContext)
  if (!context) {
    throw new Error('useHousehold must be used within a HouseholdProvider')
  }
  return context
}

export const HouseholdProvider = ({ children }: { children: ReactNode }) => {
  const { user } = useAuth()
  const { data: households, isLoading } = useHouseholds()

  const value = useMemo(() => {
    // MVP: use first household
    const activeHousehold = households?.[0] ?? null
    const householdId = activeHousehold?.id ?? null
    const isOwner = activeHousehold?.created_by === user?.id

    return {
      activeHousehold,
      householdId,
      isOwner,
      isLoading,
      hasHousehold: !!activeHousehold,
    }
  }, [households, isLoading, user?.id])

  return <HouseholdContext.Provider value={value}>{children}</HouseholdContext.Provider>
}
