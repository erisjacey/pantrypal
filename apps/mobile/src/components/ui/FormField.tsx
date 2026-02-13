import { Controller, type Control, type FieldPath, type FieldValues } from 'react-hook-form'
import { View, StyleSheet } from 'react-native'
import { TextInput, HelperText, type TextInputProps } from 'react-native-paper'

type FormFieldProps<T extends FieldValues> = {
  control: Control<T>
  name: FieldPath<T>
  label: string
  error?: string
} & Omit<TextInputProps, 'value' | 'onChangeText' | 'error'>

const FormField = <T extends FieldValues>({
  control,
  name,
  label,
  error,
  ...textInputProps
}: FormFieldProps<T>) => {
  return (
    <View style={styles.container}>
      <Controller
        control={control}
        name={name}
        render={({ field: { onChange, onBlur, value } }) => (
          <TextInput
            label={label}
            mode="outlined"
            value={value ?? ''}
            onChangeText={onChange}
            onBlur={onBlur}
            error={!!error}
            {...textInputProps}
          />
        )}
      />
      {error ? (
        <HelperText type="error" visible>
          {error}
        </HelperText>
      ) : null}
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    marginBottom: 4,
  },
})

export default FormField
