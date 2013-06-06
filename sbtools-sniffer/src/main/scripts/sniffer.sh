#!/bin/sh
#
# LPC-debugger start script for Unix like operating systems
#

topDir=`dirname $0`
if [ -n "$topDir" ]; then
   cd "$topDir" || (echo "Cannot chdir into $topDir"; exit 1)
fi

set -x
exec java -cp libs -jar libs/MAIN_JAR $*
