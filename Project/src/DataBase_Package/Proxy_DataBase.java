package DataBase_Package;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
* Questa Classe permette di effettuare operazioni su di un specifico DataBase ( es. MySQL - Oracle ) dove la sua creazione
* e popolamento � stato preso da File ( es. CSV o TXT )
* @author Angelo Casolaro
* @version 1.0
*/
public class Proxy_DataBase implements Operazioni_DataBase
{
	private Real_DataBase DataBase = null;
	
	public Proxy_DataBase() 
	{
		DataBase = null;
	}
	
	public Proxy_DataBase( Connessione_DB Conn )
	{
		DataBase = new Real_DataBase(Conn);
	}
	
	public Proxy_DataBase( Connessione_DB Conn, String NameTable, Estrazione_Dati_File File )
	{
		DataBase = new Real_DataBase(Conn);
		
		// Andiamo ad effettuare la creazione e popolamento del DataBase appena creato.
		Costruzione_PopolamentoDB(File,NameTable);
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la creazione e il popolamento delle Tabella all'interno del DataBase 
	   * preso in gestione, secondo lo schema di suddivisione fatto sul file fornito in Input.
	   * @param File Oggetto che contiene tutte le informazioni e metodi necessari per la gestione del File caricato
	   * @param NameTable Nome della Tabella da creare e popolare.
	   */
	private void Costruzione_PopolamentoDB( Estrazione_Dati_File File, String NameTable )
	{	
		// Si va a verificare l'esistenza di una Tabella con ugual nome fornito in Input
		VerifyExistTable( NameTable );
		
		// Creazione Tabella dei Fenomeni
		String Create_Table = "CREATE TABLE " + NameTable + " (N_FENOMENO INT PRIMARY KEY AUTO_INCREMENT,";
		
		int Num_Caratt = File.get_Num_Caratt();		// Andiamo a prendere il numero di Caratteristiche 
		
		// Si passa alla formulazione della query per la creazione della Tabella.
		for( int i=0; i<Num_Caratt; i++ )
		{
			String NomeCaratt = "CARATTERISTICA_";
			NomeCaratt += (i+1);
			Create_Table += NomeCaratt + " DOUBLE";
			
			if( i+1 < Num_Caratt )
				Create_Table += ",";
		}
		
		Create_Table += ")";
		
		// Si va ad eseguire la Query per la creazione della Tabella
		DataBase.UpdateRecord(Create_Table);
			
		// Popolamento Tabella con tutte le Caratteristiche dei Fenomeni
		ArrayList<Double> Caratt_Fenomeno = null;
			
		String Insert = "INSERT INTO " + NameTable + " (";
			
		// Preparazione della Query per l'inserimento dei Dati
		for( int i=0; i<Num_Caratt; i++ )
		{
			String NomeCaratt = "CARATTERISTICA_";
			NomeCaratt += (i+1);
			Insert += NomeCaratt;
				
			if( i+1 < Num_Caratt )
				Insert += ",";
		}
			
		Insert += ")";
				
		// Fin quando non abbiamo inserito tutti i fenomeni...
		while( (Caratt_Fenomeno = File.get_NextFenomeno()) != null )
		{
			// Si passa alla costruzione di ogni querty per ogni fenomeno...
			// in questo modo stiamo popolando la tabella
			String InsertTMP = Insert;
			boolean Esito = true;
			double value = 0.0;
				
			InsertTMP += " VALUES ( ";
				
			// Si vanno ad inserire tutti i Valori di ogni singola Caratteristiche nella Query SQL
			for( int i=0; i<Num_Caratt; i++ )
			{
				try
				{
					value = Caratt_Fenomeno.get(i);
				}
				catch( IndexOutOfBoundsException e )
				{
					Esito = false;
				}
					
				InsertTMP += value;
				
				if( i+1 < Num_Caratt )
					InsertTMP += ",";
			}
				
			InsertTMP += " )";
				
			if( Esito )
				DataBase.InserimentoRecord(InsertTMP);
		}
	}
	
	/**
	   * Questo Metodo ha il compito di verificare se un determinata Tabella esiste o meno all'interno del DataBase.
	   * In caso affermativo viene eliminata.
	   * @param NameTable Nome della Tabella da ricercare.
	   */
	public void VerifyExistTable( String NameTable )
	{
		String Query = "SHOW TABLES LIKE '" + NameTable + "';";
		
		// Andiamo ad eseguire la query che ci mostra le Tabelle presenti nel DataBase
		// con lo stesso Nome fornito in Input.
		ResultSet Risultato = DataBase.Query(Query);
		
		try
		{
			// Se abbiamo risultato, vuol dire che esiste una stessa tabella con ugual nome...
			// allora passiamo alla sua distruzione.
			if( Risultato.next() )
			{
				String DropTable = " DROP TABLE " + NameTable + ";";
				DataBase.InserimentoRecord(DropTable);
			}
		}
		catch( SQLException e )
		{
			System.out.println("Errore nella Query di Esistenza della Tabella");
		}
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare l'inserimento di un Record o di una Tabella all'interno del DataBase preso
	   * in getione.
	   * @param Sql Stringa SQL che deve essere eseguita sul DataBase
	   */
	@Override
	public void InserimentoRecord(String Sql) 
	{
		if( DataBase != null )
			DataBase.InserimentoRecord(Sql);
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
		if( DataBase == null )
			return null;
		else
			return DataBase.Query(Sql);
	}

	/**
	   * Questo Metodo ha il compito di effettuare l'aggiornamento di un Record all'interno del DataBase preso
	   * in getione.
	   * @param Sql Stringa SQL che deve essere eseguita sul DataBase
	   */
	@Override
	public void UpdateRecord(String Sql) 
	{
		if( DataBase != null )
			DataBase.UpdateRecord(Sql);
	}
}
