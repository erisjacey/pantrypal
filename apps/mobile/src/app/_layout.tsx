import { useEffect } from 'react'
import { Stack } from 'expo-router'
import { StatusBar } from 'expo-status-bar'
import * as SplashScreen from 'expo-splash-screen'
import { QueryClientProvider } from '@tanstack/react-query'
import { PaperProvider } from 'react-native-paper'
import { AuthProvider, useAuth } from '@/contexts/AuthContext'
import { SnackbarProvider } from '@/contexts/SnackbarContext'
import { queryClient } from '@/lib/queryClient'
import { useAppStateRefetch } from '@/hooks/useAppState'
import { useAppTheme } from '@/constants/theme'

SplashScreen.preventAutoHideAsync()

const RootNavigator = () => {
  const { isLoading } = useAuth()

  useEffect(() => {
    if (!isLoading) {
      SplashScreen.hideAsync()
    }
  }, [isLoading])

  if (isLoading) {
    return null
  }

  return (
    <>
      <Stack screenOptions={{ headerShown: false }}>
        <Stack.Screen name="sign-in" />
        <Stack.Screen name="sign-up" />
        <Stack.Screen name="(app)" />
        <Stack.Screen name="+not-found" />
      </Stack>
      <StatusBar style="auto" />
    </>
  )
}

const RootLayout = () => {
  const theme = useAppTheme()
  useAppStateRefetch()

  return (
    <QueryClientProvider client={queryClient}>
      <PaperProvider theme={theme}>
        <SnackbarProvider>
          <AuthProvider>
            <RootNavigator />
          </AuthProvider>
        </SnackbarProvider>
      </PaperProvider>
    </QueryClientProvider>
  )
}

export default RootLayout
