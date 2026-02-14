import { useState } from 'react'
import { View, StyleSheet, ScrollView } from 'react-native'
import {
  Text,
  Button,
  Card,
  Chip,
  Dialog,
  Portal,
  TextInput,
  IconButton,
  List,
} from 'react-native-paper'
import * as Clipboard from 'expo-clipboard'
import { useQueryClient } from '@tanstack/react-query'
import { useRouter } from 'expo-router'
import { useAuth } from '@/contexts/AuthContext'
import { useHousehold } from '@/contexts/HouseholdContext'
import { useSnackbar } from '@/contexts/SnackbarContext'
import { householdsApi } from '@/lib/api'
import { HOUSEHOLDS_QUERY_KEY } from '@/hooks/useHouseholds'
import {
  useHouseholdMembers,
  useRemoveMember,
  useRegenerateInviteCode,
} from '@/hooks/useHouseholdMembers'
import QueryView from '@/components/ui/QueryView'

const HouseholdScreen = () => {
  const { user } = useAuth()
  const { activeHousehold, householdId, isOwner } = useHousehold()
  const { showSnackbar } = useSnackbar()
  const router = useRouter()
  const queryClient = useQueryClient()
  const membersQuery = useHouseholdMembers(householdId)
  const removeMember = useRemoveMember()
  const regenerateCode = useRegenerateInviteCode()

  const [renameVisible, setRenameVisible] = useState(false)
  const [deleteVisible, setDeleteVisible] = useState(false)
  const [newName, setNewName] = useState('')
  const [renaming, setRenaming] = useState(false)
  const [deleting, setDeleting] = useState(false)

  const handleCopyCode = async () => {
    if (activeHousehold?.invite_code) {
      await Clipboard.setStringAsync(activeHousehold.invite_code)
      showSnackbar('Invite code copied')
    }
  }

  const handleRename = async () => {
    if (!householdId || !newName.trim()) {
      return
    }
    setRenaming(true)
    try {
      await householdsApi.update(householdId, { name: newName.trim() })
      queryClient.invalidateQueries({ queryKey: HOUSEHOLDS_QUERY_KEY })
      setRenameVisible(false)
      showSnackbar('Household renamed')
    } catch (e) {
      showSnackbar(e instanceof Error ? e.message : 'Rename failed', 'error')
    } finally {
      setRenaming(false)
    }
  }

  const handleDelete = async () => {
    if (!householdId) {
      return
    }
    setDeleting(true)
    try {
      await householdsApi.delete(householdId)
      queryClient.invalidateQueries({ queryKey: HOUSEHOLDS_QUERY_KEY })
      setDeleteVisible(false)
      router.replace('/(app)/(tabs)')
    } catch (e) {
      showSnackbar(e instanceof Error ? e.message : 'Delete failed', 'error')
    } finally {
      setDeleting(false)
    }
  }

  if (!activeHousehold) {
    return null
  }

  return (
    <ScrollView style={styles.container} contentContainerStyle={styles.content}>
      <Card style={styles.card}>
        <Card.Title title={activeHousehold.name} subtitle="Household" />
        <Card.Content>
          <View style={styles.inviteRow}>
            <View style={styles.inviteInfo}>
              <Text variant="labelSmall" style={styles.inviteLabel}>
                Invite Code
              </Text>
              <Text variant="headlineSmall">{activeHousehold.invite_code ?? '—'}</Text>
            </View>
            <View style={styles.inviteActions}>
              <IconButton icon="content-copy" onPress={handleCopyCode} size={20} />
              {isOwner && (
                <IconButton
                  icon="refresh"
                  onPress={() => regenerateCode.mutate(householdId!)}
                  disabled={regenerateCode.isPending}
                  size={20}
                />
              )}
            </View>
          </View>
        </Card.Content>
      </Card>

      <Card style={styles.card}>
        <Card.Title title="Members" />
        <Card.Content>
          <QueryView query={membersQuery} emptyMessage="No members found.">
            {(members) => (
              <>
                {members.map((member) => {
                  const isCurrentUser = member.user_id === user?.id
                  return (
                    <List.Item
                      key={member.id}
                      title={
                        isCurrentUser
                          ? `${member.profile?.display_name ?? 'You'} (You)`
                          : (member.profile?.display_name ?? `${member.user_id.slice(0, 8)}...`)
                      }
                      right={() => (
                        <View style={styles.memberRight}>
                          <Chip compact>{member.role}</Chip>
                          {isOwner && !isCurrentUser && (
                            <IconButton
                              icon="close"
                              size={16}
                              onPress={() =>
                                removeMember.mutate({
                                  memberId: member.id,
                                  householdId: householdId!,
                                })
                              }
                            />
                          )}
                        </View>
                      )}
                    />
                  )
                })}
              </>
            )}
          </QueryView>
        </Card.Content>
      </Card>

      {isOwner && (
        <Card style={styles.card}>
          <Card.Title title="Settings" />
          <Card.Content>
            <Button
              mode="outlined"
              onPress={() => {
                setNewName(activeHousehold.name)
                setRenameVisible(true)
              }}
              style={styles.settingsButton}
            >
              Rename Household
            </Button>
            <Button
              mode="outlined"
              onPress={() => setDeleteVisible(true)}
              textColor="#D32F2F"
              style={styles.settingsButton}
            >
              Delete Household
            </Button>
          </Card.Content>
        </Card>
      )}

      <Portal>
        <Dialog visible={renameVisible} onDismiss={() => setRenameVisible(false)}>
          <Dialog.Title>Rename Household</Dialog.Title>
          <Dialog.Content>
            <TextInput mode="outlined" label="New Name" value={newName} onChangeText={setNewName} />
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setRenameVisible(false)}>Cancel</Button>
            <Button
              onPress={handleRename}
              loading={renaming}
              disabled={renaming || !newName.trim()}
            >
              Rename
            </Button>
          </Dialog.Actions>
        </Dialog>

        <Dialog visible={deleteVisible} onDismiss={() => setDeleteVisible(false)}>
          <Dialog.Title>Delete Household</Dialog.Title>
          <Dialog.Content>
            <Text variant="bodyMedium">
              Are you sure? This will permanently delete the household, all pantries, and all items.
              This action cannot be undone. You will need to create or join a new household to
              continue.
            </Text>
          </Dialog.Content>
          <Dialog.Actions>
            <Button onPress={() => setDeleteVisible(false)}>Cancel</Button>
            <Button
              onPress={handleDelete}
              loading={deleting}
              disabled={deleting}
              textColor="#D32F2F"
            >
              Delete
            </Button>
          </Dialog.Actions>
        </Dialog>
      </Portal>
    </ScrollView>
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
  inviteRow: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  inviteInfo: {
    flex: 1,
  },
  inviteLabel: {
    opacity: 0.6,
    marginBottom: 2,
  },
  inviteActions: {
    flexDirection: 'row',
  },
  memberRight: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  settingsButton: {
    marginBottom: 8,
  },
})

export default HouseholdScreen
