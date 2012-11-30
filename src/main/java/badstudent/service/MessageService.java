package badstudent.service;


import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import badstudent.common.Common;
import badstudent.resource.*;


public class MessageService extends Application {
	
	public MessageService() {
		super();
	}

	public MessageService(Context context) {
		super(context);
	}
	
	//@Override
	public synchronized Restlet createInboundRoot(){
		Common.d("initiaing router");
		Router router = new Router(getContext());
		String applicationPrefix = "/api/badstudent";
		String versionPrefix = "/v0.9";
		String messageResourcesPrefix = "/messages";
		router.attach(applicationPrefix + versionPrefix + messageResourcesPrefix, MessageResource.class);
		router.attach(applicationPrefix + versionPrefix + messageResourcesPrefix + "/{id}", MessageResourceId.class);
		
		String searchResourcesPrefix = "/primarySearch";
		router.attach(applicationPrefix + versionPrefix + searchResourcesPrefix, PrimarySearch.class);
		
		return router;
	}
	
	
}