import { useEffect } from 'react';
import { AppState, type AppStateStatus } from 'react-native';
import { focusManager, onlineManager } from '@tanstack/react-query';
import NetInfo from '@react-native-community/netinfo';

/**
 * Integrates TanStack Query with React Native app lifecycle.
 *
 * - Refetches stale queries when the app returns to the foreground
 * - Updates online status when network connectivity changes
 *
 * Call this hook once in the root layout.
 */
export const useAppStateRefetch = () => {
  useEffect(() => {
    const subscription = AppState.addEventListener(
      'change',
      (status: AppStateStatus) => {
        focusManager.setFocused(status === 'active');
      },
    );

    return () => subscription.remove();
  }, []);

  useEffect(() => {
    const unsubscribe = NetInfo.addEventListener((state) => {
      onlineManager.setOnline(
        state.isConnected != null &&
        state.isConnected &&
        Boolean(state.isInternetReachable),
      );
    });

    return () => unsubscribe();
  }, []);
};
