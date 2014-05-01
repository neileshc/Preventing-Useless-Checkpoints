import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class SctpMessage implements Serializable {

	// Declare all the content and add it to appropriate functions here that you
	// want to be a part of message
	private String content;
	private int clock;
	public String msgtype;
	private int[] ClockArr = new int[Configfilereader.totalnodes];
	private int[] CKPT = new int[Configfilereader.totalnodes];
	private boolean[] Taken = new boolean[Configfilereader.totalnodes];

	private int node_no;
	public boolean isterminationmsg;

	public SctpMessage(int nodeno) {
		clock = 0;
		content = "Initialized message";
		node_no = nodeno;
		isterminationmsg = false;

		for (int i = 0; i < Configfilereader.totalnodes; i++) {
			Taken[i] = true;
		}

		Taken[nodeno - 1] = false;

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

	public int[] getClockArr() {
		return ClockArr;
	}

	public void setClockArr(int[] clockArr) {
		ClockArr = clockArr;
	}

	public int getClockArr_index(int nodeno) {
		return ClockArr[nodeno];
	}

	public int[] getCKPT() {
		return CKPT;
	}

	public void setCKPT(int[] cKPT) {
		CKPT = cKPT;
	}

	public int getCKPT_index(int nodeno) {
		return CKPT[nodeno - 1];
	}

	public boolean[] getTaken() {
		return Taken;
	}

	public void setTaken(boolean[] taken) {
		Taken = taken;
	}

	public boolean getTaken_index(int nodeno) {
		return Taken[nodeno - 1];
	}

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
