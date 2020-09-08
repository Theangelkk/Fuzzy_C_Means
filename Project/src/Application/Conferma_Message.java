package Application;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Conferma_Message 
{
	public Conferma_Message( Stage P, String E )
	{
		Alert alertBox = new Alert(Alert.AlertType.CONFIRMATION, "Conferma", ButtonType.YES, ButtonType.NO );
		
		alertBox.setContentText(E);
		
		alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.initOwner(P);                      
        alertBox.showAndWait();
	
        if (alertBox.getResult() == ButtonType.YES) 
        {
        	TestMain.getSessione_InCorso().SalvaClustering();
        	Platform.exit();     	
        }
	}
}