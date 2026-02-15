import { View, StyleSheet, ScrollView, KeyboardAvoidingView, Platform } from 'react-native'
import { Button } from 'react-native-paper'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useRouter } from 'expo-router'
import FormField from '@/components/ui/FormField'
import { useCreatePantry } from '@/hooks/usePantries'

const schema = z.object({
  name: z.string().min(1, 'Pantry name is required').max(50, 'Max 50 characters'),
  location: z.string().max(100, 'Max 100 characters').optional().or(z.literal('')),
})

type FormData = z.infer<typeof schema>

const CreatePantryScreen = () => {
  const router = useRouter()
  const createMutation = useCreatePantry()

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
    defaultValues: { name: '', location: '' },
  })

  const onSubmit = (data: FormData) => {
    createMutation.mutate(
      { name: data.name, location: data.location || undefined },
      { onSuccess: () => router.back() },
    )
  }

  return (
    <KeyboardAvoidingView
      style={styles.flex}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <ScrollView contentContainerStyle={styles.content} keyboardShouldPersistTaps="handled">
        <View style={styles.form}>
          <FormField
            control={control}
            name="name"
            label="Pantry Name"
            error={errors.name?.message}
            placeholder="e.g. Kitchen"
            autoCapitalize="words"
          />

          <FormField
            control={control}
            name="location"
            label="Location (optional)"
            error={errors.location?.message}
            placeholder="e.g. Main floor"
            autoCapitalize="sentences"
          />

          <Button
            mode="contained"
            onPress={handleSubmit(onSubmit)}
            loading={createMutation.isPending}
            disabled={createMutation.isPending}
            style={styles.button}
          >
            Create Pantry
          </Button>
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  )
}

const styles = StyleSheet.create({
  flex: {
    flex: 1,
  },
  content: {
    padding: 24,
  },
  form: {
    gap: 4,
  },
  button: {
    marginTop: 8,
  },
})

export default CreatePantryScreen
