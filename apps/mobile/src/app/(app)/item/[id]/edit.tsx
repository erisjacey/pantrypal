import { useState } from 'react'
import {
  View,
  StyleSheet,
  ScrollView,
  KeyboardAvoidingView,
  Platform,
  Pressable,
} from 'react-native'
import { Button, Dialog, Menu, Portal, Text, TextInput } from 'react-native-paper'
import { useForm, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useLocalSearchParams, useRouter } from 'expo-router'
import { DatePickerInput } from 'react-native-paper-dates'
import { parseISO } from 'date-fns'
import FormField from '@/components/ui/FormField'
import LoadingScreen from '@/components/ui/LoadingScreen'
import { useItem, useUpdateItem, useDeleteItem } from '@/hooks/useItems'
import { useCategories } from '@/hooks/useCategories'

const schema = z.object({
  name: z.string().min(1, 'Item name is required').max(100, 'Max 100 characters'),
  expiryDate: z.date().optional(),
  categoryId: z.string().optional(),
  unitPrice: z.string().optional(),
  notes: z.string().max(500, 'Max 500 characters').optional().or(z.literal('')),
})

type FormData = z.infer<typeof schema>

const EditItemScreen = () => {
  const { id } = useLocalSearchParams<{ id: string }>()
  const router = useRouter()
  const itemQuery = useItem(id)
  const { data: categories } = useCategories()
  const [deleteVisible, setDeleteVisible] = useState(false)
  const [categoryMenuVisible, setCategoryMenuVisible] = useState(false)

  const pantryId = itemQuery.data?.pantry_id ?? ''
  const updateMutation = useUpdateItem(pantryId)
  const deleteMutation = useDeleteItem(pantryId)

  const {
    control,
    handleSubmit,
    formState: { errors },
    setValue,
    watch,
  } = useForm<FormData>({
    resolver: zodResolver(schema),
    values: itemQuery.data
      ? {
          name: itemQuery.data.name,
          expiryDate: itemQuery.data.expiry_date ? parseISO(itemQuery.data.expiry_date) : undefined,
          categoryId: itemQuery.data.category_id ?? undefined,
          unitPrice: itemQuery.data.unit_price?.toString() ?? '',
          notes: itemQuery.data.notes ?? '',
        }
      : undefined,
  })

  const selectedCategoryId = watch('categoryId')
  const selectedCategory = categories?.find((c) => c.id === selectedCategoryId)

  if (itemQuery.isLoading) {
    return <LoadingScreen />
  }

  const onSubmit = (data: FormData) => {
    const unitPrice = data.unitPrice ? parseFloat(data.unitPrice) : undefined

    updateMutation.mutate(
      {
        itemId: id!,
        name: data.name,
        expiryDate: data.expiryDate?.toISOString().split('T')[0],
        categoryId: data.categoryId || undefined,
        unitPrice: unitPrice && !isNaN(unitPrice) ? unitPrice : undefined,
        notes: data.notes || undefined,
      },
      { onSuccess: () => router.back() },
    )
  }

  const handleDelete = () => {
    deleteMutation.mutate(id!, {
      onSuccess: () => {
        setDeleteVisible(false)
        router.replace(`/(app)/pantry/${pantryId}`)
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
            label="Item Name"
            error={errors.name?.message}
            autoCapitalize="words"
          />

          <Controller
            control={control}
            name="expiryDate"
            render={({ field: { value, onChange } }) => (
              <DatePickerInput
                locale="en"
                label="Expiry Date (optional)"
                value={value}
                onChange={onChange}
                inputMode="start"
                mode="outlined"
                style={styles.dateInput}
              />
            )}
          />

          <Controller
            control={control}
            name="categoryId"
            render={() => (
              <Menu
                visible={categoryMenuVisible}
                onDismiss={() => setCategoryMenuVisible(false)}
                anchor={
                  <Pressable onPress={() => setCategoryMenuVisible(true)}>
                    <TextInput
                      mode="outlined"
                      label="Category (optional)"
                      value={selectedCategory?.name ?? ''}
                      editable={false}
                      right={
                        selectedCategoryId ? (
                          <TextInput.Icon
                            icon="close"
                            onPress={() => setValue('categoryId', undefined)}
                          />
                        ) : (
                          <TextInput.Icon icon="menu-down" />
                        )
                      }
                    />
                  </Pressable>
                }
              >
                {categories?.map((cat) => (
                  <Menu.Item
                    key={cat.id}
                    onPress={() => {
                      setValue('categoryId', cat.id)
                      setCategoryMenuVisible(false)
                    }}
                    title={cat.name}
                  />
                ))}
              </Menu>
            )}
          />

          <FormField
            control={control}
            name="unitPrice"
            label="Unit Price (optional)"
            error={errors.unitPrice?.message}
            placeholder="e.g. 3.50"
            keyboardType="decimal-pad"
          />

          <FormField
            control={control}
            name="notes"
            label="Notes (optional)"
            error={errors.notes?.message}
            multiline
            numberOfLines={3}
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
            Delete Item
          </Button>
        </View>
      </ScrollView>

      <Portal>
        <Dialog visible={deleteVisible} onDismiss={() => setDeleteVisible(false)}>
          <Dialog.Title>Delete Item</Dialog.Title>
          <Dialog.Content>
            <Text variant="bodyMedium">
              Are you sure? This will permanently delete this item batch. This action cannot be
              undone.
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
  dateInput: {
    marginTop: 4,
  },
  button: {
    marginTop: 8,
  },
})

export default EditItemScreen
