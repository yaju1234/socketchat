import java.net.*;
import java.io.*;
import java.util.*;

public class Ping extends Thread
{
   boolean isPinged;
   public Connections c;
   Socket mySocket;
   Servicer myServicer;
   String me;
   String myChannel;

   public Ping(Connections cs, String name, Socket s, Servicer sr, String c)
   {
      this.c          = cs;
      this.me         = name;
      this.mySocket   = s;
      this.myServicer = sr;
      this.myChannel  = c;
      this.isPinged   = true;
   }
   
   public void setTrue()
   {
      isPinged = true;
   }

   public void setFalse()
   {
      isPinged = false;
   }

   public boolean isTrue()
   {
      return(isPinged);
   }

   public void run()
   {
      while(true)
      {
         try
         {
              this.sleep(30000);
         }
         catch(InterruptedException e)
         {
             System.out.println("SLEEP Interrupted");
         }

         if(isTrue())
         {
            setFalse();
         }
         else
         {
            String tmp = new String();
            tmp = "q:" + me;
            System.out.println(me + " has not been pinged");
            myServicer.multicast(tmp, myChannel, me);
            c.killUser(me);
            try
            {
                  System.out.println("Before being killed: " + mySocket.toString());
                  mySocket.close();
            }
            catch (IOException e)
            {
                    System.out.println("While Closing:");
                    System.out.println(e);
            }
            break;
            
         }
      }
   }
}
