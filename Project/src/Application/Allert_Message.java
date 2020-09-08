package Application;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Allert_Message 
{
	public Allert_Message( Stage P, String E )
	{
		Alert alertBox = new Alert(Alert.AlertType.ERROR, "Errore", ButtonType.OK );
		
		alertBox.setContentText(E);
		
		alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.initOwner(P);                      
        alertBox.showAndWait();
	
        if (alertBox.getResult() == ButtonType.OK) 
        	alertBox.close();   	
	}
}
