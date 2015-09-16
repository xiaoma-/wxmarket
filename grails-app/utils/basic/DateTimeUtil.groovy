package basic

import java.text.SimpleDateFormat
import java.util.Calendar;
import java.util.Date

class DateTimeUtil {
	
	private static final int SECOND = 1000
	private static final int MINUTE = 60 * SECOND
	private static final int HOUR = 60 * MINUTE
	private static final int DAY = 24 * HOUR

	
	def static Date dateTodate(Date date,String format) {
		Date d = null
		SimpleDateFormat sdf
		try {
			if(date){
				sdf = new SimpleDateFormat(format?:'yyyy-MM-dd HH:mm:ss')
				d = sdf.parse(sdf.format(date))
			}
		} catch(Exception ex) {
			println ex
		}
		return d
	}
	
	/**
	 * Parse a datetime string.
	 * @param param datetime string, pattern: "MM/dd/yyyy".
	 * @return java.util.Date
	 */
	def static Date parse(String format) {
		Date date = null
		SimpleDateFormat sdf
		if(format && format.indexOf('-') != -1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd")
		} else {
			sdf = new SimpleDateFormat("MM/dd/yyyy")
		}
		try {
			date = sdf.parse(format)
		} catch(Exception ex) {
		}
		return date
	}
	
	def static Date parseTime(String format) {
		Date date = null
		SimpleDateFormat sdf
		if(format) {
			if(format.indexOf('-') != -1) {
				sdf = new SimpleDateFormat("yyyy-MM-dd")
				if(format.indexOf(':') != -1) {
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm")
				}
			} else {
				sdf = new SimpleDateFormat("MM/dd/yyyy")
				if(format.indexOf(':') != -1) {
					sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm")
				}
			}
		}
		try {
			date = sdf.parse(format)
		} catch(Exception ex) {
		}
		return date
	}
	
	def static String getTimeAgo(long time) {
		// TODO: use DateUtils methods instead
		if (time < 1000000000000L) {
			// if timestamp given in seconds, convert to millis
			time *= 1000
		}

		long now = System.currentTimeMillis()//UIUtils.getCurrentTime(ctx);
		if (time > now || time <= 0) {
			return null
		}

		final long diff = now - time
		if (diff < MINUTE) {
			return "刚刚"
		} else if (diff < 2 * MINUTE) {
			return "1分钟前"
		} else if (diff < 50 * MINUTE) {
			return (diff / MINUTE).intValue() + "分钟前"
		} else if (diff < 90 * MINUTE) {
			return "1小时前"
		} else if (diff < 24 * HOUR) {
			return (diff / HOUR).intValue() + "小时前"
		} else if (diff < 48 * HOUR) {
			return "昨天"
		} else {
			return (diff / DAY).intValue() + "天前"
		}
	}
	
	def static Date addSeconds(date, seconds) {
		def result = date
		result.setTime(date.time + seconds * 1000)
		return result
	}
	/**
	 * 月初日期.
	 * @param year
	 * @param month(月份是从零开始)
	 * @return
	 */
	 def static Date getMonthlyFrom(int year, int month){
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(Calendar.YEAR,year);
		  calendar.set(Calendar.MARCH,month-1);
		  calendar.set(Calendar.HOUR_OF_DAY,0);
		  calendar.set(Calendar.MINUTE,0);
		  calendar.set(Calendar.SECOND,0);
		  int days = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		  calendar.set(Calendar.DAY_OF_MONTH,days);
		  return dateTodate(calendar.getTime(),"yyyy-MM-dd");
	 }
	 
	 /**
	 * 月末日期.
	 * @param year
	 * @param month
	 * @return
	 */
	 def static Date getMonthlyTo(int year, int month){
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(Calendar.YEAR,year);
		  //增加这部分设置,否则月末设置不正确.
		  calendar.set(Calendar.DATE, 1);
		  calendar.set(Calendar.MARCH,month-1);
		  calendar.set(Calendar.HOUR_OF_DAY,24);
		  calendar.set(Calendar.MINUTE,0);
		  calendar.set(Calendar.SECOND,0);
		  int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		  calendar.set(Calendar.DAY_OF_MONTH,days);
		  
		  return dateTodate(calendar.getTime(),"yyyy-MM-dd");
	 }
	 
	 /**
	 * 日期增加一天
	 * @param date
	 * @param day
	 * @return
	 */
	 def static Date getDayTo(Date date, int day){
		 def d = dateTodate(date,'yyyy-MM-dd HH:mm:ss')
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(d);
		 calendar.add(Calendar.DATE, day);//增加天数
		 return dateTodate(calendar.getTime(),'yyyy-MM-dd HH:mm:ss')
	 }
	 
	public static void main(String[] args){
	}
}
