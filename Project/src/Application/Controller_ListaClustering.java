package Application;

import Clustering.FCM_Fast;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Controller_ListaClustering 
{
	@FXML
	private Label Title;
	
	@FXML
	private BorderPane layout;
	
	@FXML
	private VBox Lista_Record = null;
	
	@FXML
	private Button Modifica_DS = null;
	
	@FXML
	private Button Fine = null;
	
	public Controller_ListaClustering()
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
		Modifica_DS.getStyleClass().add("button_next");
		Modifica_DS.setOnAction( e -> TestMain.getSessione_InCorso().Carica_Stage_ListDataSet() );
		Fine.getStyleClass().add("button_next");
		Fine.setOnAction( e -> new Conferma_Message( TestMain.getStage(), "Sei sicuro di voler uscire ??") );
	}
	
	public void InizializzaTable()
	{
		Lista_Record = new VBox();
		Lista_Record.setPrefHeight(40);
		Lista_Record.setPrefWidth(80);
		Lista_Record.setSpacing(10);
		Lista_Record.setPadding(new Insets(70, 0, 10, 5));
		Platform.runLater( () -> layout.getChildren().set( 2, Lista_Record ) );
		TestMain.setCenterLayout(layout);
	}
	
	public void addDataSet( String Nome_DS, FCM_Fast F )
	{
		Label N = new Label();
		Button Visualizza = new Button("Cluster");
		Button Visualizza_Memb = new Button("Membership");
		Label Label_Caratt_X = new Label("Caratt X");
		TextField Text_Caratt_X = new TextField();
		Label Label_Caratt_Y = new Label("Caratt Y");
		TextField Text_Caratt_Y = new TextField();
		Label Membership = new Label("Membership");
		TextField Text_Membership = new TextField();
		HBox Riga = new HBox();
		
		N.setText(Nome_DS);
		N.getStyleClass().add("Nome_DataSet");
		N.setMinHeight( 40 );
		N.setMinWidth( 70 );
		
		Label_Caratt_X.getStyleClass().add("Label_Setting");
		Label_Caratt_X.setMinHeight( 40 );
		Label_Caratt_X.setMinWidth( 70 );
		Label_Caratt_X.setFont(Font.font("Amble CN", FontWeight.BOLD, 10));
		Label_Caratt_X.setPadding(new Insets(0, 0, 0, 15));
		
		Label_Caratt_Y.getStyleClass().add("Label_Setting");
		Label_Caratt_Y.setMinHeight( 40 );
		Label_Caratt_Y.setMinWidth( 70 );
		Label_Caratt_Y.setFont(Font.font("Amble CN", FontWeight.BOLD, 10));
		Label_Caratt_Y.setPadding(new Insets(0, 0, 0, 15));
		
		Text_Caratt_X.setMinHeight( 30 );
		Text_Caratt_X.setMinWidth( 40 );
		Text_Caratt_X.setPadding(new Insets(0, 0, 0, 15));
		Text_Caratt_X.setText("0");
		Text_Caratt_X.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	int NumCar = F.getDataSet().getNumCar();
		        	try
					{
		        		int Caratt_X = Integer.parseInt(Text_Caratt_X.getText());
						int Caratt_Y = Integer.parseInt(Text_Caratt_Y.getText());
						
						if( Caratt_X < 0 || Caratt_X > NumCar-1 )
							Text_Caratt_X.setText("0");
						
						if( Caratt_X == Caratt_Y )
						{
							Text_Caratt_X.setText("0");
							Text_Caratt_Y.setText("1");
						}
					}
					catch ( NumberFormatException exception )
					{
						Text_Caratt_X.setText("0");
					}
		        }
		    }
		});

		Text_Caratt_Y.setMinHeight( 30 );
		Text_Caratt_Y.setMinWidth( 40 );
		Text_Caratt_Y.setPadding(new Insets(0, 0, 0, 15));
		Text_Caratt_Y.setText("1");
		Text_Caratt_Y.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	int NumCar = F.getDataSet().getNumCar();
		        	try
					{
		        		int Caratt_X = Integer.parseInt(Text_Caratt_X.getText());
						int Caratt_Y = Integer.parseInt(Text_Caratt_Y.getText());
						
						if( Caratt_Y < 0 || Caratt_Y > NumCar-1 )
							Text_Caratt_Y.setText("1");
						
						if( Caratt_X == Caratt_Y )
						{
							Text_Caratt_X.setText("0");
							Text_Caratt_Y.setText("1");
						}
					}
					catch ( NumberFormatException exception )
					{
						Text_Caratt_Y.setText("1");
					}
		        }
		    }
		});
		
		Visualizza.getStyleClass().add("button_setting");
		Visualizza.setMinHeight( 40 );
		Visualizza.setMinWidth( 80 );
	
		Visualizza.setOnAction( e -> new Grafico_Clustering( TestMain.getStage(), F, Integer.parseInt(Text_Caratt_X.getText()), Integer.parseInt(Text_Caratt_Y.getText())) );
		
		VBox V1 = new VBox();
		V1.getChildren().add( Visualizza );
		V1.setPadding(new Insets(0, 0, 0, 20));
		
		Membership.getStyleClass().add("Label_Setting");
		Membership.setMinHeight( 40 );
		Membership.setMinWidth( 60 );
		Membership.setFont(Font.font("Amble CN", FontWeight.BOLD, 10));
		Membership.setPadding(new Insets(0, 0, 0, 10));
		
		Text_Membership.setMinHeight( 30 );
		Text_Membership.setMinWidth( 40 );
		Text_Membership.setPadding(new Insets(0, 0, 0, 15));
		Text_Membership.setText("0");
		Text_Membership.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (!newPropertyValue)
		        {
		        	int NumCluster = F.getNum_Cluster();
		        	try
					{
		        		int New = Integer.parseInt(Text_Membership.getText());
						
						if( New < 0 || New > NumCluster-1 )
							Text_Membership.setText("0");
					}
					catch ( NumberFormatException exception )
					{
						Text_Membership.setText("0");
					}
		        }
		    }
		});
		
		Visualizza_Memb.getStyleClass().add("button_setting");
		Visualizza_Memb.setMinHeight( 40 );
		Visualizza_Memb.setMinWidth( 80 );
	
		Visualizza_Memb.setOnAction( e -> new Grafico_Membership( TestMain.getStage(), F, Integer.parseInt(Text_Membership.getText() )) );
		
		VBox V2 = new VBox();
		V2.getChildren().add( Visualizza_Memb );
		V2.setPadding(new Insets(0, 0, 0, 20));
		
		Riga.getChildren().addAll( N, Label_Caratt_X, Text_Caratt_X, Label_Caratt_Y, Text_Caratt_Y, V1, Membership, Text_Membership, V2 );
		Platform.runLater( () -> Lista_Record.getChildren().add( Riga ) );
		
		Platform.runLater( () -> layout.getChildren().set( 2, Lista_Record ) );
		TestMain.setCenterLayout(layout);
	}
}