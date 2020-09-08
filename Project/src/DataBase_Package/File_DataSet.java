package DataBase_Package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
* Questa Classe gestisce la connessione del File DataSet.
* @author Angelo Casolaro
* @version 1.0
*/
public class File_DataSet 
{
	private static File DataSet_File = null;
	private static RandomAccessFile Accesso = null;
	
	/**
	   * Questo Metodo ha il compito di effettuare la creazione della connessione con il File DataSet specificato dal path fornito come Input.
	   * @param Path_File Path Assoluto del File DataSet con cui effettuare la connessione.
	   */
	private static void Creazione_Conn( String Path_File )
	{
		DataSet_File = new File(Path_File);
		
		try
		{
			Accesso = new RandomAccessFile( DataSet_File, "r" );
		}
		catch( FileNotFoundException e )
		{
			DataSet_File = null;
			Accesso = null;
			System.out.println("File Non Trovato");
		}
	}
	
	/**
	   * Questo Metodo ha il compito di fornire il puntatore al file per accedere ai suoi record interni.
	   * @param Path_File Path Assoluto del File DataSet con cui effettuare la connessione.
	   * @return Ritorn il puntatore al file con accesso casuale.
	   */
	public static RandomAccessFile GetPointerFile( String Path_File )
	{
		// Andiamo a creare una connessione se precedentemente non era già stata creata...
		if( DataSet_File == null )
			Creazione_Conn(Path_File);
		
		return Accesso;
	}
}
