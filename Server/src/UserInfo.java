import java.net.*;
import java.io.*;
import java.util.*;

public class UserInfo
{
   public String username;
   public String loginname;
   public String chatroom;
   public DataInputStream input;
   public DataOutputStream output;
   
   public UserInfo(String name, String lname, String c, DataInputStream in, DataOutputStream out)
   {
      this.username = name;
      this.loginname = lname;
      this.chatroom = c;
      this.input = in;
      this.output = out;
   }
}
