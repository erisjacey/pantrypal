import { createContext, useCallback, useContext, useState, type ReactNode } from 'react'
import { Snackbar } from 'react-native-paper'
import { StyleSheet } from 'react-native'

type SnackbarType = 'success' | 'error'

interface SnackbarContextValue {
  showSnackbar: (message: string, type?: SnackbarType) => void
}

const SnackbarContext = createContext<SnackbarContextValue | null>(null)

export const useSnackbar = () => {
  const context = useContext(SnackbarContext)
  if (!context) {
    throw new Error('useSnackbar must be used within a SnackbarProvider')
  }
  return context
}

export const SnackbarProvider = ({ children }: { children: ReactNode }) => {
  const [visible, setVisible] = useState(false)
  const [message, setMessage] = useState('')
  const [type, setType] = useState<SnackbarType>('success')

  const showSnackbar = useCallback((msg: string, snackbarType: SnackbarType = 'success') => {
    setMessage(msg)
    setType(snackbarType)
    setVisible(true)
  }, [])

  const onDismiss = useCallback(() => {
    setVisible(false)
  }, [])

  return (
    <SnackbarContext.Provider value={{ showSnackbar }}>
      {children}
      <Snackbar
        visible={visible}
        onDismiss={onDismiss}
        duration={3000}
        action={{ label: 'Dismiss', onPress: onDismiss }}
        style={type === 'error' ? styles.error : undefined}
      >
        {message}
      </Snackbar>
    </SnackbarContext.Provider>
  )
}

const styles = StyleSheet.create({
  error: {
    backgroundColor: '#D32F2F',
  },
})
