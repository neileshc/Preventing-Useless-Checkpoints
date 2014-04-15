import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Random;

import com.sun.nio.sctp.AbstractNotificationHandler;
import com.sun.nio.sctp.AssociationChangeNotification;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.ShutdownNotification;

public class SctpClient extends Thread {
	static Integer SERVER_PORT;
	String machine = "";
	public static int mynodeno = 0;
	public static final int MESSAGE_SIZE = 10000;
	public int msgcntr = 0;
	ArrayList<SctpChannel> sc = new ArrayList<>();  // holding all connections with other servers
	
	
	SctpClient(int nodeno) {
		mynodeno = nodeno;
	
				
	}

	public void run() {
		
		SctpChannel temp_sc = null;
		try {
			// Loop for making desired connections
			for (int j = 0; j < Configfilereader.totalnodes; j++) {
				if (j == (mynodeno - 1))
					continue;

				// Allocating the Server port for connection
				SERVER_PORT = Configfilereader.Machineport[j];
				machine = Configfilereader.Machinename[j];

				// Create a socket address for server
				InetSocketAddress serverAddr = new InetSocketAddress(machine,
						SERVER_PORT);

				// Open a channel.
				temp_sc = SctpChannel.open(serverAddr, 0, 0);
				if(temp_sc==null)
				{
					j=j-1;
					continue;
				}
				// Save this servers connection in list
				sc.add(temp_sc);
						
				System.out.println("Client : Client connected to Machine: " + machine
						+ " at Port number: " + SERVER_PORT);
			}

			SctpMessage newmsg = null;
			
			// Inducing sleep as when connection established we may see corrupt
						// stream error
						this.sleep(3000);

								
						
						
//------------------------------------------------------------------------------------------------------			
					
			int temp=0;
			
						Random rand= new Random();
						do{	
						
//							 						
							if(SctpVectorClock.send_msg_flag)
							{
							temp =rand.nextInt(100);
							System.out.println("\n Random number :\t"+temp);
							temp = (temp % sc.size());	
							
								System.out.println("\n Clock value before send :\t"+SctpVectorClock.log_clk);
								SctpVectorClock.increment_Log_clk();
								
								SctpMain.sm.setClock(SctpVectorClock.log_clk);
								
								System.out.println("\nProcessed  Random number :\t"+temp);
								// send message randomely to any node
								SendMsg(sc.get(temp));
								
								System.out.println("\n Clock value after send :\t"+SctpVectorClock.log_clk);
								System.out.println("\n Message sent");
								SctpVectorClock.send_msg_flag=false;
								
							}
									
							
			
							
						}while(!Thread.currentThread().isInterrupted());
						
						System.out.println("Client : Exiting ");
		}

		catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public boolean send_terminatation()
	{
		SctpMain.sm.isterminationmsg=true;
			
		// Broadcast message to every node in network
					for (int j = 0; j < sc.size(); j++) {
										
						SendMsg(sc.get(j));
						}
		
		
		return true;
	}
	
	
	public void SendMsg(SctpChannel sc) {
		// Buffer to hold messages in byte format
		ByteBuffer buf = ByteBuffer.allocate(MESSAGE_SIZE);
		try {
			
				SctpMain.sm.setContent("\n Hello from Machine : "+Configfilereader.Machinename[(mynodeno - 1)] +"(Port : "
						+ SERVER_PORT );
			
			// Before sending messages add additional information about the
			// message
			MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);

			buf.clear();

			// convert the string message into bytes and put it in the byte
			// buffer
			buf.put(SctpMessage.serialize(SctpMain.sm));

			// Reset a pointer to point to the start of buffer
			buf.flip();

			// Send a message in the channel (byte format)
			sc.send(buf, messageInfo);
			
			// System.out.println("Server" + newmsg.getContent());

			buf.clear();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public String byteToString(ByteBuffer byteBuffer) {
		byteBuffer.position(0);
		byteBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byteBuffer.remaining()];
		byteBuffer.get(bufArr);
		return new String(bufArr);
	}

}