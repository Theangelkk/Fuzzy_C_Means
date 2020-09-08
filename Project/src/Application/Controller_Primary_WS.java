package Application;

import java.awt.Desktop;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class Controller_Primary_WS {

	@FXML
	private MenuItem Spiegazione;
	
	@FXML
	private MenuItem Fuzzy_C_Means;
	
	@FXML
	private MenuItem Autore;
	
	@FXML 
	private Label nomeCompleto;
	
	@FXML 
	private Label TempoAccesso;
	
	@FXML
	private Button Inizia_Cluster;
	
	@FXML
	private Label Fuzzy;

	private Desktop dt = Desktop.getDesktop();
	
	@FXML
	void initialize()
	{	
		Fuzzy.setId("Fuzzy_Text");
		Inizia_Cluster.getStyleClass().add("Inizia_Cluster");
		Inizia_Cluster.setOnAction( e -> 	{	
												DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
												Date date = new Date();
												TempoAccesso.setText(dateFormat.format(date)); 
												Controller_Inserimento_Dati.esegui();
											});
		
		Spiegazione.setOnAction( e -> {
											try
											{
												URI uri = new URI("https://mega.nz/#!KGwTFSBZ!wcWCl8-uipxV36GK1dy7jd4-1GtrcZ-liMHwkqeg0CM");
												dt.browse(uri);
											} 
											catch (Exception e1) 
											{
												e1.printStackTrace();
											}
										});
		
		Fuzzy_C_Means.setOnAction( e -> {
			try
			{	
				URI uri = new URI("https://mega.nz/#!3DxBRAoa!EOZ2OcGgQ7-8qbQ_N4d5bvJAgmQQtCdjQ7jyHxU_Ndo");
				dt.browse(uri);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
		
		Autore.setOnAction( e -> {
			try
			{
				URI uri = new URI("https://www.linkedin.com/in/angelo-casolaro/");
				dt.browse(uri);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
	}
	
	public void setNomeCompleto( String S )
	{
		nomeCompleto.setText(S);
	}
}