package DataSet;

import java.util.ArrayList;

/**
* Questa Classe permetta le gestione dei vari stati dei Fenomeni e del DataSet.
* @author Angelo Casolaro
* @version 1.0
*/
public class List_Of_Memento<T> 
{
	private ArrayList<Memento<T>> Stati = null;
	private int Versione = 0;
	
	public List_Of_Memento() 
	{
		Stati = new ArrayList<>();
	}
	
	/**
	   * Questo Metodo ha il compito di salvare lo stato attuale di un Fenoemno.
	   * @param Caratteristiche Insieme delle caratteristiche di un Fenomeno.
	   */
	public void Save_State( ArrayList<T> Caratteristiche )
	{
		Memento<T> Stato_Attuale = new Memento<T>(Caratteristiche);
		Stati.add(Stato_Attuale);
		Versione++;
	}
	
	/**
	   * Questo Metodo ha il compito di ritornare lo stato precedente di un Fenoemno.
	   * @return Insieme delle caratteristiche di un Fenomeno nello stato precedente.
	   */
	public ArrayList<T> Restore_State()
	{
		if( Versione > 0 )
		{	
			Versione--;
			Memento<T> Stato = Stati.get(Versione);
			Stati.remove(Versione);
			
			return Stato.Get_State(); 
		}
		else
			return null;
	}
}
