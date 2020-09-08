package DataSet;

import java.util.ArrayList;

/**
* Questa Classe permette la gestione del DataSet in memoria.
* Risulta essere un contenitore per tutti i Fenomeni e consente la loro gestione attraverso specifici metodi.
* @author Angelo Casolaro
* @version 1.0
*/
public class DataSet
{
	private String NomeDataSet = "";
	private ArrayList<Fenomeno<?>> AllFenomeni = null;
	private int Num_Caratteristiche = 0;
	private State Stato_Attuale = null;
	private int Numero_Fenomeni = 0;
	
	public DataSet( String N, int Num )
	{
		Num_Caratteristiche = Num;
		setNomeDataSet(N);
		AllFenomeni = new ArrayList<>();
		Stato_Attuale = new Normal_State();
		Numero_Fenomeni = 0;
	}

	/**
	   * Questo Metodo ha il compito di effettuare l'inserimento di un nuovo Fenomeno
	   * @param newFenomeno Nuovo Fenomeno da inserire nella Lista di Fenomeni
	   */
	public void New_Fenomeno( Fenomeno<?> newFenomeno )
	{
		AllFenomeni.add(newFenomeno);
		Numero_Fenomeni++;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare l'eliminazione di un Fenomeno nella i-esima posizione.
	   * @param i Posizione del Fenomeno.
	   */
	public void Delete_Fenomeno( int i )
	{
		if( i >= 0 && i < AllFenomeni.size() )
		{
			AllFenomeni.remove(i);
			Numero_Fenomeni--;
		}
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la restituizione dell'i-esimo Fenomeno.
	   * @param i Posizione del Fenomeno.
	   * @return Fenomeno posto nella i-esima posizione.
	   */
	public Fenomeno<?> getFenomeno( int i )
	{
		if( i >= 0 && i < AllFenomeni.size() )
			return AllFenomeni.get(i);
		else
			return null;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare il ritorno allo stato precedente di tutto il DataSet.
	   */
	public void Ritorna_Stato_Prec()
	{
		Stato_Attuale = new Stato_Precedente();
		NotifyAll();
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare l'eliminazione di una i-esima Caratteristica a tutti i Fenomeni.
	   * @param i Caratteristica i-esima del Fenomeno.
	   */
	public void Delete_Caratteristica( int i )
	{
		if( Num_Caratteristiche > 0 )
		{
			Stato_Attuale = new Elimina_Caratt(i);
			Num_Caratteristiche--;
			NotifyAll();
		}
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la notifica di variazione di stato per tutti i Fenomeni.
	   */
	public void NotifyAll()
	{
		for( Fenomeno<?> Fen : AllFenomeni )
			Fen.Aggiornamento();
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la restituzione dello stato attuale del DataSet
	   * @return Stato attuale del DataSet
	   */
	public State getStato()
	{
		return Stato_Attuale;
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare il numero di caratteristiche di un Fenomeno.
	   * @return Numero di Caratteristiche del DataSet.
	   */
	public int getNumCar()
	{
		return Num_Caratteristiche;
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare il numero di Fenomeni presenti nel DataSet
	   * @return Numero Fenomeni presenti nel DataSet
	   */
	public int getNumeroFen()
	{
		return Numero_Fenomeni;
	}
	
	/**
	   * Questo Metodo ha il compito di settare il nome fornito in Input al DataSet
	   * @param S Strina che indica il Nome del DataSet.
	   */
	public void setNomeDataSet( String S )
	{
		if( S != "" )
			NomeDataSet = S;
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare il Nome del DataSet
	   * @return Nome del DataSet.
	   */
	public String getNomeDataSet()
	{
		return NomeDataSet;
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la copia esatta del DataSet.
	   * Viene restituito un nuovo DataSet con le stesse caratteristiche e Fenomeni di quello attuale.
	   * @return DataSet uguale a quello attuale.
	   */
	public DataSet copy()
	{
		DataSet Clone = new DataSet( "", Num_Caratteristiche );
		
		// Si effettua una copia anche di tutti i suoi Fenomeni che contiene il DataSet
		for( Fenomeno<?> fenomeno : AllFenomeni )
		{
			Fenomeno<?> New_fen = fenomeno.copy( Clone );
			Clone.New_Fenomeno(New_fen);
		}
		
		return Clone;
	}
}
