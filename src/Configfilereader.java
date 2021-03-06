import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Configfilereader {
	public static Integer totalnodes;
	public static boolean dontcountmsg=false;
	public static int numberofmessages = 0;
	public static int numberofcheckpoints = 0;
	public static int MTT = 0;
	public static int[] Machineno = new int[100];
	public static String[] Machinename = new String[100];
	public static int[] Machineport = new int[100];
	int i = -1;
	public static boolean isexample = false;
	public static int ICT = 0;

	public void readfile() throws IOException {
		File file = new File("Config.txt");
		BufferedReader fr = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), "ASCII"));
		String line = fr.readLine();
		// do nothing its para name line

		line = fr.readLine();
		String[] tokens = line.split(" ");

		// Fetching the first line of config file
		totalnodes = Integer.parseInt(tokens[0]);
		numberofmessages = Integer.parseInt(tokens[1]);
		if(numberofmessages==0)
		{
			dontcountmsg=true;
		}
		
		numberofcheckpoints = Integer.parseInt(tokens[2]);
		MTT = Integer.parseInt(tokens[3]);
		ICT = Integer.parseInt(tokens[4]);

		// Fetch rest of the details from config file
		if (tokens[5].equals("EXAMPLE")) {
			isexample = true;
			Configfilereader.totalnodes = 2;
			Configfilereader.numberofmessages = 1;
			Configfilereader.numberofcheckpoints = 2;
			Configfilereader.MTT = 0;
			Configfilereader.ICT = 0;
		}

		while (true) {
			line = fr.readLine();
			if (line == null)
				break;
			i++;
			tokens = line.split(" ");// those are your words

			Machineno[i] = Integer.parseInt(tokens[0]);
			Machinename[i] = tokens[1];
			Machineport[i] = Integer.parseInt(tokens[2]);
		}

	}

}
