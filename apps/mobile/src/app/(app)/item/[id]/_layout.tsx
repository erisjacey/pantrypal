import { Stack } from 'expo-router'
import { useTheme } from 'react-native-paper'

const ItemLayout = () => {
  const theme = useTheme()

  const screenOptions = {
    headerShown: true,
    headerTintColor: theme.colors.onSurface,
    headerStyle: { backgroundColor: theme.colors.surface },
    headerTitleStyle: { color: theme.colors.onSurface },
  }

  return (
    <Stack screenOptions={screenOptions}>
      <Stack.Screen name="index" options={{ title: 'Item' }} />
      <Stack.Screen name="edit" options={{ title: 'Edit Item' }} />
    </Stack>
  )
}

export default ItemLayout
