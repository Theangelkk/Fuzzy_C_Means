package Application;

import java.io.IOException;
import Clustering.FCM_Fast;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Controller_ListaDataSet 
{
	@FXML
	private Label Title;
	
	@FXML
	private BorderPane layout;
	
	@FXML
	private VBox Lista_Record = null;
	
	@FXML
	private Button New_DS = null;
	
	@FXML
	private Button Avanti = null;
	
	private int Max_Cluster = 0;
	
	public Controller_ListaDataSet()
	{
		Lista_Record = new VBox();
	}

	public void setInit( BorderPane L ) 
	{
		layout = L;
		TestMain.setCenterLayout(layout);
	}
	
	@FXML
	void initialize()
	{	
		Title.getStyleClass().add("Title_Page");
		New_DS.getStyleClass().add("button_next");
		New_DS.setOnAction( e -> NuovoDS() );
		Avanti.getStyleClass().add("button_next");
		Avanti.setOnAction( e -> Controller_Wait_Clustering.esegui( 3 * Max_Cluster ) );
	}
	
	public void InizializzaTable()
	{
		Lista_Record = new VBox();
		Lista_Record.setPrefHeight(40);
		Lista_Record.setPrefWidth(80);
		Lista_Record.setSpacing(10);
		Lista_Record.setPadding(new Insets(70, 0, 10, 10));
		layout.getChildren().set( 2, Lista_Record );
		Max_Cluster = 0;
		TestMain.setCenterLayout(layout);
	}
	
	public void addDataSet( String NomeDataSet )
	{
		Label N = new Label();
		Button Modifica = new Button("Modifica");
		Button Elimina = new Button("Elimina");
		Label Label_Num_Clus = new Label("N Cluster");
		TextField Text_Num_Clus = new TextField();
		Label Label_Epsilon = new Label("Epsilon");
		TextField Text_Epsilon = new TextField();
		Label Label_Iter = new Label("Iter");
		TextField Text_Iter = new TextField();
		HBox Riga = new HBox();
		FCM_Fast F_DS = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
		
		if( Max_Cluster < F_DS.getNum_Cluster() )
			Max_Cluster = F_DS.getNum_Cluster();
		
		N.setText(NomeDataSet);
		N.getStyleClass().add("Nome_DataSet");
		Modifica.getStyleClass().add("button_setting");
		Modifica.setOnAction( e -> ModificaDataSet(NomeDataSet) );
		Elimina.getStyleClass().add("button_setting");
		Elimina.setOnAction( e -> EliminaDataSet(NomeDataSet) );
		
		N.setMinHeight( 40 );
		N.setMinWidth( 80 );
		
		Modifica.setMinHeight( 40 );
		Elimina.setMinHeight( 40 );
		Modifica.setMinWidth( 90 );
		Elimina.setMinWidth( 80 );
		
		Label_Num_Clus.getStyleClass().add("Label_Setting");
		Label_Num_Clus.setMinHeight( 40 );
		Label_Num_Clus.setMinWidth( 90 );
		Label_Num_Clus.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
		Label_Num_Clus.setPadding(new Insets(0, 0, 0, 15));
		
		Label_Epsilon.getStyleClass().add("Label_Setting");
		Label_Epsilon.setMinHeight( 40 );
		Label_Epsilon.setMinWidth( 70 );
		Label_Epsilon.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
		Label_Epsilon.setPadding(new Insets(0, 0, 0, 15));
		
		Label_Iter.getStyleClass().add("Label_Setting");
		Label_Iter.setMinHeight( 40 );
		Label_Iter.setMinWidth( 50 );
		Label_Iter.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
		Label_Iter.setPadding(new Insets(0, 0, 0, 15));
		
		Text_Num_Clus.setMinHeight( 30 );
		Text_Num_Clus.setMinWidth( 40 );
		Text_Num_Clus.setPadding(new Insets(0, 0, 0, 15));
		Text_Num_Clus.setText( "" + F_DS.getNum_Cluster() );
		Text_Num_Clus.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	try
					{
		        		int Num_Clu = Integer.parseInt(Text_Num_Clus.getText());
						Double Eps = Double.parseDouble(Text_Epsilon.getText());
						int Iter = Integer.parseInt(Text_Iter.getText());
						TestMain.getSessione_InCorso().Change_Param_Clust( NomeDataSet, Num_Clu, Eps, Iter );
						
						if( Max_Cluster < Num_Clu )
							Max_Cluster = Num_Clu;
						
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
						Text_Iter.setText("" + F.getMax_Iter());
					}
					catch ( NumberFormatException exception )
					{
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						F.InserisciNumClass(2);
						Text_Iter.setText("" + F.getMax_Iter());
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
					}
		        }
		    }
		});

		Text_Epsilon.setMinHeight( 30 );
		Text_Epsilon.setMinWidth( 60 );
		Text_Epsilon.setPadding(new Insets(0, 0, 0, 15));
		Text_Epsilon.setText( "" + F_DS.get_Epsilon() );
		Text_Epsilon.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	try
					{
		        		int Num_Clu = Integer.parseInt(Text_Num_Clus.getText());
						Double Eps = Double.parseDouble(Text_Epsilon.getText());
						int Iter = Integer.parseInt(Text_Iter.getText());
						TestMain.getSessione_InCorso().Change_Param_Clust( NomeDataSet, Num_Clu, Eps, Iter );
						
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
						Text_Iter.setText("" + F.getMax_Iter());
					}
					catch ( NumberFormatException exception )
					{
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						F.setEpsilon(0.001);
						Text_Iter.setText("" + F.getMax_Iter());
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
					}
		        }
		    }
		});
		
		Text_Iter.setMinHeight( 30 );
		Text_Iter.setMinWidth( 60 );
		Text_Iter.setPadding(new Insets(0, 0, 0, 15));
		Text_Iter.setText( "" + F_DS.getMax_Iter() );
		Text_Iter.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	try
					{
		        		int Num_Clu = Integer.parseInt(Text_Num_Clus.getText());
						Double Eps = Double.parseDouble(Text_Epsilon.getText());
						int Iter = Integer.parseInt(Text_Iter.getText());
						TestMain.getSessione_InCorso().Change_Param_Clust( NomeDataSet, Num_Clu, Eps, Iter );
						
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
						Text_Iter.setText("" + F.getMax_Iter());
					}
					catch ( NumberFormatException exception )
					{
						FCM_Fast F = TestMain.getSessione_InCorso().get_FCM(NomeDataSet);
						F.setMaxIter(1000);
						Text_Iter.setText("" + F.getMax_Iter());
						Text_Num_Clus.setText("" + F.getNum_Cluster());
						Text_Epsilon.setText("" + F.get_Epsilon());
					}
		        }
		    }
		});
	
		VBox V1 = new VBox();
		V1.getChildren().add( Modifica );
		V1.setPadding(new Insets(0, 10, 0, 15));
		VBox V2 = new VBox();
		V2.getChildren().add( Elimina );
		V2.setPadding(new Insets(0, 0, 0, 10));
		
		Riga.getChildren().addAll( N, Label_Num_Clus, Text_Num_Clus, Label_Epsilon, Text_Epsilon, Label_Iter, Text_Iter, V1, V2 );
		Lista_Record.getChildren().add( Riga );
		
		layout.getChildren().set( 2, Lista_Record );
		TestMain.setCenterLayout(layout);
	}
	
	private void ModificaDataSet( String M )
	{
		try
		{
			FXMLLoader file_xml = new FXMLLoader();
		
			file_xml.setLocation(getClass().getResource("Modifica_DataSet.fxml"));
			BorderPane layout = file_xml.load();

			Controller_Modifica_DataSet Mod = file_xml.getController();
		
			Mod.setInit( TestMain.getStage(), layout, M );
			
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
	
	public void EliminaDataSet( String E )
	{
		TestMain.getSessione_InCorso().EliminaDataSet(E);
	}
	
	private void NuovoDS()
	{
		try
		{
			FXMLLoader file_xml = new FXMLLoader();
		
			file_xml.setLocation(getClass().getResource("Modifica_DataSet.fxml"));
			BorderPane layout = file_xml.load();

			Controller_Modifica_DataSet Mod = file_xml.getController();
		
			Mod.setInit( TestMain.getStage(), layout, "" );
			
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}