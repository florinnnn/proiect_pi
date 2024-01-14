package agentie_imobiliara;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class PythonScript {

	public PythonScript(String receiver) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
	
            // Replace "python3" with "python" if you are using Python 2
            String pythonCommand = "python3";
            String scriptPath = "C:\\Users\\fflor\\eclipse-workspace\\agentie_imobiliara\\mail.py";
            
            ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, scriptPath, "--receiver", receiver);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            /*String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }*/
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);
            InputStream inputStream = process.getInputStream();
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String scriptOutput = scanner.hasNext() ? scanner.next() : "";
            System.out.println("Output from Python script:\n" + scriptOutput);
        
	}
}