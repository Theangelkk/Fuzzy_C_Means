package Application;

import java.util.ArrayList;
import Clustering.Command_Clustering;
import Clustering.FCM_Fast;
import DataBase_Package.Connessione_DB;
import DataBase_Package.Estrazione_Dati_File;
import DataBase_Package.MySQL;
import DataBase_Package.Proxy_DataBase;
import DataSet.AdapterDS;
import DataSet.DataSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
* Questa Classe dispone di tutti i metodi per avviare una completa sessione all'utente.
* Risulta essere la parte principale del programma perchè definisce il comportamento di tutto l'applicativo
* gestendo anche la parte grafica.
* @author Angelo Casolaro
* @version 1.0
*/
public class Sessione {

	private Utente Utente_Loggato = null;
	private Connessione_DB Connessione = null;
	private Estrazione_Dati_File File = null;
	private Proxy_DataBase DB = null;
	private Controller_ListaDataSet Controller_ListDataset = null;
	private Controller_ListaClustering Controller_Clust = null;
	private DataSet OriginalDS = null;
	private ArrayList<FCM_Fast> List_FCM = null; 
	private Command_Clustering command_Clustering = null;
	
	public Sessione() 
	{
		Utente_Loggato = new Utente();
		List_FCM = new ArrayList<>();
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare l'istanza Utente.
	   * @return Istanza dell'Utente loggato.
	   */
	public Utente getUtente()
	{
		return Utente_Loggato;
	}
	
	/**
	   * Questo Metodo ha il compito di aprire il file DataSet scelto e di estrarre alcune informazioni ( es. Numero Fenomeni e Numero
	   * Caratteristiche ).
	   * @param Path Percorso Assoluto del File DataSet scelto.
	   * @return Numero Fenomeni e Numero Caratteristiche.
	   */
	public int[] Inserimento_File_DataSet( String Path )
	{
		int Elem[] = new int[2];
		
		File = new Estrazione_Dati_File(Path, ",");
		
		Elem[0] = File.getNumFen();
		Elem[1] = File.get_Num_Caratt();
		
		return Elem;
	}
	
	/**
	   * Questo Metodo ha il compito di creare e popolare il DataBase utilizzando il File DataSet scelto.
	   * @param user Nome Utente del DataBase
	   * @param Pass Password dell'utente del DataBase.
	   * @param Name_DB Nome del DataBase da utilizzare.
	   * @param NameTable Nome della Tabella da creare all'interno del DataBase.
	   * @return Valore booleano che indica se l'inserimento delle credenziali del DataBase sono corrette o meno.
	   */
	public boolean Crea_DataBase( String user, String Pass, String Name_DB, String NameTable )
	{
		Connessione = new MySQL();
		
		if( Connessione.Get_Connessione( user, Pass, Name_DB ) == null )
			return false;
		else
		{
			DB = new Proxy_DataBase( Connessione, NameTable, File );
			AdapterDS Adapt = new AdapterDS(DB);
			Adapt.Caricamento_DataSet( NameTable, File.get_Num_Caratt() );
			OriginalDS = Adapt.getDataSet();
			List_FCM.add( new FCM_Fast( Adapt.getDataSet(), 1, 0.001, 1000, 2 ) );
			return true;
		}
	}
	
	/**
	   * Questo Metodo ha il compito di creare un nuovo DataSet conentuto all'interno del File scelto.
	   * @param NameTable Nome della Tabella da creare all'interno del DataBase.
	   * @param File File DataSet da utilizzare per la costruzione del DataSet.
	   * @return Valore booleano che assume false se non esiste già un DataSet con lo stesso nome, oppure true in caso di esito
	   * positivo.
	   */
	public boolean Crea_DataSet( String NameTable, Estrazione_Dati_File File )
	{
		for( FCM_Fast F : List_FCM )
		{
			DataSet DS = F.getDataSet();
			if( DS.getNomeDataSet() == NameTable )
				return false;
		}
		
		DB = new Proxy_DataBase( Connessione, NameTable, File );
		AdapterDS Adapt = new AdapterDS(DB);
		Adapt.Caricamento_DataSet( NameTable, File.get_Num_Caratt() );
		
		List_FCM.add( new FCM_Fast( Adapt.getDataSet(), 1, 0.001, 1000, 2) );
		
		return true;
	}
	
	public void Carica_Stage_ListDataSet()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controller_ListaDataSet.class.getResource("Lista_DataSet.fxml"));
			BorderPane layout = loader.load();
			layout.getStylesheets().add(Controller_ListaDataSet.class.getResource("Style_Lista_DataSet.css").toExternalForm());
			Controller_ListDataset = loader.getController();
			Controller_ListDataset.setInit( layout );
			RicaricaDataSet();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private void RicaricaDataSet()
	{
		Controller_ListDataset.InizializzaTable();
		
		for( FCM_Fast F : List_FCM )
			Controller_ListDataset.addDataSet( F.getDataSet().getNomeDataSet() );
	}
	
