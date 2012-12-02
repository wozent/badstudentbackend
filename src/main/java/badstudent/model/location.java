package badstudent.model;

/**
 * Important: please note, each entry here much not contain white space
 */


public class Location {
    private String province;
    private String city;
    private String region;
    private String school;
    
    public Location(){
    	this.province = "province";
        this.city = "city";
        this.region = "region";
        this.school = "schoolofblablabla";
    }
    
    public Location(String province, String city ,String region ,String school){
        this.province = province;
        this.city = city;
        this.region = region;
        this.school = school.replaceAll(" ", "");
    }

    //for locations that does not have region
    public Location(String province, String city ,String school){
        this.province = province;
        this.city = city;
        this.region = null;
        this.school = school.replaceAll(" ", "");
    }

    public Location(String locationString){
        String result[] = locationString.split(" ");
        this.province = result[0];
        this.city = result[1];
        this.school = result[2].replaceAll(" ", "");
    }
    
    public boolean sameLocation(Location location){
    	return (this.province.compareTo(location.getProvince()) == 0) && (this.city.compareTo(location.getCity()) ==0) && (this.school.compareTo(location.getCity()) == 0);
    }
    
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        if(this.region==null){
            return "NULL";
        }
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String toString(){
        String retVal = this.getProvince() + " " + this.getCity() + " " + this.getRegion() + " " + this.getSchool();
        return retVal;
    }

}
