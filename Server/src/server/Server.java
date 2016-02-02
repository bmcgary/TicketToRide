package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Server {
	
//------CLASS VARIABLES BELOW--------------------------------------
//-----------------------------------------------------------------
	
	//SET OF USERS BELOW NOT TO BE USED RIGHT NOW
    private static HashSet<User> users=new HashSet<User>(); //populate this guy from the run method of each thread.
    private static int ID_to_give_out=0;
    private static Map<String, PrintWriter> user_to_printwriter=new HashMap<String, PrintWriter>();
    
//-----------------------------------------------------------------
//------END OF CLASS VARIABLES-------------------------------------
    
    
    
    
    
//-----MAIN METHOD FOR SERVER CLASS BELOW--------------------------
//-----------------------------------------------------------------
    
    public static void main(String[] args) throws Exception {
        System.out.println("The Ticket to Ride server is running.");
        
        int port;
        
        if(args.length==0)
        	port=9001;
        else
        	port=Integer.valueOf(args[0]);		//get the integer value of the port that was passed in as an argument.
        
        ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                new Connection_Manager(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }
    
//------------------------------------------------------------------
//-----END OF MAIN METHOD FOR SERVER CLASS--------------------------
    
    
    
    
    
//-----INTERNAL THREAD CLASS THAT HANDLES EACH SPECIFIC CLIENT BELOW------------
//------------------------------------------------------------------------------
    
    private static class Connection_Manager extends Thread {
        private String login_credentials;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        
        //CONSTRUCTOR BELOW
        
        public Connection_Manager(Socket socket) {
            this.socket = socket;
        }
        
        //END OF CONSTRUCTOR
        
        //RUN METHOD IS WHERE MOST OF THE ACTION IS
        
        public void run()
        {
        	try
        	{
	        	// Create character streams for the socket.
	            in = new BufferedReader(new InputStreamReader(
	                socket.getInputStream()));
	            out = new PrintWriter(socket.getOutputStream(), true);
	            
	            User user;
	            
	            while(true)  //this is where we wait till a user is successfully logged in.
	            {
	            	login_credentials = in.readLine();
	                if (login_credentials == null) {
	                   return;
	                }
	                synchronized (user_to_printwriter)
	                {
	                	try {
	                		
							JSONObject login=new JSONObject(login_credentials);  //make a json out of what client gave server
							String username=login.getString("username");
							
							if(!user_to_printwriter.containsKey(username));		//make sure that user isn't already logged in
							{
								user_to_printwriter.put(username, out);
								
								break;
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }
	            
	            while(true)		//this is where we listen after a user is logged in.
	            {
	            	String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    
                    //here, figure out what the client just said to you and hand it off to 
                    //command factory or facade or whoever is supposed to get it.
	            }
        	} 
        	catch(IOException e)
        	{
        		System.out.println(e);
        	}
        	finally
        	{
        		//this is where we go when a client leaves.  Close the socket and remove from user list
        	}
        }
        
        //RUN METHOD IS NOW OVER
    }
    
//-------------------------------------------------------------------------------
//-----END OF THREAD CLASS-------------------------------------------------------

}
