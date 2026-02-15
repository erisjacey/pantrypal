import { View, StyleSheet, FlatList } from 'react-native'
import { Card, Chip, FAB, IconButton, Text } from 'react-native-paper'
import { useLocalSearchParams, useRouter } from 'expo-router'
import { isPast, isBefore, addDays, parseISO } from 'date-fns'
import { usePantry } from '@/hooks/usePantries'
import { useItems } from '@/hooks/useItems'
import { getUnitLabel } from '@/constants/units'
import QueryView from '@/components/ui/QueryView'
import type { Item } from '@/types/database'

const getExpiryStatus = (expiryDate: string | null) => {
  if (!expiryDate) {
    return null
  }
  const date = parseISO(expiryDate)
  if (isPast(date)) {
    return { label: 'Expired', color: '#D32F2F' } as const
  }
  if (isBefore(date, addDays(new Date(), 7))) {
    return { label: 'Expiring soon', color: '#F57C00' } as const
  }
  return { label: 'Fresh', color: '#388E3C' } as const
}

const ItemCard = ({ item }: { item: Item }) => {
  const router = useRouter()
  const expiry = getExpiryStatus(item.expiry_date)

  return (
    <Card style={styles.itemCard} onPress={() => router.push(`/(app)/item/${item.id}`)}>
      <Card.Title
        title={item.name}
        subtitle={`${item.current_quantity} ${getUnitLabel(item.unit)}`}
        right={() =>
          expiry ? (
            <Chip
              compact
              style={[styles.expiryChip, { backgroundColor: `${expiry.color}20` }]}
              textStyle={{ color: expiry.color, fontSize: 11 }}
            >
              {expiry.label}
            </Chip>
          ) : null
        }
      />
    </Card>
  )
}

const PantryDetailScreen = () => {
  const { id } = useLocalSearchParams<{ id: string }>()
  const router = useRouter()
  const pantryQuery = usePantry(id)
  const itemsQuery = useItems(id)

  return (
    <QueryView query={pantryQuery} emptyMessage="Pantry not found.">
      {(pantry) => (
        <View style={styles.container}>
          <Card style={styles.headerCard}>
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
            <QueryView
              query={itemsQuery}
              emptyMessage="No items in this pantry yet. Add your first item!"
            >
              {(items) => (
                <FlatList
                  data={items}
                  keyExtractor={(item) => item.id}
                  renderItem={({ item }) => <ItemCard item={item} />}
                />
              )}
            </QueryView>
          </View>

          <FAB
            icon="plus"
            style={styles.fab}
            onPress={() => router.push(`/(app)/item/add?pantryId=${id}`)}
          />
        </View>
      )}
    </QueryView>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  headerCard: {
    marginBottom: 16,
  },
  itemsSection: {
    flex: 1,
  },
  sectionTitle: {
    marginBottom: 12,
  },
  itemCard: {
    marginBottom: 8,
  },
  expiryChip: {
    marginRight: 8,
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 16,
  },
})

export default PantryDetailScreen
