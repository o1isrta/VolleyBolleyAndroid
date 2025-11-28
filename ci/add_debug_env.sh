#!/bin/bash

properties="
SERVER_URL=${VOLLEY_SERVER_URL}
MAPS_API_KEY=${VOLLEY_MAPS_API_KEY}
"

touch local.properties | (echo "$properties" | grep -E '.+=.+') >> local.properties
