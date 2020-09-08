package DataBase_Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* Questa Classe permette di effettuare operazioni su di un specifico DataBase ( es. MySQL - Oracle )
* @author Angelo Casolaro
* @version 1.0
*/
public class Real_DataBase implements Operazioni_DataBase
{
	private Connessione_DB DataBase = null;
	
	public Real_DataBase() 
	{
		DataBase = null;
	}
	
	public Real_DataBase( Connessione_DB D )
	{
		DataBase = D;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare l'inserimento di un Record o di una Tabella all'interno del DataBase preso
	   * in getione.
	   * @param Sql Stringa SQL che deve essere eseguita sul DataBase
	   */
	@Override
	public void InserimentoRecord(String Sql) 
	{	
		try
		{
			// Dato che la connessione risulta essere già pronta... forniamo parametri vuoti per avere solo
			// la restituzione della connessione già creata.
			Statement statement = DataBase.Get_Connessione( "", "", "" ).createStatement();
			statement.executeUpdate(Sql);
		}
		catch( SQLException e )
		{
			System.out.println("Inserimento Valori Record Errati !!!");
		}
	}

	/**
	   * Questo Metodo ha il compito di effettuare una Query, fornita in Input, e di restituire la Tabella ottenuta 
	   * dall'interrogazione effettutata.
	   * @param Sql Stringa SQL che deve essere eseguita sul DataBase
	   * @return Tabella risultato ottenuta dalla Query SQL, null se non � stato ottenuto risultato dalla Query.
	   */
	@Override
	public ResultSet Query(String Sql) 
	{	
		ResultSet Table = null;
		
		try
		{
			// Dato che la connessione risulta essere già pronta... forniamo parametri vuoti per avere solo
			// la restituzione della connessione già creata.
			Statement statement = DataBase.Get_Connessione( "", "", "" ).createStatement();
			Table = statement.executeQuery(Sql);
		}
		catch( Exception e )
		{	
			System.out.println("Errore Immissione SQL Query");
		}
		
		return Table;
	}

	/**
	   * Questo Metodo ha il compito di effettuare l'aggiornamento di un Record all'interno del DataBase preso
	   * in getione.
	   * @param Sql Stringa SQL che deve essere eseguita sul DataBase
	   */
	@Override
	public void UpdateRecord(String Sql) 
	{	
		try
		{
			// Dato che la connessione risulta essere già pronta... forniamo parametri vuoti per avere solo
			// la restituzione della connessione già creata.
			Statement statement = DataBase.Get_Connessione( "", "", "" ).createStatement();
			statement.executeUpdate(Sql);
		}
		catch( SQLException e )
		{
			System.out.println("Errore Immissione Query for Update");
		}	
	}
}
