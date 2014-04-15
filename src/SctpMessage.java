import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class SctpMessage implements Serializable {
	
	//Declare all the content and add it to appropriate functions here that you want to be a part of message
	private String content;
	//private int[] Vector = new int[Configfilereader.totalnodes];
	private int clock;

	

	private int node_no;
	public boolean isterminationmsg;
	
	public SctpMessage(int nodeno) {
		
		// Initialize Vector clock to 0
//		for (int i = 0; i < Configfilereader.totalnodes; i++) {
//			Vector[i] = 0;
//
//		}
		clock=0;
		content = "Initialized message";
		node_no=nodeno;
		isterminationmsg=false;
	}

	public int getClock() {
		return clock;
	}


	public void setClock(int clock) {
		this.clock = clock;
	}

	public int getNode_no() {
		return node_no;
	}

		
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
//	public int[] getVector() {
//		return Vector;
//	}
//
//	public void setVector(int[] Vector) {
//		this.Vector = Vector;
//	}
		
		
	
	
	public static byte[] serialize(Object obj) throws IOException {
		ObjectOutputStream out;// = new ObjectOutputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		out = new ObjectOutputStream(bos);
		out.writeObject(obj);
		return bos.toByteArray();
	}

	public static Object deserialize(byte[] obj) throws IOException,
			ClassNotFoundException {
		ObjectInputStream in;// = new ObjectOutputStream();
		ByteArrayInputStream bos = new ByteArrayInputStream(obj);
		in = new ObjectInputStream(bos);
		return in.readObject();
	}

}
