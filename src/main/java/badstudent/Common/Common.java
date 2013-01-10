package badstudent.common;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import badstudent.model.Message;

public class Common {
    public static void d(String message){
        System.out.println("DEBUG MESSAGE BY BAD STUDENT " + message);
    }
    
    public static void d_Chinese(String message){
        try {
            PrintStream ps = new PrintStream(System.out, true, "UTF-8");
            ps.println(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public static Date getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");  
        String today = formatter.format(new Date());
        Date retVal = null;
        try {
            retVal =  formatter.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retVal;
    }
    
    public static boolean isEntryNull(String entry){
        if(entry==null){
            return true;
        }
        if(entry.equals("")){
            return true;
        }
        return false;
    }
    
    public static String extend(String shitYouAreTooShort){
    	Common.d("extend::" + shitYouAreTooShort);
    	int length = shitYouAreTooShort.length();
    	if ( length < 3){
    		for (int i = 0; i < 3 - length; i++){
    			shitYouAreTooShort += ("x");
    		}
    	}
    	return shitYouAreTooShort; 
    }

	public static boolean infoHasChanged(Message message, Message oldMessage) {
		if (!(message.getEmail() == null && oldMessage.getEmail() == null) || (message.getEmail() != null && oldMessage.getEmail() != null && message.getEmail().compareTo(oldMessage.getEmail()) == 0)){
			return true;
		}
		if (!(message.getPhone() == null && oldMessage.getPhone() == null) || (message.getPhone() != null && oldMessage.getPhone() != null && message.getPhone().compareTo(oldMessage.getPhone()) == 0)){
			return true;
		}
		if (!(message.getQq() == null && oldMessage.getQq() == null) || (message.getQq() != null && oldMessage.getQq() != null && message.getQq().compareTo(oldMessage.getQq()) == 0)){
			return true;
		}
		if (!(message.getTwitter() == null && oldMessage.getTwitter() == null) || (message.getTwitter() != null && oldMessage.getTwitter() != null && message.getTwitter().compareTo(oldMessage.getTwitter()) == 0)){
			return true;
		}
		if (!(message.getSelfDefined() == null && oldMessage.getSelfDefined() == null) || (message.getSelfDefined() != null && oldMessage.getSelfDefined() != null && message.getSelfDefined().compareTo(oldMessage.getSelfDefined()) == 0)){
			return true;
		}
		
		return false;
	}
}
