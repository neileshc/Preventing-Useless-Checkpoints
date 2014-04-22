import java.util.LinkedList;
import java.util.Queue;



public class SctpQueueProc implements Runnable {

	public static Queue<SctpMessage> queueproc=new LinkedList<>();
		
	
	public static void addQ(SctpMessage msg) {
		
		queueproc.add(msg);
		//System.out.println("\nQUEUE: message added to queue");
	}
	
		
	@Override
	public void run() {
		
		while(true)
		{
			System.out.print("");
			
			if(!queueproc.isEmpty())
			{
				if(queueproc.peek().msgtype.equals("RECEIVED"))
				{
					SctpMain.Processdata(queueproc.poll());
					
									
				}
				else if(queueproc.peek().msgtype.equals("SENDING"))
				{
					queueproc.poll();
					SctpMain.sm.msgtype="SENDING";
					SctpVectorClock.send_msg_flag=true;					
					
										
					
				}
				else if(queueproc.peek().msgtype.equals("CheckPoint"))
				{
					queueproc.poll();
					
					SctpCheckpoint.take_Checkpoint();
				
					
				}
				
			}
			
			
		}
		
		
	}

}
