set -e

if ! npm --version | grep -q "^[0-9]"; then
  echo $(uname -a)
  echo $(uname -m)
  echo '🔴 npm is not installed, installing node now.'
  if [ "$(uname -m)" == "x86_64" ];then
    echo 'ARM64 system'
    arch -arm64 brew install node
  else
    echo 'NonARM64 system'
    brew install node
  fi
else
  echo '🟢 npm is installed.'
fi
