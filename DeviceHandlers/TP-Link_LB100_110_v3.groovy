/*
TP-Link LB100 and LB110 Device Handler
FOR USE ONLY WITH 'TP-LinkServer_v4.js'

Copyright 2017 Dave Gutheinz

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:

        http://www.apache.org/licenses/LICENSE-2.0
        
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

Supported models and functions:  This device supports the TP-Link LB100 and LB110 bulbs without Energy Meter functions.

Update History
07-04-2017    - Updated to common format and interface with Emeter Version
*/

metadata {
    definition (
        name: "TP-Link LB110 and LB100", namespace: "djg", author: "Dave Gutheinz") {
        capability "switch"
        capability "Switch Level"
        capability "refresh"
        capability "Sensor"
        capability "Actuator"
    }
    tiles(scale: 2) {
        multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.light.on", backgroundColor:"#00a0dc",
                nextState:"waiting"
                attributeState "off", label:'${name}', action:"switch.on", icon:"st.switches.light.off", backgroundColor:"#ffffff",
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
        standardTile("refresh", "capability.refresh", width: 2, height: 1,  decoration: "flat") {
            state ("default", label:"Refresh", action:"refresh.refresh", backgroundColor:"#ffffff")
        }         
        main("switch")
        details("switch", "colorTempSliderControl", "colorTemp", "power", "weekTotal", "monthTotal", "engrToday", "weekAverage", "monthAverage", "bulbMode", "refresh" ,"refreshStats")
    }
}

preferences {
    input("deviceIP", "text", title: "Device IP", required: true, displayDuringSetup: true)
    input("gatewayIP", "text", title: "Gateway IP", required: true, displayDuringSetup: true)
}

def installed() {
    updated()
}

def updated() {
    unschedule()
    runEvery15Minutes(refresh)
    runIn(2, refresh)
}

//    ----- BASIC BULB COMMANDS ------------------------------------
def on() {
    sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":1}}}', "commandResponse")
}
def off() {
    sendCmdtoServer('{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"on_off":0}}}', "commandResponse")
}

def setLevel(percentage) {
    percentage = percentage as int
    sendCmdtoServer("""{"smartlife.iot.smartbulb.lightingservice":{"transition_light_state":{"ignore_default":1,"on_off":1,"brightness":${percentage}}}}""", "commandResponse")
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

//    ----- REFRESH ------------------------------------------------
def refresh(){
    sendCmdtoServer('{"system":{"get_sysinfo":{}}}', "refreshResponse")
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

//    ----- Parse State from Bulb Responses ------------------------
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
    log.info "$device.name $device.label: Power: ${status} / Brightness: ${level}%"
    sendEvent(name: "switch", value: status, isStateChange: true)
    sendEvent(name: "bulbMode", value: mode, isStateChange: true)
    sendEvent(name: "level", value: level, isStateChange: true)
    sendEvent(name: "colorTemperature", value: color_temp, isStateChange: true)
}

//    ----- Send the Command to the Bridge -------------------------
private sendCmdtoServer(command, action){
    def headers = [:] 
    headers.put("HOST", "$gatewayIP:8082")   // Same as on Hub.
    headers.put("tplink-iot-ip", deviceIP)
    headers.put("tplink-command", command)
    headers.put("command", "deviceCommand")
    sendHubCommand(new physicalgraph.device.HubAction([
        headers: headers],
        device.deviceNetworkId,
        [callback: action]
    ))
}