package DataSet;

/**
* Questa Classe Astratta definisce le caratteristiche e funzionalità di un Fenomeno.
* @author Angelo Casolaro
* @version 1.0
*/
public abstract class Fenomeno<T> implements Cloneable {

	protected DataSet DS;
	protected List_Of_Memento<T> Stati_Prec = null;
	protected int Cluster = -1;
	protected double Grado_Di_Relazione = 0.0f;

	abstract public void Aggiornamento();

	abstract public void Restore_State();

	abstract public T getCaratteristica(int i);

	abstract public void InserisciNewCaratt(T NewCarat);

	abstract public void ModificaCaratteristica(int i, T modObject);
	
	abstract public Fenomeno<T> copy( DataSet DS );

	public Fenomeno(DataSet D) {
		DS = D;
		Stati_Prec = new List_Of_Memento<T>();
	}
	
	/**
	   * Questo Metodo ha il compito di settare il Cluster di appartenenza del Fenomeno
	   * @param C Numero del Cluster di appartenenza.
	   */
	public void setCluster(int C) {
		Cluster = C;
	}

	/**
	   * Questo Metodo ha il compito di sritornare il Cluster di appartenenza del Fenomeno
	   * @return Numero del Cluster di appartenenza.
	   */
	public int getCluster() {
		return Cluster;
	}

	/**
	   * Questo Metodo ha il compito di settare il Grado di appartenenza del Fenomeno al Cluster.
	   * @param G Grado di appartenenza ad un Cluster.
	   */
	public void setGrado(double G) {
		Grado_Di_Relazione = G;
	}

	/**
	   * Questo Metodo ha il compito di ritornare il Grado di appartenenza del Fenomeno al Cluster.
	   * @return Grado di appartenenza ad un Cluster.
	   */
	public double getGrado() {
		return Grado_Di_Relazione;
	}
}
