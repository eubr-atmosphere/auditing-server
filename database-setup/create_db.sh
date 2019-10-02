#!/bin/bash
set -e

POSTGRES="psql --username postgres"

echo "Creating database: ${DB_NAME}"

$POSTGRES <<EOSQL
CREATE DATABASE ${DB_NAME} OWNER ${DB_USER};
GRANT ALL PRIVILEGES ON DATABASE ${DB_NAME} to ${DB_USER};
EOSQL