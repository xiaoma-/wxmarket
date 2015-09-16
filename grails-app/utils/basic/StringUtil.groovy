package basic
import java.util.regex.Pattern

class StringUtil {

	/**
	 * 生成数字和字母随机数
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length) {
		String password = ""
		Random random = new Random()
		for(int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2)%2 == 0 ? "char" : "num";
			if("char".equalsIgnoreCase(charOrNum)) {
				// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97 //取得大写字母还是小写字母
				password += (char) (choice + random.nextInt(26))
			}else if("num".equalsIgnoreCase(charOrNum)) {
				// 数字
				password += String.valueOf(random.nextInt(10))
			}
		}

		return password
	}

	/**
	 * Filter the contact info.
	 * @param content
	 * @return
	 */
	public static String filter(String content) {

		if(!content) return ''

		// filter email
		def pattern = ~/[\w-\.]+@(.|\s+|[\w-]+\.com)/
		def matcher = pattern.matcher(content)
		def count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],'*')
		}

		// filter phone number
		pattern = ~/(\d|\s|-){8,15}/
		matcher = pattern.matcher(content)
		count = matcher.getCount()
		(0..<count).each { i ->
			content = content.replaceAll(matcher[i][0],' **** ')
		}
		return content
	}

	/**
	 * 判断中英文姓名
	 */
	public static boolean ifGbkUserName(String nickName) {
		boolean isGbk = false;
		for (int i = 0; i < nickName.length(); i++) {
			String str = nickName.substring(i, i+1);
			//生成一个Pattern,同时编译一个正则表达式
			isGbk = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", str);
			if(isGbk){
				break;
			}
		}
		return isGbk;
	}
	
	def static getList(def strs) {
		def result = []
		if(strs) {
			if(strs instanceof String) {
				result << strs
			} else {
				result = strs
			}
		}
		return result
	}
	
	def static list2string(def strs) {
		def ls = getList(strs)
		def result = ""
		ls.each {
			result += "${it},"
		}
		if(result.endsWith(",")) {
			result = result.substring(0, result.size() - 1)
		}
		return result
	}
	
	//html标签过滤
	def static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

}
