package DataBase_Package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* Questa Classe gestisce una Connessione con un DataBase MySQL.
* @author Angelo Casolaro
* @version 1.0
*/
public class MySQL implements Connessione_DB
{
	private static Connection connessioneDB = null;
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String Username;
	private static String Password;
	@SuppressWarnings("unused")
	private static String DataBase;
	
	/**
	   * Questo Metodo ha il compito di restituisce una Connessione ad un DataBase MySQL, nel caso di crearla utilizzando
	   * le credenziali fornite in Input.
	   * @param user Username del DataBase MySQL
	   * @param pass Password dello username del DataBase MySQL
	   * @param DB Nome del DataBase a cui ci dobbiamo connettere.
	   * @return Connessione al DataBase
	   */
	@Override
	public Connection Get_Connessione( String user, String pass, String DB ) 
	{
		// Viene creata una connessione se e solo se non è già stata creata un in precedenza
		if( connessioneDB == null )
		{
			Username = user;
			Password = pass;
			DataBase = DB;
			
			try
			{
				Class.forName(DRIVER);
			}
			catch( ClassNotFoundException e )
			{
				e.printStackTrace();
			}
			
			try
			{
				// Andiamo a creare la connessione inserendo i Parametri forniti in Input
				connessioneDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB + "?user=" + Username + "&password=" + Password + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
			}
			catch( SQLException e )
			{
				System.out.println("Errore Connessione DataBase MySQL");
				connessioneDB = null;
			}
		}
		
		return connessioneDB;
	}

}
