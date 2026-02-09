import { View, StyleSheet } from 'react-native';
import { Text } from 'react-native-paper';
import { useAuth } from '@/contexts/AuthContext';

const HomeScreen = () => {
  const { user } = useAuth();

  return (
    <View style={styles.container}>
      <Text variant="headlineMedium">Welcome to PantryPal</Text>
      <Text variant="bodyMedium" style={styles.subtitle}>
        Logged in as: {user?.email}
      </Text>
      <Text variant="bodySmall">Dashboard content will go here</Text>
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
    marginBottom: 16,
  },
});

export default HomeScreen;
