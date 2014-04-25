
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

import com.sun.nio.sctp.AbstractNotificationHandler;
import com.sun.nio.sctp.AssociationChangeNotification;
import com.sun.nio.sctp.HandlerResult;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.ShutdownNotification;


public class SctpClient extends Thread {
    static int SERVER_PORT = 7454;

    static int mynodeno=0;
    
    SctpClient(int nodeid){
    	mynodeno=nodeid;
    	
    }
    
    


public void run() {
		String machine="";
		if(mynodeno==1)
		machine="net01.utdallas.edu";
		else if(mynodeno==2) 
		//	machine="localhost";
		machine="net02.utdallas.edu";
		else
		machine="net03.utdallas.edu";	
		//machine="localhost";
			
        
        ByteBuffer buf = ByteBuffer.allocate(120);
        ArrayList<SctpChannel> sc=new ArrayList<>();
        //SctpChannel sc;
		try {
			
			 for(int j=0;j<Configfilereader.totalnodes;j++)
			 {
	        	if(j==(mynodeno-1))
	        			continue;
	        	
			InetSocketAddress serverAddr = new InetSocketAddress(machine, 
					Integer.parseInt(Configfilereader.Machineport[j]));
			
			SctpChannel temp_sc = SctpChannel.open(serverAddr, 0, 0);
			temp_sc.connect(serverAddr, 0, 0);
			sc.add(temp_sc);
			
				}
			do{
				for(int i=0; i<sc.size();i++)
				{
					sc.get(i).configureBlocking(false);
						receiveMsg(sc.get(i), buf);
				}
			}while(true);
			
           // sc.close();
            
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

void receiveMsg(SctpChannel sc, ByteBuffer buf) 
{  
	 try {
		 
	MessageInfo messageInfo = null;
do {
	messageInfo = sc.receive(buf, System.out,null);
	
    buf.flip();
  
    //SctpMsg m1= new SctpMsg();
    SctpMsg m1 = (SctpMsg) seriliazeUtil.Deserialize(buf);
    
    //m1=(SctpMsg) SctpMsg.deserialize(buf.array());

    System.out.println("message received from"+m1.msg);
    buf.clear();
   
} while (messageInfo != null);

	 } 
	 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


}
  
    
}