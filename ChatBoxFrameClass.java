import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ChatBoxFrameClass extends JFrame implements ActionListener
{
JButton sendbt;
JTextField replytf;
CTS cts;
//================================================
ChatBoxFrameClass()
	{
	System.out.println("Calling ChatBoxFrameClass() Constructor");
    Container cp;
    JPanel mainPanel;
    JPanel chatPanel;
    JPanel textFieldPanel;
	JPanel buttonsPanel;

    sendbt = new JButton("Send");

    replytf = new JTextField();

    sendbt.setActionCommand("SEND");

    sendbt.addActionListener(this);

    getRootPane().setDefaultButton(sendbt);
    chatPanel = new JPanel();
    textFieldPanel = new JPanel();
    textFieldPanel.add(replytf);

    buttonsPanel = new JPanel();
    buttonsPanel.add(sendbt);

    mainPanel = new JPanel(new BorderLayout());

    cp= getContentPane();
    cp.add(chatPanel, BorderLayout.CENTER);
    cp.add(textFieldPanel, BorderLayout.SOUTH);
	cp.add(buttonsPanel, BorderLayout.SOUTH);
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
	if(e.getActionCommand().equals("SEND"))
		{

		}

	}// ap
} // end of class