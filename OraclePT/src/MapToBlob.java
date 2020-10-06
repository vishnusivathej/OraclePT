import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class MapToBlob {
	
	void loadBlob(){
		try {
			Connection oraCon = DBConnection.getOraConn();
			PreparedStatement pstmt = oraCon.prepareStatement("insert into bloob (t1, t2) values (?,?)");
			for (int i = 0 ; i < 10000; i++) {
				pstmt.setInt(1,oraSequence.nextVal());
				pstmt.setBlob(2, new ByteArrayInputStream(genBytes()));
				pstmt.executeUpdate();
			}
			pstmt.close();
			oraCon.close();
			
			
		}
		catch(Exception E) {
			E.printStackTrace();
		}
	}
	
	void getMap(){
		try {
			Connection getOra = DBConnection.getOraConn();
			Statement stmt = getOra.createStatement();
			String SQL = "select t2 from bloob where t1=123";
			ResultSet rs = stmt.executeQuery(SQL);
			Blob blob = null;
			while (rs.next()) {
				blob = rs.getBlob(1);
			}
			byte [] bytes = blob.getBytes(1l, (int)blob.length());
			ByteArrayInputStream byteInstream = new ByteArrayInputStream(bytes);
		    ObjectInputStream objectInStream;
		    
		    try {
		        objectInStream = new ObjectInputStream(byteInstream);
		        @SuppressWarnings("unchecked")
				HashMap<String,String> data  = (HashMap<String, String>)objectInStream.readObject();
		        objectInStream.close();
		        for (String s : data.keySet()) {
		        	System.out.println(s + " ---- " + data.get(s));
		        }

		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }

		}
		catch(Exception E) {
			
		}
		
	}
	
	byte[] genBytes() {
		byte[] bytearray = null;
	    ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();

	    ObjectOutputStream objectOutStream;
	    try {
	        objectOutStream = new ObjectOutputStream(byteOutStream);
	        objectOutStream.writeObject(genMap());
	        bytearray = byteOutStream.toByteArray();
	        objectOutStream.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println(bytearray.length);
	    return bytearray;
	}
	
	HashMap<String, String> genMap() {
		HashMap<String,String> asdf = new HashMap<>();
		for (int i = 0 ; i < 1000; i++) {
			asdf.put("T"+i,OraRandom.randomString(100));
		}
		return asdf;
	}
}
