import { View, StyleSheet, FlatList } from 'react-native'
import { Card, FAB, List } from 'react-native-paper'
import { useRouter } from 'expo-router'
import { usePantries } from '@/hooks/usePantries'
import QueryView from '@/components/ui/QueryView'
import type { Pantry } from '@/types/database'

const PantryCard = ({ pantry }: { pantry: Pantry }) => {
  const router = useRouter()

  return (
    <Card style={styles.card} onPress={() => router.push(`/(app)/pantry/${pantry.id}`)}>
      <Card.Title
        title={pantry.name}
        subtitle={pantry.location ?? undefined}
        left={(props) => <List.Icon {...props} icon="fridge-outline" />}
      />
    </Card>
  )
}

const PantriesScreen = () => {
  const router = useRouter()
  const pantriesQuery = usePantries()

  return (
    <View style={styles.container}>
      <QueryView query={pantriesQuery} emptyMessage="No pantries yet. Create one to get started!">
        {(pantries) => (
          <FlatList
            data={pantries}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => <PantryCard pantry={item} />}
            contentContainerStyle={styles.list}
          />
        )}
      </QueryView>

      <FAB icon="plus" style={styles.fab} onPress={() => router.push('/(app)/pantry/create')} />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  list: {
    padding: 16,
  },
  card: {
    marginBottom: 12,
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 16,
  },
})

export default PantriesScreen
