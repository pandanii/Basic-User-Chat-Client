import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Hashtable;

class ChatLoginClass extends JFrame implements ActionListener
{
JButton addContactbt, deleteContactbt, startChatbt, logOutbt;
JLabel contactListlb, addContactlb, namelb;
JTextField addContacttf;
DefaultListModel <JLabel> contactDlm;
JList <JLabel> contactList;
CTS cts;
ChatFrameClass cfs;
JScrollPane jsp;
int contactListSize;
Hashtable<String,ChatBoxClass> chatBoxTable;
//================================================
ChatLoginClass()
	{
	System.out.println("Calling ChatLoginFrameClass() Constructor");
    Container cp;
    JPanel myMainPanel;
	JPanel buttonsPanel;
	JPanel textFieldPanel;

	chatBoxTable = new Hashtable<String,ChatBoxClass>();

    addContactbt = new JButton("Add Contact");
    deleteContactbt = new JButton("Delete Contact");
    startChatbt = new JButton("Start Convo");
    logOutbt = new JButton("Log Out");

    contactListlb = new JLabel("Contact List: ");
	addContactlb = new JLabel("Add Contact: ");
	namelb = new JLabel();

    addContacttf = new JTextField(30);

    addContactbt.setActionCommand("ADD");
    deleteContactbt.setActionCommand("DELETE");
    startChatbt.setActionCommand("START");
    logOutbt.setActionCommand("LOGOUT");

    addContactbt.addActionListener(this);
    deleteContactbt.addActionListener(this);
    startChatbt.addActionListener(this);
    logOutbt.addActionListener(this);

	contactDlm = new DefaultListModel<JLabel>();
	contactList = new JList<JLabel>(contactDlm);
	contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	contactList.setCellRenderer(new ListRenderer());

	jsp = new JScrollPane(contactList);

    getRootPane().setDefaultButton(addContactbt);
    textFieldPanel = new JPanel();
    textFieldPanel.add(addContactlb);
    textFieldPanel.add(addContacttf);

    buttonsPanel = new JPanel();
    buttonsPanel.add(addContactbt);
    buttonsPanel.add(deleteContactbt);
    buttonsPanel.add(startChatbt);
    buttonsPanel.add(logOutbt);

    myMainPanel = new JPanel(new BorderLayout());

    cp= getContentPane();
    cp.add(textFieldPanel, BorderLayout.NORTH);
    cp.add(jsp, BorderLayout.CENTER);
    cp.add(buttonsPanel, BorderLayout.SOUTH);

    setupMainFrame();
    cfs = new ChatFrameClass(this, cts);
   }
//================================================
void setupMainFrame()
	{
     System.out.println("Calling setupMainFrame()...");
     Toolkit tk;
     Dimension d;
     tk= Toolkit.getDefaultToolkit();
     d= tk.getScreenSize();
     setSize(d.width/3, d.height/3);
     setLocation(d.width/3, d.height/3);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setTitle("Chat");
     setVisible(false);
     }
//================================================
public void actionPerformed(ActionEvent e)
	{
	String tmpContact;
	if(e.getActionCommand().equals("ADD"))
		{
		tmpContact = addContacttf.getText().trim().replaceAll("\\W", "");
		addContacttf.setText("");
		System.out.println("id:>>>"+ cts.id+"<<<");
		System.out.println("tmpContacr:>>>"+ tmpContact+"<<<");
		if(cts.id.equals(tmpContact))
			{
			JOptionPane.showMessageDialog(null, "This is you. ", "No this is you. ", JOptionPane.ERROR_MESSAGE);
			}
		else if(!this.isContact(tmpContact))
			{
			cts.sendMsg("CONTACT_REQ " +  tmpContact);
			}
		else
			{
			JOptionPane.showMessageDialog(null, "This user is already in your contacts. ", "Already in your Contacts. ", JOptionPane.ERROR_MESSAGE);
			}

		}
	else if(e.getActionCommand().equals("DELETE"))
		{
		int j = contactList.getSelectedIndex();
		int sel;
		String contactName;

		if(j != -1)
			{
			sel = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this contact", "Delete Contact", JOptionPane.YES_NO_OPTION);
			if(sel == JOptionPane.YES_OPTION)
				{
				contactName = contactDlm.elementAt(j).getText();
				contactDlm.remove(j);
				cts.sendMsg("CONTACT_DEL " + contactName);
				}
			}
		}
	else if(e.getActionCommand().equals("START"))
		{
			int i = contactList.getSelectedIndex();
			ChatBoxClass tmpcbc;
			if(i != -1)
				{
				tmpcbc = chatBoxTable.get(contactDlm.elementAt(i).getText());

				if(tmpcbc == null)
					{
					 tmpcbc = new ChatBoxClass(contactDlm.elementAt(i).getText(),this);
					 chatBoxTable.put(contactDlm.elementAt(i).getText(),tmpcbc);
					}
				if(!tmpcbc.isVisible())
					tmpcbc.setVisible(true);
				else
					 tmpcbc.toFront();
				}

		}
	else if(e.getActionCommand().equals("LOGOUT"))
		{
			this.setVisible(false);
			cfs = new ChatFrameClass(this,cts);
		}

	}// ap

//=================================================
public void procContactReq(String str)
{
int sel = JOptionPane.showConfirmDialog(null, str + " would like to add you to their contact list.", "Add Contact ", JOptionPane.YES_NO_OPTION);
if(sel == JOptionPane.YES_OPTION)
	cts.sendMsg("CONTACT_ACCEPT " +str);
}

//=================================================
public void procContactUpdate(String  cl)
{
String[] str;
int counter = 2;
JLabel tmpLabel;
str = cl.split(" ");
int clSize = Integer.parseInt(str[1]);
System.out.println("clSize: "+ clSize);
while(counter < (clSize+2))
	{
    System.out.println("str[counter+1]: "+ str[counter+1]);
	tmpLabel = new JLabel(str[counter+1]);
	System.out.println("str[counter]: "+ str[counter]);
	if(Integer.parseInt(str[counter]) == 1)
	  {
		  tmpLabel.setForeground(Color.green);
		  System.out.println("User online setting color to green");
      }
    else
      {
		  tmpLabel.setForeground(Color.gray);
		  System.out.println("User offline setting color gray");
	  }
      contactDlm.addElement(tmpLabel);
      counter = counter + 2;
	}
}
//==================================================
public void changeStatusOfContact(int status,String contact)
{
boolean found = false;
int counter = 0;

while(counter < contactDlm.size() && !found)
	{
	found = contact.equals(contactDlm.elementAt(counter).getText());
	if(found)
	{
		if(status == 0)
		{
		  contactDlm.elementAt(counter).setForeground(Color.green);
		  System.out.println("user online setting to green");
		}
		else
		{
		  contactDlm.elementAt(counter).setForeground(Color.gray);
		  System.out.println("user offline setting to gray");
		}
	}
	counter++;
	}
}

//==================================================
public void sendMsgToChat(String sender,String msg)
{
ChatBoxClass tempChat;

tempChat = chatBoxTable.get(sender);
if(tempChat == null)
	{
	tempChat = new ChatBoxClass(sender,this);
	chatBoxTable.put(sender,tempChat);
	}
if(!tempChat.isVisible())
	{
	tempChat.setVisible(true);
	}
else
 	tempChat.toFront();

tempChat.msgProccess(msg);

}

//==================================================
public void removeContact(String contact)
{
JLabel tmpContact;
boolean found = false;
int counter = 0;
System.out.println("REMOVING CONTACT?!!!!!!!!!!: "+ contact);
while(counter < contactDlm.size() && !found)
	{
	found = contact.equals(contactDlm.elementAt(counter).getText());
	if(found)
		{
		contactDlm.remove(counter);
		System.out.println("Contact removed i think?");
		}

	counter++;
	}
}
//==================================================
public boolean isContact(String contactCheck)
{
boolean found = false;
int counter = 0;

while(counter < contactDlm.size() && !found)
	{
	found = contactCheck.equals(contactDlm.elementAt(counter).getText());
	counter++;
	}
return found;
}
//==================================================
} // end of class





