import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Threading {
	private static final int THREADS = 30;
	public static void main(String args[]) throws Exception{
		if(args.length > 0) {
			ExecutorService executor = Executors.newFixedThreadPool(THREADS);
            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            //String strLine;
            //Read File Line By Line
            for (String strLine = br.readLine(); strLine !=null; strLine=br.readLine()){
            	//while ((strLine = br.readLine()) != null)   {
    			Runnable worker = new MyRunnable(strLine);
    			executor.execute(worker);
           		
            	}
    			br.close();
           
            executor.shutdown();
            while(!executor.isTerminated()){
            }	
            }
		}

	
		public static class MyRunnable implements Runnable{
			private String strLine;
			
			MyRunnable(String strLine){
				this.strLine = strLine;
			}
			
			@Override
			public void run(){
    			
	              // Print the content on the console
	              URL url;
				try {
					url = new URL ("https://www.googleapis.com/pagespeedonline/v3beta1/mobileReady?url=" + strLine);
		              URLConnection uc = url.openConnection();
		              BufferedReader in = new BufferedReader(new InputStreamReader(
		                      uc.getInputStream(), "UTF-8"));
		              String inputLine;
		              StringBuilder sb = new StringBuilder();
		              while ((inputLine = in.readLine()) != null)
					      sb.append(inputLine);
		              in.close();
		              
		              if (sb.indexOf("\"pass\": true") != -1){
		            	  System.out.printf(strLine + ": " + "good \n");
		              }
		              else{
		            	  System.out.printf(strLine + ": " + "bad \n");
		              }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            }

		
		}
		
	}