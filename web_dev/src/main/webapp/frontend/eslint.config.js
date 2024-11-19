import js from '@eslint/js'
import globals from 'globals'
import reactHooks from 'eslint-plugin-react-hooks'
import reactRefresh from 'eslint-plugin-react-refresh'
import tsPlugin from '@typescript-eslint/eslint-plugin'
import tsParser from '@typescript-eslint/parser'

const baseConfig = {
  languageOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    parser: tsParser,
    globals: {
      ...globals.browser,
      ...globals.node,
    },
  },
  plugins: {
    'react-hooks': reactHooks,
    'react-refresh': reactRefresh,
    '@typescript-eslint': tsPlugin,
  },
  rules: {
    'no-var': 'error', // var 사용 금지
    'no-console': ['error', { allow: ['warn', 'error', 'info'] }], // console.log() 금지
    eqeqeq: 'error', // 일치 연산자 사용
    'no-unused-vars': ['error', { argsIgnorePattern: '^_' }], // 미사용 변수 금지
    '@typescript-eslint/no-unused-vars': ['error', { argsIgnorePattern: '^_' }], // TypeScript 미사용 변수 금지
    'react-hooks/rules-of-hooks': 'error', // React Hooks 규칙
    'react-hooks/exhaustive-deps': 'warn', // Hooks 종속성 규칙
    'react-refresh/only-export-components': [
      'warn',
      { allowConstantExport: true },
    ],
  },
}

// Exporting the configuration
export default [
  {
    // ESLint 기본 추천 규칙
    ...js.configs.recommended,
    ignores: ['dist', 'node_modules'], // 무시할 디렉토리
  },
  {
    // TypeScript 추천 규칙
    files: ['**/*.{ts,tsx}'],
    ignores: ['dist', 'node_modules'], // 무시할 디렉토리
    languageOptions: baseConfig.languageOptions,
    plugins: baseConfig.plugins,
    rules: {
      ...baseConfig.rules,
      // TypeScript에 필요한 추가 규칙
    },
  },
  {
    // React 관련 규칙
    files: ['**/*.{js,jsx,ts,tsx}'],
    ignores: ['dist', 'node_modules'],
    languageOptions: baseConfig.languageOptions,
    plugins: baseConfig.plugins,
    rules: {
      ...baseConfig.rules,
    },
  },
]
