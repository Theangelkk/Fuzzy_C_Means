package Application;

import java.util.ArrayList;

import Clustering.FCM_Fast;
import DataSet.DataSet;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Grafico_Clustering 
{
	private ScatterChart< String, Number > Grafico_Scatter;
	
	private Stage dialog = null;
	
	private Stage Parent = null;
	
	private BorderPane layout;
	
	public Grafico_Clustering( Stage P, FCM_Fast F, int X, int Y ) 
	{
		Parent = P;
		dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Parent);
		layout = new BorderPane();
		
		PlotClustering( F, X, Y );
		
		Scene Scena = new Scene(layout, 1000, 800);
		layout.getStylesheets().add(Grafico_Membership.class.getResource("Style.css").toExternalForm());
		dialog.setScene(Scena);
		dialog.showAndWait();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void PlotClustering( FCM_Fast F, int X, int Y )
	{
		NumberAxis xAxisScatter = new NumberAxis(0,10,1);
		NumberAxis yAxisScatter = new NumberAxis();
		Grafico_Scatter = new ScatterChart(xAxisScatter,yAxisScatter);
		
		Grafico_Scatter.setTitle("Clustering Plot");
		
		xAxisScatter.setLabel("Caratteristica " + X );       
	    yAxisScatter.setLabel("Caratteristica " + Y );
		
		DataSet DS = F.getDataSet();
		
		for( int j=0; j<F.getNum_Cluster(); j++ )
		{	
			XYChart.Series Serie = new XYChart.Series();
			Serie.setName("Cluster " + j );
		
			for( int i=0; i<DS.getNumeroFen(); i++ )
				if( DS.getFenomeno(i).getCluster() == j )
					Serie.getData().add( new XYChart.Data( (double) DS.getFenomeno(i).getCaratteristica(X), (double) DS.getFenomeno(i).getCaratteristica(Y) ) );
				
			Grafico_Scatter.getData().add( Serie );
		}
		
		for( int i=0; i<F.getNum_Cluster(); i++ )
		{
			ArrayList<Double> Cluster = F.getCentralCluster(i);
			
			XYChart.Series Serie_Clustering = new XYChart.Series();
			Serie_Clustering.setName("Central Cluster " + i );
			
			Serie_Clustering.getData().add( new XYChart.Data( (double) Cluster.get(X), (double) Cluster.get(Y) ) );
			
			Grafico_Scatter.getData().add( Serie_Clustering );
		}
		
		layout.setCenter(Grafico_Scatter);
	}
}