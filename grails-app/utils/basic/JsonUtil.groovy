package basic

import grails.converters.JSON
import groovy.json.JsonSlurper

class JsonUtil {

	def static string2json(str) {
		def result = [:]
		println "JsonUtil[string2json] DATA: "+str
		if(str!=null&&str!=""&&str) {
			result = new JsonSlurper().parseText(str)
		}
		return result
	}
	
	def static json2string(json) {
		def result = (json as JSON).toString()
		result = result?.replace('\\"', '').replace('\\r', '').replace('\\n', '').replace("'", "")
		return result
	}
	
	def static string2xml(str) {
		def result = [:]
		if(str!=null&&str!=""&&str) {
			result = new XmlParser().parseText(str)
		}
		return result
	}
}
