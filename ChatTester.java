public class ChatTester
{
	public static void main(String args[])
	{
	ChatServer chatServer;
	System.out.println("Chattest starting.");
	chatServer = new ChatServer();
	chatServer.accept();
	}
}