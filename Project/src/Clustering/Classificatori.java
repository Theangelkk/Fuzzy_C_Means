package Clustering;

import java.util.ArrayList;
import DataSet.DataSet;

/**
* Questa Classe Astratta definisce ed implementa le caratteristiche di un Classificatore
* @author Angelo Casolaro
* @version 1.0
*/
public abstract class Classificatori 
{
	protected DataSet DS = null;
	protected ArrayList<ArrayList<Double>> CentralCluster = null;
	protected int Num_Cluster = 2;
	
	public Classificatori( DataSet D, int C )
	{
		DS = D;
		InserisciNumClass(C);
		CentralCluster = new ArrayList<>();
		SetCentralCluster();
	}
	
	/**
	   * Questo Metodo ha il compito di settare il Numero di partizioni da determinare nell'algoritmo di Clustering
	   * @param C Numero di Cluster da determinare.
	   */
	public void InserisciNumClass( int C )
	{
		if( ( C >= 2 && C < DS.getNumeroFen() ) )
			Num_Cluster = C;
		else
			Num_Cluster = 2;
	}
	
	/**
	   * Questo Metodo ha il compito di restituire il Numero di Cluster.
	   * @return Numero di Cluster settato.
	   */
	public int getNum_Cluster()
	{
		return Num_Cluster;
	}
	
	/**
	   * Questo Metodo ha il compito di restituire il DataSet utilizzato nell'algoritmo di Clustering
	   * @return DataSet utilizzato nell'algoritmo di Clustering.
	   */
	public DataSet getDataSet()
	{
		return DS;
	}
	
	/**
	   * Questo Metodo ha il compito di restituire l'i-esimo centro del Cluster determinato nell'algoritmo di Clustering
	   * @param i I-esimo centro del Cluster.
	   * @return Posizione del Centro Cluster.
	   */
	public ArrayList<Double> getCentralCluster( int i )
	{
		return CentralCluster.get(i);
	}
	
	protected void SetCentralCluster()
	{
		// Si va ad inizializzare tutti i centri dei Cluster a 0.0
		for( int i=0; i<Num_Cluster; i++ )
		{
			ArrayList<Double> Riga = new ArrayList<>();
			for( int j=0; j<DS.getNumCar(); j++ )
				Riga.add(0.0);
			
			CentralCluster.add(Riga);
		}
	}
	
	public abstract void AlgorithmClustering();
}
