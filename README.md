# PantryPal

Smart pantry inventory management for households.

## Features

- **Batch-Based Inventory** - Track each purchase separately with FIFO consumption
- **Expiry Tracking** - Never waste food again with expiry notifications
- **Household Sharing** - Share pantries with family members in real-time
- **Budget Tracking** - Track spending per item and purchase

## Tech Stack

- **Mobile:** React Native + Expo (TypeScript)
- **Backend:** Supabase (PostgreSQL + Auth + Edge Functions)
- **Open Source:** MIT License

## Local Development

### Prerequisites

- Node.js 18+
- Docker Desktop
- [Deno](https://deno.land/) (for Edge Functions development and testing, installed automatically by `setup.sh)
- [Supabase CLI](https://supabase.com/docs/guides/local-development/cli/getting-started) (installed automatically by `setup.sh`)

### Quick Start

```bash
git clone https://github.com/erisjacey/pantrypal.git
cd pantrypal
./scripts/setup.sh
cd apps/mobile
npm install
npx expo start
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md).

## License

MIT - see [LICENSE](LICENSE).
