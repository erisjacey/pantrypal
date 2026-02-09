import { View, StyleSheet } from 'react-native';
import { Text, Button } from 'react-native-paper';
import { useAuth } from '@/contexts/AuthContext';

const SettingsScreen = () => {
  const { signOut, user } = useAuth();

  return (
    <View style={styles.container}>
      <Text variant="headlineMedium">Settings</Text>
      <Text variant="bodyMedium" style={styles.email}>{user?.email}</Text>
      <Button mode="contained" onPress={signOut} style={styles.button}>
        Sign Out
      </Button>
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
  email: {
    marginTop: 8,
    marginBottom: 24,
  },
  button: {
    marginTop: 8,
  },
});

export default SettingsScreen;
