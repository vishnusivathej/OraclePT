import java.sql.Connection;
import java.sql.DriverManager;

import oracle.jdbc.OracleConnection;

class DBConnection {
	
	
	
		static Connection getFailedCon() {
			try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                return DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(SDU=32767)(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.21)(PORT=1521))(CONNECT_DATA=(SID=noncdb)(SERVER=DEDICATED)))","asdf","asdf");
			}
        catch(Exception E) {
        	System.out.println("Failed Once");
               return null;
        	}
        }
			
			
		
        static Connection getOraConn() {
                try {
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        return DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(SDU=32767)(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.21)(PORT=1521))(CONNECT_DATA=(SID=noncdb)(SERVER=DEDICATED)))","vishnu","oracle");
                }
                catch(Exception E) {
                        if (E.toString().contains("ClassNotFoundException")) {
                                System.out.println("Java Driver not found");
                                System.out.println("Please Download Oracle JDBC driver(ojdbc10.jar) and place in $JRE_HOME/lib/ext");
                        }
                        else {
                        	E.printStackTrace();
                        }
                        return null;
                }
        } 
        static Connection getOraSysConn() {
            try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    return DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.21:1521:noncdb","sys as sysdba","oracle");
            }
            catch(Exception E) {
                    if (E.toString().contains("ClassNotFoundException")) {
                            System.out.println("Java Driver not found");
                            System.out.println("Please Download Oracle JDBC driver(ojdbc10.jar) and place in $JRE_HOME/lib/ext");
                    }
                    else {
                    	E.printStackTrace();
                    }
                    return null;
            }
    } 
}
