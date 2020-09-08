package DataBase_Package;

import java.sql.Connection;

public interface Connessione_DB 
{
	 public Connection Get_Connessione( String user, String pass, String DB );	
}
