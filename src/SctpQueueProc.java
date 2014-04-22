import java.util.LinkedList;
import java.util.Queue;



public class SctpQueueProc implements Runnable {

	public static Queue<SctpMessage> queueproc=new LinkedList<>();
		
	
	public static void addQ(SctpMessage msg) {
		
		queueproc.add(msg);
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
				if(queueproc.peek().msgtype.equals("RECEIVED"))
				{
					SctpMain.Processdata(queueproc.poll());
					
									
				}
				else if(queueproc.peek().msgtype.equals("SENDING"))
				{
//					do{
//						
//						System.out.print("");
//						
//					}while(SctpVectorClock.send_msg_flag=true);
					
					
					queueproc.poll();
					SctpMain.sm.msgtype="SENDING";
					SctpVectorClock.send_msg_flag=true;		
					count++;
					System.out.println("message ready for sending " +count );
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
					
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
