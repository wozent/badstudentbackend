package badstudent.dao.service;

import java.util.List;
import java.util.Set;

import badstudent.model.*;
import badstudent.dao.resource.*;

public class DaoService{
	
	//private static Log log = LogFactory.getLog(ScheduleResource.class);
	private Dao dao;
	private Set<String> ids;
	
	public DaoService(){
		this.dao = new Dao();
	}
	
	/*checks if the id exist in Redis*/
	public boolean checkExistance(String id){
		if (dao.getMessageById(id) == null){
			return false;
		}
		return true;
	}
}
