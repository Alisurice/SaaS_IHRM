#!/bin/bash
mysql -uroot -proot <<EOF
source /usr/local/ihrm.sql;
source /usr/local/act.sql;