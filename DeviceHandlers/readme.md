# TP-Link Bulb, Plug, and Switch Integration Device Handlers

07-13-2017 - Updated to common format for supportability
07-13-2017 - Added communications path for 'Emeter' version device handlers only.

# Device Handler Installation Instructions

CPMPATABILITY:  At any time, all files within this repository are compatible; however, if you are installing a new device handler, it is suggested that you also update your other device handlers at the same time.  Currently, all device handlers have on line 7 a list of "COMPATABILITY KEYs" that link to line 4 of the Java Script.

A  INSTALL THE DEVICE HANDLERS ON SMARTTHINGS
1.	Log in to SmartThings IDE.  (You may have to create an acccount.)
2.	After log in, go to "My Locations" and select your current location.
3.	Go to "My Device Handler" and select "+ Create New Device Handler".  You will need to do this for each device type you install.
3.	Select the tab "From Code".
4.	Open the GROOVY file associated with your device and copy the contents.
5.	Past the contents into the the IDE window.  Select "Create" at the bottom.
6.	On the next page that opens, click “Publish”, then “For Me” near the top-right of the page. 

B.	INSTALL THE ACTUAL DEVICES ON SMARTTHINGS
Go to "My Devices" in IDE, click on New Device in the top right corner (you will repeat this step for each of the outlets you have).
1.	Name - enter a name for the product *i.e., “TP-Link HS-100", "TP-Link HS-200", "TP-Link LB-120", TP-Link LB-130").
2.	Label - enter a label, this is what will show in the SmartThings app, (i.e., “Den Lamp”, "Bedroom Fan").
3.	Device Network Id - enter a unique ID (i.e., “LB100-1”, “LB120-1”, and “LB120-2”).
4.	Type - select the appropriate groovy file name from the drop down list (should be near the bottom of the list).
5.	Version - Published
6.	Location and Hub - select for your setup
7.	Group - leave blank for now, you can assign to a room later through the app
8.	 Click Create

C.  SET UP DEVICE AND HUB IP FOR EACH DEVICE
For each device, 0pen SmartThings on your smart phone and select your device.  Go to the Settings page.
1.	Device IP.  Enter the IP Address for your TP Link device.
2.	Gateway IP.  Enter the IP Address of the server PC.

Note:  There is a tool in the Utilities folder that (after node.js is installed) allow you to get a list of the found TP-Link devices.  From Windows, run "GetDeviceIPs.bat".

Installation instructions can also be found in the documentation folder.
