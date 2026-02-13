# Contributing to PantryPal

## Getting Started

1. Fork the repository
2. Clone: `git clone https://github.com/YOUR-USERNAME/pantrypal.git`
3. Setup: `./scripts/setup.sh`
4. Branch: `git checkout -b feat/your-feature`

## Commit Convention

Conventional Commits: `<type>(<scope>): <summary>`

Types: feat, fix, docs, style, refactor, test, chore, ci, perf
Scopes: db, edge, mobile, docs, scripts, config

## Branch Naming

`<type>/<short-description>` (e.g., `feat/auth-screens`, `fix/rls-pantries`)

## PR Process

1. Branch from `main`, make changes, push
2. Open PR with: What, Why, How to test, Screenshots (UI)
3. Reference the issue in your PR description: `Closes #123`
4. Squash merge when approved

## Code Quality & Tooling

PantryPal uses **two toolchains** (one for mobile, one for edge functions) but enforces the **same visual style** across the entire codebase.

### Shared Style (Enforced Everywhere)

- No semicolons
- Single quotes
- 2-space indentation
- 100 character line width

### Mobile App (`apps/mobile/`)

**Formatter:** Prettier (configured via `.prettierrc` at root)
**Linter:** ESLint (configured via `apps/mobile/eslint.config.js`)
**Type Checker:** TypeScript (`tsc`)

```bash
cd apps/mobile
npm run format      # Format with Prettier
npm run lint        # Lint with ESLint
npm run typecheck   # Type check with tsc
```

### Edge Functions (`supabase/functions/`)

**Formatter:** Deno fmt (configured via `supabase/functions/deno.json`)
**Linter:** Deno lint (configured via `supabase/functions/deno.json`)
**Type Checker:** Deno check

```bash
deno fmt supabase/functions/           # Format
deno lint supabase/functions/          # Lint
deno check supabase/functions/**/*.ts  # Type check
```

### Why Two Toolchains?

- **Mobile app** uses React Native → Node.js ecosystem → ESLint + Prettier
- **Edge Functions** use Deno runtime → Deno's built-in tooling → `deno fmt` + `deno lint`
- Deno doesn't support Prettier/ESLint (different module system: `npm:`, `jsr:`)
- ESLint can't parse Deno-specific imports

### VSCode Setup

The `.vscode/settings.json` configures both toolchains:

- Prettier formats `.ts`/`.tsx` files in `apps/mobile/`
- Deno extension handles `.ts` files in `supabase/functions/`
- Make sure you have both extensions installed:
  - **Prettier** (`esbenp.prettier-vscode`)
  - **Deno** (`denoland.vscode-deno`)

### Before Committing

Run all checks for the area you modified:

**Mobile changes:**
```bash
cd apps/mobile
npm run format && npm run lint && npm run typecheck
```

**Edge Function changes:**
```bash
deno fmt supabase/functions/
deno lint supabase/functions/
deno check supabase/functions/**/*.ts
deno test supabase/functions/
```

## Architecture

```
Mobile App (UI) -> Edge Functions (Logic) -> Database (Storage)
```

## Running Tests

### Unit Tests

```bash
deno test supabase/functions/
```

### Local API Testing

1. Start Supabase: `supabase start`
2. Serve functions: `supabase functions serve --no-verify-jwt`
3. Sign in to get a bearer token:
   ```bash
   curl -X POST 'http://127.0.0.1:54321/auth/v1/token?grant_type=password' \
     -H 'apikey: <anon-key>' \
     -H 'Content-Type: application/json' \
     -d '{"email": "test@example.com", "password": "password123"}'
   ```
4. Call functions with the `access_token`:
   ```bash
   curl -X POST 'http://127.0.0.1:54321/functions/v1/<function-name>' \
     -H 'Authorization: Bearer <token>' \
     -H 'apikey: <anon-key>' \
     -H 'Content-Type: application/json' \
     -d '{ ... }'
   ```

Test users are seeded by `supabase db reset` (see `supabase/seed.sql`).

> **Note:** `--no-verify-jwt` works around a local ES256 key handling issue in the edge runtime.
> Auth is still enforced via RLS and `supabase.auth.getUser()` in function code.

## Database Changes

```bash
supabase migration new your_migration_name
supabase db reset
```

## Questions?

Open an issue or discussion!
