/*
TP-Link LB120 Device Handler
FOR USE ONLY WITH 'TP-LinkServer_v3.js'

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
2.	This device supports the TP-Link LB100 and LB110 devices.  It supports the on/off,
	bightness color temperature, and Circadian (mode) functions.

Update History
	06/01/2017	- Initial release of Version 3.0.  Following add-ons from version 2.3
				  1.  Compatible with SmartTiles / ActionTiles
                  2.  Added error messaging to final version.
                  3.  Added error messages to appear in ST phone app, Recently tab.
                  4.  Modified coloring and added a Waiting state that displays whenever
                  	  a function is actuated to indicate waiting for response from the
                      Bridge.
*/
metadata {
	definition (name: "TP-Link LB120", namespace: "djg", author: "Dave Gutheinz") {
		capability "Switch"
		capability "Switch Level"
		capability "Color Temperature"
		capability "refresh"
        capability "Sensor"
		capability "Actuator"
		attribute "bulbMode", "string"
		command "setModeNormal"
		command "setModeCircadian"
	}
	tiles(scale:2) {
		multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
			tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#00a0dc",
				nextState:"turningOff"
				attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#ffffff",
				nextState:"waiting"
				attributeState "turningOff", label:'waiting', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#15EE10",
				nextState:"waiting"
				attributeState "waiting", label:'${name}', action:"switch.on", icon:"st.switches.light.on", backgroundColor:"#15EE10",
				nextState:"on"
                attributeState "offline", label:'Comms Error', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#e86d13",
                nextState:"waiting"
			}
			tileAttribute ("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", label: "Brightness: ${currentValue}", action:"switch level.setLevel"
			}
		}
		controlTile("colorTempSliderControl", "device.colorTemperature", "slider", width: 4, height: 1, inactiveLabel: false,
		range:"(2700..6500)") {
        	state "colorTemperature", action:"color temperature.setColorTemperature"
		}
		valueTile("colorTemp", "device.colorTemperature", inactiveLabel: false, decoration: "flat", height: 1, width: 2) {
			state "colorTemp", label: '${currentValue}K'
		}
		standardTile("bulbMode", "bulbMode", width: 3, height: 2, decoration: "flat") {
			state "normal", label:'Normal\n\rMode', action:"setModeCircadian", backgroundColor:"#ffffff", nextState: "circadian"
			state "circadian", label:'Circadian\n\rMode', action:"setModeNormal", backgroundColor:"#00a0dc", nextState: "normal"
		}
		standardTile("refresh", "capability.refresh", width: 3, height: 2,  decoration: "flat") {
			state ("default", label:"Refresh", action:"refresh.refresh", icon:"st.secondary.refresh", backgroundColor:"#ffffff")
		}
		main("switch")
		details(["switch", "colorTempSliderControl", "colorTemp", "bulbMode", "refresh"])
	}
}
preferences {
	input("deviceIP", "text", title: "Device IP", required: true, displayDuringSetup: true)
	input("gatewayIP", "text", title: "Gateway IP", required: true, displayDuringSetup: true)
}
def on() {
	log.info "${device.name} ${device.label}: Turning ON"
	sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":1}}}', "commandResponse")
}
def off() {
	log.info "${device.name} ${device.label}: Turning OFF"
	sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":0}}}', "commandResponse")
}
def setLevel(percentage) {
	percentage = percentage as int
	log.info "${device.name} ${device.label}: Setting Brightness to ${percentage}%"
	sendEvent(name: "switch", value: "waiting", isStateChange: true)
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"brightness":${percentage}}}}""", "commandResponse")
}
def setColorTemperature(kelvin) {
	kelvin = kelvin as int
	log.info "${device.name} ${device.label}: Setting Color Temperature to ${kelvin}K"
	sendEvent(name: "switch", value: "waiting", isStateChange: true)
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"color_temp": ${kelvin},"hue":0,"saturation":0}}}""", "commandResponse")
}
def setModeNormal() {
	log.info "${device.name} ${device.label}: Changing Mode to NORMAL"
	sendEvent(name: "switch", value: "waiting", isStateChange: true)
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"mode":"normal"}}}""", "commandResponse")
}
def setModeCircadian() {
	log.info "${device.name} ${device.label}: Changing Mode to CIRCADIAN"
	sendEvent(name: "switch", value: "waiting", isStateChange: true)
	sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"mode":"circadian"}}}""", "commandResponse")
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
def commandResponse(response){
	if (response.headers["cmd-response"] == "TcpTimeout") {
		log.error "$device.name $device.label: Communications Error"
		sendEvent(name: "switch", value: "offline", descriptionText: "ERROR - OffLine - mod commandResponse", isStateChange: true)
     } else {
		def cmdResponse = parseJson(response.headers["cmd-response"])
		state =  cmdResponse["smartlife.iot.smartbulb.lightingservice"]["transition_light_state"]
		parseStatus(state)
	}
}
def refreshResponse(response){
	if (response.headers["cmd-response"] == "TcpTimeout") {
		log.error "$device.name $device.label: Communications Error"
		sendEvent(name: "switch", value: "offline", descriptionText: "ERROR - OffLine - mod refreshResponse", isStateChange: true)
     } else {
		def cmdResponse = parseJson(response.headers["cmd-response"])
		state = cmdResponse.system.get_sysinfo.light_state
		parseStatus(state)
	}
}
def parseStatus(state){
	def status = state.on_off
	if (status == 1) {
		status = "on"
	} else {
		status = "off"
		state = state.dft_on_state
	}
	def mode = state.mode
	def level = state.brightness
	def color_temp = state.color_temp
	log.info "$device.name $device.label: Power: ${status} / Mode: ${mode} / Brightness: ${level}% / Color Temp: ${color_temp}K"
	sendEvent(name: "switch", value: status, isStateChange: true)
	sendEvent(name: "bulbMode", value: mode, isStateChange: true)
	sendEvent(name: "level", value: level, isStateChange: true)
	sendEvent(name: "colorTemperature", value: color_temp, isStateChange: true)
}