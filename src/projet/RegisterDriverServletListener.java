package projet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.sql.Driver;

/**
 * Web application lifecycle listener.
 * 
 * @author rbastide
 */
public class RegisterDriverServletListener implements ServletContextListener {
	private final Logger logger = Logger
			.getLogger(RegisterDriverServletListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			// Register your drivers here
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		Driver d = null;
		while (drivers.hasMoreElements()) {
			try {
				d = drivers.nextElement();
				DriverManager.deregisterDriver(d);
				logger.log(Level.INFO,
						String.format("Driver %s deregistered", d));
			} catch (SQLException ex) {
				logger.log(Level.SEVERE,
						String.format("Error deregistering driver %s", d), ex);
			}
		}
	}
}
