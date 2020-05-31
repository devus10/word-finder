#!/bin/bash

if [[ -v PROFILE ]]; then
    bash -c "java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILE} -jar app.jar"
else
  echo "ERROR: Spring profile not set"
  exit 1;
fi
