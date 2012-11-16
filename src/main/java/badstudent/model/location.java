package badstudent.model;

public class location {
    private String province;
    private String city;
    private String region;
    private String school;

    public location(String province, String city ,String region ,String school){
        this.province = province;
        this.city = city;
        this.region = region;
        this.school = school;
    }

    //for locations that does not have region
    public location(String province, String city ,String school){
        this.province = province;
        this.city = city;
        this.region = null;
        this.school = school;
    }

    public location(String locationString){
        String result[] = locationString.split(" ");
        this.province = result[0];
        this.city = result[1];
        if(result[2].equalsIgnoreCase("NULL")){
            this.region = result[2];
        }else{
            this.region = null;
        }
        this.school = result[4];
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
