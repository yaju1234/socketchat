import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.StringTokenizer;



class ConfUsersDraw extends Frame
{

  ConfDraw     client;           /** reference to the main frame class **/
  Label        label_mes;
  Label        label_Username;
  Label        label_Ulist;
  Label        label_tmesg;
  Label        label_pmesg;
  TextField    ts;
  TextArea     ts1; 
  List         list_Chat1;
  Button iprivate;
  Button icancel;
  StringTokenizer tokens;

  public ConfUsersDraw(ConfDraw client)
  {
    super("Private Chatroom.");
    this.client = client;  /** reference to the main frame class **/
    setLayout(null);

    this.setBackground(new Color(72,96,80));
    addNotify();
    reshape(insets().left +15,0, insets().left + insets().right + 450, insets().top + insets().bottom + 420);
    this.setResizable(false);


    Label label_pmesg = new Label("Private Message:");
    add(label_pmesg);
    label_pmesg.setFont(new Font("Dialog",Font.BOLD,12));
    label_pmesg.setForeground(Color.white);
    label_pmesg.reshape(insets().left + 30,insets().top + 30,100,15);

    ts1 = new TextArea(15,0);
    add(ts1);
    ts1.show();
    ts1.setEditable(false);
    ts1.setBackground(new Color(144,128,112));
    ts1.setForeground(Color.black);
    ts1.reshape(insets().left + 21,insets().top + 50,310,250);

    Label label_Ulist = new Label("User(s) List");
    add(label_Ulist);
    label_Ulist.setFont(new Font("Dialog",Font.BOLD,12));
    label_Ulist.setForeground(Color.white);
    label_Ulist.reshape(insets().left + 342,insets().top + 30,80,15);

    list_Chat1 = new List(12,false);
    add(list_Chat1);
    list_Chat1.show();
    list_Chat1.setBackground(new Color(144,128,112));
    list_Chat1.setForeground(Color.black);
    list_Chat1.reshape(insets().left + 342,insets().top + 50,80,250);


    Label label_tmesg = new Label("Type Here..");
    add(label_tmesg);
    label_tmesg.setFont(new Font("Dialog",Font.BOLD,12));
    label_tmesg.setForeground(Color.white);
    label_tmesg.reshape(insets().left + 21,insets().top + 310,100,15);

    ts = new TextField("");
    add(ts);
    ts.show();
    ts.setBackground(new Color(144,128,112));
    ts.setForeground(Color.black);
    ts.reshape(insets().left + 21,insets().top + 335,400,20);

    Label label_mes = new Label("You are",Label.CENTER);
    add(label_mes);
    label_mes.setFont(new Font("Dialog",Font.BOLD+Font.ITALIC,15));
    label_mes.setForeground(Color.white);
    label_mes.reshape(insets().left + 21,insets().top + 365,70,18);

    label_Username = new Label("",Label.CENTER);
    add(label_Username);
    label_Username.setFont(new Font("Dialog",Font.BOLD+Font.ITALIC,15));
    label_Username.setForeground(Color.white);
    label_Username.reshape(insets().left + 21,insets().top + 385,70,18);

    iprivate= new Button("Send");
    add(iprivate);
    iprivate.show();
    iprivate.setFont(new Font("Dialog",Font.BOLD,10));
    iprivate.reshape(insets().left+120,insets().top+370,120,18);
    iprivate.setBackground(new Color(144,128,112));
    iprivate.setForeground(Color.black);

    icancel= new Button("Cancel");
    add(icancel);
    icancel.show();
    icancel.setFont(new Font("Dialog",Font.BOLD,10));
    icancel.reshape(insets().left+260,insets().top+370,120,18);
    icancel.setBackground(new Color(144,128,112));
    icancel.setForeground(Color.black);

    client.UserName = client.introcanvas.Nick;
    client.UserName1 = client.introcanvas.Name;
    label_Username.setText(client.UserName);
	}

  public boolean action(Event e,Object obj)
	{
    String buttonText= new String((String) e.arg);

    if(buttonText.equals("Send"))
    {
      if(list_Chat1.countItems()==1)
      {
        ts1.appendText("-- You are alone in this chat room --" + "\n");
        list_Chat1.select(0);
        ts.setText("");
        return true;
      }
      else if(list_Chat1.countItems()>1)
      {
        if(ts.getText()!=null)
        { 
          String xx = new String(ts.getText());
          if(xx.length() > 55)
          {
            ts1.appendText("-- Do not type in long messages --" + "\n");
            ts1.appendText("-- TRY AGAIN... --" + "\n");
            ts.setText("");
            return true;
          }

          if(list_Chat1.getSelectedItem().equals(client.UserName1))
          {
            ts1.appendText("-- You can not send message to yourself --"+"\n");
            ts.setText("");
            return true;
          }
          int z=0;
          String sendto= null;
          if(list_Chat1.getSelectedItem().endsWith("**"))
          {
            z=list_Chat1.getSelectedItem().indexOf("**");
            sendto=list_Chat1.getSelectedItem().substring(0,z);
          }
          else
          {
            sendto=list_Chat1.getSelectedItem();
          }

          if(sendto!=null)
          {
            String message=new String(ts.getText());
            String lastmesg="$"+":"+sendto+":"+message;
            client.cp.sendData(lastmesg);
            ts1.appendText(" ("+client.UserName1+") "+message+" to ("+sendto+") "+"\n");
            ts.setText(" ");
          }
        }
        return true;
      }
    }          
    if(buttonText.equals("Cancel"))
    {
      ts1.setText("");
      this.dispose();
      client.show();
      return true;
    }
    if(e.id == Event.WINDOW_DESTROY)
    {
      this.dispose();
      return true;
    }

    if(e.key == 0)
    {
      if(list_Chat1.countItems()==1)
      {
        ts1.appendText("-- You are alone in this chat room --" + "\n");
        list_Chat1.select(0);
        ts.setText("");
        return true;
      }
      else if(list_Chat1.countItems()>1)
      {
        if(ts.getText()!=null)
        { 
          String xx = new String(ts.getText());
          if(xx.length() > 55)
          {
            ts1.appendText("-- Do not type in long messages --" + "\n");
            ts1.appendText("-- TRY AGAIN... --" + "\n");
            ts.setText("");
            return true;
          }


          if(list_Chat1.getSelectedItem().equals(client.UserName1))
          {
            ts1.appendText("-- You can not send message to yourself --"+"\n");
            ts.setText("");
            return true;

          }
          int z=0;
          String sendto= null;
          if(list_Chat1.getSelectedItem().endsWith("**"))
          {
            z=list_Chat1.getSelectedItem().indexOf("**");
            sendto=list_Chat1.getSelectedItem().substring(0,z);
          }
          else
          {
            sendto=list_Chat1.getSelectedItem();
          }

          if(sendto!=null)
          {
            String message=new String(ts.getText());
            String lastmesg="$"+":"+sendto+":"+message;
            client.cp.sendData(lastmesg);
            ts1.appendText(" ("+client.UserName1+") "+message+" to ("+sendto+") "+"\n");
            ts.setText(" ");
          }
        }
        return true;
      }
    }          



    return false;
  }
}

