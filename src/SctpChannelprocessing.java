import java.nio.ByteBuffer;

import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;

public class SctpChannelprocessing implements Runnable {

	SctpChannel sc;

	public SctpChannelprocessing(SctpChannel sc) {
		this.sc = sc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (true) {
			
			if(SctpMain.Oktoterminate)
			{
				break;
			}

			// its blocking call
			SctpMessage newmsg = null;
			newmsg = receiveMsg(sc);

			if (newmsg != null) {

				newmsg.msgtype = "RECEIVED";
				SctpQueueProc.queueproc.add(newmsg);
				
				
				if (newmsg.isterminationmsg == true) {
				
					System.out.println(" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%termination message received closing channels %%%%%%%%%%%%%%%%%%%%%%%%%%%");
					break;
				}
				
			}
			
			

		}

		SctpMain.LOG.logger.info("\tChannel Processing : Exiting ");
	}

	SctpMessage receiveMsg(SctpChannel sc) {

		SctpMessage newmsg = null;
		ByteBuffer buf = ByteBuffer.allocate(SctpClient.MESSAGE_SIZE);

		try {
			MessageInfo messageInfo = null;
			messageInfo = sc.receive(buf, System.out, null);

			// To resolve corrupted stream error
			// you keep checking if this message is after termination and if yes
			// you dont deserialize it
			if (SctpMain.Oktoterminate) {
				return null;
			}

			// Converting bytes to string.
			if (messageInfo != null) {
				newmsg = (SctpMessage) SctpMessage.deserialize(buf.array());

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newmsg;
	}

}
