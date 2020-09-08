package Caricamento_Finestra;

import javafx.application.Platform;

public class WorkerThread extends Thread
{
	private RingProgressIndicator rpi;
	private int Progress = 0;
	private int Time = 0;
	
	public WorkerThread( RingProgressIndicator r, int T ) 
	{
		rpi = r;
		Time = T;
	}
	
	@Override
	public void run()
	{
		while (true)
		{	
			try
			{
				Thread.sleep(Time);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			Platform.runLater( () -> rpi.setProgress(Progress) );
		
			Progress++;
			
			if( Progress > 100 )
				break;
		}
	}
}