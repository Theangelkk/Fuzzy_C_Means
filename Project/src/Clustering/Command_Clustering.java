package Clustering;

import java.util.ArrayList;
import DataSet.DataSet;

/**
* Questa Classe permette di incapsulare tutte le richieste fatte su i vari DataSet e di applicare gli algoritmi
* di Clustering voluti in maniera concorrente.
* @author Angelo Casolaro
* @version 1.0
*/
public class Command_Clustering 
{
	private ArrayList<Classificatori> Class_DataSet = null;
	
	public Command_Clustering() 
	{
		Class_DataSet = new ArrayList<>();
	}
	
	/**
	   * Questo Metodo ha il compito di inserire un nuovo Classificatore
	   * @param C Nuovo Classificatore.
	   */
	public void Inserisci_Class( Classificatori C )
	{
		Class_DataSet.add(C);
	}
	
	/**
	   * Questo Metodo ha il compito di eliminare l'i-esimo Classificatore
	   * @param i I-esimo Classificatore da eliminare.
	   */
	public void Elimina_Class( int i )
	{
		if( i >= 0 && i < Class_DataSet.size() )
			Class_DataSet.remove(i);
	}
	
	/**
	   * Questo Metodo ha il compito di avviare per ogni Classificatore il proprio algoritmo di Clustering.
	   */
	public void Esegui_Classificazioni()
	{
		// Viene creato una ArrayList di Thread_Clustering in modo tale che dopo è
		// possibile su ogni istanza creata effettuare il join
		ArrayList<Thread_Clustering> thrArrayList = new ArrayList<>();
		
		// Per ogni Classificatore di cui si deve avviare il proprio algoritmo di clustering...
		// Viene creato un Thread apposito che si preoccupera' della sua esecuzione in maniera concorrente
		for( Classificatori C : Class_DataSet )
		{
			Thread_Clustering Thread_new = new Thread_Clustering(C); 
			thrArrayList.add( Thread_new );
			Thread_new.run();
		}
		
		try
		{
			// Attendiamo la terminazione di tutti i Thread
			for( Thread_Clustering T : thrArrayList )
				T.join();
		}
		catch( InterruptedException exception )
		{
			this.Esegui_Classificazioni();
		}
	}
	
	/**
	   * Questo Metodo ha il compito di restituire l'i-esimo DataSet di un Classificatore.
	   * @param i I-esimo DataSet da restituire.
	   * @return I-esimo Dataset oppure null in caso di non esistenza.
	   */
	public DataSet getDataSet( int i )
	{
		if( i >= 0 && i < Class_DataSet.size() )
		{
			return Class_DataSet.get(i).getDataSet();
		}
		else
			return null;
	}
	
	/**
	   * Questo Metodo ha il compito di verificare l'esistenza di un Classificatore con lo stesso.
	   * @param DS Nuovo DataSet da inserire.
	   * @return Valore booleano che indica true se esiste gi� un DataSet con lo stesso nome, oppure false in caso contrario.
	   */
	public boolean Check_Exist_DS( DataSet DS )
	{
		for( Classificatori C : Class_DataSet )
			if( C.getDataSet() == DS )
				return true;
		
		return false;
	}
}

class Thread_Clustering extends Thread
{
	private Classificatori Class = null;
	
	public Thread_Clustering( Classificatori C ) 
	{
		Class = C;
	}
	
	@Override
	public void run() 
	{
		Class.AlgorithmClustering();
	}
}