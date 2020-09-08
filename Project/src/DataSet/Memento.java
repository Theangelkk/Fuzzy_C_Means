package DataSet;

import java.util.ArrayList;

public class Memento<T> 
{
	// Stato Interno
	private ArrayList<T> Lista_Caratteristiche = null;
	
	public Memento() 
	{
		Lista_Caratteristiche = new ArrayList<>();
	}
	
	public Memento( ArrayList<T> List )
	{
		Lista_Caratteristiche = List;
	}
	
	public ArrayList<T> Get_State()
	{
		return Lista_Caratteristiche;
	}
}
