# Hub-Based TP-Link Bulb, Plug, and Switch SmartThing Integration

Two versions of TP-Link to Smart Things Integraion now exist:

a.  Hub-Based TP-Link to Smart Things Integraion:  The Hub-based version that requires an always on Hub device (bridge).   Attributes:

   1) Requires user-configured (PC, Android, Raspberry) Hub with node.js and server script.
   2) Does not require a token captured from the TP-Link cloud.
   3) Manual device installation and setting static IP addresses.

b.  Cloud-Based TP-Link to Smart Things Integraion:  The new version (currently in Beta) that relies on the TP-Link Kasa cloud.  Attributes:

   1) Reliant on TP-Link cloud (and the continued availabilty of same).
   2) Must have TP-Link account.
   3) Simpler setup.  Install Service Manager and applicable device handlers.  Runs service Manager.


# Hub-Based Pre-requisites:
-  bridge device running node.js and the TP-LinkServer applet.  This device must be on continuously with th program running.  Examples of devices:

   --  Window PC, Laptop, Tablet, Stick Computer
   
   -- Android devices with 'Server Ultimate'
   
   -- Amazon Kindle Fire (tablet) with 'Server Ultimate'
   
   --  Raspberry PI
-  Static IP address for the bridge and TP-Link devices.  (done through your WiFi router as DHCP address reservations.

<img src="https://github.com/DaveGut/TP-Link-to-SmartThings-Integration/blob/master/FamilyScreenshot.png" align="center"/>


# TP-Link Devices Supported:
-  HS100, Hs105, HS110, HS200 - TP-Link_HS_Series.groovy
-  HS110 with energy monitor functions - TP-Link_HS110_Emeter.groovy
-  LB100, LB110 - TP-Link_LB100_110.groovy
-  LB110 with energy monitor functions - TP-Link_LB110_Emeter.groovy
-  LB120 - TP-Link_LB120.groovy
-  LB120 with energy monitor functions - TP-Link_LB120_Emeter.groovy
-  LB130 - TP-Link_LB130.groovy
-  LB130 with energy monitor functions - TP-Link_LB130_Emeter.groovy

Installation instructions can be found in the documentation folder.
-  New (initial):  'Instructions - TP-Link Server Install.txt' in folder 'Documentation'
-  Upgrade:  'Update from earlier versions.txt' in folder 'Documentation'.

# Files:
Top Level.  Contains the 'TP-LinkServer_v3.js' and windows 'TP-LinkServer_v3.bat' files for the bridge installation.

DeviceHandlers.  All SmartThings device handlers.  Names are clear as to device applicability.

Utilities.  Windows batch file 'cmdPrompt.bat'.  he tool file 'GetDeviceIPs.js' and windows companion 'GetDeviceIPs.bat' will pop a window on your device with the TP-Link devices, IP, MAC Address, and Alias.  Useful in installation.

Documentation.  Installation instructions, Design Notes, and Interface description.

