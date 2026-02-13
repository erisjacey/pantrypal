import { useState } from 'react'
import { View, StyleSheet, ScrollView, KeyboardAvoidingView, Platform } from 'react-native'
import { Text, Button, Banner } from 'react-native-paper'
import { Link, Redirect } from 'expo-router'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useAuth } from '@/contexts/AuthContext'
import FormField from '@/components/ui/FormField'

const DEV_EMAIL = 'test@example.com'
const DEV_PASSWORD = 'password123'

const signInSchema = z.object({
  email: z.email('Invalid email address'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
})

type SignInFormData = z.infer<typeof signInSchema>

const SignInScreen = () => {
  const { session, signIn } = useAuth()
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<SignInFormData>({
    resolver: zodResolver(signInSchema),
    defaultValues: { email: '', password: '' },
  })

  if (session) {
    return <Redirect href="/(app)/(tabs)" />
  }

  const onSubmit = async (data: SignInFormData) => {
    setError(null)
    setLoading(true)
    try {
      await signIn(data.email, data.password)
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Sign in failed')
    } finally {
      setLoading(false)
    }
  }

  const handleDevSignIn = async () => {
    setError(null)
    setLoading(true)
    try {
      await signIn(DEV_EMAIL, DEV_PASSWORD)
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Sign in failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <KeyboardAvoidingView
      style={styles.flex}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <ScrollView contentContainerStyle={styles.scrollContent} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <Text variant="headlineLarge" style={styles.title}>
            Welcome Back
          </Text>
          <Text variant="bodyMedium" style={styles.subtitle}>
            Sign in to your PantryPal account
          </Text>

          <Banner
            visible={!!error}
            actions={[{ label: 'Dismiss', onPress: () => setError(null) }]}
            icon="alert-circle"
          >
            {error}
          </Banner>

          <View style={styles.form}>
            <FormField
              control={control}
              name="email"
              label="Email"
              error={errors.email?.message}
              keyboardType="email-address"
              autoCapitalize="none"
              autoComplete="email"
              textContentType="emailAddress"
            />

            <FormField
              control={control}
              name="password"
              label="Password"
              error={errors.password?.message}
              secureTextEntry
              autoComplete="password"
              textContentType="password"
            />

            <Button
              mode="contained"
              onPress={handleSubmit(onSubmit)}
              loading={loading}
              disabled={loading}
              style={styles.submitButton}
            >
              Sign In
            </Button>
          </View>

          <Link href="/sign-up" asChild>
            <Button mode="text">Don&apos;t have an account? Sign up</Button>
          </Link>

          {__DEV__ && (
            <Button
              mode="outlined"
              onPress={handleDevSignIn}
              loading={loading}
              disabled={loading}
              style={styles.devButton}
            >
              Dev Sign In (test@example.com)
            </Button>
          )}
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  )
}

const styles = StyleSheet.create({
  flex: {
    flex: 1,
  },
  scrollContent: {
    flexGrow: 1,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 24,
  },
  title: {
    textAlign: 'center',
    marginBottom: 4,
  },
  subtitle: {
    textAlign: 'center',
    opacity: 0.6,
    marginBottom: 24,
  },
  form: {
    marginBottom: 16,
  },
  submitButton: {
    marginTop: 8,
  },
  devButton: {
    marginTop: 16,
  },
})

export default SignInScreen
