import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Configfilereader {
	
	public static Integer totalnodes;
	public static String[] Machineno = new String[100];
	static String[] Machinename = new String[100];
	static String[] Machineport = new String[100];
	
	int i =-1;
	public void readfile() throws IOException
	{
		File file = new File("Config.txt");
		
		    BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ASCII"));
		    String line = fr.readLine();
           
            String[] tokens = line.split(" ");
            totalnodes= Integer.parseInt(tokens[0]);
		    
		    while(true)
	        {
	            line = fr.readLine();
	            if(line==null)
	                break;
	            i++;
	            tokens = line.split(" ");//those are your words
	           
			    Machineno[i] = tokens[0];
			    Machinename[i] = tokens[1];
			    Machineport[i]= tokens[2];
	            	            
	            System.out.println(Machineno[i]);
			    System.out.println(Machinename[i]);
			    System.out.println(Machineport[i]);
			    
	            
	        }
	  

		
	}
	
	public void findmyserversocketport(Integer node)
	{
		
	
	}
	
	public void findmyclientsocketports(Integer node)
	{
	
	}
	

	
}
