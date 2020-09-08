package DataSet;

import java.util.ArrayList;

public class Real_Fenomeno<T> extends Fenomeno<T>
{
	private ArrayList<T> Caratteristiche = null;
	
	public Real_Fenomeno( DataSet D ) 
	{
		super(D);
		Caratteristiche = new ArrayList<>();
	}

	/**
	   * Questo Metodo ha il compito di verificare il cambiamento di Stato del DataSet.
	   * Asseconda del cambiamento avuto, vengono effettuate operazioni di conseguenza.
	   */
	@Override
	public void Aggiornamento() 
	{
		// Andiamo a prendere lo stato attuale del DataSet di dove è contenuto tale Fenomeno...
		State StatoDataSet = DS.getStato();

		if( StatoDataSet.Situazione_Attuale() == -1 )
			Restore_State();
		else if( StatoDataSet.Situazione_Attuale() >= 0 )
			EliminaCaratteristica( StatoDataSet.Situazione_Attuale() );
	}

	/**
	   * Questo Metodo ha il compito di far ritornare ad uno stato precedente il Fenomeno.
	   */
	@Override
	public void Restore_State() 
	{
		Caratteristiche = Stati_Prec.Restore_State();
	}
	
	/**
	   * Questo Metodo ha il compito di eliminare una i-esima caratteristica del Fenomeno.
	   * @param i I-esima Caratteristica da eliminare.
	   */
	public void EliminaCaratteristica( int i )
	{
		Caratteristiche.remove(i);
		Stati_Prec.Save_State(Caratteristiche);
	}

	/**
	   * Questo Metodo ha il compito di ritornare una i-esima caratteristica del Fenomeno.
	   * @param i I-esima Caratteristica da ritornare.
	   */
	@Override
	public T getCaratteristica(int i) 
	{
		if( i >= 0 && i < Caratteristiche.size() )
			return Caratteristiche.get(i);
		else
			return null;
	}
	
	/**
	   * Questo Metodo ha il compito di inserire una nuova caratteristica del Fenomeno.
	   * @param NewCarat Nuova Caratteristica da Inserire.
	   */
	@Override
	public void InserisciNewCaratt(T NewCarat) 
	{
		Caratteristiche.add(NewCarat);
	}

	/**
	   * Questo Metodo ha il compito di modificare una i-esima caratteristica del Fenomeno.
	   * @param i I-esima Caratteristica da modificare.
	   * @param modObject Valore della nuova Caratteristica.
	   */
	@Override
	public void ModificaCaratteristica(int i, T modObject) 
	{
		if( i >= 0 && i < Caratteristiche.size() )
		{
			Caratteristiche.set(i, modObject);
			Stati_Prec.Save_State(Caratteristiche);
		}
	}
	
	/**
	   * Questo Metodo ha il compito di effettuare la copia esatta del Fenomeno.
	   * Viene restituito un nuovo Fenomeno con le stesse caratteristiche attuali.
	   * @param DS DataSet al quale il fenomeno � collegato.
	   * @return Fenomeno uguale a quello attuale.
	   */
	@Override
	public Real_Fenomeno<T> copy( DataSet DS ) 
	{
		Real_Fenomeno<T> New_Fen = new Real_Fenomeno<T>(DS);
		
		// Si vanno a prendere tutte le sue caratteristiche
		for( T Caratt : Caratteristiche )
			New_Fen.InserisciNewCaratt(Caratt);
		
		New_Fen.Grado_Di_Relazione = Grado_Di_Relazione;
		New_Fen.Cluster = Cluster;
		
		return New_Fen;
	}
}
