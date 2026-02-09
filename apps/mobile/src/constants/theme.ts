import { MD3LightTheme, MD3DarkTheme, type MD3Theme } from 'react-native-paper';
import { useColorScheme } from 'react-native';

const brandColors = {
  primary: '#2E7D32',
  primaryContainer: '#A5D6A7',
  secondary: '#FF8F00',
  secondaryContainer: '#FFE082',
  tertiary: '#1565C0',
  tertiaryContainer: '#BBDEFB',
  error: '#C62828',
  errorContainer: '#FFCDD2',
};

export const lightTheme: MD3Theme = {
  ...MD3LightTheme,
  colors: {
    ...MD3LightTheme.colors,
    ...brandColors,
    background: '#FAFAFA',
    surface: '#FFFFFF',
    surfaceVariant: '#F5F5F5',
    onPrimary: '#FFFFFF',
    onSecondary: '#FFFFFF',
    onBackground: '#212121',
    onSurface: '#212121',
    onSurfaceVariant: '#757575',
  },
};

export const darkTheme: MD3Theme = {
  ...MD3DarkTheme,
  colors: {
    ...MD3DarkTheme.colors,
    primary: '#81C784',
    primaryContainer: '#1B5E20',
    secondary: '#FFB74D',
    secondaryContainer: '#E65100',
    tertiary: '#64B5F6',
    tertiaryContainer: '#0D47A1',
    error: '#EF9A9A',
    errorContainer: '#B71C1C',
    background: '#121212',
    surface: '#1E1E1E',
    surfaceVariant: '#2C2C2C',
    onPrimary: '#000000',
    onSecondary: '#000000',
    onBackground: '#E0E0E0',
    onSurface: '#E0E0E0',
    onSurfaceVariant: '#BDBDBD',
  },
};

export const useAppTheme = (): MD3Theme => {
  const colorScheme = useColorScheme();
  return colorScheme === 'dark' ? darkTheme : lightTheme;
};

export const theme = lightTheme;
