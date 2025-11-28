#!/bin/bash

keystore_base64="${TEAM_DEBUG_KEYSTORE}"

mkdir -p keystore
echo "$keystore_base64" | base64 -d > keystore/team-debug.keystore
