set -e

echo '--- checkForNPM ---'

if ! npm --version | grep -q "^[0-9]"; then
  echo '🔴 npm is not installed, installing node now.'
  brew install node
else
  echo '🟢 npm is installed.'
fi
