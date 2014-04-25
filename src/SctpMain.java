import java.io.IOException;


public class SctpMain {
	public static void main(String args[]) throws InterruptedException, IOException 
	{
		//Configfilereader r= new Configfilereader();
		//r.findmyserversocketport(Integer.parseInt(args[1]));
		//r.findmyclientsocketports(Integer.parseInt(args[1]));
		//r.readfile();
		
		int nodeno = Integer.parseInt(args[0]);
	
		SctpServer s1= new SctpServer(nodeno);
		Thread t1 = new Thread(s1);
		t1.start();		
		
				
		SctpClient c1= new SctpClient(nodeno);
		Thread t2 = new Thread(c1);
		t2.start();
		
		t1.join();
		t2.join();
		
		
	}

}
