import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

class ChatFrameClass extends JFrame implements ActionListener, DocumentListener
{
JButton logInbt, registerbt, cancelbt;
JLabel userNamelb, passwordlb;
JTextField logintf, passwordtf;
//JPassword passwordtf;
ChatLoginClass clc;
CTS cts;

//================================================
ChatFrameClass(ChatLoginClass clc, CTS cts)
	{
	System.out.println("Calling ChatFrameClass() Constructor");
	this.clc = clc;
	this.cts = cts;

    Container cp;
    JPanel myMainPanel;
    JPanel textFieldPanel;
	JPanel buttonsPanel;

    logInbt = new JButton("Log in");
    registerbt = new JButton("Register");
    cancelbt = new JButton("Cancel");

    userNamelb = new JLabel("User Name ");
    passwordlb = new JLabel("Password ");

    logintf = new JTextField(30);
    passwordtf = new JTextField(30);

    logInbt.setActionCommand("LOGIN");
    registerbt.setActionCommand("REGISTER");
    cancelbt.setActionCommand("CANCEL");

    logInbt.addActionListener(this);
    registerbt.addActionListener(this);
    cancelbt.addActionListener(this);

    logInbt.setEnabled(false);
    registerbt.setEnabled(false);

    logintf.getDocument().addDocumentListener(this);
    passwordtf.getDocument().addDocumentListener(this);

    getRootPane().setDefaultButton(logInbt);
    textFieldPanel = new JPanel();
    textFieldPanel.add(userNamelb);
    textFieldPanel.add(logintf);
    textFieldPanel.add(passwordlb);
    textFieldPanel.add(passwordtf);

    buttonsPanel = new JPanel();
    buttonsPanel.add(logInbt);
    buttonsPanel.add(registerbt);
	buttonsPanel.add(cancelbt);
    myMainPanel = new JPanel(new BorderLayout());

    cp= getContentPane();
    cp.add(buttonsPanel, BorderLayout.SOUTH);
    cp.add(textFieldPanel, BorderLayout.CENTER);

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
     setSize(d.width/4, d.height/4);
     setLocation(d.width/4, d.height/4);
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setTitle("Chat");
     setVisible(true);
     }
//================================================
public void actionPerformed(ActionEvent e)
	{
	if(e.getActionCommand().equals("LOGIN"))
		{
		cts = new CTS("127.0.0.1", 12345, logintf.getText().trim(), clc);
		cts.id = logintf.getText().trim();
		cts.sendMsg("LOGIN "+logintf.getText().replaceAll("\\W", "") + " " + passwordtf.getText().trim().replaceAll("\\W", ""));

		}
	else if(e.getActionCommand().equals("REGISTER"))
		{
		cts = new CTS("127.0.0.1", 12345, logintf.getText().trim(), clc);
		cts.id = logintf.getText().trim();
		cts.sendMsg("REGISTER "+logintf.getText().replaceAll("\\W", "") + " " + passwordtf.getText().trim().replaceAll("\\W", ""));
		}
	else if(e.getActionCommand().equals("CANCEL"))
		{
		System.exit(1);
		}
	}// ap
//=================================================
public void changedUpdate(DocumentEvent de)
{}
//=================================================
public void insertUpdate(DocumentEvent de)
{
if(logintf.getText().trim().equals("") || passwordtf.getText().trim().equals(""))
	{
	logInbt.setEnabled(false);
	registerbt.setEnabled(false);
	}
else
	{
	logInbt.setEnabled(true);
	registerbt.setEnabled(true);
	}
} // end of IU
//=================================================
public void removeUpdate(DocumentEvent de)
{
if(logintf.getText().trim().equals("") || passwordtf.getText().trim().equals(""))
	{
	logInbt.setEnabled(false);
	registerbt.setEnabled(false);
	}
else
	{
	logInbt.setEnabled(true);
	registerbt.setEnabled(true);
	}
}

} // end of class