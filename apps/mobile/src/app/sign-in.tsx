import { useState } from 'react'
import { View, StyleSheet } from 'react-native'
import { Text, Button } from 'react-native-paper'
import { Link, Redirect } from 'expo-router'
import { useAuth } from '@/contexts/AuthContext'

const DEV_EMAIL = 'dev@pantrypal.local'
const DEV_PASSWORD = 'devpassword123'

const SignInScreen = () => {
  const { session, signUp, signIn } = useAuth()
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  if (session) {
    return <Redirect href="/(app)/(tabs)" />
  }

  const handleDevSignIn = async () => {
    setError(null)
    setLoading(true)
    try {
      // Try sign in first; if user doesn't exist, sign up then sign in
      try {
        await signIn(DEV_EMAIL, DEV_PASSWORD)
      } catch {
        await signUp(DEV_EMAIL, DEV_PASSWORD)
        await signIn(DEV_EMAIL, DEV_PASSWORD)
      }
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Sign in failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <View style={styles.container}>
      <Text variant="headlineLarge">Sign In</Text>
      <Text variant="bodyMedium" style={styles.subtitle}>
        Email/password form will go here
      </Text>
      <Button
        mode="contained"
        onPress={handleDevSignIn}
        loading={loading}
        disabled={loading}
        style={styles.devButton}
      >
        Dev Sign In
      </Button>
      {error && (
        <Text variant="bodySmall" style={styles.error}>
          {error}
        </Text>
      )}
      <Link href="/sign-up" asChild>
        <Button mode="text">Don&apos;t have an account? Sign up</Button>
      </Link>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
  },
  subtitle: {
    marginTop: 8,
    marginBottom: 24,
  },
  devButton: {
    marginBottom: 16,
  },
  error: {
    color: 'red',
    marginBottom: 16,
  },
})

export default SignInScreen