	/**
	   * Questo Metodo ha il compito di restituire il DataSet indicato dal nome in Input.
	   * @param N Nome del DataSet da ricercare e restituire.
	   * @return DataSet corrispondente al nome Inserito in Input, null in caso di esito negativo.
	   */
	public DataSet getDataSet_Name( String N )
	{
		if( N.length() == 0 )
			return OriginalDS;
		
		for( FCM_Fast F : List_FCM )
		{
			DataSet DS = F.getDataSet();
			if( DS.getNomeDataSet() == N )
				return DS;
		}
		
		return null;
	}
	
	public FCM_Fast get_FCM( String N )
	{
		for( FCM_Fast F : List_FCM )
		{
			DataSet DS = F.getDataSet();
			if( DS.getNomeDataSet() == N )
				return F;
		}
		
		return null;
	}
	
	/**
	   * Questo Metodo ha il compito di eliminare il DataSet indicato dal nome in Input.
	   * @param N Nome del DataSet da eliminare.
	   */
	public void EliminaDataSet( String N )
	{
		FCM_Fast Elim = null;
		
		if( List_FCM.size() == 1 )
			return;
		
		for( FCM_Fast F : List_FCM )
		{
			DataSet DS = F.getDataSet();
			if( DS.getNomeDataSet() == N )
				Elim = F;
		}
		
		if( Elim != null )
		{
			if(  List_FCM.remove(Elim) == true )
				RicaricaDataSet();
		}
	}
	
	public boolean SalvaDataSet( DataSet DS, String N )
	{
		FCM_Fast Ric = null;
		
		for( FCM_Fast F : List_FCM )
		{
			DataSet D = F.getDataSet();
			if( D.getNomeDataSet() == N )
				Ric = F;
		}
		
		if( Ric != null )
			return false;
		else
		{
			DS.setNomeDataSet(N);
			List_FCM.add( new FCM_Fast(DS, 1, 0.001, 1000, 2) );
			RicaricaDataSet();
			return true;
		}
	}
	
	/**
	   * Questo Metodo ha il compito di cambiare i paramentri di un algoritmo di Clustering indicato dal Nome del DataSet inserito.
	   * @param NomeDataSet Nome del DataSet da ricercare e cambiare i paramentri di clustering.
	   * @param Num_Cluster Nuovo Numero Cluster da settare.
	   * @param Eps Nuovo valore di arresto da settare.
	   * @param Iter Nuovo numero massimo di iterazioni da settare.
	   */
	public void Change_Param_Clust( String NomeDataSet, int Num_Cluster, double Eps, int Iter )
	{
		for( FCM_Fast F : List_FCM )
		{
			DataSet DS = F.getDataSet();
			if( DS.getNomeDataSet() == NomeDataSet )
			{
				F.InserisciNumClass(Num_Cluster);
				F.setEpsilon(Eps);
				F.setMaxIter(Iter);
			
				break;
			}
		}
	}
	
	/**
	   * Questo Metodo ha il compito di avviare l'algoritmo di Clustering per ogni DataSet inserito.
	   */
	public void AvviaClustering()
	{
		command_Clustering = new Command_Clustering();
		
		for( FCM_Fast F : List_FCM )
			command_Clustering.Inserisci_Class(F);
		
		command_Clustering.Esegui_Classificazioni();
		
	}
	
	public void Carica_Stage_ListClustering()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Controller_ListaClustering.class.getResource("Lista_Clustering.fxml"));
			BorderPane layout = loader.load();
			layout.getStylesheets().add(Controller_ListaClustering.class.getResource("Style_Lista_DataSet.css").toExternalForm());
			Controller_Clust = loader.getController();
			Controller_Clust.setInit( layout );
			CaricaListClustering();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void SalvaClustering()
	{
		if( List_FCM.size() > 0 )
		{
			for( FCM_Fast F : List_FCM )
			{
				DataSet DS = F.getDataSet();
				AdapterDS Adapt = new AdapterDS( DS );
				Adapt.SalvaRisultati( DB, DS.getNomeDataSet() );
			}
		}
	}
	
	private void CaricaListClustering()
	{
		Controller_Clust.InizializzaTable();
		
		for( FCM_Fast F : List_FCM )
			Controller_Clust.addDataSet( F.getDataSet().getNomeDataSet(), F );
	}
}