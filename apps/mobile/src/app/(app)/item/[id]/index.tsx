import { View, StyleSheet, ScrollView } from 'react-native'
import { Card, Chip, IconButton, List, Text } from 'react-native-paper'
import { useLocalSearchParams, useRouter } from 'expo-router'
import { format, isPast, isBefore, addDays, parseISO } from 'date-fns'
import { useItem } from '@/hooks/useItems'
import { useCategories } from '@/hooks/useCategories'
import { getUnitLabel } from '@/constants/units'
import QueryView from '@/components/ui/QueryView'

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

const formatDate = (dateStr: string | null) => {
  if (!dateStr) {
    return '—'
  }
  return format(parseISO(dateStr), 'MMM d, yyyy')
}

const ItemDetailScreen = () => {
  const { id } = useLocalSearchParams<{ id: string }>()
  const router = useRouter()
  const itemQuery = useItem(id)
  const { data: categories } = useCategories()

  return (
    <QueryView query={itemQuery} emptyMessage="Item not found.">
      {(item) => {
        const expiry = getExpiryStatus(item.expiry_date)
        const category = categories?.find((c) => c.id === item.category_id)

        return (
          <ScrollView style={styles.container} contentContainerStyle={styles.content}>
            <Card style={styles.card}>
              <Card.Title
                title={item.name}
                subtitle={`${item.current_quantity} ${getUnitLabel(item.unit)}`}
                right={(props) => (
                  <IconButton
                    {...props}
                    icon="pencil"
                    onPress={() => router.push(`/(app)/item/${id}/edit`)}
                  />
                )}
              />
            </Card>

            {expiry && (
              <Chip
                style={[styles.expiryChip, { backgroundColor: `${expiry.color}20` }]}
                textStyle={{ color: expiry.color }}
              >
                {expiry.label}
              </Chip>
            )}

            <Card style={styles.card}>
              <Card.Content>
                <List.Item
                  title="Purchase Date"
                  description={formatDate(item.purchase_date)}
                  left={(props) => <List.Icon {...props} icon="calendar" />}
                />
                <List.Item
                  title="Expiry Date"
                  description={formatDate(item.expiry_date)}
                  left={(props) => <List.Icon {...props} icon="calendar-clock" />}
                />
                {category && (
                  <List.Item
                    title="Category"
                    description={category.name}
                    left={(props) => <List.Icon {...props} icon="tag" />}
                  />
                )}
                {item.unit_price != null && (
                  <List.Item
                    title="Unit Price"
                    description={`$${item.unit_price.toFixed(2)}`}
                    left={(props) => <List.Icon {...props} icon="currency-usd" />}
                  />
                )}
                {item.notes && (
                  <List.Item
                    title="Notes"
                    description={item.notes}
                    left={(props) => <List.Icon {...props} icon="note-text" />}
                  />
                )}
              </Card.Content>
            </Card>

            <Card style={styles.card}>
              <Card.Content>
                <Text variant="labelSmall" style={styles.metaLabel}>
                  Batch Info
                </Text>
                <View style={styles.metaRow}>
                  <Text variant="bodySmall" style={styles.metaText}>
                    Initial: {item.initial_quantity} {getUnitLabel(item.unit)}
                  </Text>
                  <Text variant="bodySmall" style={styles.metaText}>
                    Remaining: {item.current_quantity} {getUnitLabel(item.unit)}
                  </Text>
                </View>
              </Card.Content>
            </Card>
          </ScrollView>
        )
      }}
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
    marginBottom: 12,
  },
  expiryChip: {
    alignSelf: 'flex-start',
    marginBottom: 12,
  },
  metaLabel: {
    opacity: 0.6,
    marginBottom: 4,
  },
  metaRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  metaText: {
    opacity: 0.7,
  },
})

export default ItemDetailScreen
