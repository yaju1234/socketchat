import java.net.*;
import java.io.*;
import java.util.*;

public class ChannelList
{
   Vector Channels;
   public ChannelList()
   {
      Channels = new Vector();
   }
   
   void addChannel(String n, String d)
   {
      Channels.addElement(new Channel(n, d));   
      Channels.trimToSize();
   }
   
   String getChannelList()
   {
       Channel tmp;
       int size = Channels.size();
       String ret = new String();

       for(int i = 0; i < size; i++)
       {
          tmp = (Channel)Channels.elementAt(i);
          ret += tmp.name + ":";
       }
       return(ret);
   }
 
   String getChannelDescription(String c)
   {
       Channel tmp;
       int size = Channels.size();
       String ret = new String();

       for(int i = 0; i < size; i++)
       {
          tmp = (Channel)Channels.elementAt(i);
          if(tmp.name.equals(c))
          {
             return(tmp.description);
          }
       }
       return(null);
   }
} 

