#!/bin/bash
#
# macOS testsuite prerequisite: bring up loopback aliases for every
# 127.0.0.x address referenced in the testsuite configs.
#
# macOS does NOT route all of 127.0.0.0/8 to loopback by default (unlike
# Linux), and these aliases are NON-PERSISTENT (gone after reboot). The
# jdiameter testsuite binds multi-homing / multi-peer tests to several
# 127.0.0.x addresses; without these aliases they fail with
# "Can't assign requested address".
#
# Linux/CI does not need this (127.0.0.0/8 is fully loopback-routed),
# which is why the suite is green on Jenkins but fragile on local macOS.
#
# Usage:  ./scripts/macos-testsuite-loopback.sh          (add aliases)
#         ./scripts/macos-testsuite-loopback.sh --down   (remove them)
#
set -euo pipefail

# Resolve repo root (this script lives in <root>/scripts/)
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Discover every 127.0.0.x address used in testsuite XML configs.
# Sorted numerically by last octet; excludes target/ and 127.0.0.1.
ADDRS=$(
  grep -rhoE '127\.0\.0\.[0-9]+' "$ROOT/testsuite" --include='*.xml' \
    | grep -v 'target/' \
    | sort -u -t. -k4 -n \
    | grep -vx '127.0.0.1' || true
)

if [ -z "$ADDRS" ]; then
  echo "No extra 127.0.0.x addresses found in testsuite configs."
  exit 0
fi

ACTION="${1:-up}"

if [ "$ACTION" = "--down" ]; then
  echo "Removing loopback aliases..."
  for ip in $ADDRS; do
    if sudo ifconfig lo0 -alias "$ip" 2>/dev/null; then
      echo "  removed $ip"
    else
      echo "  $ip not present"
    fi
  done
else
  echo "Adding loopback aliases for testsuite..."
  for ip in $ADDRS; do
    if ifconfig lo0 | grep -q "inet $ip "; then
      echo "  $ip already up"
    else
      sudo ifconfig lo0 alias "$ip" up && echo "  added $ip"
    fi
  done
fi

echo
echo "Current loopback (lo0) 127.x addresses:"
ifconfig lo0 | grep 'inet 127' | awk '{print "  " $2}'
