
public class SctpCheckpoint implements Runnable {

	SctpMessage newmsg=new SctpMessage(SctpServer.mynodeno);
	int count=0;
	
	long find_ICT()
	{
		if(SctpServer.mynodeno==1)
			return 15000;
			else
				return 3000;
			
			
			
		
	}
	
	
	public static synchronized void take_Checkpoint()
	{
		
		
		// increment logical clock and clockarray
		SctpVectorClock.increment_Log_clk();
		
		//reset the value of sent_to, taken and min_to
		SctpZDStruct.Reset_all();
		
		System.out.println("CKPT before CP :"+SctpZDStruct.CKPT[SctpServer.mynodeno-1]);
		
		// increment checkpoint number
		SctpZDStruct.CKPT[SctpServer.mynodeno-1]++;
		
				
		System.out.println("\n checkpoint taken");
		System.out.println("CKPT after CP :"+SctpZDStruct.CKPT[SctpServer.mynodeno-1]);
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		do{
			
			try {
				
			// get the ICT time and sleep for that much time	
			if(count>0)
				Thread.sleep(find_ICT());
			
			// take independant chackpoint here
			newmsg.msgtype="CheckPoint";
			SctpQueueProc.addQ(newmsg);
			
		
			count++;
			
			} 
			
			
			
			
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
		}while(count<Configfilereader.numberofcheckpoints); // change this to handle termination
		
		
		//System.out.println("checkpoint thread exiting");
		
	}

	
	
}
