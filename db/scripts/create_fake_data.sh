#!/bin/bash

psql -U myuser -d mydb -f ./sql/gen_tables.sql

psql -U myuser -d mydb -f ./sql/gen_users.sql

psql -U myuser -d mydb -f ./sql/gen_recipes.sql

psql -U myuser -d mydb -f ./sql/gen_msgs.sql
