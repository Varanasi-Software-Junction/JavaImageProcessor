import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

//import javax.swing.plaf.metal.MetalLookAndFeel;
public class LookAndFeelMenu extends JMenu implements ActionListener
{

//***********************************************************************
class ThemeHandler implements ActionListener
{
public void actionPerformed(ActionEvent evt)
{
int n=Integer.parseInt(evt.getActionCommand());
if(n==0)
MetalLookAndFeel.setCurrentTheme(new OceanTheme());

if(n==1)
MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
SwingUtilities.updateComponentTreeUI(parent.getContentPane());
}
}

//***********************************************************************
 public void updateLookAndFeel(String currentLookAndFeel) 
{
try 
{
UIManager.setLookAndFeel(currentLookAndFeel);
SwingUtilities.updateComponentTreeUI(parent.getContentPane());

}
catch (Exception ex)
{
System.out.println("Failed loading L&F: " + currentLookAndFeel);
System.out.println(ex);
}
}

//***********************************************************************
UIManager.LookAndFeelInfo plaf[];
public void actionPerformed(ActionEvent evt)
{
int n=Integer.parseInt(evt.getActionCommand());
System.out.println(plaf[n].getClassName());
updateLookAndFeel(plaf[n].getClassName());
}
//***********************************************************************

private JFrame parent;
public LookAndFeelMenu(JFrame parent)
{
this.parent=parent;
setText("Installed Look and Feels");
 plaf= UIManager.getInstalledLookAndFeels();
for (int i = 0, n = plaf.length; i < n; i++) 
{
JMenuItem mi=new JMenuItem( plaf[i].getName());
add(mi);
mi.setActionCommand("" + i);
mi.addActionListener(this);
}
JMenu thememenu=new JMenu("Theme");
//add(thememenu);
JMenuItem oceanmenu=new JMenuItem("Ocean");
thememenu.add(oceanmenu);
oceanmenu.setActionCommand("0");
JMenuItem defaultmenu=new JMenuItem("DefaultMetal");
thememenu.add(defaultmenu);
defaultmenu.setActionCommand("1");
ThemeHandler th=new ThemeHandler();
oceanmenu.addActionListener(th);
defaultmenu.addActionListener(th);
}
}