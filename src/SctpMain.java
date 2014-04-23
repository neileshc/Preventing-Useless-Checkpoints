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
	public static SctpApp sap;
	public static SctpLogger LOG;
	public static SctpQueueProc QP;
	public static boolean Oktoterminate=false;
	public static boolean terminationreceived=false;
	public static int nodeno;
	public static SctpCheckpoint ckpt;
	public static SctpZDStruct zstruct;
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
				System.out.println("Process Data : Termination Request Received ");
				terminationreceived=true;
				Oktoterminate=true;
			}
			
			else
			{
				System.out.println("\n Message received");
			
				// evaluate if need forced checkpoint
				
				// verify Z cycle path
				if((newmsg.getCKPT_index(nodeno)==SctpZDStruct.getCKPT_index(nodeno)) && (newmsg.getTaken_index(nodeno)) )
				{
					// Take forced check point
					SctpCheckpoint.take_Checkpoint();
					System.out.println("Forced Checkpoint taken :Type 1*************************************");
					
				}
				else 
				{
					for(int i =0;i<Configfilereader.totalnodes;i++)
					{
						// if sent_to is true for some process
						if(SctpZDStruct.Sent_to[i])
						{
							int received_from =newmsg.getNode_no();
							
							if(newmsg.getClock()>SctpZDStruct.Min_to[i])
							{
								
								if(newmsg.getClock()>Math.max(SctpZDStruct.ClockArr[i],newmsg.getClockArr_index(i)))
								{
									SctpCheckpoint.take_Checkpoint();
									System.out.println("Forced Checkpoint taken :Type 2***************************");
								}
								
								}
								
							}
							
						}
							
						
					}
					
											
				
				//Update  clock
				SctpVectorClock.updateLog_clk(newmsg.getClock());   
			 
				// update clock array
				SctpZDStruct.updateClockArr(newmsg.getClockArr());
						
				// update CKPT and taken
				SctpZDStruct.updateCKPTTaken(newmsg.getCKPT(), newmsg.getTaken());
				
				
				//System.out.println("\n Clock received value :\t"+newmsg.getClock());
				System.out.println("\n Clock updated value :\t"+SctpVectorClock.log_clk);
			}
			
						
		}
	
	
	}
	
	public static void main(String args[]) throws InterruptedException,
			IOException {
		LOG=new SctpLogger();
		nodeno = Integer.parseInt(args[0]);
		Configfilereader r = new Configfilereader();
		r.readfile();
	
		
		s1 = new SctpServer(nodeno);
		c1 = new SctpClient(nodeno);
		sm = new SctpMessage(nodeno);
		sv = new SctpVectorClock(nodeno);
		QP= new SctpQueueProc();
		ckpt = new SctpCheckpoint();
		sap = new SctpApp();
		zstruct=new SctpZDStruct();
		
		Thread t5=new Thread(QP);
		t5.start();
		
		Thread t1 = new Thread(s1);
		t1.start();
				
		Thread.sleep(1000);
		
		System.out
		.println("Main : Press enter to start Application...Make sure your all node servers are UP");
		br.readLine();
				
		Thread t2 = new Thread(c1);
		t2.start();

			// start the application thread
		
		Thread t4 = new Thread(sap);
		t4.start();
		
		Thread t6 = new Thread(ckpt);
		t6.start();

		
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
