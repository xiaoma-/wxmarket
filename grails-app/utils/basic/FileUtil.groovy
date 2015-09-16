package basic

import grails.converters.JSON

import java.awt.Rectangle
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import com.wxap.util.HttpClientUtil

class FileUtil {

	def static grailsApplication

	/**
	 * 抓取远程文件，返回文件路径
	 * @param imgWebUrl
	 * @return
	 */
	def static getFilePathByUrl(def fileUrl) {
		try {

			// 远程获取文件
			def tempFilePath = "${grailsApplication.config.filePath.download}/"
			def f = new File(tempFilePath)
			if(!f.exists()) {
				f.mkdirs()
			}
			def tempFileName = HttpClientUtil.getFileByUrl(fileUrl, tempFilePath)
			return tempFileName
		} catch(Exception e) {
			e.printStackTrace()
		}
	}

	/**
	 * 上传本地文件，返回文件路径
	 * @param multipartFile
	 * @return
	 */
	def static getFilePathByUpload(def multipartFile) {
		try {

			// 上传图片
			def tempFilePath = "${grailsApplication.config.filePath.temp}/"
			def f = new File(tempFilePath)
			if(!f.exists()) {
				f.mkdirs()
			}
			def fileNameWithoutPath	// 图片文件名
			def fileAbsolutePath = multipartFile.fileItem.name	// 文件路径
			def filename	// 文件路径  + 随机数  + 文件名 --> 文件路径  + 随机数  + 后缀名（由于文件名上传出错）
			if(fileAbsolutePath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("."), fileAbsolutePath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}

			filename= tempFilePath + fileNameWithoutPath

			// 上传到本地
			multipartFile.transferTo(new File(filename))

			return filename

		} catch(Exception e) {
			e.printStackTrace()
		}
	}

	/**
	 * 处理图片并保存
	 * @param imgPath
	 * @param tempFileName
	 * @return
	 */
	def static handleImg(def imgPath, def tempFileName) {

		def image // = new TImage().save(flush:true)

		// 图片处理
		def filePath = "${grailsApplication.config.filePath.upload}/${imgPath}"
		def filename = filePath + "/${image.id}"
		def f = new File(filePath)
		if(!f.exists()) {
			f.mkdirs()
		}

		// 处理并保持图片
		def imagetool //= new ImageTool()
		String os = System.getProperty("os.name")
		if(os.indexOf("indow") == -1) {
			println "convert ${tempFileName} -strip -quality ${TConfig.findByCode('image.quality.big',[cache:true]).value} -resize ${TConfig.findByCode('image.size.big',[cache:true]).value} ${filename}.JPEG".execute().errorStream?.text
//			println "convert ${tempFileName} -strip -quality ${TConfig.findByCode('image.quality.mid',[cache:true]).value} -resize ${TConfig.findByCode('image.size.mid',[cache:true]).value} ${filename}_mid.JPG".execute().errorStream?.text
	//		def subTempFileName = tempFileName.substring(0, tempFileName.lastIndexOf('.')) + '_sub' + tempFileName.substring(tempFileName.lastIndexOf('.'), tempFileName.size())
	//		def originalWidth = imagetool.getImgWidth(tempFileName)
	//		def originalHeight = imagetool.getImgHeight(tempFileName)
	//		if(originalWidth > originalHeight) {
	//			println "convert ${tempFileName} -resize x${originalHeight} -gravity center -extent ${originalHeight}x${originalHeight} ${subTempFileName}".execute().errorStream?.text
	//		} else {
	//			println "convert ${tempFileName} -resize ${originalWidth}x -gravity center -extent ${originalWidth}x${originalWidth} ${subTempFileName}".execute().errorStream?.text
	//		}
	//		println "convert ${subTempFileName} -strip -quality ${TConfig.findByCode('image.quality.small',[cache:true]).value} -resize ${TConfig.findByCode('image.size.small',[cache:true]).value} ${filename}_small.JPG".execute().errorStream?.text
	
		} else {
			copy(tempFileName, filename + ".JPEG")
		}

		image.imgPath = imgPath + "/${image.id}.JPEG"
		image.imgWidth = imagetool.getImgWidth(filename + ".JPEG")
		image.imgHeight = imagetool.getImgHeight(filename + ".JPEG")
		image.save()
		return image
	}

