import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
public class ImageProcessorFrame extends JFrame implements ActionListener
{
	private JMenuBar mb;
	private JMenu file,utilities;
	private LookAndFeelMenu themes;
	private ImagePanel p1,p2,p3;
	private JFileChooser chooser;
	private TrayIcon trayIcon;
	private JFrame frame;	
	public ImageProcessorFrame()
	{


if(!SystemTray.isSupported())
{
	System.err.println("System tray is not supported");
	return;
}
SystemTray tray=SystemTray.getSystemTray();
Image img=Toolkit.getDefaultToolkit().getImage("LOGO.jpg");
PopupMenu popup=new PopupMenu();
MenuItem exit=new MenuItem("Exit");
exit.addActionListener(this);
popup.add(exit);
trayIcon =new TrayIcon(img,"Image Processing ",popup);
trayIcon.setImageAutoSize(true);

try
{
 tray.add(trayIcon);
trayIcon.setActionCommand("Hello");
trayIcon.addActionListener(this);
}
catch(Exception ex)
{
System.out.println(ex);
}

		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		setSize(d.width,d.height);
		setTitle("DIGITAL IMAGE PROCESSOR ");
		chooser=new JFileChooser();
		mb=new JMenuBar();
		setJMenuBar(mb);
		file=new JMenu("File");
		themes=new LookAndFeelMenu(this);
		mb.add(themes);
		file.setMnemonic('F');
		mb.add(file);
		JMenuItem quit=new JMenuItem("Quit");
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.ALT_MASK));
		file.add(quit);
		utilities=new JMenu("Utilities");
		utilities.setMnemonic('U');
		mb.add(utilities);
		JMenuItem tograylevel=new JMenuItem("ToGrayLevel");
		tograylevel.setMnemonic('G');
		tograylevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,InputEvent.CTRL_MASK));
		utilities.add(tograylevel);
		JMenuItem toblackwhite=new JMenuItem("ToB/W");
		toblackwhite.setMnemonic('B');
		toblackwhite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK));
		utilities.add(toblackwhite);
		JMenuItem edgeextraction=new JMenuItem("EdgeExtraction");
		edgeextraction.setMnemonic('E');
		edgeextraction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		utilities.add(edgeextraction);
		JMenuItem artisticfilter=new JMenuItem("ArtisticFilter");
		artisticfilter.setMnemonic('A');
		artisticfilter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		utilities.add(artisticfilter);
		JMenuItem combineimage=new JMenuItem("CombineImage");
		combineimage.setMnemonic('C');
		combineimage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		utilities.add(combineimage);
		JMenuItem digitalnegative=new JMenuItem("Digital negative");
		digitalnegative.setMnemonic('N');
		digitalnegative.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		utilities.add(digitalnegative);
		JMenuItem compareedge=new JMenuItem("CompareEdge");
		compareedge.setMnemonic('P');
		compareedge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,InputEvent.CTRL_MASK));
		//utilities.add(compareedge);
		JMenuItem imageimplanting=new JMenuItem("ImageImplanting");
		imageimplanting.setMnemonic('I');
		imageimplanting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK));
		utilities.add(imageimplanting);
		JMenuItem desktopcapture=new JMenuItem("Desktopcapture");
		desktopcapture.setMnemonic('D');
		desktopcapture.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK));
		utilities.add(desktopcapture);
		JMenuItem thumbcompare=new JMenuItem("ThumbCompare");
		thumbcompare.setMnemonic('A');
		thumbcompare.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
		utilities.add(thumbcompare);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setLayout(new GridLayout(1,3));
		p1=new ImagePanel();
		p1.setBackground(Color.RED);
		add(p1);
		p2=new ImagePanel();
		p2.setBackground(Color.GREEN);
		add(p2);
		p3=new ImagePanel();
		p3.setBackground(Color.BLUE);
		add(p3);
		quit.addActionListener(this);
		tograylevel.addActionListener(this);
		toblackwhite.addActionListener(this);
		edgeextraction.addActionListener(this);
		artisticfilter.addActionListener(this);
		combineimage.addActionListener(this);
		digitalnegative.addActionListener(this);
		compareedge.addActionListener(this);
		imageimplanting.addActionListener(this);
		desktopcapture.addActionListener(this);
		thumbcompare.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ev)
	{
		Object ob=ev.getActionCommand();
		if(ob.equals("Hello"))
		{
		int currentstate=this.getState();
		if(currentstate==NORMAL)
		this.setState(ICONIFIED);
		else
		this.setState(NORMAL);
			//System.exit(0);
		}
		
		if(ob.equals("Quit")||ob.equals("Exit"))
		{
			System.exit(0);
		}
		if(ob.equals("ToGrayLevel"))
		{
		BufferedImage img=p1.getIcon();
		BufferedImage newimage=ImageUtilities.toGrayLevel(img);
		p3.setIcon(newimage);
		}
		if(ob.equals("ToB/W"))
		{
		BufferedImage img=p1.getIcon();
		BufferedImage newimage=ImageUtilities.toBlackWhite(img);
		p3.setIcon(newimage);		
		}
		if(ob.equals("EdgeExtraction"))
		{
			BufferedImage img= ImageUtilities.getEdges(p1.getIcon());
			p3.setIcon(img);
		}
		if(ob.equals("ArtisticFilter"))
		{
		BufferedImage img=p1.getIcon();
		BufferedImage newimage=ImageUtilities.toArtistFilter(img);
		p3.setIcon(newimage);
		}
		if(ob.equals("Digital negative"))
		{
			BufferedImage img=p1.getIcon();
			BufferedImage newimage=ImageUtilities.digitalNegative(img);
			p3.setIcon(newimage);
			
		}
		if(ob.equals("CombineImage"))
		{
			int w1=p1.getIcon().getWidth();
			int w2=p2.getIcon().getWidth();
			int maxw=Math.max(w1,w2);
			int h1=p1.getIcon().getHeight();
			int h2=p2.getIcon().getHeight();
			int maxh=Math.max(h1,h2);
			BufferedImage img1=ImageUtilities.resizeImage(p1.getIcon(),maxw,maxh);
			BufferedImage img2=ImageUtilities.resizeImage(p2.getIcon(),maxw,maxh);
			BufferedImage img=ImageUtilities.combineImage(img1,img2);
			p3.setIcon(img);
		}
		if(ob.equals("CompareEdge"))
		{
			int w1=p1.getIcon().getWidth();
			int w2=p2.getIcon().getWidth();
			int maxw=Math.max(w1,w2);
			int h1=p1.getIcon().getHeight();
			int h2=p2.getIcon().getHeight();
			int maxh=Math.max(h1,h2);
			BufferedImage img1=ImageUtilities.resizeImage(p1.getIcon(),maxw,maxh);
			BufferedImage img2=ImageUtilities.resizeImage(p2.getIcon(),maxw,maxh);
			float per=ImageUtilities.edgeComparator(img1,img2);
			System.out.println("Edges matched "+per +" %");
		}
		if(ob.equals("ImageImplanting"))
		{
			BufferedImage img=p1.getIcon();
			BufferedImage temp=ImageUtilities.getCopyImage(img);
			do
			{
				PixelData max=ImageImplanting.getZeroPixels(temp);
				ImageImplanting.setPixelValue(temp,max);
			}while(ImageImplanting.zeropixellist.size()>0);
			p3.setIcon(temp);
		}
		if(ob.equals("Desktopcapture"))
		{
			BufferedImage img=ImageUtilities.getDesktopImage();
			p3.setIcon(img);
		}
		if(ob.equals("ThumbCompare"))
		{
			int w1=p1.getIcon().getWidth();
			int w2=p2.getIcon().getWidth();
			int maxw=Math.max(w1,w2);
			int h1=p1.getIcon().getHeight();
			int h2=p2.getIcon().getHeight();
			int maxh=Math.max(h1,h2);
			BufferedImage img1=ImageUtilities.resizeImage(p1.getIcon(),maxw,maxh);
			BufferedImage img2=ImageUtilities.resizeImage(p2.getIcon(),maxw,maxh);
			int r=ImageUtilities.compareThumb(img1,img2);
			if(r==1)
				JOptionPane.showMessageDialog(frame,"!!!! SAME THUMB IMPRESSION !!!!");
			else	
				JOptionPane.showMessageDialog(frame,"!!!! DIFFER THUMB IMPRESSION !!!!");
			
		}
	}
	public static void main(String args[])
	{
		ImageProcessorFrame ipf=new ImageProcessorFrame();
		ipf.setVisible(true);
	}
}