/**
 *  SmartLight
 *
 *  Copyright 2017 Greg Swartzell
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "SmartLight",
    namespace: "gswartzell",
    author: "Greg Swartzell",
    description: "A smart app to turn the lights on only when dark outside",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Title") {
		// TODO: put inputs here
	}
    section("Turn on this light") {
        input "switches", "capability.switch", title: "Which lights to turn on?"
        input "offset", "number", title: "Enable this many minutes after sunset"
    }
        section ("Who to detect") {
        input "presenceSensors", "capability.presenceSensor"
    }
}

def isDark = false

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    // TODO: subscribe to attributes, devices, locations, etc.
    subscribe(location, "sunset", sunsetHandler)
    subscribe(location, "sunrise", sunriseHandler)
	subscribe(presenceSensors, "presence", presenceHandler)
    //schedule it to run today too
    //scheduleTurnOn(location.currentValue("sunsetTime"))
}

def presenceHandler(evt) {
	if(evt.value == "present") {
    	log.debug "Found Someone $evt.value"
        isDark = true
    	log.debug "isDark: $isDark"
    } else {
        log.debug "Found Someone $evt.value"
        isDark = false
    	log.debug "isDark: $isDark"
	}        
}

def sunsetHandler(evt) {
	log.debug "Found Sunset $evt.value"
    isDark = true
    log.debug "isDark: $isDark"
}

def sunriseHandler(evt) {
	log.debug "Found Sunrise $evt.value"
    isDark = false
    log.debug "isDark: $isDark"
    
}