import { View, StyleSheet } from 'react-native';
import { Text, Button } from 'react-native-paper';
import { Link, Redirect } from 'expo-router';
import { useAuth } from '@/contexts/AuthContext';

const SignUpScreen = () => {
  const { session } = useAuth();

  if (session) {
    return <Redirect href="/(app)/(tabs)" />;
  }

  return (
    <View style={styles.container}>
      <Text variant="headlineLarge">Sign Up</Text>
      <Text variant="bodyMedium" style={styles.subtitle}>
        Registration form will go here
      </Text>
      <Link href="/sign-in" asChild>
        <Button mode="text">Already have an account? Sign in</Button>
      </Link>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
  },
  subtitle: {
    marginTop: 8,
    marginBottom: 24,
  },
});

export default SignUpScreen;
