package Application;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestMain extends Application{

	static private Stage primaryStage;
	static private Scene mainScena;
	static private BorderPane mainLayout;
	static private Controller_Primary_WS Controller;
	static private Sessione SessioneAperta;

	public static void main(String[] args) {
		SessioneAperta = new Sessione();
		launch(args);
	}
	
	@Override
	public void start(Stage p) {
		try {
			primaryStage = p;
			primaryStage.setTitle("FCM Clustering");
			StartXML();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void StartXML() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TestMain.class.getResource("Primary_Windows.fxml"));
		mainLayout = loader.load();
		Controller = loader.getController();
		mainScena = new Scene(mainLayout);
		mainScena.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		mainLayout.setId("Background_Iniziale");
		primaryStage.setScene(mainScena);
		primaryStage.show();
	}
		
	public static void setCenterLayout( Node Tmp )
	{
		Platform.runLater( () -> mainLayout.setCenter(Tmp) );
	}
	
	public static Stage getStage()
	{
		return primaryStage;
	}
	
	public static Controller_Primary_WS getController()
	{
		return Controller;
	}
	
	public static Sessione getSessione_InCorso()
	{
		return SessioneAperta;
	}
}
