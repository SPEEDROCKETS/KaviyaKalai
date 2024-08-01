#!/usr/bin/env sh
BRANCH_REF_NAME="$(printf "%s" "$1" | sed -e "s/^origin\\///")"
PR_NUMBER="$(curl -v -H "Authorization: token $2" https://github.ford.com/api/v3/repos/FBS-FC-API/CES/pulls | jq ".[] | select (.head.ref | contains(\"$BRANCH_REF_NAME\")) | .number")"

if [ "$PR_NUMBER" != "" ]; then
  ./gradlew sonarqube -Dsonar.pullrequest.key="$PR_NUMBER" -Dsonar.pullrequest.branch="$1" -Dsonar.pullrequest.github.repository=FBS-FC-API/CES
else
  printf "No Pull Request found for %s" "$BRANCH_REF_NAME"
fi