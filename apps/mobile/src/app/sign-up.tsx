import { useState } from 'react'
import { View, StyleSheet, ScrollView, KeyboardAvoidingView, Platform } from 'react-native'
import { Text, Button, Banner } from 'react-native-paper'
import { Link, Redirect } from 'expo-router'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useAuth } from '@/contexts/AuthContext'
import FormField from '@/components/ui/FormField'

const signUpSchema = z
  .object({
    email: z.email('Invalid email address'),
    password: z.string().min(6, 'Password must be at least 6 characters'),
    confirmPassword: z.string().min(1, 'Please confirm your password'),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: 'Passwords do not match',
    path: ['confirmPassword'],
  })

type SignUpFormData = z.infer<typeof signUpSchema>

const SignUpScreen = () => {
  const { session, signUp, signIn } = useAuth()
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<SignUpFormData>({
    resolver: zodResolver(signUpSchema),
    defaultValues: { email: '', password: '', confirmPassword: '' },
  })

  if (session) {
    return <Redirect href="/(app)/(tabs)" />
  }

  const onSubmit = async (data: SignUpFormData) => {
    setError(null)
    setLoading(true)
    try {
      await signUp(data.email, data.password)
      // Supabase local auto-confirms, so sign in immediately
      await signIn(data.email, data.password)
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Sign up failed')
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
            Create Account
          </Text>
          <Text variant="bodyMedium" style={styles.subtitle}>
            Sign up for PantryPal
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
              autoComplete="new-password"
              textContentType="newPassword"
            />

            <FormField
              control={control}
              name="confirmPassword"
              label="Confirm Password"
              error={errors.confirmPassword?.message}
              secureTextEntry
              autoComplete="new-password"
              textContentType="newPassword"
            />

            <Button
              mode="contained"
              onPress={handleSubmit(onSubmit)}
              loading={loading}
              disabled={loading}
              style={styles.submitButton}
            >
              Create Account
            </Button>
          </View>

          <Link href="/sign-in" asChild>
            <Button mode="text">Already have an account? Sign in</Button>
          </Link>
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
})

export default SignUpScreen
