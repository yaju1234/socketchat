import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer
{
   ServerSocket serverSocket = null;
   static Connections conn;
   static ChannelList clist;

   public static void main(String args[])
   {
      new ChatServer();
   }
  
   public ChatServer()
   {
      conn = new Connections();
      clist = new ChannelList();
      clist.addChannel("General Chat", "Talk about all things general, particularly the Universe");
      clist.addChannel("Music", "Talk about Music");
      clist.addChannel("Science & Technology", "Does anybody want to get on this bus??");
      try
      {
            serverSocket = new ServerSocket(2000);
      }
      catch (IOException e)
      {
            System.out.println("Could not listen on port: " + 2000 + ", " + e);
            System.exit(1);
      }
      System.out.println("For ServerSocket: " + serverSocket.toString());
      System.out.println("Server stared");

      while(true)
      {
	       Socket clientSocket = null;
	       try
         {
	            clientSocket = serverSocket.accept();
              System.out.println("Client connected");
	       }
         catch (IOException e)
         {
              System.out.println("Accept failed: " + 2000 + ", " + e);
              System.exit(1);
         }
         new Servicer(clientSocket, conn, clist).start();
      }
   }
}
