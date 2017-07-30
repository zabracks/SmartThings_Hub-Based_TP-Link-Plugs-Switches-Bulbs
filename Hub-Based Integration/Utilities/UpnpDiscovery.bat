REM  Place this file in the TP-Link Bulb top level directory.
REM  Add path to the TP-Link Bulb directory if auto-starting.
color 3f
title UDP Node Testing
prompt $_
Echo off
CLS
date /t
time /t
node UpnpDiscovery
pause