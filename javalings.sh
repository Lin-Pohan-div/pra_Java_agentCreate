#!/usr/bin/env bash
# Javalings launcher for Linux / macOS. Builds on first run, then starts the CLI.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CLASSES="$ROOT/build/classes"
MARKER="$ROOT/build/.last-build"

needs_build=1
if [ -f "$MARKER" ]; then
    if [ -z "$(find "$ROOT/src/main/java" -name '*.java' -newer "$MARKER" -print -quit)" ]; then
        needs_build=0
    fi
fi

if [ "$needs_build" -eq 1 ]; then
    echo "[javalings] Building..."
    mkdir -p "$CLASSES"
    find "$ROOT/src/main/java" -name '*.java' -print0 \
        | xargs -0 javac -encoding UTF-8 -d "$CLASSES"
    echo ok > "$MARKER"
fi

exec java -cp "$CLASSES" dev.javaling.Javalings "$@"
