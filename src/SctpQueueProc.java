import java.util.LinkedList;
import java.util.Queue;



public class SctpQueueProc implements Runnable {

	public static Queue<SctpMessage> queueproc=new LinkedList<>();
	public static boolean readytoprocess=true;	
	
	public static synchronized void addQ(SctpMessage msg) {
		
		queueproc.add(msg);
		//readytoprocess=true;
		SctpMain.LOG.logger.info("\tQUEUE: message added to queue");
		
	}
	int count=0;
		
	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted())
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
					SctpMain.LOG.logger.info("\tQUEUE: Processing Receive");
					readytoprocess=true;				
				}
				else if(queueproc.peek().msgtype.equals("SENDING"))
				{
					
					queueproc.poll();
					SctpMain.sm.msgtype="SENDING";
					SctpVectorClock.send_msg_flag=true;		
					count++;
					SctpMain.LOG.logger.info("\tQUEUE: processing send");								
					
				}
				else if(queueproc.peek().msgtype.equals("CheckPoint"))
				{
					queueproc.poll();
					
					SctpCheckpoint.take_Checkpoint();
					SctpMain.LOG.logger.info("\tQUEUE: processing checkpoint");
					readytoprocess=true;
					
				}
				
				else if(queueproc.peek().msgtype.equals("SENDING_TERMINATION"))
				{
					queueproc.poll();
					SctpMain.terminate();
					
					SctpMain.c1.send_terminatation();
					SctpMain.LOG.logger.info("\tQUEUE: processing send termination");
					readytoprocess=true;
					
				}
				
								
			}
			}
			
		}
		
		SctpMain.LOG.logger.info("\tQUEUE PROC: Exiting");
	}

}
