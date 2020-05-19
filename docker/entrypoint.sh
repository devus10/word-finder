#! /bin/bash

if [[ -v PROFILE ]]; then
    bash -c "java -Dspring.profiles.active=${PROFILE} -jar app.jar"
else
  echo "ERROR: Spring profile not set"
  exit 1;
fi
