import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.DataInputStream;
import java.net.URL;
class ConfDraw extends Frame
{
  public Conference    client;
  public IntroCanvas   introcanvas;
  Label         label_UserName;
  Label         label_mesg;
  Label         label_ChannelName;
  Label         label_UserList;
  Label         label_ChannelList;
  Label         label_mood;
  Label         label_sendto;
  Label         label_type;

  TextField     ts;
  
  List          ls;
  List          lm;
  TextArea      lChat;
  List          lUser;
  List          lCh;
  

  Button         isend;
  Button         ibye;
  Button         ichangeroom;
  Button         eprivate;
  Button         isquelch;
  Button         iunsquelch;

  String        UserName = null;
  String        UserName1=null;
  String        ChannelName = "General Chat";
  boolean usersOn = false;
  boolean send = true;
  int Count = 1;
		
  String lastAction;
  String lastMessage;

  ConnectProcessing  cp;
  RefreshUsers       refreshUsers;
  PingChatServer     ping;
  ConfUsersDraw      confusersdraw;
		
  URL url;
  DataInputStream inStream;
  String inputLine;

  public ConfDraw(Conference client,IntroCanvas i)
  {
    super("Welcome to the ChatRoom");
    System.out.println("In Constructor of ConfDraw " );      
    this.client = client;
    this.introcanvas = i;
    setLayout(null);

    this.setBackground(new Color(164,108,153));
    addNotify();
    reshape(insets().left + 15,0,insets().left + insets().right + 598,insets().top + insets().bottom + 420);
    this.setResizable(false);

    label_ChannelName = new Label("Name of Channel : " + ChannelName);
    add(label_ChannelName);
    label_ChannelName.show();
    label_ChannelName.setForeground(new Color(220,216,39));
    label_ChannelName.reshape(insets().left + 120,insets().top + 7,300,15);

    lChat = new TextArea(50,0);
    add(lChat);
    lChat.show();
    lChat.setEditable(false);
    lChat.setBackground((new Color(70,48,129)));
    lChat.setForeground(Color.white);
    lChat.reshape(insets().left + 21,insets().top + 30,413,250);

    label_ChannelList = new Label("Channel List " ,Label.CENTER);
    add(label_ChannelList);
    label_ChannelList.show();
    label_ChannelList.setForeground(new Color(220,216,39));
    label_ChannelList.reshape(insets().left + 476,insets().top + 7,98,15);

    lCh = new List(6,false);
    add(lCh);
    lCh.show();
    lCh.setBackground((new Color(70,48,129)));
    lCh.setForeground(Color.white);
    lCh.reshape(insets().left + 470,insets().top + 30,119,98);

    ichangeroom= new Button("Change Channel");
    add(ichangeroom);
    ichangeroom.show();
    ichangeroom.setFont(new Font("Dialog",Font.BOLD,10));
    ichangeroom.reshape(insets().left+470,insets().top+133,120,18);
    ichangeroom.setBackground(new Color(125,38,117));
    ichangeroom.setForeground(new Color(160,160,164));


    Label label_type = new Label("Type Here..");
    add(label_type);
    label_type.setFont(new Font("Dialog",Font.PLAIN,12));
    label_type.setForeground(new Color(220,216,39));
    label_type.reshape(insets().left + 21,insets().top + 285,80,15);

    ts = new TextField(45);
    ts.setFont(new Font("Dialog",Font.PLAIN,12));
    add(ts);
    ts.show();
    ts.setEditable(true);
    ts.reshape(insets().left + 21,insets().top + 305,413,20);
    ts.setBackground((new Color(70,48,129)));
    ts.setForeground(Color.white);
    ts.setText(" ");

    label_UserList = new Label("User List " ,Label.CENTER);
    add(label_UserList);
    label_UserList.show();
    label_UserList.setForeground(new Color(220,216,39));
    label_UserList.reshape(insets().left + 474,insets().top +170,115,15);

    lUser = new List(6, false);
    add(lUser);
    lUser.show();
    lUser.setBackground((new Color(70,48,129)));
    lUser.setForeground(Color.white);
    lUser.reshape(insets().left + 470,insets().top + 192,119,133);

    Label label_mess = new Label("You are",Label.CENTER);
    add(label_mess);
    label_mess.setFont(new Font("Dialog",Font.BOLD+Font.ITALIC,15));
    label_mess.setForeground(new Color(220,216,39));
    label_mess.reshape(insets().left + 30,insets().top + 355,80,17);

    label_UserName = new Label("",Label.CENTER);
    add(label_UserName);
    label_UserName.setFont(new Font("Dialog",Font.BOLD+Font.ITALIC,15));
    label_UserName.setForeground(new Color(220,216,39));
    label_UserName.reshape(insets().left + 30,insets().top + 375,80,17);

    label_mood = new Label("Choose Your Mood");
    add(label_mood);
    label_mood.setFont(new Font("Dialog",Font.PLAIN,12));
    label_mood.setForeground(new Color(220,216,39));
    label_mood.reshape(insets().left + 125,insets().top + 335,150,15);

    ls = new List(3,false);
    add(ls);
    ls.setForeground(Color.white);
    ls.show();
    ls.reshape(insets().left + 125,insets().top + 360,150,35);
    ls.setBackground((new Color(70,48,129)));
    ls.addItem("Select an action");
    ls.addItem("shouts at");
    ls.addItem("yells");
    ls.addItem("grins");
    ls.addItem("sneezes");
    ls.select(0);

    /*Adding buttons */

    isend= new Button("Send");
    add(isend);
    isend.show();
    isend.setFont(new Font("Dialog",Font.BOLD,10));
    isend.reshape(insets().left+300,insets().top+350,120,18);
    isend.setBackground(new Color(125,38,117));
    isend.setForeground(new Color(160,160,164));

    isquelch= new Button("(Un)squelch User");
    add(isquelch);
    isquelch.show();
    isquelch.setFont(new Font("Dialog",Font.BOLD,10));
    isquelch.reshape(insets().left+300,insets().top+375,120,18);
    isquelch.setBackground(new Color(125,38,117));
    isquelch.setForeground(new Color(160,160,164));


    eprivate= new Button("Private Chat");
    add(eprivate);
    eprivate.show();
    eprivate.setFont(new Font("Dialog",Font.BOLD,10));
    eprivate.reshape(insets().left+470,insets().top+350,120,18);
    eprivate.setBackground(new Color(125,38,117));
    eprivate.setForeground(new Color(160,160,164));

    ibye= new Button("Exit Chat");
    add(ibye);
    ibye.show();
    ibye.setFont(new Font("Dialog",Font.BOLD,10));
    ibye.reshape(insets().left+470,insets().top+375,120,18);
    ibye.setBackground(new Color(125,38,117));
    ibye.setForeground(new Color(160,160,164));


    UserName = introcanvas.Nick;
    label_UserName.setText(UserName);
    UserName1 = introcanvas.Name;

    /** drawing the user(s) online frame **/

    confusersdraw = new ConfUsersDraw(this);

    /** call the thread which will make the socket communications **/

    cp = new ConnectProcessing(this,this.client);
    cp.setPriority(10);
    cp.readFile();
    cp.start();

    /* This is the first message that client sends to the Server */

    cp.sendData("n" + ":" + UserName1 + ":" +UserName );

    /** call another thread which will update the total users online **/

    refreshUsers = new RefreshUsers(this);
    refreshUsers.setPriority(10);
    refreshUsers.start();
                                                                                                               			
    lCh.select(0);

    // call the ping thread.

    ping = new PingChatServer(this);
    ping.setPriority(10);
    ping.start();
  }

