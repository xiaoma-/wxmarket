package business

import basic.FileUtil
import basic.StringUtil

class AttachmentUtil {

	def static getcontent(companyid, params, field) {
		def result = ""
		def attachments = StringUtil.getList(params."${field}")
		attachments.each {
			if(it) {
				def file = it
				if(it.contains("temp/")) {
					def image = FileUtil.handleImg(companyid, it)
					file = "upload/${image.imgPath}"
				}
				result += "${file},"
			}
		}
		if(result.endsWith(",")) {
			result = result.substring(0, result.size() - 1)
		}
		return result?:null
	}
}
