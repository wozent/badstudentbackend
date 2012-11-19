package badstudent.common;

import badstudent.model.Message;

public class Common {
    
    public static void d(String message){
        System.out.println("DEBUG MESSAGE BY BAD STUDENT " + message);
    }
    
    //Determine sequence of messages in time order
    public static boolean isMessageBefore(Message msg1, Message msg2){
        if(msg1.getDate()>msg2.getDate()){
            return false;
        }
        return true;
    }
}
