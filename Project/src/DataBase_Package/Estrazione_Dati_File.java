package DataBase_Package;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
* Questa Classe gestisce l'estrazione dei Fenomeni dal File DataSet.
* Contiene una serie di metodi utili per determianre le caratteristiche del file caricato.
* @author Angelo Casolaro
* @version 1.0
*/
public class Estrazione_Dati_File 
{
	private int Num_Caratteristiche = 0;
	private int Num_Fenomeni = 0;
	private RandomAccessFile LetturaFile = null;
	private String Separatore = ",";
	
	public Estrazione_Dati_File() 
	{
		Num_Caratteristiche = 0;
		Num_Fenomeni = 0;
		LetturaFile = null;
	}
	
	public Estrazione_Dati_File( String Path )
	{
		setFile(Path);
	}
	
	public Estrazione_Dati_File( String Path, String Separatore )
	{
		setFile(Path);
		
		// Andiamo a settare l'eventuale separatore che può essere diverso dalla
		// classica "virgola"
		if( Separatore != "" )
			this.Separatore = Separatore;
	}
	
	public void setFile( String Path )
	{	
		if( LetturaFile == null )
			LetturaFile = File_DataSet.GetPointerFile(Path);
	}
	
	/**
	   * Questo Metodo ha il compito di restituire il numero di Caratteristiche del Fenomeni del DataSet.
	   * @return Numero di Caratteristiche dei Fenomeni osservati.
	   */
	public int get_Num_Caratt()
	{
		String Record;
		
		// Se abbiamo gia' calcolato il Numero di Caratteristiche del Fenomeno
		if( Num_Caratteristiche > 0 )
			return Num_Caratteristiche;
		
		try
		{	
			// Ci posizioniamo all'inizio del File
			LetturaFile.seek(0);
			
			// Andiamo a leggere la prima riga...
			Record = LetturaFile.readLine();
			
			// Si vanno a prendere tutti i campi che sono divisi dal separatore scelto
			String[] Campi = Record.split(Separatore);
			int Numero_Campi = Campi.length;
				
			// Per tutti i campi estratti...
			for( int i=0; i<Numero_Campi; i++ )
			{
				try 
				{
					@SuppressWarnings("unused")
					double value = 0.0;
					
					// Andiamo a verificare eventualmente se vi sono dei campi di tipo Stringa...
					// quelli nel DataSet non vengono considerati
					if( Campi[i] == " " || Campi[i] == "" )
						Num_Caratteristiche++;
					else
					{	
						value = Double.parseDouble(Campi[i]);
						Num_Caratteristiche++;
					}
				}
				catch (NumberFormatException e) 
				{	}
			}
			
			LetturaFile.seek(0);
		}	
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		return Num_Caratteristiche;
	}
	
	/**
	   * Questo Metodo ha il compito di restituire tutte le caratteristiche del Fenomeno attualmente elaborato.
	   * @return ArrayList di tutte le caratteristiche del Fenomeno attuale.
	   */
	public ArrayList<Double> get_NextFenomeno()
	{
		String Record = "";
		ArrayList<Double> List_Caratteristiche = new ArrayList<>();
				
		try
		{		
			// Ci posizioniamo all'inizio del File
			Record = LetturaFile.readLine();
			
			// Se abbiamo la possibilità di leggere...
			if( Record != null )
			{
				// Si vanno a prendere tutti i campi che sono divisi dal separatore scelto
				String[] Campi = Record.split(Separatore);
				int Numero_Campi = Campi.length;
				
				// Per tutti i campi estratti...
				for( int i=0; i<Numero_Campi; i++ )
				{
					try 
					{	
						// Andiamo ad inserire tutte le caratteristiche che risultano essere
						// di tipo double.
						double value = 0.0;
						
						// Nel caso in cui di valore Mancante
						if( Campi[i] == " " || Campi[i] == "" )
							value = 0.0;
						else
							value = Double.parseDouble(Campi[i]);
						
						List_Caratteristiche.add(value);
					}
					catch (NumberFormatException e) 
					{	}
				}
			}
			else
				List_Caratteristiche = null;
		}
		catch( IOException e )
		{	
			List_Caratteristiche = null;
			e.printStackTrace();
		}
		
		return List_Caratteristiche;
	}
	
	/**
	   * Questo Metodo ha il compito di restituire il numero di Fenomeni del DataSet
	   * @return Numero Fenomeni del DataSet
	   */
	public int getNumFen()
	{
		Num_Fenomeni = 0;
		try
		{		
			while( LetturaFile.readLine() != null )
				Num_Fenomeni++;
		}
		catch( IOException e )
		{	
			e.printStackTrace();
		}
		
		return Num_Fenomeni;
	}
}
