import { Redirect, Stack } from 'expo-router'
import { useAuth } from '@/contexts/AuthContext'
import { HouseholdProvider, useHousehold } from '@/contexts/HouseholdContext'
import LoadingScreen from '@/components/ui/LoadingScreen'

const AppNavigator = () => {
  const { isLoading, hasHousehold } = useHousehold()

  if (isLoading) {
    return <LoadingScreen />
  }

  if (!hasHousehold) {
    return <Redirect href="/(app)/onboarding" />
  }

  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen name="(tabs)" />
      <Stack.Screen name="pantry/[id]" options={{ headerShown: true, title: 'Pantry' }} />
      <Stack.Screen name="item/[id]" options={{ headerShown: true, title: 'Item' }} />
      <Stack.Screen name="household/index" options={{ headerShown: true, title: 'Household' }} />
      <Stack.Screen name="onboarding" options={{ headerShown: false }} />
    </Stack>
  )
}

const AppLayout = () => {
  const { session, isLoading } = useAuth()

  if (isLoading) {
    return null
  }

  if (!session) {
    return <Redirect href="/sign-in" />
  }

  return (
    <HouseholdProvider>
      <AppNavigator />
    </HouseholdProvider>
  )
}

export default AppLayout
