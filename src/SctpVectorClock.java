public class SctpVectorClock {

	// Vector clock values
	public static int log_clk;
	public static boolean send_msg_flag;
	public static int totalmsgsreceived;
	public static int totalmsgssent;
	public static int totalIndependantCKPT;
	public static int totalForcedCKPT;

	static int mynodeno = 0;

	public SctpVectorClock(int x) {
		log_clk = 0;
		mynodeno = x;
		send_msg_flag = false;
		totalmsgsreceived=0;
		totalmsgssent=0;
		totalIndependantCKPT=0;
		totalForcedCKPT=0;
	}

	public static int getLog_clk() {
		return log_clk;
	}

	public static void setLog_clk(int log_clk) {
		SctpVectorClock.log_clk = log_clk;
	}

	public static void updateLog_clk(int received_log_clk) {
		if (log_clk >= received_log_clk) {
			// do nothing
		} else
			log_clk = received_log_clk;

		increment_Log_clk();
	}

	public static void increment_Log_clk() {
		log_clk++;

		// keeping the zdata struct upto date with own clock
		SctpZDStruct.ClockArr[mynodeno - 1] = log_clk;
	}

}
