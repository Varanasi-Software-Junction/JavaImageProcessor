import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
public class ImagePanel extends JPanel implements java.awt.event.ActionListener
{
	private BufferedImage img;
	public void actionPerformed(java.awt.event.ActionEvent evt)
	{
		JFileChooser chooser=new JFileChooser();
		Object ob=evt.getActionCommand();
		if(ob.equals("Open"))
		{
			int result=chooser.showOpenDialog(this);
			if(result!=chooser.APPROVE_OPTION)
			return;
			setIcon(ImageUtilities.readBufferedImage(chooser.getSelectedFile()));
		}
		if(ob.equals("Save"))
		{
			int result=chooser.showSaveDialog(this);
			if(result!=chooser.APPROVE_OPTION)
			return;
			ImageUtilities.saveImage(getIcon(),chooser.getSelectedFile(),"png");
		}
	}
	public ImagePanel()
	{
		JPopupMenu p=new JPopupMenu();
		JMenuItem openmenu=new JMenuItem("Open");
		openmenu.setMnemonic('O');
		openmenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		p.add(openmenu);
		JMenuItem savemenu=new JMenuItem("Save");
		savemenu.setMnemonic('S');
		savemenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		p.add(savemenu);
		savemenu.addActionListener(this);
		openmenu.addActionListener(this);
		setComponentPopupMenu(p);
	}
	public void setIcon(BufferedImage img)
	{
		this.img=img;
		repaint();
	}
	public BufferedImage getIcon()
	{
		return img;
	}	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(img==null)
			return;
		Dimension d=getSize();
		g.drawImage(img,0,0,d.width,d.height,null);
	}
}