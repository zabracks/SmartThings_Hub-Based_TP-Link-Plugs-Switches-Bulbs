# TP-Link Bulb, Plug, and Switch Integration with SmartThing Version 3
Version 3.0.  New version with modifications to user interface, error handling, support to the HS110 plug energy monitor functions.

CURRENT EFFORT.  Currently, I am developing a Service Manager and Device Handler set for all devices that use the TP-Link Cloud vice the local hub.  Expected BETA version is Aug 1. Even after that, this version will be maintained since I see a great value in not relying on yet another cloud service.

# Pre-requisites:
-  bridge device running node.js and the TP-LinkServer applet.  This device must be on continuously with th program running.  Examples of devices:

   --  Window PC, Laptop, Tablet, Stick Computer
   
   -- Android devices with 'Server Ultimate'
   
   -- Amazon Kindle Fire (tablet) with 'Server Ultimate'
   
   --  Raspberry PI
-  Static IP address for the bridge and TP-Link devices.  (done through your WiFi router as DHCP address reservations.

<img src="https://github.com/DaveGut/TP-Link-to-SmartThings-Integration/Hub-Based_Integration/blob/master/FamilyScreenshot.png" align="center"/>


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

# Files:
Top Level.  Contains the 'TP-LinkServer_v3.js' and windows 'TP-LinkServer_v3.bat' files for the bridge installation.

DeviceHandlers.  All SmartThings device handlers.  Names are clear as to device applicability.

Utilities.  Windows batch file 'cmdPrompt.bat'.  he tool file 'GetDeviceIPs.js' and windows companion 'GetDeviceIPs.bat' will pop a window on your device with the TP-Link devices, IP, MAC Address, and Alias.  Useful in installation.

Documentation.  Installation instructions, Design Notes, and Interface description.

