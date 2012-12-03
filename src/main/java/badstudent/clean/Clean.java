package badstudent.clean;

import badstudent.model.*;
import badstudent.dbservice.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class Clean{
	private static DaoService daoService = new DaoService();
	public static String timeZoneId = "asia/shanghai";
	
	public static Calendar dateToCalendar(Date date){ 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static void writeMessageToFile(Message message){
		try {
            // Create instances of FileOutputStream and ObjectOutputStream.
            FileOutputStream fos = new FileOutputStream("removedMessages.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
 
            oos.writeObject(message);
            oos.flush();
            oos.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public Clean(){
	}

	
	public void cleanSchedules(){
		Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
		Set<String> keys = daoService.getIds();
		
		//go through each Message and clears outdated data, write them to a txt file
		for (String key : keys){
			Message message = daoService.getMessageById(key);
			Calendar messageEndCal = dateToCalendar(message.getDate());
			messageEndCal.add(Calendar.DAY_OF_YEAR, 1);    		//add an offset of 1 day since date from front end always indicates 00:00AM on the last day
			if (messageEndCal.before(currentCal)){
				daoService.deleteMessage(message.getId());
			}
		}
		

	}
	
	
	
	
	
}


