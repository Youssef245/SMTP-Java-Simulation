package SMTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		int port;
		if(args.length!=0)
			port=Integer.parseInt(args[0]);
		else
			port=26;
		
		ServerSocket Serversocket = null;
		try {
			Serversocket = new ServerSocket (port);
		} catch (IOException e) {
			System.out.println("Problem in IO of server socket");
		}
		while(true) {
			try {
		Socket client = Serversocket.accept();
		Thread clients = new Connection_Thread (client);
		clients.start();
		}
		
	
	
		catch (IOException e) {
		System.out.println("Problem in IO of server socket");
	}

}
		}
	
	}


