import { useState } from 'react'
import {
  View,
  StyleSheet,
  ScrollView,
  KeyboardAvoidingView,
  Platform,
  Pressable,
} from 'react-native'
import { Button, Menu, Text, TextInput } from 'react-native-paper'
import { useForm, Controller } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useLocalSearchParams, useRouter } from 'expo-router'
import { DatePickerInput } from 'react-native-paper-dates'
import { format } from 'date-fns'
import FormField from '@/components/ui/FormField'
import { useAddItem } from '@/hooks/useItems'
import { useCategories } from '@/hooks/useCategories'
import { UNIT_OPTIONS, getUnitLabel } from '@/constants/units'

const schema = z.object({
  name: z.string().min(1, 'Item name is required').max(100, 'Max 100 characters'),
  quantity: z.string().min(1, 'Quantity is required'),
  unit: z.string().min(1, 'Unit is required'),
  purchaseDate: z.date(),
  expiryDate: z.date().optional(),
  categoryId: z.string().optional(),
  unitPrice: z.string().optional(),
  notes: z.string().max(500, 'Max 500 characters').optional().or(z.literal('')),
})

type FormData = z.infer<typeof schema>

const AddItemScreen = () => {
  const { pantryId } = useLocalSearchParams<{ pantryId: string }>()
  const router = useRouter()
  const addMutation = useAddItem(pantryId!)
  const { data: categories } = useCategories()
  const [unitMenuVisible, setUnitMenuVisible] = useState(false)
  const [categoryMenuVisible, setCategoryMenuVisible] = useState(false)

  const {
    control,
    handleSubmit,
    formState: { errors },
    setValue,
    watch,
  } = useForm<FormData>({
    resolver: zodResolver(schema),
    defaultValues: {
      name: '',
      quantity: '',
      unit: 'piece',
      purchaseDate: new Date(),
      expiryDate: undefined,
      categoryId: undefined,
      unitPrice: '',
      notes: '',
    },
  })

  const selectedUnit = watch('unit')
  const selectedCategoryId = watch('categoryId')
  const selectedCategory = categories?.find((c) => c.id === selectedCategoryId)

  const onSubmit = (data: FormData) => {
    const quantity = parseFloat(data.quantity)
    if (isNaN(quantity) || quantity <= 0) {
      return
    }

    const unitPrice = data.unitPrice ? parseFloat(data.unitPrice) : undefined

    addMutation.mutate(
      {
        pantryId: pantryId!,
        name: data.name,
        quantity,
        unit: data.unit,
        purchaseDate: format(data.purchaseDate, 'yyyy-MM-dd'),
        expiryDate: data.expiryDate ? format(data.expiryDate, 'yyyy-MM-dd') : undefined,
        categoryId: data.categoryId || undefined,
        unitPrice: unitPrice && !isNaN(unitPrice) ? unitPrice : undefined,
        notes: data.notes || undefined,
      },
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
            label="Item Name"
            error={errors.name?.message}
            placeholder="e.g. Milk"
            autoCapitalize="words"
          />

          <View style={styles.row}>
            <View style={styles.rowHalf}>
              <FormField
                control={control}
                name="quantity"
                label="Quantity"
                error={errors.quantity?.message}
                placeholder="e.g. 2"
                keyboardType="decimal-pad"
              />
            </View>
            <View style={styles.rowHalf}>
              <Controller
                control={control}
                name="unit"
                render={() => (
                  <Menu
                    visible={unitMenuVisible}
                    onDismiss={() => setUnitMenuVisible(false)}
                    anchor={
                      <Pressable onPress={() => setUnitMenuVisible(true)}>
                        <TextInput
                          mode="outlined"
                          label="Unit"
                          value={getUnitLabel(selectedUnit)}
                          editable={false}
                          right={<TextInput.Icon icon="menu-down" />}
                          error={!!errors.unit}
                        />
                      </Pressable>
                    }
                  >
                    {UNIT_OPTIONS.map((option) => (
                      <Menu.Item
                        key={option.value}
                        onPress={() => {
                          setValue('unit', option.value)
                          setUnitMenuVisible(false)
                        }}
                        title={option.label}
                      />
                    ))}
                  </Menu>
                )}
              />
            </View>
          </View>

          <Controller
            control={control}
            name="purchaseDate"
            render={({ field: { value, onChange } }) => (
              <DatePickerInput
                locale="en"
                label="Purchase Date"
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
            placeholder="Any additional notes"
            multiline
            numberOfLines={3}
          />

          {addMutation.isError && (
            <Text variant="bodySmall" style={styles.errorText}>
              {addMutation.error.message}
            </Text>
          )}

          <Button
            mode="contained"
            onPress={handleSubmit(onSubmit)}
            loading={addMutation.isPending}
            disabled={addMutation.isPending}
            style={styles.button}
          >
            Add Item
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
  row: {
    flexDirection: 'row',
    gap: 12,
  },
  rowHalf: {
    flex: 1,
  },
  dateInput: {
    marginTop: 4,
  },
  errorText: {
    color: '#D32F2F',
    marginTop: 4,
  },
  button: {
    marginTop: 12,
  },
})

export default AddItemScreen
