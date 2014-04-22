
public class SctpVectorClock {
	
// Vector clock values
//public static int[] vector_clk=new int[Configfilereader.totalnodes];
public static int log_clk;
public static boolean send_msg_flag;


static int mynodeno=0;

public SctpVectorClock(int x)
{
//	System.out.println("VectorClock : Initializing Vector clock content to 0");
//	for(int i=0;i<Configfilereader.totalnodes;i++)
//	{
//				vector_clk[i]=0;
//				
//	}
	log_clk=0;
	mynodeno=x;
	send_msg_flag=false;
}


public static int getLog_clk() {
	return log_clk;
}

public static void setLog_clk(int log_clk) {
	SctpVectorClock.log_clk = log_clk;
}

public static void updateLog_clk(int received_log_clk)
{
	  if(log_clk >= received_log_clk)
	  {
		  //do nothing
	  }  
	  else
	    	log_clk =received_log_clk;
	  
increment_Log_clk();
}

public static void increment_Log_clk()
{
log_clk++;

// keeping the zdata struct upto date with own clock
SctpZDStruct.ClockArr[mynodeno-1]=log_clk;
}


//public static int[] getVector_clk() {
//	return vector_clk;
//}
//
//
//
//public static void setVector_clk(int[] vector_clk) {
//	SctpVectorClock.vector_clk = vector_clk;
//}
//
//public static void update_Vector_clk(int[] received_Vector_clk,int received_frm_node)
//{
//	for(int i=0;i<Configfilereader.totalnodes;i++)
//	{
//		//currentvectorClock[i] = Math.max(currentvectorClock[i], receiveVector[i]);
//	    if(vector_clk[i] >= received_Vector_clk[i])
//	    	vector_clk[i] = received_Vector_clk[i];
//	    else
//	    	vector_clk[i] =received_Vector_clk[i];
//	}
//	increment_Vector_clk(received_frm_node);
//}
//
//public static void increment_Vector_clk(int node)
//{
//	vector_clk[node]=vector_clk[node]+1;
//}
//
//
}
