package badstudent.serverMain;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

import badstudent.common.Common;
import badstudent.service.*;


public class ServerMain {

	//private static Log log = LogFactory.getLog(ServiceMain.class);

	private static ServerMain me;

	private Component component;

	public void init(String[] arguments) {

	}

	/**
	 * Start the Thread, accept incoming connections
	 * 
	 * Use this entry point to start with embedded HTTP Server
	 * 
	 * @throws Exception
	 */
	public void start() throws Exception {
		component = new Component();

		// Add a new HTTP server listening on port

		Server server = component.getServers().add(Protocol.HTTP, 8015);
		server.getContext().getParameters().add("maxThreads", "128");

		// Attach the sample application.

		MessageService messageService = new MessageService();

		component.getDefaultHost().attach(messageService);

		// Start the component.
		//log.info("ready to start");
		Common.d("ready to start");
		component.start();

	}

	/**
	 * Stops RESTlet application
	 */
	public void stop() {
		component.getDefaultHost().detach(component.getApplication());
	}

	public static ServerMain getInstance() {
		if (me == null) {
			me = new ServerMain();
		}

		return me;
	}
	


	public static void main(String... args) throws Exception {
		Common.d("Excuting");
		// Load server logic
		try {
			ServerMain.getInstance().init(args);
			ServerMain.getInstance().start();
		} catch (Exception e) {
			//log.error("Failed to start server", e);
		}
		
	}

}
