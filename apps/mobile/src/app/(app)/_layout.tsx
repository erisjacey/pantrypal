import { Redirect, Stack } from 'expo-router'
import { useAuth } from '@/contexts/AuthContext'

const AppLayout = () => {
  const { session, isLoading } = useAuth()

  if (isLoading) {
    return null
  }

  if (!session) {
    return <Redirect href="/sign-in" />
  }

  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen name="(tabs)" />
      <Stack.Screen name="pantry/[id]" options={{ headerShown: true, title: 'Pantry' }} />
      <Stack.Screen name="item/[id]" options={{ headerShown: true, title: 'Item' }} />
      <Stack.Screen name="household/index" options={{ headerShown: true, title: 'Household' }} />
    </Stack>
  )
}

export default AppLayout
