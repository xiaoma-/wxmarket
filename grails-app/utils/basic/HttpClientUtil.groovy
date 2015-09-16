package basic

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import javax.servlet.http.HttpServletRequest

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.http.Consts
import org.apache.http.HttpEntity
import org.apache.http.HttpException
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.params.HttpParams
import org.apache.http.util.EntityUtils

import sun.net.www.content.audio.basic

class HttpClientUtil {

	private static Log log = LogFactory.getLog(HttpClientUtil.class)

	/**
	 * 发送http请求并返回响应信息，方式为get
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getResponseByUrl(String url) throws Exception {
		return getResponseByUrlMethod(url, "get")
	}

	/**
	 * 发送http请求并返回响应信息，方式为post或get
	 * @param url
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String getResponseByUrlMethod(String url, String methodName) throws Exception {

		// Create an instance of HttpClient.
		DefaultHttpClient client = new DefaultHttpClient()
		
		// Create a method instance.
		HttpRequestBase method = null
		if("post".equals(methodName)) {
			String baseUrl = url.substring(0, url.indexOf("?"))
			method = getPostMethod(url)
		} else {
			url = getEncodedUrl(url)
			method = new HttpGet(url)
		}
		
		// Define response and input stream
		HttpResponse res
		
		try {
			res = client.execute(method)
			
			int statusCode = res.getStatusLine().getStatusCode()

			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + res.getStatusLine())
			}

			// Read the response body.
			HttpEntity entity = res.getEntity()
			String content = EntityUtils.toString(entity, ContentType.getOrDefault(entity).charset?:'UTF-8')
			return content

		} catch (HttpException e) {
			log.error("Fatal protocol violation: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (IOException e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (Exception e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} finally {
			// Release the connection.
			method.releaseConnection()
		}
	}
	
	public static getResponseByPostJson(url, jsonString) {
		
		// Create an instance of HttpClient.
		DefaultHttpClient client = getSecuredHttpClient(new DefaultHttpClient())
		
		// Create a method instance.
		HttpPost method = new HttpPost(url)
		
		// Define response and input stream
		HttpResponse res
		
		try {
			StringEntity se = new StringEntity(jsonString, 'UTF-8')
			se.setContentType("application/json")
			method.setEntity(se)
			res = client.execute(method)
			
			int statusCode = res.getStatusLine().getStatusCode()

			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + res.getStatusLine())
			}

			// Read the response body.
			HttpEntity entity = res.getEntity()
			String content = EntityUtils.toString(entity, ContentType.getOrDefault(entity).charset?:'UTF-8')
			return content

		} catch (HttpException e) {
			log.error("Fatal protocol violation: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (IOException e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (Exception e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} finally {
			// Release the connection.
			method.releaseConnection()
		}
	}
	
	/**
	 * 生成PostMethod
	 * @param url
	 * @return
	 */
	private static HttpPost getPostMethod(String url) {
		String baseUrl = url.substring(0, url.indexOf("?"))
		HttpPost postMethod = new HttpPost(baseUrl)
		String params = url.substring(url.indexOf("?") + 1, url.length())
		String[] s = params.split("&")
		List <NameValuePair> nvps = new ArrayList <NameValuePair>()
		for(int i = 0; i < s.length; i ++) {
			nvps.add(new BasicNameValuePair(s[i].split("=")[0], s[i].split("=")[1]))
		}
		postMethod.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8))
		return postMethod
	}

	/**
	 * 得到参数转码后的url（get方法）
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getEncodedUrl(String url) throws UnsupportedEncodingException {
		url = url.trim()
		if(url.indexOf("?") != -1)
		{
			String result = ""
			String baseUrl = url.substring(0, url.indexOf("?") + 1)
			String params = url.substring(url.indexOf("?") + 1, url.length())
			String[] s = params.split("=")
			result = result + s[0]
			for(int i = 1; i < s.length; i ++) {
				if(s[i].contains("&")) {
					result = result + "=" + java.net.URLEncoder.encode(s[i].split("&")[0], "utf-8") + "&" + s[i].split("&")[1]
				} else {
					result = result + "=" + java.net.URLEncoder.encode(s[i], "utf-8")
				}
			}
			
			result = baseUrl.replaceAll(" ", "%20") + result
			return result
		}
		url = url.replaceAll(" ", "%20")
		return url
	}

	/**
	 * getClientIp
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for")

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP")
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR")
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr()
		}

		return ip
	}

	/**
	 * 发送http请求并返回响应信息，方式为get
	 * @param url
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static getFileByUrl(String url, String filePath) throws Exception {

		url = getEncodedUrl(url)
		
		// Create an instance of HttpClient.
		DefaultHttpClient client = new DefaultHttpClient()
		
		// Create a method instance.
		HttpRequestBase method = new HttpGet(url)
		
		// Define response and input stream
		HttpResponse res
		InputStream inputStream
		
		try {
			res = client.execute(method)
			
			int statusCode = res.getStatusLine().getStatusCode()

			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + res.getStatusLine())
				return null
			}
			
			inputStream = res.getEntity().getContent()
			def fileName = filePath + StringUtil.getCharacterAndNumber(10) + '.JPEG'
			def file = new File(fileName)
			OutputStream os = new FileOutputStream(file)
			int bytesRead = 0
			byte[] buffer = new byte[8192]
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead)
			}
			os.close()
			inputStream.close()
			return file

		} catch (HttpException e) {
			log.error("Fatal protocol violation: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (IOException e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (Exception e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} finally {
			// Release the connection.
			method.releaseConnection()
		}
	}
	
	/**
	 * 发送http请求并返回响应信息，
	 * @param url
	 * @param methodName
	 * @return
	 * @throws Exception
	 */

	public static String responseByUrl(String url,Map param) throws Exception {
			//设置超时
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
			HttpConnectionParams.setSoTimeout(httpParameters, 20000);
			
			// Create an instance of HttpClient.
			DefaultHttpClient client = new DefaultHttpClient(httpParameters)
			// Create a method instance.
			HttpPost method  = new HttpPost(url)
			List <NameValuePair> nvps = new ArrayList <NameValuePair>()
				for(Map.Entry<String, String> entry : param.entrySet()){
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()))
				}
				
			method.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8))
			
			// Define response and input stream
			HttpResponse res
			InputStream inputStream
			
			try {
				res = client.execute(method)
				int statusCode = res.getStatusLine().getStatusCode()
				//println 'request statusCode '+statusCode
				if (statusCode != HttpStatus.SC_OK) {
					log.error("Method failed: " + res.getStatusLine())
				}
	
				// Read the response body.
				//inputStream = res.getEntity().getContent()
				//String responseBody = inputStream2String(inputStream)
				String responseBody=EntityUtils.toString(res.getEntity(), "UTF8");
				//println 'result: '+responseBody
				return responseBody

		} catch (HttpException e) {
			 log.error("Fatal protocol violation: " + e.getMessage())
			 e.printStackTrace()
		}catch (HttpException e) {
			log.error("Fatal protocol violation: " + e.getMessage())
			e.printStackTrace()
			return null
		} catch (IOException e) {
			log.error("Fatal transport error: " + e.getMessage())
			e.printStackTrace()
			return null
		} finally {
			// Release the connection.
			//inputStream.close()
			method.releaseConnection()
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
		}
	}
	
	private static DefaultHttpClient getSecuredHttpClient(HttpClient httpClient) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null
				}
	
				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
	
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}
			};
			javax.net.ssl.TrustManager[] tms = [tm]
			ctx.init(null, tms, new SecureRandom());
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			return new DefaultHttpClient(ccm, httpClient.getParams());
		} catch (Exception e) {
			System.out.println("=====:=====");
			e.printStackTrace();
		}
		return null;
	}
}
