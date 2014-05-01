import java.util.Random;

public class SctpCheckpoint implements Runnable {

	SctpMessage newmsg = new SctpMessage(SctpServer.mynodeno);
	static int count = 0;
	protected static Random rand= new Random();
    Random R =new Random();
    
    public static long find_ICT() {
		// code for example simulation
		if (Configfilereader.isexample) {
			if (count > 0) {
				if (SctpServer.mynodeno == 1)
					return 15000;
				else
					return 3000;
			} else
				return 10;

		} 
		
		// normal execution code
		else {
			
			double start,end;
	        start=0.5 * (Configfilereader.ICT*1000);
	        end= 1.5 * (Configfilereader.ICT*1000);
	        double range = end - start;
	        double scaled = rand.nextDouble() * range;
	        double shifted = scaled +start;
	       // shifted = shifted*
	        System.out.println("ICT :"+shifted);
	        SctpMain.LOG.logger.info("\tCHECKPOINT : ICT mean value: "+shifted);
	        return (long)shifted;
					
			
		}

	}

	public static synchronized void take_Checkpoint() {
		// increment logical clock and clockarray
		SctpVectorClock.increment_Log_clk();

		// reset the value of sent_to, taken and min_to
		SctpZDStruct.Reset_all();

		// increment checkpoint number
		SctpZDStruct.CKPT[SctpServer.mynodeno - 1]++;

		System.out.println("Checkpoint taken");
		System.out.println("CKPT Value :"
				+ SctpZDStruct.CKPT[SctpServer.mynodeno - 1]);
		SctpMain.LOG.logger.info("\tCHECKPOINT : Checkpoint taken");
		SctpMain.LOG.logger.info("\tCHECKPOINT : CKPT Value :"
				+ SctpZDStruct.CKPT[SctpServer.mynodeno - 1]);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		do {
			try {

				// get the ICT time and sleep for that much time
				Thread.sleep(find_ICT());

				// take independant chackpoint here
				newmsg.msgtype = "CheckPoint";
				SctpQueueProc.addQ(newmsg);
				SctpMain.LOG.logger.info("\tCHECKPOINT : new CP added to q");
				count++;
			}

			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (count < Configfilereader.numberofcheckpoints); // change this
																// to handle
		if(Configfilereader.dontcountmsg)
		{
			SctpMessage termmsg =new SctpMessage(SctpServer.mynodeno);
			termmsg.msgtype = "SENDING_TERMINATION";
			SctpQueueProc.addQ(termmsg);
			SctpMain.t4.interrupt();
			
		}
		SctpMain.terminate();
		
		SctpMain.LOG.logger.info("\tCHECKPOINT : checkpoint thread exiting");
	}

}
