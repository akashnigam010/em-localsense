package in.cw.sense.app.job;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

public class SenseContextLoader extends ContextLoaderListener {
	private static final Logger LOG = Logger.getLogger(SenseContextLoader.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		LOG.debug("Loading context... ");
		super.contextInitialized(event);
	}
}
