import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SctpApp implements Runnable {

	// no of app execution per node
	int count;
	SctpMessage newmsg = new SctpMessage(SctpServer.mynodeno);
	SctpMessage termmsg = new SctpMessage(SctpServer.mynodeno);
	long mtt = 0;
	BufferedWriter bufferedWriter;
	FileWriter fileWriter;
	String fileName = "/home/004/n/nx/nxc121930/AOS/Project3/"
			+ SctpServer.mynodeno + ".log";
	protected static Random rand= new Random();


	public SctpApp() {
		System.out.println("Application : Application is initiated");
		SctpMain.LOG.logger.info("\tApplication : Application is initiated");
		//newmsg.msgtype = "SENDING";
		count = 0;
  

		try {
			// clear the content
			fileWriter = new FileWriter(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static double findMTT() {

		// code for example simulation
		if (Configfilereader.isexample) {
			if (SctpServer.mynodeno == 1)
				return 100;
			else
				return 5000;

		} else {
	
			
		double U=rand.nextDouble();
		// System.out.println("\tApplication : U values : "+ U);
        double lamda= ((double)1/(double)Configfilereader.MTT);
        double X;
        double mid = Math.log(1-U);
        double mid2= mid/lamda;
        X= -1 * mid2;
        SctpMain.LOG.logger
        .info("\tApplication : MTT values : "+ X);
        return X;
		        
		        
			
		}
	}

	@Override
	public void run() {

		do {
			try {

				Thread.sleep((long)findMTT());

				newmsg.msgtype = "SENDING";
				SctpQueueProc.addQ(newmsg);
				SctpMain.LOG.logger
						.info("\tApplication : new msg added to Queue");

				count++;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();

			}
			
			if(!Configfilereader.dontcountmsg)
			{
				if(count >= Configfilereader.numberofmessages)
				break;
			
			}

		} while (!Thread.currentThread().isInterrupted());

		if(Configfilereader.dontcountmsg)
		{
// do nothing
		}
		else
		{
			SctpMessage termmsg =new SctpMessage(SctpServer.mynodeno);
			termmsg.msgtype = "SENDING_TERMINATION";
			SctpQueueProc.addQ(termmsg);
			
		}
		// System.out.println("Send mesg thread exiting " + count);
		SctpMain.LOG.logger.info("\tApplication :Send mesg thread exiting ");

	}
}
