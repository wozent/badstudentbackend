package badstudent.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * date: in strict format "YYYY MM DD"
 * location:   Area
 * isMale:     gender
 * content:    User-defined
 * email:      Usual email format
 * phone:      11-digit
 * qq:         10-digit 
 * selfDefined:User-defined
 * price:      double
 * type:       ask(0) or help(1) or invalid(-1)
 */

public class Message{

    private String id;

    private String userName;

    private String password;

    private Date date;

    private Location location;

    private boolean isMale;

    private String content;

    private String email;

    private String phone;

    private String qq;
    
    private String twitter;

    private String selfDefined;

    private double price;

    private int type;

    //for serializer/deSerializer
    public Message(){
        this.id = "defaultId";
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.date = new Date();
        this.location = new Location("dafault dafault default dafault");
        this.isMale = true;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.twitter = "defaultTwitter";
        this.selfDefined = "dafultSelfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
    }

    //this constructor is used for testing purposes and potentially @Options HTTP calls only, please ignore it for any other usages
    public Message(String id){
        this.id = Constants.key_message_prefix+id;
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.date = new Date();
        this.location = new Location("dafault dafault default dafault");
        this.isMale = true;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.twitter = "defaultTwitter";
        this.selfDefined = "dafultselfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
    }

    public Message(String userName,String password,String date,Location location,boolean isMale,String content,String email,
            String phone,String qq, String twitter, String selfDefined,double price,int type){
        this.userName = userName;
        this.password = password;
        try {
            this.date = new SimpleDateFormat("yyyy MM dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.location = location;
        this.isMale = isMale;
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
    }

    public Message(String id,String userName,String password,String date,Location location,boolean isMale,String content,String email,
            String phone,String qq, String twitter, String selfDefined,double price,int type){
        this.id = id;
        this.userName = userName;
        this.password = password;
        try {
            this.date = new SimpleDateFormat("yyyy MM dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.location = location;
        this.isMale = isMale;
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
    	return (this.date.compareTo(date)==0);
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

    public Date getDate(){
        return this.date;
    }

    public Location getLocation(){
        return this.location;
    }

    public boolean isMale(){
        return this.isMale;
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

    public void setDate(Date date){
        this.date = date;
    }

    public void setLocation(String location){
        this.location = new Location(location);
    }

    public void setToMale(boolean isMale){
        this.isMale = isMale;
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

}

