#!/bin/bash
set -e

echo "Setting up PantryPal locally..."

if ! command -v supabase &> /dev/null; then
    echo "Supabase CLI not found. Installing..."
    if command -v brew &> /dev/null; then
        brew install supabase/tap/supabase
    else
        echo "Homebrew not found. Installing Supabase CLI via .deb package..."
        LATEST_VERSION=$(curl -s https://api.github.com/repos/supabase/cli/releases/latest | grep '"tag_name"' | sed -E 's/.*"v([^"]+)".*/\1/')
        curl -sSL -o /tmp/supabase.deb "https://github.com/supabase/cli/releases/download/v${LATEST_VERSION}/supabase_${LATEST_VERSION}_linux_amd64.deb"
        sudo dpkg -i /tmp/supabase.deb
        rm /tmp/supabase.deb
    fi
fi

if ! docker info > /dev/null 2>&1; then
    echo "Docker is not running. Please start Docker Desktop and try again."
    exit 1
fi

if [ ! -f "supabase/config.toml" ]; then
    echo "Initializing Supabase..."
    supabase init
fi

echo "Starting local Supabase..."
supabase start

SUPABASE_URL=$(supabase status | grep "Project URL" | grep -oE 'http://[0-9.:]+')
SUPABASE_ANON_KEY=$(supabase status | grep "Publishable" | grep -oE 'sb_[a-zA-Z0-9_-]+')

cat > apps/mobile/.env.local <<EOF
EXPO_PUBLIC_SUPABASE_URL=$SUPABASE_URL
EXPO_PUBLIC_SUPABASE_ANON_KEY=$SUPABASE_ANON_KEY
EOF

echo ""
echo "Setup complete!"
echo "  Supabase Studio: http://localhost:54323"
echo "  Next: cd apps/mobile && npm install && npx expo start"
