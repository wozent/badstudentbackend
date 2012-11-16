package badstudent.model;

/**
 * The main data model
 * 
 * Fields of userName, password, dataString, location, isMale, title, content should be sent from front end, Id should be generate by backend
 */

public class Message{
	
	public static String prefix = "message-";
	
	private String id;
	
	private String userName;
	
	private String password;
	
	private String dateString;
	
	private location location;
	
	private boolean isMale;
	
	private String title;
	
	private String content;
	
	//for serializer/deSerializer
    public Message(){
    	this.id = "defaultId";
    	this.userName = "defaultUserName";
    	this.password = "defaultPassword";
    	this.dateString = "defaultDataString";
    	this.location = new location("dafault dafault default dafault");
    	this.isMale = true;
    	this.title = "defaultTitle";
    	this.content = "defaultContent";
    }
    
    //this constructor is used for testing purposes and potentially @Options HTTP calls only, please ignore it for any other usages
    public Message(String id){
    	this.id = id;
    	this.userName = "defaultUserName";
    	this.password = "defaultPassword";
    	this.dateString = "defaultDataString";
    	this.location = new location("dafault dafault default dafault");
    	this.isMale = true;
    	this.title = "defaultTitle";
    	this.content = "defaultContent";
    }
    
    public Message(String userName, String password, String dateString, location location, boolean isMale, String title, String content){
    	this.generateId();
    	this.userName = userName;
    	this.password = password;
    	this.dateString = dateString;
    	this.location = location;
    	this.isMale = isMale;
    	this.title = title;
    	this.content = content;
    }
	
    public Message(String id, String userName, String password, String dateString, location location, boolean isMale, String title, String content){
    	this.id = id;
    	this.userName = userName;
    	this.password = password;
    	this.dateString = dateString;
    	this.location = location;
    	this.isMale = isMale;
    	this.title = title;
    	this.content = content;
    }
    
	private void generateId(){
		this.id = "defaultId";
		//some code to generate a unique id for each message	
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
	
	public String getTitle(){
		return this.title;
	}
	
	public String getContent(){
		return this.content;
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
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
}

