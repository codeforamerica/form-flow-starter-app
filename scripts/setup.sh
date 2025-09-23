set -e

install_jenv() {
    if ! grep -q jenv "$1"; then
      echo "export PATH=""$HOME"/.jenv/bin:"$PATH""" >> "$1"
      echo "eval "$(jenv init -)"" >> "$1"
      $("$2 $1")
    fi
}

echo '--- StarterApp Setup Script ---'

if ! brew --version; then
  curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh
else
  brew update
fi

echo 'Installing brew packages'
brew tap homebrew/cask-versions
brew install --cask temurin@21
brew install jenv gradle postgresql@16 node

# Install jenv in either the .bashrc or zshrc, whichever is present
if [ -f ~/.bashrc ]; then
  install_jenv "$HOME/.bashrc" "sh"
elif [ -f ~/.zshrc ]; then
  install_jenv "$HOME/.zshrc" "zsh"
else
  echo 'No shell config file found, cant install jenv'
  exit 1
fi

# M1 Mac install stuff
if [[ $(uname -m) == 'arm64' ]]; then
  export PATH="$HOME/.jenv/bin:$PATH"
  export JENV_ROOT="/opt/homebrew/Cellar/jenv/"
  eval "$(/opt/homebrew/bin/brew shellenv)"
  eval "$(jenv init -)"
fi

# Check if jenv can find java 21
if ! jenv versions | grep -q 21; then
  jenv add /Library/Java/JavaVirtualMachines/temurin-21.jdk/Contents/Home
fi

# If the postgres service isn't running in brew, start it
if ! brew services list | grep postgresql@16 | grep started; then
  brew services restart postgresql@16
fi

# Create starter-app database and user in postgres, if they don't exist
if ! psql -lqt | cut -d \| -f 1 | tr -d ' ' | grep -qx starter-app; then
  printf "Creating database starter-app\n"
  createdb starter-app
  createuser -s starter-app
fi

# Create starter-app-test database and user in postgres, if they don't exist
if ! psql -lqt | cut -d \| -f 1 | tr -d ' ' | grep -qx starter-app-test; then
  printf "Creating database starter-app-test\n"
  createdb starter-app-test
  createuser -s starter-app-test
fi

# Run tests
./gradlew clean test

echo '--- StarterApp Setup Script Complete ---'
