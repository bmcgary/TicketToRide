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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ServerMain {
	
	private Map<Object, Object> paramMap = new HashMap<>();

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
            theCommand = deparameterize(theCommand, paramMap);
            
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
                if(ext.equals("img") || ext.equals("jpg"))
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
                	factory.getPolicy().setIdleTimeout(TimeUnit.HOURS.toMillis(10));
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
    
    public static String deparameterize(String uri, Map parameters) {
        int i = uri.lastIndexOf('?');
        if (i == -1) {
            return uri;
        }

        parameters.clear();
        String[] params = uri.substring(i + 1).split("&");
        for (int j = 0; j < params.length; j++) {
            String p = params[j];
            int k = p.indexOf('=');
            if (k == -1) {
                break;
            }
            String name = p.substring(0, k);
            String value = p.substring(k + 1);
            Object values = parameters.get(name);
            if (values == null) {
                parameters.put(name, new String[]{value});
            } else {
                String[] v1 = (String[])values;
                String[] v2 = new String[v1.length + 1];
                System.arraycopy(v1, 0, v2, 0, v1.length);
                v2[v1.length] = value;
                parameters.put(name, v2);
            }
        }

        return uri.substring(0, i);
    }
}

