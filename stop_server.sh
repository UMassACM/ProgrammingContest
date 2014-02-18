#!/bin/sh
kill `ps aux | grep RunServer | grep -v grep | awk '{print $2}'`
