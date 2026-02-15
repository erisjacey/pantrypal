import { useState } from 'react'
import { View, StyleSheet, ScrollView, KeyboardAvoidingView, Platform } from 'react-native'
import { Button, Dialog, Portal, Text } from 'react-native-paper'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useLocalSearchParams, useRouter } from 'expo-router'
import FormField from '@/components/ui/FormField'
import { usePantry, useUpdatePantry, useDeletePantry } from '@/hooks/usePantries'
import LoadingScreen from '@/components/ui/LoadingScreen'

const schema = z.object({
  name: z.string().min(1, 'Pantry name is required').max(50, 'Max 50 characters'),
  location: z.string().max(100, 'Max 100 characters').optional().or(z.literal('')),
})

type FormData = z.infer<typeof schema>

const EditPantryScreen = () => {
  const { id } = useLocalSearchParams<{ id: string }>()
  const router = useRouter()
  const pantryQuery = usePantry(id)
  const updateMutation = useUpdatePantry()
  const deleteMutation = useDeletePantry()
  const [deleteVisible, setDeleteVisible] = useState(false)

  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>({
    resolver: zodResolver(schema),
    values: pantryQuery.data
      ? { name: pantryQuery.data.name, location: pantryQuery.data.location ?? '' }
      : undefined,
  })

  if (pantryQuery.isLoading) {
    return <LoadingScreen />
  }

  const onSubmit = (data: FormData) => {
    updateMutation.mutate(
      { id: id!, name: data.name, location: data.location || null },
      { onSuccess: () => router.back() },
    )
  }

  const handleDelete = () => {
    deleteMutation.mutate(id!, {
      onSuccess: () => {
        setDeleteVisible(false)
        router.replace('/(app)/(tabs)/pantries')
      },
    })
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
            loading={updateMutation.isPending}
            disabled={updateMutation.isPending}
            style={styles.button}
          >
            Save Changes
          </Button>

          <Button
            mode="outlined"
            onPress={() => setDeleteVisible(true)}
            textColor="#D32F2F"
            style={styles.button}
          >
            Delete Pantry
          </Button>
        </View>
      </ScrollView>

      <Portal>
        <Dialog visible={deleteVisible} onDismiss={() => setDeleteVisible(false)}>
          <Dialog.Title>Delete Pantry</Dialog.Title>
          <Dialog.Content>
            <Text variant="bodyMedium">
              Are you sure? This will permanently delete the pantry and all its items. This action
              cannot be undone.
            </Text>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setDeleteVisible(false)}>Cancel</Button>
            <Button
              onPress={handleDelete}
              loading={deleteMutation.isPending}
              disabled={deleteMutation.isPending}
              textColor="#D32F2F"
            >
              Delete
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
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

export default EditPantryScreen
