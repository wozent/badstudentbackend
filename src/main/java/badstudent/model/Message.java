package badstudent.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import badstudent.common.Common;
import badstudent.common.Constants;
import badstudent.database.DaoMessage;

/**
 * The main data model
 * 
 * id: unique id for each message
 * userName:   User-defined
 * password:   User-defined
 * startDate: in strict format "YYYY MM DD"
 * endDate: in strict format "YYYY MM DD"
 * courseLengthInMinutes: 
 * location:   Area
 * isMale:     gender
 * content:    User-defined
 * email:      Usual email format
 * phone:      11-digit
 * qq:         10-digit 
 * twitter:    whatever
 * selfDefined:User-defined
 * price:      double
 * type:       ask(0) or help(1) or invalid(-1)
 * authCode:   default -1
 */


public class Message{
	
	public static final String goofyPasswordTrickHackers = "o god you are so gay";

    private String id;

    private String userName;

    private String password;

    private Date startDate;
    
    private Date endDate;
    
    private int courseLengthInMinutes;

    private Location location;

    private int gender;

    private String content;

    private String email;

    private String phone;

    private String qq;
    
    private String twitter;

    private String selfDefined;

    private double price;

    private int type;
    
    private int authCode;

    //for serializer/deSerializer
    public Message(){
        this.id = "defaultId";
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.startDate = new Date();
        this.endDate = new Date();
        this.location = new Location("江苏省 南京市 东南大学");
        this.gender = Constants.geneder_both;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.twitter = "defaultTwitter";
        this.selfDefined = "dafultSelfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
        this.authCode = -1;
    }

    //this constructor is used for testing purposes and potentially @Options HTTP calls only, please ignore it for any other usages
    public Message(String id){
        this.id = Constants.key_message_prefix+id;
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.startDate = new Date();
        this.endDate = new Date();
        this.location = new Location("江苏省 南京市 东南大学");
        this.gender = Constants.geneder_both;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.twitter = "defaultTwitter";
        this.selfDefined = "dafultselfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
        this.authCode = -1;
    }

