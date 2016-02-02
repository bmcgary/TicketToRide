package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
	
//------CLASS VARIABLES BELOW--------------------------------------
//-----------------------------------------------------------------
	
    private static HashSet<User> users=new HashSet<User>(); //populate this guy from the run method of each thread.
    private static int ID_to_give_out=0;
    
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
        private String name;
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
