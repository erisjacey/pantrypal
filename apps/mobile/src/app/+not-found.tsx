import { View, StyleSheet } from 'react-native'
import { Text, Button } from 'react-native-paper'
import { Link, Stack } from 'expo-router'

const NotFoundScreen = () => {
  return (
    <>
      <Stack.Screen options={{ title: 'Not Found' }} />
      <View style={styles.container}>
        <Text variant="headlineMedium">Page not found</Text>
        <Link href="/" asChild>
          <Button mode="contained" style={styles.button}>
            Go Home
          </Button>
        </Link>
      </View>
    </>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
  },
  button: {
    marginTop: 16,
  },
})

export default NotFoundScreen