    public Message(String userName,String password,String startDate,String endDate,int courseLengthInMinutes, Location location,int gender,String content,String email,
            String phone,String qq, String twitter, String selfDefined,double price,int type){
        this.userName = userName;
        this.password = password;
        try {
            this.startDate = new SimpleDateFormat("yyyy MM dd").parse(startDate);
            this.endDate = new SimpleDateFormat("yyyy MM dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.courseLengthInMinutes = courseLengthInMinutes;
        this.location = location;
        this.gender = gender;
        this.content = content;
        if(email.length()<3){
            email = email + "   ";
        }
        if(phone.length()<3){
            phone = phone + "   ";
        }
        if(qq.length()<3){
            qq = qq + "   ";
        }
        if(twitter.length()<3){
            twitter = twitter + "   ";
        }
        if(selfDefined.length()<3){
            selfDefined = selfDefined + "   ";
        }
        this.email = email;
        this.phone = phone;
        this.qq = qq;
        this.twitter = twitter;
        this.selfDefined = selfDefined;
        this.price = price;
        this.type = type;
        this.generateId();    //this has to be placed at the bottom of the constructor to avoid NullPointerException
        this.authCode = -1;
    }

    public Message(String id,String userName,String password,String startDate,String endDate,int courseLengthInMinutes, Location location,int gender,String content,String email,
            String phone,String qq, String twitter, String selfDefined,double price,int type){
        this.id = id;
        this.userName = userName;
        this.password = password;
        try {
            this.startDate = new SimpleDateFormat("yyyy MM dd").parse(startDate);
            this.endDate = new SimpleDateFormat("yyyy MM dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.courseLengthInMinutes = courseLengthInMinutes;
        this.location = location;
        this.gender = gender;
        this.content = content;
        if(email.length()<3){
            email = email + "   ";
        }
        if(phone.length()<3){
            phone = phone + "   ";
        }
        if(qq.length()<3){
            qq = qq + "   ";
        }
        if(twitter.length()<3){
            twitter = twitter + "   ";
        }
        if(selfDefined.length()<3){
            selfDefined = selfDefined + "   ";
        }
        this.email = email;
        this.phone = phone;
        this.qq = qq;
        this.twitter = twitter;
        this.selfDefined = selfDefined;
        this.price = price;
        this.type = type;
        this.authCode = -1;
    }
    
    public Message(String id,String userName,String password,String startDate,String endDate,int courseLengthInMinutes, Location location,int gender,String content,String email,
            String phone,String qq, String twitter, String selfDefined,double price,int type, int authCode){
        this.id = id;
        this.userName = userName;
        this.password = password;
        try {
            this.startDate = new SimpleDateFormat("yyyy MM dd").parse(startDate);
            this.endDate = new SimpleDateFormat("yyyy MM dd").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.courseLengthInMinutes = courseLengthInMinutes;
        this.location = location;
        this.gender = gender;
        this.content = content;
        if(email.length()<3){
            email = email + "   ";
        }
        if(phone.length()<3){
            phone = phone + "   ";
        }
        if(qq.length()<3){
            qq = qq + "   ";
        }
        if(twitter.length()<3){
            twitter = twitter + "   ";
        }
        if(selfDefined.length()<3){
            selfDefined = selfDefined + "   ";
        }
        this.email = email;
        this.phone = phone;
        this.qq = qq;
        this.twitter = twitter;
        this.selfDefined = selfDefined;
        this.price = price;
        this.type = type;
        this.authCode = authCode;
    }

    private void generateId(){
        String newId = Constants.key_message_prefix + extend(this.email).substring(0,3) + "-" +
                extend(this.phone).substring(this.phone.length()-3) + "-" + extend(this.qq).substring(this.qq.length()-3) + "-" + extend(this.twitter).substring(this.twitter.length()-3)  + "-" + extend(this.selfDefined).substring(this.selfDefined.length()-3) + "-" + this.type + "-" + DaoMessage.generateId() ;
        this.id = newId;
        Common.d(newId);
    }
    
    private String extend(String shitYouAreTooShort){
    	if (shitYouAreTooShort.length() < 3){
    		for (int i = 0; i < 3 - shitYouAreTooShort.length(); i++){
    			shitYouAreTooShort.concat(" ");
    		}
    	}
    	return shitYouAreTooShort;  //Michael, solving first world male problems once for all!!
    }
    
    //test if this message is on the same day as target date
    public boolean sameDay(Date date){
    	return (this.startDate.compareTo(date)==0);
    }
    
    public boolean isDayInRange(Date date){
    	
    	if (this.startDate.equals(date) || this.endDate.equals(date)){
    		return true;
    	}
    	else{
    		boolean tooEarly = date.before(this.startDate);
        	
        	Calendar endCal = dateToCalendar(this.endDate);
        	Calendar curCal = dateToCalendar(date);
        	endCal.add(Calendar.DAY_OF_YEAR, 1);    		//add an offset of 1 day since date from front end always indicates 00:00AM on the last day
        	boolean tooLate = (endCal.before(curCal) || endCal.equals(curCal));
        	
        	return !(tooEarly || tooLate);
    	}
    }
    
    //test if this message is at the same location as target location 
    public boolean sameLocation(Location location){
    	return (this.location.toString().compareTo(location.toString()) == 0);
    }
    
    public String getId(){
        return this.id;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getPassword(){
        return this.password;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public Location getLocation(){
        return this.location;
    }

    public int getGender(){
        return this.gender;
    }


    public String getContent(){
        return this.content;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setStartDate(Date date){
        this.startDate = date;
    }

    public void setLocation(String location){
        this.location = new Location(location);
    }

    public void setGender(int gender){
        this.gender = gender;
    }

    public void setContent(String content){
        this.content = content;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSelfDefined() {
        return selfDefined;
    }

    public void setSelfDefined(String selfDefined) {
        this.selfDefined = selfDefined;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
    
    public String getTwitter(){
    	return twitter;
    }
    
    public void setTwitter(String twitter){
    	this.twitter = twitter;
    }
    
    public int getAuthCode(){
    	return this.authCode;
    }
    
    public void setAuthCode(int authCode){
    	this.authCode = authCode;
    }
    
    public void restoreAuthCode(){
    	this.authCode = -1;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    

    public int getCourseLengthInMinutes() {
        return courseLengthInMinutes;
    }

    public void setCourseLengthInMinutes(int courseLengthInMinutes) {
        this.courseLengthInMinutes = courseLengthInMinutes;
    }
    
    public static Calendar dateToCalendar(Date date){ 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}

