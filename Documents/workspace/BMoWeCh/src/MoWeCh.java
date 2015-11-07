import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;




public class MoWeCh{
	
	public static void main(String[] args) throws Exception{
		// handles the url file and starts the threads
		if(args.length > 0) {
            File file = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String strLine = br.readLine(); strLine !=null; strLine=br.readLine()){
            	BMoWeCh t = new BMoWeCh(strLine);
            	//System.out.println("Thread started: " + strLine);
            	t.start();

            }
            br.close();
		}
		
	}


}

class BMoWeCh extends Thread {
	private Thread t;
	private String s_url;
	
	BMoWeCh(String name){
		s_url=name;
		//System.out.println("Thread Creating" + s_url);
		//Thread created
	}
	public void run(){
		//handles the website check
		URL url;
		//System.out.println("Thread running" + s_url);
		try {
			
			url = new URL ("https://www.googleapis.com/pagespeedonline/v3beta1/mobileReady?url=" + s_url);
              URLConnection uc = url.openConnection();
              BufferedReader in = new BufferedReader(new InputStreamReader(
                      uc.getInputStream(), "UTF-8"));
              String inputLine;
              StringBuilder sb = new StringBuilder();
              while ((inputLine = in.readLine()) != null)
			      sb.append(inputLine);
              in.close();
              
              if (sb.indexOf("\"pass\": true") != -1){
            	  System.out.printf(s_url + ": " + "good \n");
              }
              else{
            	  System.out.printf(s_url + ": " + "bad \n");
              }
		}
		catch (Exception e){
			System.out.println(e);
		}
		//System.out.println("Thread exiting");
	}
	
	public void start(){
		if (t == null){
			
			t = new Thread (this, s_url);
			t.start();
			
		}
		
	}

}