	/**
	 * 得到截图后的图片路径
	 * @param srcImgPath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	def static subImg(def srcImgPath, def x, def y, def width, def height) {
		try {

			def file = new File(srcImgPath)
			def is = new FileInputStream(file)
			def srcImage = ImageIO.read(is)
			def subImageBounds = new Rectangle(x, y, width, height)
			def tempFilePath = "${grailsApplication.config.filePath.temp}/"	// 文件路径
			def fileNameWithoutPath	// 图片文件名
			def filename	// 文件路径  + 随机数  + 文件名
			if(srcImgPath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + srcImgPath.substring(srcImgPath.lastIndexOf("."), srcImgPath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}
			def targetImgPath = tempFilePath + fileNameWithoutPath

			BufferedImage subImage = srcImage.getSubimage(subImageBounds.x.intValue(), subImageBounds.y.intValue(), subImageBounds.width.intValue(), subImageBounds.height.intValue())
			String formatName = targetImgPath.substring(targetImgPath.lastIndexOf('.') + 1)
			def f = new File(targetImgPath)
			if(!f.exists()) {
				f.mkdirs()
			}
			ImageIO.write(subImage, formatName, f)
			is.close()
			return targetImgPath
		} catch(Exception e) {
			e.printStackTrace()
		}
	}
	
	/**
	 * 拷贝文件
	 * @param srcFilePath
	 * @param targetFielPath
	 * @return
	 */
	def static copy(def srcFilePath, def targetFielPath) {
		try {
			
			def f = new File(targetFielPath.substring(0, targetFielPath.lastIndexOf('/')))
			if(!f.exists()) {
				f.mkdirs()
			}
			
			String os = System.getProperty("os.name")
			if(os.indexOf("indow") == -1) {
				def errorMsg =  "cp -Rf ${srcFilePath} ${targetFielPath}".execute().errorStream?.text
				if(errorMsg) println 'copy'+errorMsg
			} else {
				def BUFFER = 2048
				InputStream is = new FileInputStream(srcFilePath)
				BufferedInputStream bis = new BufferedInputStream(is)
				FileOutputStream fos = new FileOutputStream(targetFielPath)
				BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER)
				int c
				byte[] data = new byte[BUFFER]
				while((c = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, c)
				}
				bos.close()
				fos.close()
				bis.close()
			}
			
			return targetFielPath
		} catch(Exception e) {
			println e.message
		}
	}
	
	/**
	 * Upload file
	 */
	def static uploadImage(def multipartFiles) {

		try {
			// 上传图片
			def tempFilePath = "${grailsApplication.config.filePath.upload}/"
			def f = new File(tempFilePath)
			if(!f.exists()) {
				f.mkdirs()
			}
			def fileNameWithoutPath	// 图片文件名
			def fileAbsolutePath = multipartFiles.fileItem.name	// 文件路径
			def filename	// 文件路径  + 随机数  + 文件名 --> 文件路径  + 随机数  + 后缀名（由于文件名上传出错）
			if(fileAbsolutePath.indexOf('.') != -1) {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("."), fileAbsolutePath.length())
			} else {
				fileNameWithoutPath = StringUtil.getCharacterAndNumber(10) + '.jpg'
			}

			filename = tempFilePath + fileNameWithoutPath

			def file = new File(filename)

			// 上传到本地
			multipartFiles.transferTo(file)
			def result = [error:'0',message:'',url:"${basic.TConfig.findByCode('website.address')?.value}${filename.substring(filename.indexOf('upload') - 1, filename.size())}"]
			return result as JSON
		} catch(Exception e) {
			println e.message
			def result = [error:'1',message:'上传出错',url:'']
			return result as JSON
		}
	}
	
	
	def static download2url(def imgPath,def urlPath){
		
			if(urlPath){
			def imageName = urlPath.substring(urlPath.lastIndexOf("/")+1)
			def filePath = "${grailsApplication.config.filePath.upload}/${imgPath}"
			def fileUrl = filePath + "/${imageName}"
			def fileName = "upload/${imgPath}/${imageName}"
			try {
					def file = new File(filePath)
					if(!file.exists()){
						file.mkdirs()
					}
					URL url = null;
					url = new URL(urlPath);
					InputStream is = url.openStream();;
					OutputStream os = new FileOutputStream(new File(fileUrl));
					 int bytesRead = 0;
					 byte[] buffer = new byte[8192];
					 while((bytesRead = is.read(buffer,0,8192))!=-1){
						 os.write(buffer,0,bytesRead);
					 }
					 return fileName
				} catch (IOException e) {
					println e.message
					e.printStackTrace();
					return null;
			   }
			}
	 }
		
}