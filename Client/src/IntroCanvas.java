import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class IntroCanvas extends Frame 
{ 
   public ConfDraw confdraw;  
   public Conference conference;
   Label label_name;
   Label label_nick;
   Label label_logo; 
   Label label_copywrite;
   Label label_enter;
   Label label_register;
   TextField ts0,ts1;
   Button sbutton; 
   public String Name = new String(""); 
   public String Nick = new String("");
   public Color backcolor,stripcolor;
   public IntroCanvas(Conference c)
   {
    super("Chatroom Login");
    this.conference = c;
    setLayout(null);
    backcolor = new Color(131,146,214);
    stripcolor= new Color(0,0,160);
    this.setBackground(backcolor);
    addNotify();
    reshape(insets().left + 45,30,insets().left+insets().right + 498,insets().top + insets().bottom + 335); 
    this.setResizable(false); 


    label_logo = new Label("Java Chat",Label.CENTER); 
    add(label_logo); 
    label_logo.setFont(new Font("Helvetica",Font.BOLD+ Font.ITALIC  ,45)); 
    label_logo.setForeground(Color.cyan);
    label_logo.setBackground(stripcolor); 
    label_logo.reshape(insets().left + 0,insets().top + 30,500,50); 

    label_copywrite = new Label("Copyright 1999 - Anuja,CV,Hiren,Neeraj",Label.CENTER);
    add(label_copywrite); 
    label_copywrite.setFont(new Font("Helvetica",Font.PLAIN,11)); 
    label_copywrite.setForeground(Color.black); 
    label_copywrite.reshape(insets().left + 0,insets().top + 90,500,15);

    label_enter = new Label("Enter your Name and Nick Name",Label.CENTER);
    add(label_enter); 
    label_enter.setFont(new Font("Helvetica",Font.PLAIN,15)); 
    label_enter.setForeground(Color.black); 
    label_enter.reshape(insets().left + 0,insets().top + 150,500,20);

    label_register = new Label("To Register yourself",Label.CENTER);
    add(label_register); 
    label_register.setFont(new Font("Helvetica",Font.PLAIN,15)); 
    label_register.setForeground(Color.black); 
    label_register.reshape(insets().left + 0,insets().top + 175,500,20);


    label_name= new Label("Name:"); 
    add(label_name); 
    label_name.setFont(new Font("Helvetica",Font.PLAIN,13)); 
    label_name.setForeground(Color.black); 
    label_name.reshape(insets().left + 55,insets().top +230,50,15); 

    ts0=new TextField(45);
    ts0.setFont(new Font("Dialog",Font.PLAIN,15)); 
    add(ts0); 
    ts0.show();
    ts0.reshape(insets().left + 55,insets().top + 250,200,25); 
    ts0.setBackground(Color.black); 
    ts0.setForeground(Color.cyan); 
 
    label_nick= new Label("Nick Name:"); 
    add(label_nick); 
    label_nick.setFont(new Font("Helvetica",Font.PLAIN,13)); 
    label_nick.setForeground(Color.black); 
    label_nick.reshape(insets().left + 260,insets().top +230,90,15); 
 
    ts1=new TextField(45); 
    ts1.setFont(new Font("Dialog",Font.PLAIN,15)); 
    add(ts1); 
    ts1.show();
    ts1.reshape(insets().left + 260,insets().top + 250,200,25); 
    ts1.setBackground(Color.black); 
    ts1.setForeground(Color.cyan);
    
    sbutton=new Button("Login");
    add(sbutton); 
    sbutton.show();
    sbutton.setFont(new Font("Helvetica",Font.PLAIN,15)); 
    sbutton.reshape(insets().left + 210,insets().top + 290,100,20);
    sbutton.setBackground(new Color(0,0,160)); 
    sbutton.setForeground(Color.cyan);
   } 
 

	public void destroy()
	{
    confdraw.cp.sendData("q" + ":");
    confdraw.dispose();
    conference.destroy();
  }                    


   public boolean action(Event e,Object SelectedItem) 
   { 
    String buttonText = new String((String)e.arg) ;
    int count=0;int count1=0; 
    if(buttonText.equals("Login"))
    {
         Name=ts0.getText();
         Nick=ts1.getText();
         if((Name.length()!=0)&&(Nick.length()!=0))
         {
         this.hide();
         confdraw = new ConfDraw(conference,this);
         confdraw.show();
         confdraw.toFront();
         return true; 
         }                     
         else return false;
    }
    else return false;
   }
}     
 
 
 
 
 
 
 
