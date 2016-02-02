package server;

import java.util.HashSet;

public class Server {
	
//------CLASS VARIABLES BELOW--------------------------------------
//-----------------------------------------------------------------
	
    private static HashSet<User> users=new HashSet<User>(); //populate this guy from the run method of each thread.
    private static int ID_to_give_out=0;
    
//-----------------------------------------------------------------
//------END OF CLASS VARIABLES-------------------------------------

}
