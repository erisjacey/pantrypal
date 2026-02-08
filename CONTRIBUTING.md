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

```bash
deno test supabase/functions/
```

## Database Changes

```bash
supabase migration new your_migration_name
supabase db reset
```

## Questions?

Open an issue or discussion!
