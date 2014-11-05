import java.applet.Applet;
import java.awt.*;
import java.util.*;

public class Conference extends Applet
{
  ConfDraw confdraw;
  IntroCanvas introcanvas ;
  public String host=new String(" ");
  public void init()
  {
    host=getParameter("IP_Address");
    this.setBackground(new Color(250,0,0));
    introcanvas = new IntroCanvas(this);
    introcanvas.show();
    introcanvas.toFront();
  }
	public void destroy()
	{
   // introcanvas.show();
    introcanvas.dispose();
  }                    
}
