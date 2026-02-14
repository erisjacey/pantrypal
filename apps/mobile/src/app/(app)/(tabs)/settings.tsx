import { View, StyleSheet } from 'react-native'
import { Text, Button, List, Divider } from 'react-native-paper'
import { useRouter } from 'expo-router'
import { useAuth } from '@/contexts/AuthContext'
import { useHousehold } from '@/contexts/HouseholdContext'
import { useProfile } from '@/hooks/useProfile'

const SettingsScreen = () => {
  const { signOut, user } = useAuth()
  const { activeHousehold } = useHousehold()
  const { data: profile } = useProfile(user?.id)
  const router = useRouter()

  return (
    <View style={styles.container}>
      <View style={styles.section}>
        <Text variant="titleMedium" style={styles.sectionTitle}>
          Account
        </Text>
        <List.Item
          title={profile?.display_name ?? user?.email ?? 'Unknown'}
          description={user?.email ?? 'Email'}
          left={(props) => <List.Icon {...props} icon="account" />}
        />
      </View>

      <Divider />

      <View style={styles.section}>
        <Text variant="titleMedium" style={styles.sectionTitle}>
          Household
        </Text>
        <List.Item
          title={activeHousehold?.name ?? 'None'}
          description="Manage members, invite code, and settings"
          left={(props) => <List.Icon {...props} icon="home-group" />}
          right={(props) => <List.Icon {...props} icon="chevron-right" />}
          onPress={() => router.push('/(app)/household')}
        />
      </View>

      <Divider />

      <View style={styles.signOutSection}>
        <Button mode="outlined" onPress={signOut} icon="logout">
          Sign Out
        </Button>
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 16,
  },
  section: {
    paddingHorizontal: 16,
    paddingVertical: 8,
  },
  sectionTitle: {
    marginBottom: 4,
    opacity: 0.6,
  },
  signOutSection: {
    padding: 16,
    marginTop: 'auto',
  },
})

export default SettingsScreen
