package SMTP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Connection_Thread  extends Thread{
	private Socket client;
	
	public	Connection_Thread (Socket client)
	{
		this.client = client;
	}
	String[] usernames = {"YoussefM","YoussefF","Hussien","Abdullah"};
	String[] passwords = {"20180352","20180348","20180089","20180162"};
	
	public void run() {
		try {
			DataInputStream input = new DataInputStream(client.getInputStream());
			DataOutputStream output=new DataOutputStream(client.getOutputStream());
			
			String login_username,login_password,check="";
			boolean login_check=false;
			int c=0,usernum=0;
			while(true) {
			while (c!=1) {
			output.writeUTF("Please Enter your Username : ");
			login_username = input.readUTF();
			output.writeUTF("Please Enter your Password : ");
			login_password = input.readUTF();
			 for(int i=0;i<4;i++){
				 if(login_username.equalsIgnoreCase(usernames[i]+"@Fci.cu"))
				 {
					 if(login_password.equalsIgnoreCase(passwords[i]))
					 {
						 login_check=true;
						 usernum=i;
					 }
						 
				 }
			 }
			output.writeBoolean(login_check);
			if(login_check==true)
			{ 
				c=1;
			}
			else {output.writeUTF("Wrong login , Try again");}
			}
			output.writeUTF(usernames[usernum]);
			String choice="";
			while (!choice.equalsIgnoreCase("quit")) {
			output.writeUTF("Server : OPTIONS: SEND NEW, SHOW MAILBOX, SHOW MAIL mail_id, QUIT");
			choice = input.readUTF();
			if (choice.equalsIgnoreCase("SEND NEW"))
			{
				
				String rcpt_full,data,rcpt;
				output.writeUTF("Server : 250");
				rcpt_full= input.readUTF();
				if(rcpt_full.equalsIgnoreCase("quit")) {
					check=rcpt_full;
					break;}
				rcpt=rcpt_full.substring(0,rcpt_full.indexOf('@'));
				boolean rcpt_ch=true;
				for (int i=0;i<4;i++)
				{
					if(rcpt.equalsIgnoreCase(usernames[i]))
					{rcpt_ch=false;
					}
				}
				if(rcpt_ch==false)
				{ 
					output.writeUTF("Server : 250");
				}
				else {output.writeUTF("Server : 252");}
				
				String dataa=input.readUTF();
				if(dataa.equalsIgnoreCase("quit")) {
					check=dataa;
					break;}
				if(!dataa.equalsIgnoreCase("data"))
					output.writeUTF("Server : 500");
				
				else {
				output.writeUTF("Server : 354");
				
				data = input.readUTF();
				if(data.equalsIgnoreCase("quit")) {
					check=data;
					break;}
				FileWriter fw= new FileWriter("E:\\Project_Files\\"+rcpt+".txt",true);
				fw.write("From : "+usernames[usernum]+"@fci.cu");
				fw.write(System.getProperty("line.separator"));
				boolean ch=false;
				while(!data.equalsIgnoreCase("."))
				{
					fw.write(data);
					fw.write(System.getProperty("line.separator"));
					data = input.readUTF();
					if(data.equalsIgnoreCase("quit")) {
						ch=true;
						break;}
				}
				if (ch==true)
				{
					check=data;
					break;
				}
				if (data.equalsIgnoreCase("."))
				{
					fw.write("--");
					fw.write(System.getProperty("line.separator"));
					fw.close();
				}
				output.writeUTF("Server : 250");
			}}
			else if (choice.equalsIgnoreCase("show mailbox"))
			{	
				File file = new File("E:\\Project_Files\\"+usernames[usernum]+".txt");
				FileReader fr = new FileReader (file);
				BufferedReader br = new BufferedReader(fr);
				
				String mails,reader;
				output.writeUTF("Server : "+br.readLine());
				while (( reader=br.readLine()) != null) 
				{
					if("--".equals(reader))
					{
						   
			               mails = br.readLine();
			               if(mails!=null)
			            	   output.writeUTF("Server : "+mails);
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
				File file = new File("E:\\Project_Files\\"+usernames[usernum]+".txt");
				FileReader fr = new FileReader (file);
				BufferedReader br = new BufferedReader(fr);
				String reader,mail;
				while (( reader=br.readLine()) != null) 
				{
					if("--".equals(reader))
					{
						 counter++;
					}
					if (counter==id-1)
					{
						if(!reader.equalsIgnoreCase("--"))
						output.writeUTF("Server : "+reader);
					}
				}
				
			}}
			if(choice.equalsIgnoreCase("quit")) {
				output.writeUTF("Server : 221");
				client.close();
				break;
			}
			if(check.equalsIgnoreCase("quit")) {
				output.writeUTF("Server : 221");
				client.close();
				break;
			}
			
			
		}	
		} catch (IOException e) {
			System.out.println("Problem in IO of server socket");
		}
		
		try {
			this.client.close();
		}
		catch (IOException e) {
			System.out.println("Problem in IO of server socket");
		}	
		
		
	}
	
	
	
	}


