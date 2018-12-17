package com.rimi.project.util;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class XBasicDataSource extends BasicDataSource{


	@Override
    public synchronized void close() throws SQLException {
		
		DriverManager.deregisterDriver(DriverManager.getDriver(this.getDriverClassName()));
		AbandonedConnectionCleanupThread.checkedShutdown();
        super.close();
    }
}
