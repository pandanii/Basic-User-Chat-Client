import java.io.*;
import java.net.*;


class CTC implements Runnable
{
String cmd;
Talker talker;
String userName;
ChatServer cServer;

//======================================
public CTC(Socket socket, String userName, ChatServer cServer) throws IOException
{
try
	{
	this.userName = userName;
	this.cServer = cServer;
	this.talker = new Talker(socket, userName);
	new Thread(this).start();
	}
catch(IOException ioe)
	{
	System.out.println("Error");
	ioe.printStackTrace();
	}
}
//======================================
public void sendMsg(String sentMsg)
{
try
	{
	talker.send(sentMsg);
	}
catch(IOException ioe)
	{
	System.out.println("Error sending msg");
	ioe.printStackTrace();
	}
}
//======================================
public void run()
{
String[] str;
boolean isConnected = true;
	while(isConnected)
		{
		try
			{
			cmd = talker.receive();
			if(cmd.startsWith("LOGIN"))
				{
				str = cmd.toString().split(" ");
				cServer.loginUser(str[1], str[2], this);
				}
			else if(cmd.startsWith("REGISTER"))
				{
				str = cmd.toString().split(" ");
				cServer.registerUser(str[1], str[2], this);
				}
			else if(cmd.startsWith("CONTACT_REQ"))
				{
				str = cmd.toString().split(" ");
				cServer.contactRequest(str[1], userName);
				}
			else if(cmd.startsWith("CONTACT_ACCEPT"))
				{
				str = cmd.toString().split(" ");
				cServer.createMutual(str[1], userName);
				}
			else if(cmd.startsWith("CONTACT_DEL"))
				{
				str = cmd.toString().split(" ");
				cServer.removeContact(str[1], userName);
				}
			else if(cmd.startsWith("CONTACT_DEL"))
				{
				str = cmd.toString().split(" ");
			    cServer.removeContact(str[1], userName);
				}
			else if(cmd.startsWith("+MSGCONT"))
				{
				str = cmd.toString().split(" ");
			    cServer.msgUser(str[1],userName, str[2]);
				}
			}
		catch(IOException ioe)
			{
			System.out.println("Error in CTC run");
			ioe.printStackTrace();
			isConnected = false;
			}
		} // end of while

} // end of run

} // end of CTC