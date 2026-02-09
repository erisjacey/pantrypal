import { View, StyleSheet } from 'react-native';
import { Text } from 'react-native-paper';

const HouseholdScreen = () => {
  return (
    <View style={styles.container}>
      <Text variant="headlineMedium">Household</Text>
      <Text variant="bodyMedium" style={styles.subtitle}>
        Household management will go here
      </Text>
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
  },
});

export default HouseholdScreen;
