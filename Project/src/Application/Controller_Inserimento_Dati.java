package Application;

import java.io.File;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class Controller_Inserimento_Dati {

	static BorderPane layout = null;

	@FXML
	private Label Title;
	
	@FXML
	private Button ButtonImage;
	
	@FXML
	private TextField TextName;
	
	@FXML
	private TextField TextCognome;
	
	@FXML
	private RadioButton Uomo;
	
	@FXML
	private RadioButton Donna;
	
	@FXML
	private DatePicker Nascita;
	
	@FXML
	private ImageView Immagine_Profilo;
	
	@FXML
	private Button Avanti;
	
	public static void esegui()
	{
		try
		{
			if( layout == null )
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(TestMain.class.getResource("Richiesta_Informazioni.fxml"));
				layout = loader.load();
				layout.getStylesheets().add(Controller_Inserimento_Dati.class.getResource("Style.css").toExternalForm());
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
		ButtonImage.getStyleClass().add("button_setting");
		ButtonImage.setOnAction( e -> ScegliImmagine() );
		TextName.textProperty().addListener( (ObservableValue,oldvalue,newvalue) -> ControllaField(TextName,20) );
		TextCognome.textProperty().addListener( (ObservableValue,oldvalue,newvalue) -> ControllaField(TextCognome, 20) );
		Avanti.getStyleClass().add("button_next");
		Avanti.setOnAction( e -> CheckInfo() );
	}
	
	private void ScegliImmagine()
	{
		FileChooser File = new FileChooser();
		File.setTitle("Scegli Immagine");
		File.setInitialDirectory( new File( System.getProperty("user.home")) );
		
		File.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("BITMAP", "*.bmp"),
				new FileChooser.ExtensionFilter("All Files", "*.*") );
		
		File Immagine_Scelta = File.showOpenDialog( TestMain.getStage() );
		
		if( Immagine_Scelta != null )
		{
			Image image = new Image( Immagine_Scelta.toURI().toString() );
			Immagine_Profilo.setImage(image);
		}
	}
	
	private void ControllaField( TextField Campo, int max )
	{
		int Lunghezza = Campo.getLength();
		String Testo = Campo.getText();
		
		if( Lunghezza == 0 )
			return;
	
		Campo.setStyle("-fx-border-color: WHITE");
		Testo = Testo.toUpperCase();
	
		if( VerificaCarattere(Testo.charAt(Lunghezza-1)) || Lunghezza > max )
			Testo = Testo.substring(0, Lunghezza-1);
		
		Campo.setText(Testo);
		TestMain.getController().setNomeCompleto( TextName.getText() + " " + TextCognome.getText() );
	}
	
	private boolean VerificaCarattere( char Carattere )
	{
		if( Carattere >= 'A' && Carattere <= 'Z' )
			return false;
		
		return true;
	}
	
	private void CheckInfo()
	{	
		Allert_Message Mess = null;
		SimpleDateFormat FormatoData = new SimpleDateFormat("yyyy-MM-dd");
		
		if( TextName.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Campo Nome Vuoto !!!" );
			
		else if( TextCognome.getLength() == 0 )
			Mess = new Allert_Message( TestMain.getStage(), "Campo Cognome Vuoto !!!" );
			
		else if( Nascita.getValue() == null )
			Mess = new Allert_Message( TestMain.getStage(), "Inserire Data di Nascita !!!" );
		
		if( Mess != null )
			return;
		
		TestMain.getSessione_InCorso().getUtente().setNome( TextName.getText() );
		TestMain.getSessione_InCorso().getUtente().setCognome( TextCognome.getText() );
		
		String DN = "";
		try
		{
			DN = FormatoData.format( FormatoData.parse(Nascita.getValue().toString()) );
		}
		catch( ParseException e )
		{
			System.out.println("Errore Formato Data!!! ");
		}
		
		TestMain.getSessione_InCorso().getUtente().setDataNascita(DN);
		
		if( Uomo.isSelected() )
			TestMain.getSessione_InCorso().getUtente().setSesso("M");
		else if( Donna.isSelected() )
			TestMain.getSessione_InCorso().getUtente().setSesso("F");
		
		Controller_InsertDS.esegui();	
	}
}