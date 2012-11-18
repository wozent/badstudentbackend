package badstudent.model;

import badstudent.Common.Common;
import badstudent.Common.Constants;
import badstudent.dao.resource.Dao;

/**
 * The main data model
 * 
 * id: unique id for each message
 * userName:   User-defined
 * password:   User-defined
 * dateString: Undefined
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

    private String dateString;

    private location location;

    private boolean isMale;

    private String content;

    private String email;
    
    private String phone;
    
    private String qq;
    
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    private String selfDefined;
    
    private double price;
    
    private int type;

    //for serializer/deSerializer
    public Message(){
        this.id = "defaultId";
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.dateString = "defaultDataString";
        this.location = new location("dafault dafault default dafault");
        this.isMale = true;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.selfDefined = "dafultselfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
    }

    //this constructor is used for testing purposes and potentially @Options HTTP calls only, please ignore it for any other usages
    public Message(String id){
        this.id = Constants.message_prefix+id;
        this.userName = "defaultUserName";
        this.password = "defaultPassword";
        this.dateString = "defaultDataString";
        this.location = new location("dafault dafault default dafault");
        this.isMale = true;
        this.content = "defaultContent";
        this.email = "defaultEmail@default.com";
        this.phone = "1383838438";
        this.qq = "1234567890";
        this.selfDefined = "dafultselfDefiend";
        this.price = 0.0;
        this.type = Constants.type_ask;
    }

    public Message(String userName,String password,String dateString,location location,boolean isMale,String content,String email,
            String phone,String qq,String selfDefined,double price,int type){
        this.generateId();
        this.userName = userName;
        this.password = password;
        this.dateString = dateString;
        this.location = location;
        this.isMale = isMale;
        this.content = content;
        this.email = email;
        this.phone = phone;
        this.qq = qq;
        this.selfDefined = selfDefined;
        this.price = price;
        this.type = type;
    }

    public Message(String id,String userName,String password,String dateString,location location,boolean isMale,String content,String email,
            String phone,String qq,String selfDefined,double price,int type){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.dateString = dateString;
        this.location = location;
        this.isMale = isMale;
        this.content = content;
        this.email = email;
        this.phone = phone;
        this.qq = qq;
        this.selfDefined = selfDefined;
        this.price = price;
        this.type = type;
    }

    private void generateId(){
        String newId = Constants.message_prefix + "-" + Dao.generateId() + "-" + this.email.substring(0,3) + "-" +
                this.phone.substring(0,3) + "-" + this.qq.substring(0,3) + "-" + this.selfDefined.substring(0,3);
        this.id = newId;
        Common.d(newId);
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

    public String getDateString(){
        return this.dateString;
    }

    public String getLocation(){
        return this.location.toString();
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

    public void setDateString(String dateString){
        this.dateString = dateString;
    }

    public void setLocation(String location){
        this.location = new location(location);
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
}

