# TP-Link Bulb, Plug, and Switch Integration with SmartThing Version 3
Version 3.0.  New version with modifications to user interface, error handling, support to the HS110 plug energy monitor functions.

07-04-2017.  Added new Device Handlers for TP-Link Bulbs with Energy Monitor Functions.  Edited other files w/o function change for common format for ease of maintenance.  ALL CHANGES ARE SUPERFICIAL AND DO NOT REQUIRE RELOAD.

https://github.com/DaveGut/TP-Link-to-SmartThings-Integration/blob/master/FamilyScreenshot.png

# TP-Link Devices Supported:
-  HS100, Hs105, HS110, HS200 - TP-Link_HS_Series.groovy
-  HS110 with energy monitor functions - TP-Link_HS110_Emeter.groovy
-  LB100, LB110 - TP-Link_LB100_110.groovy
-  LB110 with energy monitor functions - TP-Link_LB110_Emeter.groovy
-  LB120 - TP-Link_LB120.groovy
-  LB120 with energy monitor functions - TP-Link_LB120_Emeter.groovy
-  LB130 - TP-Link_LB130.groovy
-  LB120 with energy monitor functions - TP-Link_LB130_Emeter.groovy

Installation instructions can be found in the documentation folder.
-  New (initial):  'Instructions - TP-Link Server Install.txt' in folder 'Documentation'
-  Upgrade:  'Update from earlier versions.txt' in folder 'Documentation'.

# Pre-requisites:
-  bridge device running node.js and the TP-LinkServer applet.  This device must be on continuously.  Examples:

   --  Window PC, Laptop, Tablet, Stick Computer.  Must be set to auto start/restart and auto user log-in on start.
   
   --  Raspberry PI
-  Static IP address for the bridge and TP-Link devices.  (done through your WiFi router as DHCP address reservations.

# Compatibility with Version 2.3 Device Handlers and Node.js applet

Testing was completed on two MS Windows 10 PC, one for development and one for deployment.  The development PC has node.js version 6.10.3.  The deployment PC has a version in the 6.9 series.  Test results were identical on each PC.  The final version tests were error-free on the SmartThings log except as expected when a TCP time out was reported.  The node.js console also had no errors aside from programmed, except in HS110 testing against 'TP-LinkServer.js' version 2.3, as noted below.

a.  New (version 3) device handlers.  Excpet for the HS110, these device handlers are compatible with the prevision version (2.3) of the 'TP-LinkServer.js applet.  The console will log some errors on communications timeout; however, the error will not affect the device and does not forward to the SmartThings cloud.

b.  Previous (version 2.3) device handlers.  The previous set of version 2.3 device handlers are compatible with the version 3.0 'TP-LinkServer_v3.js' applet.  No errors were recorded in final testing.

c.  HS110 device handler ('TP-Link_HS110_v3.groovy').  The new HS110 is only compatible with the new (version 3) 'TP-LinkServer.js' applet.  Using the previous version will cause errors and miscalculations.

# Files:
Top Level.  Contains the 'TP-LinkServer_v3.js' and windows 'TP-LinkServer_v3.bat' files for the bridge installation.

DeviceHandlers.  All SmartThings device handlers.  Names are clear as to device applicability.

Utilities.  Windows batch file 'cmdPrompt.bat'.  he tool file 'GetDeviceIPs.js' and windows companion 'GetDeviceIPs.bat' will pop a window on your device with the TP-Link devices, IP, MAC Address, and Alias.  Useful in installation.

Documentation.  Installation instructions, Design Notes, and Interface description.

