package DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataBase_Package.Proxy_DataBase;

/**
* Questa Classe permette l'interazione tra DataBase e DataSet attualmente in memoria.
* Attraverso l'utilizzo dei suoi metodi, � possibile il salvataggio e caricamento di un dataSet
* @author Angelo Casolaro
* @version 1.0
*/
public class AdapterDS
{
	private Proxy_DataBase dataBase = null;
	private DataSet new_DataSet = null;
	
	public AdapterDS( Proxy_DataBase D )
	{
		dataBase = D;
	}
	
	public AdapterDS( DataSet DS )
	{
		new_DataSet = DS;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare il caricamento dal DataBase.
	   * @param NameTable Nome della Tabella di dove � contenuto il DataSet.
	   * @param Num_Caratt Numero delle Caratteristiche dei Fenomeni. 
	   */
	public void Caricamento_DataSet( String NameTable, int Num_Caratt )
	{
		// Andiamo a creare un nuovo DataSet
		new_DataSet = new DataSet( NameTable, Num_Caratt);
		
		// Andiamo ad estrarre tutti i Fenomeni
		String SQL = "SELECT * FROM " +  NameTable + ";";

		try
		{		
			ResultSet Risultato = dataBase.Query(SQL);
			
			// Fin quando abbiamo Fenomeni da analizzare ed inserire nel DataSet
			while( Risultato.next() )
			{	
				Fenomeno<Double> NewFenomeno = new Real_Fenomeno<Double>(new_DataSet);
				
				// Dato che il primo elemento della nostra tabella risulta essere l'identificato/chiave 
				// partiamo dal secondo elemento
				for( int i = 2; i <= Num_Caratt+1; i++ )
				{
					double value = Float.parseFloat(Risultato.getString(i));
					NewFenomeno.InserisciNewCaratt(value);
				}
				
				// Si va a creare un nuovo Fenomeno e lo si aggiunge al DataSet
				new_DataSet.New_Fenomeno(NewFenomeno);
			}
		}
		catch( SQLException e )
		{
			System.out.println("Estrazione Fenomeni dalla Tabella Fallita");
		}
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare il DataSet creato
	   * @return DataSet appena caricato da DataBase.
	   */
	public DataSet getDataSet()
	{
		return new_DataSet;
	}
	
	/**
	   * Questo Metodo ha il compito di settare un DataSet.
	   * @param DS Nuovo DataSet che deve essere settato.
	   */
	public void setDataSet( DataSet DS )
	{
		new_DataSet = DS;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare il salvataggio dell'intero DataSet, completo di clustering,
	   * in una Tabella del DataBase attualmente utilizzato.
	   * @param DB DataBase che deve contenere la Tabella del DataSet.
	   * @param NameTable Nome della Tabella del DataSet.
	   */
	@SuppressWarnings("unchecked")
	public void SalvaRisultati( Proxy_DataBase DB, String NameTable )
	{
		boolean Risultato = true;
		
		dataBase = DB;
		
		// Andiamo sempre a verificare eventualmente l'esistenza di una Tabella con ugual nome...
		// in caso affermativo viene eliminata.
		dataBase.VerifyExistTable(NameTable);
		
		// Creazione Tabella		
		String Create_Table = "CREATE TABLE " + NameTable + " (N_FENOMENO INT PRIMARY KEY AUTO_INCREMENT,";
		
		int Num_Caratt = new_DataSet.getNumCar();		// Andiamo a prendere il numero di Caratteristiche 
		
		// Preparazione della query da eseguire per effettuare il popolamento.
		for( int i=0; i<Num_Caratt; i++ )
		{
			String NomeCaratt = "CARATTERISTICA_";
			NomeCaratt += (i+1);
			Create_Table += NomeCaratt + " DOUBLE";
			Create_Table += ",";
		}
		
		String TMP = "CLUSTER INT, GRADO DOUBLE ";
		Create_Table += TMP;
		Create_Table += ");";
		
		// Si va ad eseguire la Query per la creazione della Tabella
		DB.UpdateRecord(Create_Table);
		
		if( Risultato )
		{	
			// Popolamento Tabella con tutte le Caratteristiche dei Fenomeni			
			String Insert = "INSERT INTO " + NameTable + " (";
			
			// Preparazione Insert Query
			for( int i=0; i<Num_Caratt; i++ )
			{
				String NomeCaratt = "CARATTERISTICA_";
				NomeCaratt += (i+1);
				Insert += NomeCaratt;
				Insert += ",";
			}
			
			String Other = "CLUSTER,GRADO)";
			Insert += Other;
			int i = 0;
			
			Fenomeno<Double> Fen = null;
			
			// Fin quando abbiamo Fenomeni da inserire nel nostro DataBase...
			while( ( Fen = (Fenomeno<Double>) new_DataSet.getFenomeno(i) ) != null )
			{	
				i++;
				
				String InsertTMP = Insert;
				InsertTMP += " VALUES ( ";
				
				for( int j=0; j<Num_Caratt; j++ )
				{
					double value = 0.0;
				
					// Si vanno ad inserire tutti i Valori di ogni singola Caratteristiche nella Query SQL
					value = Fen.getCaratteristica(j);
					InsertTMP += value;
					InsertTMP += ",";
					
				}
				
				InsertTMP = InsertTMP + Fen.getCluster() + "," + Fen.getGrado();
				InsertTMP += " )";
				
				// Esecuzione Inserimento
				dataBase.InserimentoRecord(InsertTMP);
			}
		}
	}
}
