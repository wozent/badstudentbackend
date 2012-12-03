package badstudent.clean;

import badstudent.model.*;
import badstudent.common.Common;
import badstudent.dbservice.*;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
		try{
			// Create file 
			FileWriter fstream = new FileWriter("messageHistory.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Hello lol");
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}


	public Clean(){
	}


	public void cleanSchedules(){
		/*Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId));
		Set<String> keys = daoService.getIds();

		//go through each Message and clears outdated data, write them to a txt file
		for (String key : keys){
			Message message = daoService.getMessageById(key);
			Calendar messageEndCal = dateToCalendar(message.getDate());
			messageEndCal.add(Calendar.DAY_OF_YEAR, 1);    		//add an offset of 1 day since date from front end always indicates 00:00AM on the last day
			if (messageEndCal.before(currentCal)){
				daoService.deleteMessage(message.getId());
				writeMessageToFile(message);
			}
		}*/
		Message message = new Message();
		writeMessageToFile(message);
		Common.d("clean thread triggered");
	}





}


