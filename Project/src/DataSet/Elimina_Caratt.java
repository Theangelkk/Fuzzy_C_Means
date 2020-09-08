package DataSet;

public class Elimina_Caratt implements State
{
	private int Stato = 0;
	
	public Elimina_Caratt(int i) 
	{
		Stato = i;
	}
	
	@Override
	public int Situazione_Attuale() 
	{
		return Stato;
	}
}
