import java.util.LinkedList;
import java.util.Queue;



public class SctpQueueProc implements Runnable {

	public static Queue<SctpMessage> queueproc=new LinkedList<>();
	public static boolean readytoprocess=true;	
	
	public static void addQ(SctpMessage msg) {
		
		queueproc.add(msg);
		//readytoprocess=true;
		//System.out.println("\nQUEUE: message added to queue");
	}
	int count=0;
		
	@Override
	public void run() {
		
		while(true)
		{
			System.out.print("");
			
			if(!queueproc.isEmpty())
			{
				
				if(readytoprocess==true)
				{
				
					readytoprocess=false;
			
					
				if(queueproc.peek().msgtype.equals("RECEIVED"))
				{
					SctpMain.Processdata(queueproc.poll());
					
					readytoprocess=true;				
				}
				else if(queueproc.peek().msgtype.equals("SENDING"))
				{
					
					
					queueproc.poll();
					SctpMain.sm.msgtype="SENDING";
					SctpVectorClock.send_msg_flag=true;		
					count++;
					//System.out.println("message ready for sending " +count );
				//	System.out.println("Queue pending elements " +queueproc.size() );
					
											
					
				}
				else if(queueproc.peek().msgtype.equals("CheckPoint"))
				{
					queueproc.poll();
					
					SctpCheckpoint.take_Checkpoint();
					readytoprocess=true;
					
				}
				
			}
			}
			
		}
		
		
	}

}
