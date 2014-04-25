import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.*;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

public class SctpServer extends Thread{
    static int SERVER_PORT;
    int mynodeno;
  //  HashMap<Integer,SctpChannel> ClientChannels= new HashMap<>(); 
    
   
    public SctpServer(int nodeno) {
    	SERVER_PORT = Integer.parseInt(Configfilereader.Machineport[(nodeno-1)]);
    	mynodeno=nodeno;
}

	public void run() {
        SctpServerChannel ssc;
		try {
			
			
		ssc = SctpServerChannel.open();
		
		
		
        InetSocketAddress serverAddr = new InetSocketAddress(SERVER_PORT);
        ssc.bind(serverAddr);

        ByteBuffer buf = ByteBuffer.allocate(120);
        
        //SctpChannel sc = ssc.accept();
        ArrayList<SctpChannel> sc=new ArrayList<>();
        for(int j=0;j<Configfilereader.totalnodes;j++)
		 {
        	if(j==(mynodeno-1))
        			continue;
        	sc.add(ssc.accept());
		 }
        
        for(int j=0;j<sc.size();j++)
        	{
        	SendMsg(buf,sc.get(j));
        	}
            
      
        
        //sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

void SendMsg(ByteBuffer buf, SctpChannel sc)
{
	try {    
		SctpMsg newmsg = new SctpMsg();
		newmsg.msg = " message creatr is "+SERVER_PORT;
		Object msg = newmsg;
		buf= ByteBuffer.wrap(seriliazeUtil.Serialize(msg));
		//buf.put(SctpMsg.serialize(newmsg));
    	//buf.putInt(1).flip();
		//buf.flip();
        /* send the message on the US stream */
        MessageInfo messageInfo = MessageInfo.createOutgoing(null,
                                                             0);
        sc.send(buf, messageInfo);
        buf.clear();
          
          
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}