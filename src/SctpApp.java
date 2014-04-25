import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

import sun.rmi.runtime.Log;

public class SctpApp implements Runnable {

	// no of app execution per node
	int count;
	SctpMessage newmsg=new SctpMessage(SctpServer.mynodeno);
	SctpMessage termmsg=new SctpMessage(SctpServer.mynodeno);
	long mtt=0;
	BufferedWriter bufferedWriter;
	FileWriter fileWriter;
	String fileName = "/home/004/n/nx/nxc121930/AOS/Project2/"+SctpServer.mynodeno+".log";
	Random rng;
	ExponentialGenerator gen;
	final long oneMinute;
	
	
	public SctpApp() {
		System.out.println("Application : Application is initiated");
		SctpMain.LOG.logger.info("\tApplication : Application is initiated");
		newmsg.msgtype="SENDING";
		count = 0;
		
		oneMinute = Configfilereader.MTT;
		rng = new MersenneTwisterRNG();
		 
		// Generate events at an average rate of 10 per minute.
		 gen= new ExponentialGenerator(Configfilereader.numberofmessages, rng);
		 boolean running = true;
		

		try {
			// clear the content
			fileWriter = new FileWriter(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	long findMTT()
	{
	
		 mtt = Math.round(gen.nextValue() * oneMinute);
			
		 System.out.println("MTT : "+mtt);
		return mtt;
	}
	
	
	@Override
	public void run() {

		

			do {
				try {
	
					
//					if(SctpServer.mynodeno==1)
//					Thread.sleep(100);
//					else
//						Thread.sleep(5000);
					
					Thread.sleep(findMTT());
					
					newmsg.msgtype="SENDING";
					SctpQueueProc.addQ(newmsg);
					SctpMain.LOG.logger.info("\tApplication : new msg added to Queue");
				
						count++;

					

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} 

			} while (count < Configfilereader.numberofmessages);

		
						
			
			termmsg.msgtype="SENDING_TERMINATION";
			SctpQueueProc.addQ(termmsg);

			//System.out.println("Send mesg thread exiting " + count);
			SctpMain.LOG.logger.info("\tApplication :Send mesg thread exiting ");
		
	}
}
