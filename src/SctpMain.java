import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sun.corba.se.impl.orbutil.concurrent.SyncUtil;
import com.sun.nio.sctp.MessageInfo;

public class SctpMain {
	public static int msgexch = 0;
	public static SctpVectorClock sv;
	public static SctpMessage sm;
	public static SctpServer s1;
	public static SctpClient c1;
	public static SctpValidateReply svr;
	public static SctpApp sap;
	public static SctpLogger LOG;
	public static boolean Oktoterminate=false;
	public static boolean terminationreceived=false;
	public static int nodeno;
	public static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));
	
	
	// Synchronized block for processing incoming request 
	// Server threads will send the data here as they receive
	public static synchronized  void Processdata(SctpMessage newmsg)
	{
		// do message processing here
		
		if (newmsg != null) {
			
		
				
			if(newmsg.isterminationmsg)
			{
				System.out.println("Process Data : Termination Received ");
				terminationreceived=true;
				Oktoterminate=true;
			}
			
			else
			{
				System.out.println("\n Message received");
				//Update  clock
				SctpVectorClock.updateLog_clk(newmsg.getClock());   
				
				System.out.println("\n Clock received value :\t"+newmsg.getClock());
				System.out.println("\n Clock updated value :\t"+SctpVectorClock.log_clk);
			}
			
						
		}
	}
	
	
	
	public static void main(String args[]) throws InterruptedException,
			IOException {

		nodeno = Integer.parseInt(args[0]);
		Configfilereader r = new Configfilereader();
		r.readfile();
	
		
		SctpServer s1 = new SctpServer(nodeno);
		SctpClient c1 = new SctpClient(nodeno);
		sm = new SctpMessage(nodeno);
		sv = new SctpVectorClock(nodeno);
		svr=new SctpValidateReply();
		LOG=new SctpLogger();	
		
		Thread t1 = new Thread(s1);
		t1.start();
				
		Thread.sleep(1000);
		
		System.out
		.println("Main : Press enter to start Application...Make sure your all node servers are UP");
		br.readLine();
				
		Thread t2 = new Thread(c1);
		t2.start();

			// start the application thread
		SctpApp sap = new SctpApp();
		Thread t4 = new Thread(sap);
		t4.start();

		
		while(Oktoterminate==false)
		{
			// do nothing
			if(Oktoterminate==true)
				break;
			
			System.out.print("");
			
		}
		
		System.out.println("Closing the connections");
		
	//	Thread.sleep(5000);
			
		if(terminationreceived==true)
		{
			if(c1.send_terminatation())
			{
			t2.interrupt();
			t1.interrupt();
			}
		}
		else if(c1.send_terminatation())
		{
		
			t2.interrupt();
			t1.interrupt();
		}
			
		
		Thread.sleep(5000);
		
//		for (int i = 0; i < Configfilereader.totalnodes; i++) {
//			System.out.print(SctpVectorClock.Request_Node[i] + "\t");
//			
//		}
		
	}
	
	
	
}
