#!/bin/bash
set -euo pipefail

migrations_path="$(dirname $0)/../src/main/resources/db/migration"

if [ ! -d "$migrations_path" ]; then
  echo "Migration directory '$migrations_path' does not exist. Creating..."
  mkdir -p "$migrations_path"
fi

migrations_path=$(realpath "$migrations_path")

printf "Generating migration file. \nEnter Description (e.g. 'Create admin users'): "
read description

filename="V$(date +%Y.%m.%d.%H.%M.%S)__$(echo "$description" | sed -E 's/[^A-Z]+/_/ig').sql"
echo "Creating ${filename}."
touch "$migrations_path/$filename"

echo "Hit enter to open."
read
open -a "IntelliJ IDEA.app" "$migrations_path/$filename"