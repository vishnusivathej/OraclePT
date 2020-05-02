import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadTable {
	
	private ArrayList<String> COLUMN_NAMES = null;
	private HashMap<Integer,String> DATA_TYPES = null;
	private Connection oraCon = null;
	private String OWNER = null;
	private String TABLE_NAME = null;
	private String LOAD_STATEMENT = null;
	private boolean PK_EXISTS = false;
	
	
	
	LoadTable(){
		try {
			oraCon = DBConnection.getOraConn();
		}
		catch (Exception E){
			E.printStackTrace();
		}
	}
	
	LoadTable(String A){
		try {
			oraCon = DBConnection.getOraConn();
			if (A.indexOf('.')==-1 || A.indexOf('.') == (A.length() - 1) || A.indexOf('.') == 0 ) {
				throw new Exception("Table name inconsistent use tablename as OWNER.TABLENAME");
			}
			else {
				OWNER = A.substring(0, A.indexOf('.')).toUpperCase();
				TABLE_NAME = A.substring(A.indexOf('.') + 1 , A.length() ).toUpperCase();
				if (checkTable()) {
					System.out.println("Table EXISTS!!");
					genLoadStatement();
					checkPK();
					
				}
				else {
					throw new Exception ("Table Does not exist!!!");
				}
			}
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	
	LoadTable(String A, int num_rows){
		
	}
	
	
	
	
	boolean checkTable() {
		try {
			Statement stmt = oraCon.createStatement();
			String SQL  = "select count(*) from dba_tables where owner='" + OWNER + "' and TABLE_NAME='" + TABLE_NAME + "'";
			ResultSet rs = stmt.executeQuery(SQL);
			int result = 0;
			while (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
			return result == 1 ? true : false;
		}
		catch(Exception E) {
			E.printStackTrace();
			return false;
		}
	}
	
	boolean genLoadStatement() {
		try {
			Statement stmt = oraCon.createStatement();
			String SQL = "select column_name, data_type, column_id from dba_tab_columns where owner='" + OWNER +"' and TABLE_NAME='" + TABLE_NAME + "'  order by column_id";
			System.out.println(SQL);
			ResultSet rs = stmt.executeQuery(SQL);
			COLUMN_NAMES = new ArrayList<>();
			DATA_TYPES = new HashMap<>();
			int NO_OF_COLUMNS = 0;
			while (rs.next()) {
				COLUMN_NAMES.add(rs.getString(1));
				DATA_TYPES.put(rs.getInt(3), rs.getString(2));
				NO_OF_COLUMNS++;
			}
			StringBuilder query = new StringBuilder("insert into ");
			query.append(OWNER + '.' + TABLE_NAME +'(');
			
			for (String S : COLUMN_NAMES) {
				query.append(S +",");
			}
			query.deleteCharAt(query.length()-1);
			query.append(") values (");
			for (int i = 0 ; i < NO_OF_COLUMNS ; i++) {
				query.append("?,");
			}
			query.deleteCharAt(query.length()-1);
			query.append(")");
			LOAD_STATEMENT = query.toString();
			rs.close();
			stmt.close();
			return true;
		}
		catch(Exception E) {
			E.printStackTrace();
			LOAD_STATEMENT = null;
			return false;
		}
	}
	
	boolean checkPK() {
		try {
			Statement stmt = oraCon.createStatement();
			String SQL = "select count(*) from dba_constraints where constraint_type='P' and owner='" + OWNER +"' and table_name='" + TABLE_NAME + "'";
			ResultSet rs = stmt.executeQuery(SQL);
			int result = 0;
			while (rs.next()) {
				result = rs.getInt(1);
			}
			if (result == 1) {
				PK_EXISTS = true;
			}
			else {
				return PK_EXISTS;
			}
			return PK_EXISTS;
			
		}
		catch(Exception E) {
			E.printStackTrace();
			return false;
		}
	}
	
	
	
}
