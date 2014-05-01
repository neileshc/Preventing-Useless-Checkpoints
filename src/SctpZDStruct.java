import javax.security.auth.login.Configuration;

public class SctpZDStruct {

	// true if the message is sent to that process
	public static boolean[] Sent_to = new boolean[Configfilereader.totalnodes];

	// timestamp of the first message sent to the process after last check point
	public static int[] Min_to = new int[Configfilereader.totalnodes];

	// Clock value of all the process as per my knowledge
	public static int[] ClockArr = new int[Configfilereader.totalnodes];

	// Number of checkpoints taken by all process as per my knowledge
	public static int[] CKPT = new int[Configfilereader.totalnodes];

	// info about causal path between that process and me
	public static boolean[] Taken = new boolean[Configfilereader.totalnodes];

	public SctpZDStruct() {
		for (int i = 0; i < Configfilereader.totalnodes; i++) {
			Sent_to[i] = false;
			Min_to[i] = Integer.MAX_VALUE;
			ClockArr[i] = 0;
			CKPT[i] = 0;
			Taken[i] = true;

		}

		Taken[SctpServer.mynodeno - 1] = false;

	}

	public static boolean[] getSent_to() {
		return Sent_to;
	}

	public static void setSent_to(boolean[] sent_to) {
		Sent_to = sent_to;
	}

	public static void setSent_to_index(boolean sent_to, int i) {
		Sent_to[i] = sent_to;
	}

	public static void Reset_all() {
		for (int i = 0; i < Configfilereader.totalnodes; i++) {
			Sent_to[i] = false;
			Min_to[i] = Integer.MAX_VALUE;
			Taken[i] = true;
		}
		Taken[SctpServer.mynodeno - 1] = false;
	}

	public static int[] getMin_to() {
		return Min_to;
	}

	public static void setMin_to(int[] min_to) {
		Min_to = min_to;
	}

	public static void setMin_to_index(int min_to, int i) {
		if (Min_to[i] > min_to)
			Min_to[i] = min_to;

	}

	public static int[] getClockArr() {
		return ClockArr;
	}

	public static void setClockArr(int[] clock) {
		ClockArr = clock;
	}

	public static void updateClockArr(int[] clock) {
		for (int i = 0; i < Configfilereader.totalnodes; i++) {
			if (ClockArr[i] < clock[i])
				ClockArr[i] = clock[i];
		}
	}

	public static int[] getCKPT() {
		return CKPT;
	}

	public static void setCKPT(int[] cKPT) {
		CKPT = cKPT;
	}

	public static int getCKPT_index(int nodeno) {
		return CKPT[nodeno - 1];
	}

	public static void updateCKPTTaken(int[] mckpt, boolean[] mtaken) {
		for (int k = 0; k < mckpt.length; k++) {
			if (k == SctpServer.mynodeno)
				continue;

			if (mckpt[k] > CKPT[k]) {
				CKPT[k] = mckpt[k];
				Taken[k] = mtaken[k];
			} else if (mckpt[k] == CKPT[k]) {
				Taken[k] = (mtaken[k] || Taken[k]);
			}
		}

	}

	public static boolean[] getTaken() {
		return Taken;
	}

	public static void setTaken(boolean[] taken) {
		Taken = taken;
	}

}
