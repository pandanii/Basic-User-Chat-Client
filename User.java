import java.io.*;
import java.util.Vector;
//import java.net.*;

class User
{
public static final int OFF_LINE = 0;
public static final int ON_LINE = 1;
String userName;
private String password;
CTC userCtc;
int onlineStatus;
int cListSize;
Vector<String> cList;
//=========================================================
User(String userName, String password, CTC userCtc)
{
this.onlineStatus = ON_LINE;
this.userName = userName;
this.password = password;
this.userCtc = userCtc;
cListSize = 0;
cList = new Vector<String>();
}// end of constuctor
//=========================================================
User(DataInputStream dis) throws IOException
{ // reads user from file
userName = dis. readUTF();
password = dis.readUTF();
userCtc = null;
onlineStatus = OFF_LINE;
cListSize = dis.readInt();
cList = new Vector<String>();
if(cListSize != 0)
{
	for( int k = 0; k< cListSize; k++)
	{
		cList.addElement(dis.readUTF());
	}
}


}
//=========================================================
void store(DataOutputStream dos) throws IOException
{ //  stores username to a file
dos.writeUTF(userName);
System.out.println("stored UN: " +userName);
dos.writeUTF(password);
System.out.println("Stored PW: " +password);
dos.writeInt(cList.size());
System.out.println("clist: " + cList.size());
	for( int k = 0; k< cList.size(); k++)
	{
		dos.writeUTF(cList.elementAt(k));
	}
}
//=========================================================
boolean checkPassword(String str)
{
return password.equals(str);
}
//=========================================================
public void addContact(String contact)// should work
{
	cList.add(contact);
	cListSize = cList.size();
	System.out.println("contact: "+ contact + " size " + cListSize);
}
//=========================================================
public void removeContact(String contact)// Might not work... idk
{
	System.out.println("KILL ME...Attempting to remove contact: "+ contact);
	cList.remove(contact);
}
//=========================================================
public String getContactList()
{
String tmpStr;
tmpStr = new String(cList.size() + " ");
for(  int s = 0; s < cList.size(); s++)
	tmpStr += (cList.elementAt(s) + " ");
System.out.println(tmpStr);
return tmpStr.toString();

}
}//end of class