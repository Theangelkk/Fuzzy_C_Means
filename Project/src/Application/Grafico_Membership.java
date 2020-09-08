package Application;

import Clustering.FCM_Fast;
import DataSet.DataSet;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Grafico_Membership 
{
	private AreaChart< String, Number > Grafico_Area;
	
	private Stage dialog = null;
	
	private Stage Parent = null;
	
	private BorderPane layout;
	
	public Grafico_Membership( Stage P, FCM_Fast F, int Cluster ) 
	{	
		Parent = P;
		dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Parent);
		
		layout = new BorderPane();
		PlotMembership(F, Cluster );
		
		Scene Scena = new Scene(layout, 1000, 800 );
		dialog.setScene(Scena);
		dialog.showAndWait();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void PlotMembership( FCM_Fast F, int Cluster )
	{
		CategoryAxis xAxisArea = new CategoryAxis();
		NumberAxis yAxisArea = new NumberAxis();
		Grafico_Area = new AreaChart(xAxisArea,yAxisArea);
		
		Grafico_Area.setTitle("Membership Cluster " + Cluster );
		Grafico_Area.setAnimated(true);
		Grafico_Area.setPadding(new Insets(0,0,0,5));
		
		xAxisArea.setLabel("Fenomeni");       
	    yAxisArea.setLabel("Membership");
	        
		DataSet DS = F.getDataSet();
		
		Grafico_Area.setCreateSymbols(false);
		XYChart.Series Clustering = new XYChart.Series();
		Clustering.setName("Cluster " + Cluster );
			
		for( int j=0; j<DS.getNumeroFen(); j++ )
			Clustering.getData().add( new XYChart.Data( "Fen" + j, F.get_Grado_Cluster(Cluster, j) ) );
			
		Grafico_Area.getData().add( Clustering );
		layout.setCenter(Grafico_Area);
	}
}