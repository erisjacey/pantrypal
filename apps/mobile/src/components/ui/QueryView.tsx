import type { UseQueryResult } from '@tanstack/react-query'
import { View, StyleSheet } from 'react-native'
import { Text, Button } from 'react-native-paper'
import LoadingScreen from './LoadingScreen'

type QueryViewProps<T> = {
  query: UseQueryResult<T>
  emptyMessage?: string
  isEmpty?: (data: T) => boolean
  children: (data: T) => React.ReactNode
}

const QueryView = <T,>({
  query,
  emptyMessage = 'Nothing here yet.',
  isEmpty,
  children,
}: QueryViewProps<T>) => {
  if (query.isLoading) {
    return <LoadingScreen />
  }

  if (query.isError) {
    return (
      <View style={styles.center}>
        <Text variant="bodyLarge" style={styles.errorText}>
          Something went wrong
        </Text>
        <Text variant="bodyMedium" style={styles.errorDetail}>
          {query.error?.message ?? 'Unknown error'}
        </Text>
        <Button mode="outlined" onPress={() => query.refetch()} style={styles.retryButton}>
          Retry
        </Button>
      </View>
    )
  }

  if (!query.data) {
    return null
  }

  const dataIsEmpty = isEmpty
    ? isEmpty(query.data)
    : Array.isArray(query.data) && query.data.length === 0

  if (dataIsEmpty) {
    return (
      <View style={styles.center}>
        <Text variant="bodyLarge" style={styles.emptyText}>
          {emptyMessage}
        </Text>
      </View>
    )
  }

  return <>{children(query.data)}</>
}

const styles = StyleSheet.create({
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 24,
  },
  errorText: {
    marginBottom: 4,
  },
  errorDetail: {
    opacity: 0.6,
    marginBottom: 16,
    textAlign: 'center',
  },
  retryButton: {
    marginTop: 8,
  },
  emptyText: {
    opacity: 0.6,
    textAlign: 'center',
  },
})

export default QueryView