  public boolean action(Event e,Object selectedItem)
  {
    String buttonText= new String((String) e.arg);

    if(buttonText.equals("Exit Chat"))
    {
      confusersdraw.dispose();
      introcanvas.destroy();
      return true;
    }

  /* for squelch Button */

    if(buttonText.equals("(Un)squelch User"))
    {
    
      int a=0;
      if(lUser.getSelectedItem().equals(UserName1))
      {
        lChat.appendText("-- You cannot (Un) Squelch yourself --"+"\n");
        return true;
      }
      if( lUser.getSelectedItem().endsWith("**") == false)
      {
        for(a=0;a<confusersdraw.list_Chat1.countItems();a++)
        {
          if(confusersdraw.list_Chat1.getItem(a).equals(lUser.getSelectedItem()))
          {
            confusersdraw.list_Chat1.replaceItem((lUser.getSelectedItem() + "**"), a);                    
          }
        }
        lUser.replaceItem(( lUser.getSelectedItem() + "**"), lUser.getSelectedIndex());
        lUser.select(0);
      }
      else
      {
        //unsquelch the squelched user
        int i =  lUser.getSelectedItem().indexOf("**");
        String tmp = new String("");
        tmp = lUser.getSelectedItem().substring(0,i);
        for(a=0;a<confusersdraw.list_Chat1.countItems();a++)
        {
          if(confusersdraw.list_Chat1.getItem(a).equals(lUser.getSelectedItem()))
          {
            confusersdraw.list_Chat1.replaceItem(tmp,a);
          }
        }
        lUser.replaceItem(tmp, lUser.getSelectedIndex());
        lUser.select(0);
      }
      return true;
    }
/* End of handling for squelch button */

    /* for Send Button */
    if(buttonText.equals("Send")  ) 
    {
      if(lUser.countItems() == 1)
			{
        lChat.appendText("-- You are alone in this channel. --" + "\n");
        ts.setText("");
			}
      else if(lUser.countItems() > 1)
			{
        if(ts.getText() != null)
				{
          String xx = new String(ts.getText());
          if(xx.length() > 55)
          {
            lChat.appendText("-- Do not type in long messages --" + "\n");
            lChat.appendText("-- TRY AGAIN... --" + "\n");
            ts.setText("");
            return true;
          }
       
          // to Check for same user name
          if(lUser.getSelectedItem().equals(UserName1))
          {
            lChat.appendText("-- You can not send message to yourself --"+"\n");
            ts.setText("");
            return true;
          }
          if(ls.getSelectedItem().compareTo("Select an action") == 0)
          {
            if(ts.getText().length() > 0)
            {
              lastMessage = ts.getText();
              lastAction = "m" + ":" + ts.getText();
              if(send)
              {
                cp.sendData(lastAction);
              }
              lChat.appendText("(" + UserName + ") " + ts.getText() + "\n");
              ts.setText("");
            }
          }
          else if(ls.getSelectedItem().compareTo("Select an action") != 0)
          {
            if(lUser.getSelectedItem() == null)
            {
            }
            else if(lUser.getSelectedItem() != null)
            {
              /** if user has been selected **/
              for(int i = 0; i< ls.countItems(); i++)
              {
                if(ls.getItem(i).compareTo(ls.getSelectedItem()) == 0)
                {
                  if(cp.slang_value[i] == 1)
                  {
                    if( lUser.getSelectedItem().endsWith("**") == true)
                    {
                      int j =  lUser.getSelectedItem().indexOf("**");
                      String tmp1 = new String("");
                      tmp1 = lUser.getSelectedItem().substring(0,j);
                      lastMessage = ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem();
                      lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " " +
                      tmp1;
                    }
                    else
                    {
                      lastMessage = ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem();
                      lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " " +
                      lUser.getSelectedItem();
                    }
                    if(send)
                    {
                      cp.sendData(lastAction);
                    }
                    lChat.appendText("(" + UserName + ") " + ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem() + "\n");
                    ls.select(0);
                  }
                  else if(cp.slang_value[i] == 0)
                  {
                    lastMessage = ls.getSelectedItem().toUpperCase() + " ";
                    lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " ";
                    if(send)
                    {
                      cp.sendData(lastAction);
                    }
                    lChat.appendText("(" + UserName + ") " + ls.getSelectedItem().toUpperCase() + " " + "\n");
                    ls.select(0);
                  }
                }
              }
              /** end of user selected **/
            }
          }
        }
        /** end of send data **/
      }
      return true;
    }
    /* End of handling for Send Button */


    if(buttonText.equals("Private Chat"))
    {
      confusersdraw.list_Chat1.clear();
      int count=lUser.countItems();
      if(count>1)
      {
        for(int i=0;i<count;i++)
        {
        	
        	System.out.println("Users"+lUser.getItem(i));
          confusersdraw.list_Chat1.addItem(new String(lUser.getItem(i)));

        }
        confusersdraw.list_Chat1.select(0);
        confusersdraw.show();
        confusersdraw.toFront();
        return true;
      }
      else
      {
        lChat.appendText("-- You are alone in this chat room --"+"\n");
        return true;
      }
		}

    if(buttonText.equals("Change Channel"))
    {
      /** handling the change of channels. **/

      if(lCh.getSelectedItem() != null)
      {
            ChannelName =  lCh.getSelectedItem();
            label_ChannelName.setText("Name of Channel : "+ ChannelName);
            cp.sendData("c" + ":" +  ChannelName);

      }
      return true;
    }
    if(e.id == Event.WINDOW_DESTROY)
    {
      this.dispose();
      client.destroy();
      return true;
    }


    /* for Enter Button */

    if(e.key == 0)
    {
      if(lUser.countItems() == 1)
			{
        lChat.appendText("-- You are alone in this channel. --" + "\n");
        ts.setText("");
			}
      else if(lUser.countItems() > 1)
			{
        if(ts.getText() != null)
				{
          String xx = new String(ts.getText());
          if(xx.length() > 55)
          {
            lChat.appendText("-- Do not type in long messages --" + "\n");
            lChat.appendText("-- TRY AGAIN... --" + "\n");
            ts.setText("");
            return true;
          }

          // to Check for same user name
          if(lUser.getSelectedItem().equals(UserName1) )
          {
            lChat.appendText("-- You can not send message to yourself --"+"\n");
            ts.setText("");
            return true;
          }
          if(ls.getSelectedItem().compareTo("Select an action") == 0)
          {
            if(ts.getText().length() > 0)
            {
              lastMessage = ts.getText();
              lastAction = "m" + ":" + ts.getText();
              if(send)
              {
                cp.sendData(lastAction);
              }
              lChat.appendText("(" + UserName + ") " + ts.getText() + "\n");
              ts.setText("");
            }
          }
          else if(ls.getSelectedItem().compareTo("Select an action") != 0)
          {
            if(lUser.getSelectedItem() == null)
            {
            }
            else if(lUser.getSelectedItem() != null)
            {
              /** if user has been selected **/
              for(int i = 0; i< ls.countItems(); i++)
              {
                if(ls.getItem(i).compareTo(ls.getSelectedItem()) == 0)
                {
                  if(cp.slang_value[i] == 1)
                  {
                    if( lUser.getSelectedItem().endsWith("**") == true)
                    {
                      int j =  lUser.getSelectedItem().indexOf("**");
                      String tmp1 = new String("");
                      tmp1 = lUser.getSelectedItem().substring(0,j);
                      lastMessage = ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem();
                      lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " " +
                      tmp1;
                    }
                    else
                    {
                      lastMessage = ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem();
                      lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " " +
                      lUser.getSelectedItem();
                    }
                    if(send)
                    {
                      cp.sendData(lastAction);
                    }
                    lChat.appendText("(" + UserName + ") " + ls.getSelectedItem().toUpperCase() + " " + lUser.getSelectedItem() + "\n");
                    ls.select(0);
                  }
                  else if(cp.slang_value[i] == 0)
                  {
                    lastMessage = ls.getSelectedItem().toUpperCase() + " ";
                    lastAction = "m" + ":" + " " + ls.getSelectedItem().toUpperCase() + " ";
                    if(send)
                    {
                      cp.sendData(lastAction);
                    }
                    lChat.appendText("(" + UserName + ") " + ls.getSelectedItem().toUpperCase() + " " + "\n");
                    ls.select(0);
                  }
                }
              }
              /** end of user selected **/
            }
          }
        }
        /** end of send data **/
      }
      return true;
    }

    /* End of handling for Enter Button */


    return false;
  }
  
}

