/*
TP-Link Plugs and Switches Device Handler
FOR USE ONLY WITH 'TP-LinkServer_v3js'

Copyright 2017 Dave Gutheinz

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this 
file except in compliance with the License. You may obtain a copy of the License at:

		http://www.apache.org/licenses/LICENSE-2.0
        
Unless required by applicable law or agreed to in writing, software distributed under 
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF 
ANY KIND, either express or implied. See the License for the specific language governing 
permissions and limitations under the License.

Supported models and functions:  This device supports the TP-Link HS100, HS105, HS110, and
HS200 devices.  It supports the on/off function only.

Notes: 
1.	This Device Handler requires an operating Windows 10 PC Bridge running version 3.0 of
	'TP-LinkServer.js'.  It is NOT fully compatible with earlier versions.
2.	This device supports the TP-Link HS100, HS105, HS110, and HS200 devices.  It supports
	the on/off function only.

Update History
	06/01/2017	- Initial release of Version 3.0.  Following add-ons from version 2.2
				  1.  Compatible with SmartTiles / ActionTiles
                  2.  Added error messaging to final version.
                  3.  Added error messages to appear in ST phone app, Recently tab.
                  4.  Modified coloring and added a Waiting state that displays whenever
                  	  a function is actuated to indicate waiting for response from the
                      Bridge.
*/

metadata {
	definition (name: "TP-Link HS Series", namespace: "djg", author: "Dave Gutheinz") {
		capability "Switch"
		capability "refresh"
		capability "Sensor"
		capability "Actuator"
	}
tiles (scale : 2) {
		standardTile("switch", "device.switch", width: 6, height: 4, canChangeIcon: true) {
			state "on", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00a0dc",nextState:"turningOff"
			state "off", label:'${name}', action:"switch.on", icon:"st.switch.off", backgroundColor:"#ffffff",nextState:"waiting"
			state "turningOff", label:'waiting', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#15EE10",nextState:"waiting"
			state "waiting", label:'${name}', action:"switch.on", icon:"st.switches.switch.on", backgroundColor:"#15EE10",nextState:"on"
            state "offline", label:'Comms Error', action:"switch.on", icon:"st.switch.off", backgroundColor:"#e86d13",nextState:"waiting"
}
		standardTile("refresh", "capability.refresh", width: 2, height: 2,  decoration: "flat") {
			state ("default", label:"Refresh", action:"refresh.refresh", icon:"st.secondary.refresh")
		}         
		main("switch")
		details(["switch", "refresh"])
    }
}
preferences {
	input("deviceIP", "text", title: "Device IP", required: true, displayDuringSetup: true)
	input("gatewayIP", "text", title: "Gateway IP", required: true, displayDuringSetup: true)
}
def on() {
	log.info "${device.name} ${device.label}: Turning ON"
	sendCmdtoServer('{"system":{"set_relay_state":{"state": 1}}}', "onOffResponse")
}
def off() {
	log.info "${device.name} ${device.label}: Turning OFF"
	sendCmdtoServer('{"system":{"set_relay_state":{"state": 0}}}', "onOffResponse")
}
def refresh(){
	log.info "Polling ${device.name} ${device.label}"
	sendEvent(name: "switch", value: "waiting", isStateChange: true)
	sendCmdtoServer('{"system":{"get_sysinfo":{}}}', "refreshResponse")
}
private sendCmdtoServer(command, action){
	def headers = [:] 
	headers.put("HOST", "$gatewayIP:8082")   // port 8082 must be same as value in TP-LInkServerLite.js
	headers.put("tplink-iot-ip", deviceIP)
    headers.put("tplink-command", command)
	headers.put("command", "deviceCommand")
	sendHubCommand(new physicalgraph.device.HubAction([
		headers: headers],
		device.deviceNetworkId,
		[callback: action]
	))
}
def onOffResponse(response){
	if (response.headers["cmd-response"] == "commError") {
		log.error "$device.name $device.label: Communications Error"
		sendEvent(name: "switch", value: "offline", descriptionText: "ERROR - OffLine - mod onOffResponse", isStateChange: true)
     } else {
		log.info "On/Off command response received from server!"
		refresh()
	}
}
def refreshResponse(response){
	if (response.headers["cmd-response"] == "commError") {
		log.error "$device.name $device.label: Communications Error"
		sendEvent(name: "switch", value: "offline", descriptionText: "ERROR - OffLine - mod refreshResponse", isStateChange: true)
     } else {
		def cmdResponse = parseJson(response.headers["cmd-response"])
		def status = cmdResponse.system.get_sysinfo.relay_state
		if (status == 1) {
			status = "on"
		} else {
   	     status = "off"
		}
		log.info "${device.name} ${device.label}: Power: ${status}"
		sendEvent(name: "switch", value: status, isStateChange: true)
	}
}