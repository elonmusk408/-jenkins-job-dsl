#!/usr/bin/env bash

env

# Interpolate the RUN_DATE now so that the downstream job is guaranteed to use
# the same exact date as this job. Otherwise, if this job runs over a date
# boundary, the downstream job would re-interpolate the value of 'yesterday' on
# a different date.
INTERPOLATED_RUN_DATE="$(date +%Y-%m-%d -d "$RUN_DATE")"
echo "RUN_DATE=${INTERPOLATED_RUN_DATE}" > "${WORKSPACE}/downstream.properties"

${WORKSPACE}/analytics-configuration/automation/run-automated-task.sh \
 VerticaSchemaToBigQueryTask --local-scheduler \
 --vertica-schema-name $SCHEMA \
 --vertica-credentials $VERTICA_CREDENTIALS \
 --gcp-credentials $GCP_CREDENTIALS \
 --date "$INTERPOLATED_RUN_DATE" \
 ${OVERWRITE} \
 ${EXCLUDE} \
 ${EXTRA_ARGS}

