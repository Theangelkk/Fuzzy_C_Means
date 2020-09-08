package Application;

import DataSet.DataSet;
import DataSet.Fenomeno;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.EventHandler;

public class Controller_Modifica_DataSet 
{
	@SuppressWarnings("rawtypes")
	@FXML
	private TableView Table_DataSet;
	
	@FXML
	private Button Carica_Elim_DS;
	
	@FXML
	private Button Salva_DS;
	
	@FXML
	private HBox hBox;
	
	@FXML
	private TextField New_NameDS;
	
	private Stage dialog = null;
	
	private Stage Parent = null;
	
	private Scene Scena = null;
	
	private BorderPane layout = null;

	private DataSet DS = null;
	
	private String NomeDS = "";
	
	public Controller_Modifica_DataSet()
	{
		dialog = new Stage();
	}

	public void setInit( Stage P, BorderPane L, String NameDataSet ) 
	{
		Parent = P;
		dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Parent);
		layout = L;
		Scena = new Scene(layout);
		NomeDS = NameDataSet;
		
		DS = TestMain.getSessione_InCorso().getDataSet_Name(NomeDS).copy();
	
		PrepareListView();
		PrepareCheckButton();
		
		dialog.setScene(Scena);
		dialog.showAndWait();
	}
	
	@FXML
	void initialize()
	{	
		Carica_Elim_DS.setOnAction( e -> Elimina_Caratt() );
		Salva_DS.setOnAction( e -> SalvaDS() );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void PrepareListView()
	{
		Table_DataSet = new TableView<>();
		Table_DataSet.setEditable(true);
	
		ObservableList<ObservableList> srcData = FXCollections.observableArrayList();;
		
		TableColumn FirstCol = new TableColumn<>("ID");
		FirstCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty( param.getValue().get(0).toString() );
            }
            
        });
		Table_DataSet.getColumns().add(FirstCol);
		
		for (int i = 0; i < DS.getNumCar(); i++ ) 
		{
			TableColumn Col = new TableColumn<>("Caratteristica " + (i+1) );
			Col.setMinWidth(20);
			Col.setCellFactory( TextFieldTableCell.forTableColumn() );
			
			final int j = i+1;
			Col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) 
                {	return new SimpleStringProperty( param.getValue().get(j).toString() );	} 
            });
			
			
			Col.setOnEditCommit(
									new EventHandler<TableColumn.CellEditEvent<ObservableList, String>>() {
											@Override 
											public void handle(TableColumn.CellEditEvent<ObservableList, String> t) {
													
													@SuppressWarnings("unused")
													String oldvalue = t.getOldValue();
													int Riga = t.getTablePosition().getRow();
													int Colonna = t.getTablePosition().getColumn();
														
													try
													{	
														double New_Val = 0.0;
														
														if( t.getNewValue().length() > 0 )
															New_Val = Double.parseDouble( t.getNewValue() );
															
														// Modifica grafica nella tabella
														t.getRowValue().set( Colonna , "" + New_Val );
														
														
														// Modifica all'interno del DataSet
														Fenomeno<Double> Fen = (Fenomeno<Double>) DS.getFenomeno(Riga);
														Fen.ModificaCaratteristica( Colonna-1, New_Val );
													}
													catch (NumberFormatException e) 
													{
														t.getRowValue().set( Colonna , t.getOldValue() );
													}	
											}
            });
			
			Table_DataSet.getColumns().add(Col);
		}
		
		for( int i=0; i<DS.getNumeroFen(); i++ )
		{
			ObservableList<String> row = FXCollections.observableArrayList();
			
			row.add("" + i);
			
			for( int k=0; k<DS.getNumCar(); k++ )
				row.add("" + DS.getFenomeno(i).getCaratteristica(k));
					
			srcData.add(row);
		}
		
        Table_DataSet.setItems(srcData);
        layout.setCenter(Table_DataSet);
	}
	
	private void PrepareCheckButton()
	{
		VBox Layout = new VBox();
		
		for( int i=0; i<DS.getNumCar(); i++ )
		{
			CheckBox checkBox = new CheckBox("Caratteristica " + (i+1));
			checkBox.setPadding(new Insets(15, 0, 0, 20));
			checkBox.setMinHeight( 20 );
			checkBox.setMinWidth( 40 );
			Layout.getChildren().add(checkBox);
		}
		
		HBox Hb = (HBox) layout.getBottom();
		Hb.getChildren().set( 0, Layout );
		hBox = Hb;
	}
	
	private void Elimina_Caratt()
	{
		boolean Esito = false;
		VBox vBox = (VBox) hBox.getChildren().get(0);
		
		for( int i=0; i<vBox.getChildren().size(); i++ )
		{
			CheckBox checkBox = (CheckBox)vBox.getChildren().get(i);
			if(checkBox.isSelected() == true )
			{
				DS.Delete_Caratteristica(i);
				Esito = true;
			}
		}
		
		if( Esito == true )
		{
			PrepareListView();
			PrepareCheckButton();
		}
	}
	
	private void SalvaDS()
	{
		Allert_Message Mess = null;
		
		if( New_NameDS.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Campo Nome DataSet Vuoto !!!" );
		else if(TestMain.getSessione_InCorso().SalvaDataSet( DS, New_NameDS.getText() ) == false )
			Mess = new Allert_Message( TestMain.getStage(), "Nome DataSet già esistente !!!" );
		
		if( Mess == null )
			dialog.close();
	}
}