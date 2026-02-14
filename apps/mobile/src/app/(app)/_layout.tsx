import { Redirect, Stack } from 'expo-router'
import { useTheme } from 'react-native-paper'
import { useAuth } from '@/contexts/AuthContext'
import { HouseholdProvider, useHousehold } from '@/contexts/HouseholdContext'
import LoadingScreen from '@/components/ui/LoadingScreen'
import OnboardingScreen from './onboarding'

const AppNavigator = () => {
  const { isLoading, hasHousehold } = useHousehold()
  const theme = useTheme()

  if (isLoading) {
    return <LoadingScreen />
  }

  if (!hasHousehold) {
    return <OnboardingScreen />
  }

  const screenOptions = {
    headerShown: true,
    headerTintColor: theme.colors.onSurface,
    headerStyle: { backgroundColor: theme.colors.surface },
    headerTitleStyle: { color: theme.colors.onSurface },
  }

  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen name="(tabs)" />
      <Stack.Screen name="pantry/[id]" options={{ ...screenOptions, title: 'Pantry' }} />
      <Stack.Screen name="item/[id]" options={{ ...screenOptions, title: 'Item' }} />
      <Stack.Screen name="household/index" options={{ ...screenOptions, title: 'Household' }} />
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
