package server;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Scanner;

public class ServerMain {

    public static void main(String[] args) throws Exception
    {
        ServerMain main = new ServerMain();
        main.new MyThread().start();
        main.new StaticServer().run();
    }

    private class StaticServer extends Thread
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

    private HttpHandler indexHandler = exchange -> {
        System.out.println("Looking for index");
        Headers head = exchange.getResponseHeaders();
        //head.set("Content-Type", "text/html");


        URI command=exchange.getRequestURI();
        String theCommand=command.toString();

        System.out.println("    Command received: " + theCommand);
        String[] params = theCommand.split("/",2);

        String file = "index.html";
        head.set("Content-Type", "text/html");

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStreamWriter sendBack= new OutputStreamWriter(exchange.getResponseBody());
        Scanner scanner;
        try{

            scanner = new Scanner(new FileReader(file));

        }
        catch(IOException e)
        {
            String notFound = "404.html";
            scanner = new Scanner(new FileReader(notFound));
        }

        StringBuilder stringBuilder = new StringBuilder();
        while(scanner.hasNextLine())
        {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }

        scanner.close();
        sendBack.write(stringBuilder.toString());

        //sendBack.write("index.html");
        sendBack.close();
    };

    private class MyThread extends Thread
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

