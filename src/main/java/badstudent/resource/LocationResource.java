package badstudent.resource;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;
import org.restlet.data.Form;
import org.restlet.data.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import badstudent.common.Common;
import badstudent.dbservice.*;
import badstudent.mappings.MappingManager;
import badstudent.model.*;


public class LocationResource extends ServerResource{

	private DaoService daoService = new DaoService();

	/*set the response header to allow for CORS*/
	public static Form addHeader(Form responseHeaders){
		if (responseHeaders == null) { 
			responseHeaders = new Form(); 
			responseHeaders.add("Access-Control-Allow-Origin", "*");
			responseHeaders.add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
			responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
			return responseHeaders;
		}
		return null;
	}
	
	
	/**
	 * take province and city from query parameter
	 * if there is no province, return all provinces
	 * if there is province but no city, return all cities under that province
	 * if there are both province and city, return all schools under that city
	 * @return
	 */
	@Get
	public Representation searchLocation() {
		//get query parameter _province _city
		String province = getQuery().getValues("province");
		String city = getQuery().getValues("city");
		List<String> searchResult = null;
		
		if (province != null && province.compareTo("") != 0 && city != null && city.compareTo("") != 0){
			try{
				Set<String> schools = MappingManager.getAllSchools(province, city);
				searchResult = new ArrayList<String>(schools);
			}
			catch(Exception e){
				e.printStackTrace();
				Common.d("invalid GETSCHOOL location query with parameter province: " + province + " city: " + city);
			}
		}
		else if (province != null && province.compareTo("") != 0){
			try{
				Set<String> cities = MappingManager.getAllCity(province);
				searchResult = new ArrayList<String>(cities);
			}
			catch(Exception e){
				e.printStackTrace();
				Common.d("invalid GETCITY location query with parameter province: " + province + " city: " + city);
			}
		}
		else if (province == null || province.compareTo("")== 0){
			Set<String> provinces = MappingManager.getAllProvince();
			searchResult = new ArrayList<String>(provinces);
		}
		else{
			Common.d("invalid location query format with parameter province: " + province + " city: " + city);
		}

		
		JSONArray jsonArray = new JSONArray(searchResult);
		
		try{
			for (int i = 0; i < jsonArray.length(); i++){
				jsonArray.getJSONObject(i).remove("messageIdentifier");
			}
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		
		Representation result = new JsonRepresentation(jsonArray);
		//set status
		setStatus(Status.SUCCESS_OK);
		
		try {
			System.out.println(result.getText() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("@Get::resources::LocationResource query parameters: province " + province + "city" + city);
		
		
		/*set the response header*/
		Form responseHeaders = addHeader((Form) getResponse().getAttributes().get("org.restlet.http.headers")); 
		if (responseHeaders != null){
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
		} 
		//TODO return result;
		return null;
	}


	
}

