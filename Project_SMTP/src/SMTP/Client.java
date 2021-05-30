package SMTP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		int port;
		if(args.length!=0)
			port=Integer.parseInt(args[0]);
		else
			port=26;
		
		try {
			InetAddress ip = InetAddress.getByName("localhost");
			Socket client = new Socket(ip,port);
			
			DataInputStream input = new DataInputStream(client.getInputStream());
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			String login_username,login_password,data,username_message,password_message,dataa,check="";
			String Server_Response;
			Scanner scanner = new Scanner(System.in);
			while(true) {
			int  c=0;
			while (c!=1)
			{
			username_message=input.readUTF();
			System.out.println(username_message);
			
			login_username = scanner.nextLine();
			output.writeUTF(login_username);
			
			password_message=input.readUTF();
			System.out.println(password_message);
			
			login_password = scanner.nextLine();
			output.writeUTF(login_password);
			
			boolean v = input.readBoolean();
			if (v==true)
				c=1;
			else 
			{Server_Response=input.readUTF();
			System.out.println(Server_Response);}
			}
			String username = input.readUTF();
			
			String choice="" ;
			while (!choice.equalsIgnoreCase("quit")) {
			String menu=input.readUTF();
			System.out.println(menu);
			
			System.out.print("Client : ");
			choice = scanner.nextLine();
			output.writeUTF(choice);
			
			if (choice.equalsIgnoreCase("SEND NEW"))
			{
			
			System.out.println("Clinet : MAIL FROM : "+username+"@fci.cu");
			Server_Response = input.readUTF();
			System.out.println(Server_Response);
			
			System.out.print("Clinet : RCPT TO : ");
			String rcpt_full= scanner.nextLine();
			output.writeUTF(rcpt_full);
			if(rcpt_full.equalsIgnoreCase("quit")) {
				check=rcpt_full;
				break;}
			Server_Response = input.readUTF();
			System.out.println(Server_Response);
			
			System.out.print("Clinet : ");
			dataa= scanner.nextLine();
			output.writeUTF(dataa);
			if(dataa.equalsIgnoreCase("quit")) {
			check=dataa;
			break;}
			Server_Response = input.readUTF();
			System.out.println(Server_Response);
			if(!Server_Response.equalsIgnoreCase("Server : 500"))
			{
			System.out.print("Clinet : ");
			data = scanner.nextLine();
			output.writeUTF(data);
			if(data.equalsIgnoreCase("quit")) {
				check=data;
				break;}
			boolean ch=false;
			while(!data.equalsIgnoreCase("."))
			{
				System.out.print("Clinet : ");
				data = scanner.nextLine();
				output.writeUTF(data);
				if(data.equalsIgnoreCase("quit")) {
					ch=true;
					break;}
			}
			if (ch==true)
			{
				check=data;
				break;
			}
			Server_Response = input.readUTF();
			System.out.println(Server_Response);
			}}
			else if (choice.equalsIgnoreCase("show mailbox"))
			{
					
				File file = new File("E:\\Project_Files\\"+username+".txt");
				FileReader fr = new FileReader (file);
				BufferedReader br = new BufferedReader(fr);
				String mails,reader,inputt,fl;
				fl=input.readUTF();
				System.out.println(fl);
				while (( reader=br.readLine()) != null) 
				{
					if("--".equals(reader))
					{
						   
			               mails = br.readLine();
			               if(mails!=null) {
			               inputt=input.readUTF();
			               System.out.println(inputt);
			               }
					}
				}
			}
			String choice2=choice.substring(0, choice.length()-2);
			if (choice2.equalsIgnoreCase("show mail"))
			{
				int counter=0,counter2=0;
				String idd;
				idd=choice.substring(choice.length()-1, choice.length());
				int id=Integer.parseInt(idd);
				File file = new File("E:\\Project_Files\\"+username+".txt");
				FileReader fr = new FileReader (file);
				BufferedReader br = new BufferedReader(fr);
				String reader,mail,inputt;
				while (( reader=br.readLine()) != null) 
				{
					if("--".equals(reader))
					{
						 counter++;
					}
					if (counter==id-1)
					{
						if(!reader.equalsIgnoreCase("--"))
						{
							inputt=input.readUTF();
							System.out.println(inputt);
						}
					}
				}
				
			}
			
				
			}
			
			if(choice.equalsIgnoreCase("quit")) {
				Server_Response = input.readUTF();
				System.out.println(Server_Response);
				client.close();
				break;
			}
			if(check.equalsIgnoreCase("quit")) {
				Server_Response = input.readUTF();
				System.out.println(Server_Response);
				client.close();
				break;
			}
			
			
			
		}	
		} catch (UnknownHostException e) {
			
		} catch (IOException e) {
			System.out.println("Problem in IO of server socket");
		}
		
	}

}
