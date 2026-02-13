// https://docs.expo.dev/guides/using-eslint/
const { defineConfig } = require('eslint/config')
const expoConfig = require('eslint-config-expo/flat')
const prettierConfig = require('eslint-config-prettier')

module.exports = defineConfig([
  expoConfig,
  prettierConfig,
  {
    ignores: ['dist/*'],
  },
  {
    files: ['**/*.ts', '**/*.tsx'],
    settings: {
      'import/resolver': {
        typescript: {
          project: './tsconfig.json',
        },
      },
    },
    rules: {
      // Enforce arrow function expressions (no function declarations)
      'func-style': ['error', 'expression'],

      // Always require curly braces for blocks
      curly: ['error', 'all'],

      // No unused vars (allow underscore prefix for intentionally unused)
      '@typescript-eslint/no-unused-vars': [
        'error',
        {
          argsIgnorePattern: '^_',
          varsIgnorePattern: '^_',
        },
      ],

      // Prefer const over let when variable is never reassigned
      'prefer-const': 'error',

      // No var declarations
      'no-var': 'error',

      // Prefer template literals over string concatenation
      'prefer-template': 'error',

      // No console.log in production (warn to allow during development)
      'no-console': ['warn', { allow: ['warn', 'error'] }],

      // Enforce import order
      'import/order': [
        'error',
        {
          groups: [
            'builtin',
            'external',
            'internal',
            ['parent', 'sibling', 'index'],
          ],
          'newlines-between': 'never',
        },
      ],

      // Disable rules that conflict with our style
      'react/react-in-jsx-scope': 'off',
    },
  },
])
