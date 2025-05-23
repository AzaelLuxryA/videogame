import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class AppletNullLayout extends Applet
{
   public AppletNullLayout()
   {
   	setLayout(null);
   	
   }
   
   public void add(Component c, int x, int y, int w, int h)
   {
   	add(c);
   	
   	c.setBounds(x, y, w, h);
   }

   public void add(Button b, int x, int y, int w, int h, ActionListener AL)
   {
   	add(b, x, y, w, h);
   	
   	b.addActionListener(AL);
   }
	
   public void add(TextField tf, int x, int y, int w, int h, ActionListener AL)
   {
   	add(tf, x, y, w, h);
   	
   	tf.addActionListener(AL);
   }
	
}
