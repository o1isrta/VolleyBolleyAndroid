#!/bin/bash

properties="
SERVER_URL=${VOLLEY_SERVER_URL}
"

touch local.properties | (echo "$properties" | grep -E '.+=.+') >> local.properties
