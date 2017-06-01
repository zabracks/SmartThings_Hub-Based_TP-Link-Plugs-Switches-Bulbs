# New-TP-Link UNDER TEST - DO NOT DOWNLOAD
Test version to verify total compatibility
# TP-Link Bulb, Plug, and Switch Integration with SmartThing

Version 3.0.  New version with modifications to user interface, error handling, support to the HS110 plug energy monitor functions.

# Features:

a. Single server for bulbs, plugs. and switches.

b. Controls following TP-Link bulbs/functions:

  1)  HS100 Plug and HS-200 switch, ON/OFF (tested)
  
  2)  HS105 and HS110 Plugs, ON/OFF (NOT tested - assume same as HS100)
  
  3)  NEW - HS110 Plug with Energy Monitor Functions (Tested by SmartThings user)
  
  3)  LB100 and LB110 bulbs, ON/OFF and Brightness (tested on LB-120)
  
  4)  LB120 bulb, ON/OFF, Brightness, Color Temperature, and Circadian mode (tested)
  
  5)  LB130 bulb, LB-120 functions plus Color using color wheel (tested)

c. Visual indication of a TP-Link device off-line.

d. Interfaces to an OPTIONAL TP-LinkBridge device handler. This DH allows checking the operational status of the TP-LinkServer.js applet and also allows a reboot of the PC from SmartThings. The TP-LinkBridge is NOT required.

# Compatibility with Version 2.3 Device Handlers and Node.js applet

a.  New (version 3) device handlers.  Excpet for the HS110, these device handlers are compatible with the prevision version (2.3) of the 'TP-LinkServer.js applet.  The console will log some errors on communications timeout; however, the error will not affect the device and does not forward to the SmartThings cloud.

b.  Previous (version 2.3) device handlers.  The previous set of version 2.3 device handlers are compatible with the version 3.0 'TP-LinkServer_v3.js' applet.  No errors were recorded in final testing.

c.  HS110 device handler ('TP-Link_HS110_v3.groovy').  The new HS110 is only compatible with the new (version 3) 'TP-LinkServer.js' applet.  Using the previous version will cause errors and miscalculations.

# Pre-requisites:

A bridge device running node.js and the TP-LinkServer applet.  This device must be on continuously.  Examples:

a.  Window PC, Laptop, Tablet, Stick Computer.  Must be set to auto start/restart and auto user log-in on start.

b.  Raspberry PI

Static IP address for the bridge and TP-Link devices.  (done through your WiFi router as DHCP address reservations.

# Installation:

Installation instructions can be found in the documentation folder.

New (initial):  'Instructions - TP-Link Server Install.txt' in folder 'Documentation'

Upgrade:  'Update from earlier versions.txt' in folder 'Documentation'.

# Files:

Top Level.  Contains the 'TP-LinkServer_v3.js' and windows 'TP-LinkServer_v3.bat' files for the bridge installation.

DeviceHandlers.  All SmartThings device handlers.  Names are clear as to device applicability.

Utilities.  

   a.  Windows batch file 'cmdPrompt.bat' is a windows command prompt window to the current directory.  

   b.  The tool file 'GetDeviceIPs.js' and windows companion 'GetDeviceIPs.bat' will pop a window on your device with the TP-Link devices, IP, MAC Address, and Alias.  Useful in installation.

Documentation.  Installation instructions, Design Notes, and Interface description.
