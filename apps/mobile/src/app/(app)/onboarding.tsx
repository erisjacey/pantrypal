import { useState } from 'react'
import { View, StyleSheet, ScrollView, KeyboardAvoidingView, Platform } from 'react-native'
import { Text, Button, SegmentedButtons } from 'react-native-paper'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import FormField from '@/components/ui/FormField'
import { useCreateHousehold, useJoinHousehold } from '@/hooks/useHouseholds'

type Tab = 'create' | 'join'

const createSchema = z.object({
  name: z.string().min(1, 'Household name is required').max(50, 'Name is too long'),
})

const joinSchema = z.object({
  inviteCode: z
    .string()
    .min(6, 'Invite code must be 6 characters')
    .max(6, 'Invite code must be 6 characters')
    .regex(/^[A-Za-z0-9]+$/, 'Only letters and numbers'),
})

type CreateFormData = z.infer<typeof createSchema>
type JoinFormData = z.infer<typeof joinSchema>

const OnboardingScreen = () => {
  const [tab, setTab] = useState<Tab>('create')
  const createMutation = useCreateHousehold()
  const joinMutation = useJoinHousehold()

  const createForm = useForm<CreateFormData>({
    resolver: zodResolver(createSchema),
    defaultValues: { name: '' },
  })

  const joinForm = useForm<JoinFormData>({
    resolver: zodResolver(joinSchema),
    defaultValues: { inviteCode: '' },
  })

  const onCreateSubmit = (data: CreateFormData) => {
    createMutation.mutate(data.name)
  }

  const onJoinSubmit = (data: JoinFormData) => {
    joinMutation.mutate(data.inviteCode.toUpperCase())
  }

  return (
    <KeyboardAvoidingView
      style={styles.flex}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <ScrollView contentContainerStyle={styles.scrollContent} keyboardShouldPersistTaps="handled">
        <View style={styles.container}>
          <Text variant="headlineLarge" style={styles.title}>
            Get Started
          </Text>
          <Text variant="bodyMedium" style={styles.subtitle}>
            Create a new household or join an existing one
          </Text>

          <SegmentedButtons
            value={tab}
            onValueChange={(value) => setTab(value as Tab)}
            buttons={[
              { value: 'create', label: 'Create' },
              { value: 'join', label: 'Join' },
            ]}
            style={styles.tabs}
          />

          <View style={[styles.form, tab !== 'create' && styles.hidden]}>
            <FormField
              control={createForm.control}
              name="name"
              label="Household Name"
              error={createForm.formState.errors.name?.message}
              placeholder="e.g. Smith Family"
              autoCapitalize="words"
            />
            <Button
              mode="contained"
              onPress={createForm.handleSubmit(onCreateSubmit)}
              loading={createMutation.isPending}
              disabled={createMutation.isPending}
              style={styles.submitButton}
            >
              Create Household
            </Button>
          </View>

          <View style={[styles.form, tab !== 'join' && styles.hidden]}>
            <FormField
              control={joinForm.control}
              name="inviteCode"
              label="Invite Code"
              error={joinForm.formState.errors.inviteCode?.message}
              placeholder="e.g. ABC123"
              autoCapitalize="characters"
              maxLength={6}
            />
            <Button
              mode="contained"
              onPress={joinForm.handleSubmit(onJoinSubmit)}
              loading={joinMutation.isPending}
              disabled={joinMutation.isPending}
              style={styles.submitButton}
            >
              Join Household
            </Button>
          </View>
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
  tabs: {
    marginBottom: 24,
  },
  form: {
    marginBottom: 16,
  },
  hidden: {
    display: 'none',
  },
  submitButton: {
    marginTop: 8,
  },
})

export default OnboardingScreen
