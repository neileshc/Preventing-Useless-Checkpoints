import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import sun.rmi.runtime.Log;

public class SctpApp implements Runnable {

	// no of app execution per node
	int count;
	SctpMessage newmsg=new SctpMessage(SctpServer.mynodeno);
	
	BufferedWriter bufferedWriter;
	FileWriter fileWriter;
	String fileName = "/home/004/n/nx/nxc121930/AOS/Project2/"+SctpServer.mynodeno+".log";
	

	public SctpApp() {
		System.out.println("Application : Application is initiated");
		SctpMain.LOG.logger.info("\tApplication : Application is initiated");
		newmsg.msgtype="SENDING";
		count = 0;

		try {
			// clear the content
			fileWriter = new FileWriter(fileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		

			do {
				try {
					
					// Inducing random sleep so get random requests for critical section
					Random r = new Random();
					Thread.sleep(r.nextInt(5000));

					
					SctpQueueProc.addQ(newmsg);
					
				//	System.out.println("\nAPP: message added to queue");
						count++;

			
					Thread.sleep(3000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} 

			} while (count < Configfilereader.numberofmessages);

		

		try {
			// if i am not requestor node and i have token then i terminnate
			// immidiately before receiving request from
			// other nodes, sleep helps avoid that
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
//		// Termination Initiation
//		int i = 0;
//		if (SctpToken.doihavetoken == true) {
//			for (i = 0; i < Configfilereader.totalnodes; i++) {
//				if (SctpVectorClock.Request_Node[i] == SctpToken.tokenVector[i]) {
//					continue;
//				} else
//					break;
//			}
//
//			if (i == Configfilereader.totalnodes) {
//				System.out
//						.println("Initiating termination as all of the requests are satisfied");
//				SctpMain.Oktoterminate = true;
//
//			}
//		}
		
	}
}
