import { View, StyleSheet, ScrollView } from 'react-native'
import { Card, IconButton, Text } from 'react-native-paper'
import { useLocalSearchParams, useRouter } from 'expo-router'
import { usePantry } from '@/hooks/usePantries'
import QueryView from '@/components/ui/QueryView'

const PantryDetailScreen = () => {
  const { id } = useLocalSearchParams<{ id: string }>()
  const router = useRouter()
  const pantryQuery = usePantry(id)

  return (
    <QueryView query={pantryQuery} emptyMessage="Pantry not found.">
      {(pantry) => (
        <ScrollView style={styles.container} contentContainerStyle={styles.content}>
          <Card style={styles.card}>
            <Card.Title
              title={pantry.name}
              subtitle={pantry.location ?? 'No location set'}
              right={(props) => (
                <IconButton
                  {...props}
                  icon="pencil"
                  onPress={() => router.push(`/(app)/pantry/${id}/edit`)}
                />
              )}
            />
          </Card>

          <View style={styles.itemsSection}>
            <Text variant="titleMedium" style={styles.sectionTitle}>
              Items
            </Text>
            <Text variant="bodyMedium" style={styles.emptyText}>
              No items in this pantry yet. Add your first item!
            </Text>
          </View>
        </ScrollView>
      )}
    </QueryView>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  content: {
    padding: 16,
  },
  card: {
    marginBottom: 16,
  },
  itemsSection: {
    marginTop: 8,
  },
  sectionTitle: {
    marginBottom: 12,
  },
  emptyText: {
    opacity: 0.6,
    textAlign: 'center',
    marginTop: 24,
  },
})

export default PantryDetailScreen
