package Application;

import Caricamento_Finestra.RingProgressIndicator;
import Caricamento_Finestra.WorkerThread;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;

public class Controller_Wait_Clustering 
{
	public static void esegui( int Time )
	{
		RingProgressIndicator ringProgressIndicator = new RingProgressIndicator();
		ringProgressIndicator.setRingWidth(1200);
		ringProgressIndicator.makeIndeterminate();
	
		BorderPane layout = new BorderPane();
		layout.setCenter(ringProgressIndicator);
		
		Thread t1 = new WorkerThread(ringProgressIndicator, Time);
		Thread t2 = new ControllorThread(t1);
		Platform.runLater( () -> t1.start() );
		Platform.runLater( () -> t2.start() );
		
		TestMain.setCenterLayout(layout);
		TestMain.getSessione_InCorso().AvviaClustering();
	}
	
	private static class ControllorThread extends Thread
	{
		private Thread T = null;
		
		public ControllorThread( Thread T1 ) 
		{
			T = T1;
		}
		
		@Override
		public void run()
		{
			try
			{
				T.join();
				TestMain.getSessione_InCorso().Carica_Stage_ListClustering();
				
			}
			catch ( InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}