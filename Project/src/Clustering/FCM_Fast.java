package Clustering;

import java.util.ArrayList;
import java.util.Random;
import DataSet.DataSet;

public class FCM_Fast extends Classificatori
{
	private double Epsilon = 0.0;
	private int MaxIterations = 0;
	private int ValueM = 0;
	private Double [][] Grado = null;
	private Double [][] Uk_1  = null;
	private Double [][] Di = null;
	
	public FCM_Fast( DataSet D, int NumCluster, double Epsilon, int MaxIter, int m ) 
	{
		super(D,NumCluster);
		setEpsilon(Epsilon);
		setMaxIter(MaxIter);
		setValueM(m);
	}
	
	public void setEpsilon( double e ) 
	{
		if( e <= 0.001 )
			Epsilon = e;
		else
			Epsilon = 0.001;
	}
	
	public void setMaxIter( int M )
	{
		if( M > 5 )
			MaxIterations = M;
		else
			MaxIterations = 1000;
	}
	
	public void setValueM( int M ) 
	{
		if( M >= 1 && M < Integer.MAX_VALUE )
			ValueM = M;
		else
			ValueM = 2;
	}
	
	public int getMax_Iter()
	{
		return MaxIterations;
	}
	
	public double get_Epsilon()
	{
		return Epsilon;
	}
	
	public double get_Grado_Cluster( int Cluster, int Fen )
	{
		return Grado[Cluster][Fen];
	}
	
	private void setInitializeGrado()
	{
		Grado = new Double[Num_Cluster][];
		Uk_1 = new Double[Num_Cluster][];
		Di = new Double[Num_Cluster][];
		
		for( int i=0; i<Num_Cluster; i++ )
		{	
			Grado[i] = new Double[DS.getNumeroFen()];
			Uk_1[i] = new Double[DS.getNumeroFen()];
			Di[i] = new Double[DS.getNumeroFen()];
			
			for( int j=0; j<DS.getNumeroFen(); j++ )
			{	
				Grado[i][j] = 0.0;
				Di[i][j] = 0.0;
				Uk_1[i][j] = 0.0;
			}
		}
		
		for( int j=0; j<DS.getNumeroFen(); j++ )
		{
			Random NumCas = new Random();
			int Numero = NumCas.nextInt(Num_Cluster);
			Grado[Numero][j] = 1.0;
		}
		
		SetCentralCluster();
	}
	
	@Override
	public void AlgorithmClustering() 
	{
		setInitializeGrado();
		
		int Iterazioni = 0;
		CopiaMatrixGrado();
			
		try
		{
			do{
				CalcoloCentralCluster();
				CopiaMatrixGrado();	
				CalcolaDist_PuntoCentrale();	
				AggiornamentoGrado();
			
				Iterazioni++;
			
			}while( !VerificaArresto( Grado, Uk_1 ) && Iterazioni < MaxIterations );
			
			InserisciFen_Cluster();
		}
		catch ( ArithmeticException exception ) 
		{
			SetCentralCluster();
			setInitializeGrado();
			AlgorithmClustering();
		}
	}
	
	private void InserisciFen_Cluster()
	{
		double Max_Cluster = 0.0;
		int Indice = 0;
		
		for( int i=0; i<DS.getNumeroFen(); i++ )
		{	
			Max_Cluster = 0.0;
			Indice = 0;
			
			for( int j=0; j<Num_Cluster; j++ )
			{
				if( Grado[j][i] > Max_Cluster )
				{
					Max_Cluster = Grado[j][i];
					Indice = j;
				}
			}
			
			DS.getFenomeno(i).setCluster( Indice );
			DS.getFenomeno(i).setGrado(Max_Cluster);
		}
	}
	
	private void CopiaMatrixGrado()
	{
		for( int i=0; i<Num_Cluster; i++ )
			for( int j=0; j<DS.getNumeroFen(); j++ )
				Uk_1[i][j] = Grado[i][j];
		
	}
	
	private void CalcoloCentralCluster()
	{
		for( int i=0; i<Num_Cluster; i++ )
			for( int j=0; j<DS.getNumCar(); j++ )
				CentralCluster.get( i ).set( j, CentroCluster( i , j ) );
	}
	
	private double CentroCluster( int N_Cluster, int N_Caratt ) throws ArithmeticException
	{
		Double Numeratore = 0.0;
		Double Denominatore = 0.0;
		
		for( int k=0; k<DS.getNumeroFen(); k++ )
		{
			double Uik = Grado[N_Cluster][k];
			
			Uik = Math.pow( Uik, ValueM );
			
			double App = (double) DS.getFenomeno(k).getCaratteristica(N_Caratt);
			App = App * Uik;
			Numeratore += App;
			Denominatore += Uik;
		}
		
		double Risultato = Numeratore/Denominatore;
		
		if (Double.isInfinite(Risultato) || Double.isNaN(Risultato))
			 throw new ArithmeticException();
		
		return Risultato;
	}
	
	private void CalcolaDist_PuntoCentrale()
	{
		for( int i=0; i<Num_Cluster; i++ )
		{
			for( int k=0; k<DS.getNumeroFen(); k++ )
			{
				ArrayList<Double> Caratteristiche = new ArrayList<>();
				for( int l=0; l<DS.getNumCar(); l++ )
					Caratteristiche.add((Double) DS.getFenomeno(k).getCaratteristica(l));
		
				Double Dist = DistanzaEuclidea( Caratteristiche , CentralCluster.get(i), DS.getNumCar() );
				Di[i][k] =  Dist;
			}
		}
	}
	
	private double DistanzaEuclidea( ArrayList<Double> X, ArrayList<Double> Y, int N )
	{
		double Dist = 0.0;
		
		for( int i=0; i<N; i++ )
		{
			double Tmp = X.get(i) - Y.get(i);
			Tmp = Tmp * Tmp;
			Dist += Tmp;
		}
		
		return Math.sqrt(Dist);
	}	
	
	private void AggiornamentoGrado() throws ArithmeticException
	{
		for( int i=0; i<Num_Cluster; i++ )
		{
			for( int k=0; k<DS.getNumeroFen(); k++ )
			{
				double Dik = Di[i][k];
				double Ris = 0.0;
				
				for( int j=0; j<Num_Cluster; j++ )
				{
					double Djk = Di[j][k];
					double App = 0.0;

					App = Dik / Djk;
					
					if (Double.isInfinite(App) || Double.isNaN(App))
						 throw new ArithmeticException();
					
					App = Math.pow( App, (2/(ValueM-1)) );
					Ris += App;
				}
				
				Ris = 1/Ris;
				
				if (Double.isInfinite(Ris) || Double.isNaN(Ris))
					 throw new ArithmeticException();

				Grado[i][k] = Ris;
			}
		}
	}
	
	private boolean VerificaArresto( Double[][] Uk, Double[][] Uk_1 )
	{
		Double A = 0.0;
		Double B = 0.0;
		Double abs = 0.0;
		Double Max = 0.0;
		
		for( int i=0; i<Num_Cluster; i++ )
		{
			for( int j=0; j<DS.getNumeroFen(); j++ )
			{
				A = Uk[i][j];
				B = Uk_1[i][j];
			
				abs = Math.abs( A - B );
				
				if( abs > Max )
					Max = abs;
			}
		}
		
		if( Max < Epsilon )
			return true;
		else
			return false;
	}
}
