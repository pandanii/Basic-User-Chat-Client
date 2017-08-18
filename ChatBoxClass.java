import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

class ChatBoxClass extends JFrame implements ActionListener,DocumentListener
{
JButton sendbt;
JTextArea cta;

JEditorPane jep;
JTextField replytf;
CTS cts;

ChatLoginClass clc;
JLabel contactLabel;
String yourName;
String contName;
//================================================
ChatBoxClass(String contact, ChatLoginClass clc)
	{
	this.clc = clc;
	System.out.println("Calling ChatBoxFrameClass() Constructor");
    Container cp;
    JPanel mainPanel;
    JPanel chatPanel;
    JPanel textFieldPanel;
	JPanel buttonsPanel;

	contName = contact;
	yourName = clc.cts.id;
    sendbt = new JButton("Send");
	jep = new JEditorPane();
	jep.setContentType("text/html");
	jep.setEditable(false);
    replytf = new JTextField(30);
    JScrollPane sp = new JScrollPane(jep);
    sendbt.setActionCommand("SEND");

    sendbt.addActionListener(this);

    getRootPane().setDefaultButton(sendbt);
    chatPanel = new JPanel();
     mainPanel = new JPanel(new FlowLayout());

    textFieldPanel = new JPanel();
    textFieldPanel.add(replytf);
    replytf.getDocument().addDocumentListener(this);

    buttonsPanel = new JPanel();
    buttonsPanel.add(sendbt);

	mainPanel.add(textFieldPanel);
	mainPanel.add(buttonsPanel);
    cp= getContentPane();
    cp.add(sp, BorderLayout.CENTER);
    cp.add(mainPanel, BorderLayout.SOUTH);
    setupMainFrame();
   }
//================================================
void setupMainFrame()
	{
     System.out.println("Calling setupMainFrame()...");
     Toolkit tk;
     Dimension d;
     tk= Toolkit.getDefaultToolkit();
     d= tk.getScreenSize();
     setSize(d.width/3, d.height/2);
     setLocation(d.width/4, d.height/4);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setTitle("Chat");
     setVisible(true);
     }
//================================================
public void actionPerformed(ActionEvent e)
	{
	String msg;
	HTMLDocument htmldoc;
	Element elem;

	if(e.getActionCommand().equals("SEND"))
		{
		try
			{
			msg = replytf.getText().trim();
			clc.cts.sendMsg("+MSGCONT "+contName+" " + msg);
			htmldoc = (HTMLDocument) jep.getDocument();
			elem = htmldoc.getElement(htmldoc.getDefaultRootElement(),StyleConstants.NameAttribute,HTML.Tag.P);
			htmldoc.insertBeforeEnd(elem, "<Font Face = \"Arial\" COLOR=\"PURPLE\"><BR>"+yourName+ ": " +msg.replaceAll("\n","<br />") +"</BR></FONT>");
			replytf.setText("");
			}
		catch(BadLocationException ble)
			{
			ble.printStackTrace();
			}
		catch(IOException ioe)
			{
			ioe.printStackTrace();
			}
		}
	}// ap

	//================================================
public void msgProccess(String msg)
	{
	HTMLDocument htmldoc;
	Element elem;

		try
			{
		   	 htmldoc = (HTMLDocument) jep.getDocument();
			 elem = htmldoc.getElement(htmldoc.getDefaultRootElement(), StyleConstants.NameAttribute,HTML.Tag.P);
			 htmldoc.insertBeforeEnd(elem,"<Font Face = \"Arial\" COLOR=\"RED\"><BR>"+contName+ ": " +msg.replaceAll("\n","<br />") +"</BR></FONT>");
			   }
		   catch(BadLocationException ble)
			   {
			   	ble.printStackTrace();
			   }
		   catch(IOException ioe)
			   {
			   	ioe.printStackTrace();
			   }
	}// ap

//=================================================
public void changedUpdate(DocumentEvent de)
	{}
//=================================================
public void insertUpdate(DocumentEvent de)
	{
	if(replytf.getText().trim().equals(""))
		{
		sendbt.setEnabled(false);
		}
	else
		{
		sendbt.setEnabled(true);
		}
	} // end of IU
//=================================================
public void removeUpdate(DocumentEvent de)
	{
	if(replytf.getText().trim().equals(""))
		{
		sendbt.setEnabled(false);
		}
	else
		{
		sendbt.setEnabled(true);
		}
	}


} // end of class