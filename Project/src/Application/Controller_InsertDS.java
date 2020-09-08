package Application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class Controller_InsertDS {

	static BorderPane layout = null;
	
	@FXML
	private Label Title;
	
	@FXML 
	private Label Nome_DS;
	
	@FXML
	private Button Scegli_DS;
	
	@FXML
	private Label Label_Num_Fen;
	
	@FXML
	private Label Label_Num_Caratt;
	
	@FXML
	private TextField UserName;
	
	@FXML
	private PasswordField Password;
	
	@FXML
	private TextField NomeDB;
	
	@FXML
	private TextField NomeTabella;
	
	@FXML
	private Button Avanti_Button;
	
	private String PathAssolutoDS = "";
	
	public static void esegui()
	{
		try
		{
			if( layout == null )
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(TestMain.class.getResource("Insert_DataSet.fxml"));
				layout = loader.load();
				layout.getStylesheets().add(Controller_InsertDS.class.getResource("Style.css").toExternalForm());
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		TestMain.setCenterLayout(layout);
	}
	
	@FXML
	void initialize()
	{
		Title.getStyleClass().add("Title_Page");
		Scegli_DS.getStyleClass().add("button_setting");
		Scegli_DS.setOnAction( e -> SelezioneDataSet() );
		Avanti_Button.getStyleClass().add("button_next");
		Avanti_Button.setOnAction( e -> CheckInfo() );
	}
	
	private void SelezioneDataSet()
	{
		FileChooser File = new FileChooser();
		File.setTitle("Scegli DataSet");
		File.setInitialDirectory( new File( System.getProperty("user.home")) );
		
		File.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Text", "*.txt"),
				new FileChooser.ExtensionFilter("DataSet", "*.data"),
				new FileChooser.ExtensionFilter("CSV", "*.csv"),
				new FileChooser.ExtensionFilter("All Files", "*.*") );
		
		
		File FileScelto = File.showOpenDialog( TestMain.getStage() );
		
		if( FileScelto != null )
		{
			PathAssolutoDS = FileScelto.getAbsolutePath();
			Nome_DS.setText( FileScelto.getName() );
			
			int Elemento[] = TestMain.getSessione_InCorso().Inserimento_File_DataSet(PathAssolutoDS);
			
			Label_Num_Fen.setText("" + Elemento[0]);
			Label_Num_Caratt.setText("" + Elemento[1] );
		}
	}
	
	private void CheckInfo()
	{
		Allert_Message Mess = null;
		
		if( PathAssolutoDS.length() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Nessun File DataSet Scelto !!!" );

		else if( UserName.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Username DataBase non Inserito !!!" );
		
		else if( NomeDB.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Nome DataBase non Inserito !!!" );
		
		else if( NomeTabella.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Nome Tabella non Inserito !!!" );
		
		if( Mess != null )
			return;
		
		if( TestMain.getSessione_InCorso().Crea_DataBase( UserName.getText(), Password.getText(), NomeDB.getText(), NomeTabella.getText() ) )
			TestMain.getSessione_InCorso().Carica_Stage_ListDataSet();
		else
			Mess = new Allert_Message( TestMain.getStage(), "Inserimento Dati DataBase non Corretto !!!" );	
	}
}