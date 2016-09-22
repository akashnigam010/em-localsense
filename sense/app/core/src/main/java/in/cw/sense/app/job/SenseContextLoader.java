package in.cw.sense.app.job;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class SenseContextLoader extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Loading context... ");
		super.contextInitialized(event);
	}
}
