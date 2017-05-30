REM  Place this file in the TP-Link top level directory.
REM  UnREM the below an change the direcory to the TP-Link directory if auto-starting.
REM  cd c:\1-TP Link\
color 3f
title TP-Link Device SmartThings Bridge Applet
prompt $_
Echo off
CLS
:startNode
date /t
time /t
node --version
node TP-LinkServer_v3.js
goto startNode