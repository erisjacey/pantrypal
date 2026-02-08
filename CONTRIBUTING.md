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
