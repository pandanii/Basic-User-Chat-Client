import java.io.*;
import java.util.*;
import java.util.Vector;
import java.util.Hashtable;


public class ChatHashTable<K,V> extends Hashtable<String, User>
{
int userCount;

ChatHashTable(DataInputStream dis)
{
User tmpUser;

try
	{
	userCount = dis.readInt();
	for(int d = 0; d < userCount; d++)
		{
		tmpUser = new User(dis);
		this.put(tmpUser.userName, tmpUser);
		}
	}
catch(IOException ioe)
	{
	System.out.println("Error hashtable stuff");
	ioe.printStackTrace();
	}

}// end of constuctor

ChatHashTable(){}

//===============================================
void saveTable(DataOutputStream dos)
{
Enumeration<User> en;
System.out.println("Attempting to store the file.");

try
	{
	userCount = this.size();
	dos.writeInt(userCount);
	System.out.println("Wrote: userCount: " + userCount);
	for(en = this.elements(); en.hasMoreElements();)
		{
		en.nextElement().store(dos);
		}
	}
catch(IOException ioe)
	{
	System.out.println("Error is saving table");
	ioe.printStackTrace();
	}

}//saveTable


} // end of class