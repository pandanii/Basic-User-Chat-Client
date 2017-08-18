import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ChatLoginFrameClass extends JFrame implements ActionListener
{
JButton addContactbt, deleteContactbt, startChatbt;
JLabel contactList, addContactlb;
JTextField addContacttf;
//?JList contactList;
CTS cts;
//================================================
ChatLoginFrameClass()
	{
	System.out.println("Calling ChatLoginFrameClass() Constructor");
    Container cp;
    JPanel myMainPanel;
    JPanel contactListPanel;
	JPanel buttonsPanel;
	JPanel textFieldPanel;

    addContactbt = new JButton("Add Contact");
    deleteContactbt = new JButton("Delete Contact");
    startChatbt = new JButton("Start Convo");

    contactList = new JLabel("Contact List: ");
	addContactlb = new JLabel("Add or Delete Contact: ");

    addContacttf = new JTextField(30);

    addContactbt.setActionCommand("ADD");
    deleteContactbt.setActionCommand("DELETE");
    startChatbt.setActionCommand("START");

    addContactbt.addActionListener(this);
    deleteContactbt.addActionListener(this);
    startChatbt.addActionListener(this);
	//?????? VV
    getRootPane().setDefaultButton(addContactbt);
    textFieldPanel = new JPanel();
    textFieldPanel.add(addContactlb);
    textFieldPanel.add(addContacttf);
    //textFieldPanel.add(addContactbt);


    buttonsPanel = new JPanel();
    buttonsPanel.add(addContactbt);
    buttonsPanel.add(deleteContactbt);
    buttonsPanel.add(startChatbt);

    myMainPanel = new JPanel(new BorderLayout());

    cp= getContentPane();
    cp.add(textFieldPanel, BorderLayout.NORTH);
    cp.add(buttonsPanel, BorderLayout.SOUTH);

    setupMainFrame();
    new ChatFrameClass(this, cts);
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
	if(e.getActionCommand().equals("ADD"))
		{

		}
	else if(e.getActionCommand().equals("DELETE"))
		{

		}
	else if(e.getActionCommand().equals("START"))
		{

		}
	}// ap
} // end of class