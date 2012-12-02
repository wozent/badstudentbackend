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
		//   API for single messages:  /api/badstudent/v0.9/messages
		String messageResourcePrefix = "/messages";
		router.attach(applicationPrefix + versionPrefix + messageResourcePrefix, MessageResource.class);
		router.attach(applicationPrefix + versionPrefix + messageResourcePrefix + "/{id}", MessageResourceId.class);
		//   API for primary search:  /api/badstudent/v0.9/primarySearch
		String searchResourcePrefix = "/primarySearch";
		router.attach(applicationPrefix + versionPrefix + searchResourcePrefix, PrimarySearch.class);
		//   API for location resources:  /api/badstudent/v0.9/location
		String locationResourcePrefix = "/location";
		router.attach(applicationPrefix + versionPrefix + locationResourcePrefix, LocationResource.class);
		//   API for password authentication:  /api/badstudent/v0.9/auth,  note it is assumed that any message that would require authentication has an ID
		String authResourcePrefix = "/auth";
		router.attach(applicationPrefix + versionPrefix + authResourcePrefix, AuthResource.class);
		
		String testPrefix = "/test";
		router.attach(applicationPrefix + testPrefix, TestResource.class);
		
		
		
		return router;
	}
	
	
}