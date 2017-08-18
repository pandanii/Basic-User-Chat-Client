import java.io.*;
import java.net.*;



class ChatServer
{
	ServerSocket serverSocket;
	Socket socket;
	//BufferedReader buffReader;
	DataOutputStream dos;
	ChatHashTable<String, User> userTable;
	File userFile;
	String fileName = "chatUserList.dat";
	String msg;

//================================================
ChatServer()
{
DataInputStream dis;
	try
		{
		serverSocket = new ServerSocket(12345); // PORT
		userFile = new File(fileName);

		if(userFile.exists())
			{
			dis = new DataInputStream(new FileInputStream(userFile));
			userTable = new ChatHashTable<String,User>(dis);
			dis.close();
			}
		else
			{
			userTable = new ChatHashTable<String,User>();
			}
		}
	catch(Exception ex)
		{
		System.out.println("Error in simple server");
        ex.printStackTrace();
		}

}
//=============================================
void accept()
{
Socket socket;
CTC aCtc;
while(true)
	{
	try
		{
		socket = serverSocket.accept();
		aCtc = new CTC(socket, "CTC",this);
		}
	catch(IOException ioe)
		{
		System.out.println("Error .accept()");
		ioe.printStackTrace();
		}
	}
}

//==============================================
void registerUser(String str1, String str2, CTC ctc)
{
User tmpUser;
tmpUser = userTable.get(str1);
System.out.println("Checking to see if user is null");
try
{
	if(tmpUser == null)
		{
		dos = new DataOutputStream(new FileOutputStream(userFile));
		tmpUser = new User(str1, str2, ctc);
		userTable.put(str1, tmpUser);
		tmpUser.userCtc.sendMsg("REGISTER_OKAY");
		tmpUser.userCtc.userName = str1;
		userTable.saveTable(dos);
		}
	else
		{
		ctc.sendMsg("User name taken");
		}
	dos.close();
}
catch(IOException ioe2)
	{
	System.out.println("Error registerUser()");
	ioe2.printStackTrace();
	}
}
//=============================================
void loginUser(String str1, String str2, CTC ctc)
{
User tmpUser;
tmpUser = userTable.get(str1);
if(tmpUser != null)
	{
	if(tmpUser.checkPassword(str2))
		{
		ctc.userName = str1;
		tmpUser.userCtc = ctc;
		tmpUser.onlineStatus = User.ON_LINE;
		tmpUser.userCtc.sendMsg("LOGIN_OKAY");
		this.sendContactList(tmpUser);
		this.setStatus(User.ON_LINE, str1);
		}
	else
		{
		ctc.sendMsg("RIP_LOGIN");
		}
	}
else
	{
	ctc.sendMsg("RIP_LOGIN");
	}
}
//=============================================
void contactRequest(String reqTarget, String reqSender)
{
User tmpUser;
if(userTable.containsKey(reqTarget))
	{
	tmpUser = userTable.get(reqTarget);
	if(tmpUser.userCtc != null)
		tmpUser.userCtc.sendMsg("CONTACT_REQ " + reqSender);
	}

}
//================================================
void createMutual(String req, String acpt)
{
try
	{
	dos = new DataOutputStream(new FileOutputStream(userFile));
	User userReq;
	User userAcpt;

	userReq = userTable.get(req);
	userAcpt = userTable.get(acpt);

	userReq.addContact(acpt);
	userAcpt.addContact(req);

	userReq.getContactList();
	userAcpt.getContactList();

	userReq.userCtc.sendMsg("+CONTACT " + acpt);
	userAcpt.userCtc.sendMsg("+CONTACT " + req);
	userTable.saveTable(dos);
	dos.close();
	}
catch(Exception e)
	{
	System.out.println("Error createMutual");
	e.printStackTrace();
	}
}

//==============================================

void msgUser(String req, String acpt, String msg )
{
User tmpuser = userTable.get(req);
if(tmpuser.userCtc != null)
	 tmpuser.userCtc.sendMsg("+MSGCONT "+ acpt +" " +msg);
}

//==============================================
void removeContact(String req, String acpt)
{
try
	{
	dos = new DataOutputStream(new FileOutputStream(userFile));
	User userReq;
	User userAcpt;

	userReq = userTable.get(req);
	userAcpt = userTable.get(acpt);


	userReq.removeContact(acpt);
	userAcpt.removeContact(req);

	if(userAcpt.userCtc != null)
		userAcpt.userCtc.sendMsg("+DELETE " + req);

	userTable.saveTable(dos);
	dos.close();
	}
catch(Exception e)
	{
	System.out.println("Error createMutual");
	e.printStackTrace();
	}
}
//==================================================================
void killUser(String user)
{
User tmpUser;
tmpUser = userTable.get(user);
if(tmpUser.userCtc != null)
	{
	tmpUser.userCtc = null;
	this.setStatus(User.OFF_LINE, user);
	}
}
//==============================================================
void setStatus(int stat, String user)
{
User tmpUser;
User contact;
String contactList;
String[] str;
int c = 1;

tmpUser = userTable.get(user);
tmpUser.onlineStatus = stat;
contactList = tmpUser.getContactList();
str = contactList.split(" ");

while(c < str.length)
	{
	contact = userTable.get(str[c]);
	if(contact.userCtc != null)
		{
		contact.userCtc.sendMsg("C_STATUS " + stat + " " + user);
		}
	c++;
	}

}
//=========================================================
void chatMsg(String recipient, String sender, String msg)
{
User tmpUser;
tmpUser = userTable.get(recipient);
if(tmpUser.userCtc != null)
	tmpUser.userCtc.sendMsg("" + sender + " " + msg);
}
//=======================================================
void sendContactList(User tmpUser)
{
System.out.println(" attempting to send contact list");
User contactUser;
String contactList;
String[] str;
String updatedList;
int c;

updatedList = new String();
str = tmpUser.getContactList().split(" ");
updatedList += str[0];
c=1;
while(c < str.length)
	{
	contactUser = userTable.get(str[c]);
	updatedList += (" " + contactUser.onlineStatus+ " "+ str[c]);
	c++;
	}
tmpUser.userCtc.sendMsg("START_CONTACTLIST " + updatedList );
}// end of send contact list
//=======================================================
} // end of chatServer class



