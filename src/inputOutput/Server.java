package inputOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;


public class Server {
	

	public interface ClientHandler{
		// define...
		void handleClient(InputStream inFromClient, OutputStream outToClient);
	}

	volatile boolean stop;
	public Server() {
		stop=false;
	}
	
	
	private void startServer(int port, ClientHandler ch) {
		// implement here the server...		
		try {
			ServerSocket server;
			
				server = new ServerSocket(port);
				server.setSoTimeout(1000);
				while(!stop)
				{
					try
					{
					Socket aclient = server.accept();
					ch.handleClient(aclient.getInputStream(), aclient.getOutputStream());
					aclient.close();
					}
					catch (SocketTimeoutException e) {
						// TODO: handle exception
					}
				}
					server.close();		
		}
			
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
	}
		
	
	// runs the server in its own thread
	public void start(int port, ClientHandler ch) {
		new Thread(()->startServer(port,ch)).start();
	}
	
	public void stop() {
		stop=true;
	}
}
