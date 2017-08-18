import java.io.*;
import java.net.*;
import javax.swing.*;


class CTS implements Runnable
{
String cmd;
String id;
Talker talker;
ChatLoginClass clc;
String[] str;
CTS(String server, int port, String id, ChatLoginClass clc)
{
try
	{
	this.id = id;
	talker = new Talker(server, port, id);
	this.clc = clc;
	new Thread(this).start();
	}
catch(IOException ioe)
	{
	System.out.println("Error");
	ioe.printStackTrace();
	}

}
//=====================================
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

//=============================================
public void run()
{
boolean isConnected = true;
	while(isConnected)
		{
		try
			{
			cmd = talker.receive();
			if(cmd.startsWith("LOGIN_OKAY"))
				{
				str = cmd.toString().split(" ");
				clc.cts = this;
				clc.setVisible(true);
				clc.cfs.dispose();
				}
			else if(cmd.startsWith("REGISTER_OKAY"))
				{
				clc.cts = this;
				clc.setVisible(true);
				clc.cfs.dispose();
				}
			else if(cmd.startsWith("RIP_LOGIN"))
				{
				isConnected = false;
				JOptionPane.showMessageDialog(null, "Unsuccessful Login", "Failure", JOptionPane.ERROR_MESSAGE);
				}
			else if(cmd.startsWith("CONTACT_REQ"))
				{
				str = cmd.toString().split(" ");
				SwingUtilities.invokeLater(
				new Runnable()
					{
					public void run()
						{
						clc.procContactReq(str[1]);
						}
					});
				}
				else if(cmd.startsWith("START_CONTACTLIST"))
					{
					SwingUtilities.invokeLater(
					new Runnable()
						{
						public void run()
							{
							clc.procContactUpdate(cmd);
							}
						});
					}
				else if(cmd.startsWith("+CONTACT"))
					{
					str = cmd.toString().split(" ");
					SwingUtilities.invokeLater(
					new Runnable()
						{
						public void run()
							{
							System.out.println("ATTEMPTING TO ADD: "+str[1]);
							clc.contactDlm.addElement(new JLabel(str[1]));
							}
						});
					}
				else if(cmd.startsWith("+DELETE"))
					{
					str = cmd.toString().split(" ");
					SwingUtilities.invokeLater(
					new Runnable()
						{
						public void run()
							{
							System.out.println("ATTEMPTING TO Delete: "+str[1]);
							clc.removeContact(str[1]);
							}
						});
					}
					else if(cmd.startsWith("C_STATUS"))
					{
					  str = cmd.toString().split(" ");
					  SwingUtilities.invokeLater(
					  new Runnable()
							{
							public void run()
								{
								System.out.println("ATTEMPTING TO change status of user: "+str[2]);
								clc.changeStatusOfContact(Integer.parseInt(str[1]),str[2]);
								}
							});
					}
					else if(cmd.startsWith("+MSGCONT"))
					{
				      str = cmd.toString().split(" ");
				      SwingUtilities.invokeLater(
					  new Runnable()
					  {
					    public void run()
					    {
					    System.out.println("ATTEMPTING TO send message: "+str[1]);
					     clc.sendMsgToChat(str[1],str[2]);
					    }
					  });
					 }
			}
		catch(IOException ioe)
			{
			System.out.println("Error in CTS run");
			ioe.printStackTrace();
			isConnected = false;
			clc.setVisible(false);
			clc.cfs = new ChatFrameClass(clc,this);
			}
		} // end of while

} // end of run

} // end of CTS