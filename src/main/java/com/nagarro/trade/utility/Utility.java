package com.nagarro.trade.utility;

import java.time.LocalTime;
import java.util.Calendar;

public class Utility {
	
	public static boolean canBuySellEquity() {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		LocalTime localTime = LocalTime.now();
		if((dayOfWeek>=2 && dayOfWeek<=6) && (localTime.getHour()>=9 && localTime.getHour()<17)) {
			return true;
		}else {
			return false;
		}
	}

}
