import java.io.*;
import java.net.*;

class Talker
{
    private DataOutputStream dos;
    BufferedReader buffRead;
    Socket mySocket;
    String user;
    String status;
    String sever;

    public Talker(String domain, int port, String str) throws IOException, UnknownHostException
    {

        mySocket = new Socket(domain, port);
        System.out.println("Socket created");
        status = new String(str);
        dos = new DataOutputStream(mySocket.getOutputStream());
        buffRead = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

    }// end of constructor

public Talker(Socket newSocket, String str) throws IOException, UnknownHostException
{
    mySocket = new Socket();
    mySocket = newSocket;
    user = new String(str);

    dos = new DataOutputStream(mySocket.getOutputStream());
    buffRead = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

}

public void send (String message) throws IOException
{
    System.out.println("Sent >> " + message);
    message += '\n';
    dos.writeBytes(message);

} // end of send

public String receive() throws IOException
{
    String status;
    status = buffRead.readLine();
    System.out.println("Received <<" + status);
    return status;
}
public void expected(String prefix) throws IOException
{
    String check;
    check = receive();
    if (!check.startsWith(prefix))
         throw new IOException("Problem in IO");

}
}