package badstudent.message;

/**
 * The main data model
 * 
 * Fields of userName, password, dataString, location, gender, title, content should be sent from front end, Id should be generate by backend
 */

public class Message{
	
	public static String prefix = "message-";
	
	private String id;
	
	private String userName;
	
	private String password;
	
	private String dateString;
	
	private String location;
	
	private int gender;
	
	private String title;
	
	private String content;
	
	//for serializer/deSerializer
    private Message(){
    	this.id = "defaultId";
    	this.userName = "defaultUserName";
    	this.password = "defaultPassword";
    	this.dateString = "defaultDataString";
    	this.location = "defaultLocation";
    	this.gender = 0;
    	this.title = "defaultTitle";
    	this.content = "defaultContent";
    }
    
    private Message(String userName, String password, String dateString, String location, int gender, String title, String content){
    	this.generateId();
    	this.userName = userName;
    	this.password = password;
    	this.dateString = dateString;
    	this.location = location;
    	this.gender = gender;
    	this.title = title;
    	this.content = content;
    }
	
	private void generateId(){
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
		return this.location;
	}
	
	public int getGender(){
		return this.gender;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getContent(){
		return this.content;
	}
	
}

