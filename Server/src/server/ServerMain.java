package server;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

public class ServerMain {

    public static void main(String[] args) throws Exception
    {
        ServerMain main = new ServerMain();
        main.new WebSocketServer().start();
        main.new HTTPServer().run();
    }

    private class HTTPServer extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                HttpServer server = HttpServer.create(new InetSocketAddress(8081), 10);

                server.setExecutor(null);

                server.createContext("/", indexHandler);

                server.start();

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private HttpHandler indexHandler = new HttpHandler()
    {
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            System.out.println("Looking for index");
            Headers head=exchange.getResponseHeaders();

            URI command=exchange.getRequestURI();
            String theCommand=command.toString();

            System.out.println("    Command received: " + theCommand);
            String[] params=theCommand.split("/",2);

            String path = null;
            
            if(params.length <= 1 || params[1].equals(""))
            {
                path = "index.html";
                head.set("Content-Type", "text/html");
            }
            else
            {
            	//Split based of the period, get the last item in the array,
            	//that will tell us what type of file this is, and therefore 
            	//what to set the content-type to
                path = params[1];
                String[] extArr = theCommand.split("\\.");
                String ext = extArr[extArr.length - 1];
                if(ext.equals("img"))
                {
                    head.set("Content-Type", "image/png");
                }
                else
                    head.set("Content-Type", "text/" + ext);
            }
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            File file = new File ("Client/src/" + path);
            byte [] bytearray  = new byte [(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytearray, 0, bytearray.length);

            // ok, we are ready to send the response.
            OutputStream os = exchange.getResponseBody();
            os.write(bytearray,0,bytearray.length);
            os.close();
            bis.close();
        }
    };

    private class WebSocketServer extends Thread
    {
        @Override
        public void run()
        {
            Server server = new Server(8080);
            WebSocketHandler wsHandler = new WebSocketHandler()
            {
                @Override
                public void configure(WebSocketServletFactory factory)
                {
                    factory.register(MyWebSocketHandler.class);
                }
            };
            server.setHandler(wsHandler);
            try {
                server.start();
                server.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

