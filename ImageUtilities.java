import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class ImageUtilities
{ 

//**************************method to get the copy of given image*********************************//


public static BufferedImage getCopyImage(BufferedImage img)
{
int height=img.getHeight();
int width=img.getWidth();
BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int y=0;y<height;y++)
		{
			for(int x=0;x<width;x++)
			{
				int newrgb=img.getRGB(x,y);
				 temp.setRGB(x,y,newrgb);
			}
		}

return temp;
}


//***************************************************************************************************//
public static BufferedImage getImplantedImage(BufferedImage img)
{
int height=img.getHeight();
int width=img.getWidth();
BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
return temp;
}
//***************************************************************************************************//

	public static float edgeComparator(BufferedImage img1,BufferedImage img2)
	{
		int height=img1.getHeight();
       		int width=img1.getWidth();		
		BufferedImage temp1,temp2;
		temp1=getEdges(img1);
		temp2=getEdges(img2);
		int count=0,matchcount=0;
		for(int y=0;y<height;y++)
		{
			for(int x=0;x<width;x++)
			{
				int rgb=temp1.getRGB(x,y);
				if(rgb!=255)
				{
					count++;
					if(temp1.getRGB(x,y)==temp2.getRGB(x,y))
						matchcount++;
				}
			}
		}
		System.out.println("count= "+count +" matched = "+matchcount);
		return(matchcount*100/count);
	}	


//***************************************************************************************************//
	public static BufferedImage getEdges(BufferedImage img)
        {
		int height=img.getHeight();
       		int width=img.getWidth();  
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            	int[][] gx =  { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };  
            	int[][] gy =  { { 1, 2, 1 }, { 0, 0, 0 }, { -1, -2, -1 } };        
            	for (int y = 1; y<height - 1;y++) 
             	{
                	for (int x = 1; x< width - 1; x++)    
                	{
                    		float new_x = 0, new_y = 0;
                    		float c;
		    		int a=0,r,g,b,rgb;
                    		for (int hw = -1; hw < 2; hw++) 
                    		{
                        		for (int wi = -1; wi < 2; wi++)
                        		{
						rgb=img.getRGB(x+wi,y);
						a=alpha(rgb);
						r=red(rgb);
						g=green(rgb);
						b=blue(rgb);
                                		c = (r+g+b) / 3;
		                            	new_x += gx[hw + 1][ wi + 1] * c;
                            			new_y += gy[hw + 1][ wi + 1] * c;
                        		}
                    		}			
                    		if (new_x * new_x + new_y * new_y > 100*100)
	                    	{     
					int newrgb=rgb(a,0,0,0);                   
                       		        temp.setRGB(x,y,newrgb);
                    		}
                    		else
                        	{
                        		int newrgb=rgb(a,255,255,255);                   
	                        	temp.setRGB(x,y,newrgb);
	                	}
                	}
            	}
            	return temp;
        }

//***************************************************************************************************//

public static BufferedImage getDesktopImage()
{
try
{
GraphicsEnvironment env=GraphicsEnvironment.getLocalGraphicsEnvironment();
GraphicsDevice screen=env.getDefaultScreenDevice();
Robot robot=new Robot(screen);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
BufferedImage img=robot.createScreenCapture(new Rectangle(0,0,d.width,d.height));
return img;
}
catch(Exception ex)
{
System.out.println(ex);
return null;
}
}
//****************method to reverse the input image***************************************************//


	public static BufferedImage reverseImage(BufferedImage img)
	{
		int width=img.getWidth();
		int height=img.getHeight();
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int y=0;y<height;y++)
			for(int x=width-1;x>=0;x--)
			{
				int rgb=img.getRGB(x,y);
				temp.setRGB(width-1-x,y,rgb);
			}
		return temp;
	}

//********************method to resize the image for the purpose of imagecomparison and imagecombine.*********//

	public static BufferedImage resizeImage(BufferedImage img,int width,int height)
	{
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g=temp.getGraphics();
		g.drawImage(img,0,0,width,height,null);
		return temp;
	}

//************************************************method to combine to images********************************//

	public static BufferedImage combineImage(BufferedImage img1,BufferedImage img2)
	{
		int height=img1.getHeight();
		int width=img1.getWidth();
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int y=0;y<height;y++)
			for(int x=0;x<width;x++)
			{
				int rgb1=img1.getRGB(x,y);
				int rgb2=img2.getRGB(x,y);
				int a1=alpha(rgb1);
				int a2=alpha(rgb2);
				int r1=red(rgb1);
				int r2=red(rgb2);
				int g1=green(rgb1);
				int g2=green(rgb2);
				int b1=blue(rgb1);
				int b2=blue(rgb2);
				int a=(a1+a2)/2;
				int r=(r1+r2)/2;
				int g=(g1+g2)/2;
				int b=(b1+b2)/2;
				int newrgb=rgb(a,r,g,b);
				temp.setRGB(x,y,newrgb);
			}
		return temp;
	}

//*******************method to adjust the attributes of RGB in the range (0-255)***********************//

	public static int adjust(int n)
	{
		if(n<0)
			return 0;
		else if(n>255)
			return 255;
		else
			return n;
	}

//*****************************************method to get digital negative********************************// 

	public static BufferedImage digitalNegative(BufferedImage img)
	{
		BufferedImage temp=toGrayLevel(img);
		int width=temp.getWidth();	
		int height=temp.getHeight();
		for(int y=0;y<height;y++)
			for(int x=0;x<width;x++)
			{
					int a=alpha(temp.getRGB(x,y));
					int avg=average(temp.getRGB(x,y));
					int t=255-avg;
					int newrgb=rgb(a,t,t,t);
					temp.setRGB(x,y,newrgb);
			}
		return temp;
	}

//*******************************method to compare thumb impression****************************************//

public static int compareThumb(BufferedImage img1,BufferedImage img2)
	{
		int height=img1.getHeight();
		int width=img1.getWidth();
		for(int y=0;y<height;y++)
			for(int x=0;x<width;x++)
			{
				if(img1.getRGB(x,y)!=img2.getRGB(x,y))
				return 0;
			}
		return 1;
	}


//***********************method to calculate the average of the rgb for graylevel image******************//

	public static int average(int rgb)
	{
		int r=red(rgb);
		int g=green(rgb);
		int b=blue(rgb);
		int avg=(r+g+b)/3;
		return(avg);
	}

//***************************method to convert a color image into black & white image*********************//

	public static BufferedImage toBlackWhite(BufferedImage img)
	{
		BufferedImage temp=toGrayLevel(img);
		int width=temp.getWidth();	
		int height=temp.getHeight();
		int min=average(temp.getRGB(0,0));
		int max=average(temp.getRGB(0,0));
		for(int y=0;y<=height-1;y++)
			for(int x=0;x<=width-1;x++)
			{
				int rgb=temp.getRGB(x,y);
				int avg=average(rgb);
				if(avg<min)
					min=avg;
				if(avg>max)
					max=avg;	
			}
			for(int y=0;y<height;y++)
				for(int x=0;x<width;x++)
				{
					int a=alpha(temp.getRGB(x,y));
					int avg=average(temp.getRGB(x,y));
					int t=avg-min;
					t=t*255/(max-min);
					int newrgb=rgb(a,t,t,t);
					temp.setRGB(x,y,newrgb);
				}
			return temp;
	}


//************************method to convert a color image into Artist filter image**************************//

	public static BufferedImage toArtistFilter(BufferedImage img)
	{
		BufferedImage temp=toGrayLevel(img);
		int width=temp.getWidth();
		int height=temp.getHeight();
		int min=temp.getRGB(0,0);
		int max=temp.getRGB(0,0);
		for(int y=0;y<=height-1;y++)
			for(int x=0;x<=width-1;x++)
			{
				if(img.getRGB(x,y)<min)
					min=temp.getRGB(x,y);
				else 
					max=temp.getRGB(x,y);	
			}
			for(int y=0;y<height;y++)
				for(int x=0;x<width;x++)
				{
					int t=temp.getRGB(x,y)-min;
					t=t*255/(max-min);
					temp.setRGB(x,y,t);
				}
		return temp;
	}


//**************************method to convert a color image into gray level image**********************//


	public static BufferedImage toGrayLevel(BufferedImage img)
	{
		int width=img.getWidth();
		int height=img.getHeight();
		BufferedImage temp=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int y=0;y<=height-1;y++)
			for(int x=0;x<=width-1;x++)
			{	
				int a=alpha(img.getRGB(x,y));
				int rgb=img.getRGB(x,y);
				int avg=average(rgb);
				int newrgb=rgb(a,avg,avg,avg);
				temp.setRGB(x,y,newrgb);
			}
		return temp;
	}

//******************************methods used to operate on RGB and over its attributes**************************//

	public static int rgb(int a,int r,int g,int b)
	{
		return (a << 24) | (r << 16) | (g << 8) | b; 
	}	
	public static int alpha(int rgb)
	{
		return ((rgb >> 24) & 0xff); 
	}
	public static int red(int rgb)
	{
		return ((rgb >> 16) & 0xff); 
	}
	public static int green(int rgb)
	{
		return ((rgb >> 8) & 0xff); 
	}
	public static int blue(int rgb)
	{
		return ((rgb) & 0xff); 
	}

//*****************************methods to extract the value of different attributes from RGB values*******************//

public static int get(int rgb,ValueType v)
{
	if(v==ValueType.ALPHA)
		return alpha(rgb);
	if(v==ValueType.RED)
		return red(rgb);
	if(v==ValueType.GREEN)
		return green(rgb);
	return blue(rgb);
}

//**********************method to save the selected image on the hard disk***********************************//

	public static boolean saveImage(BufferedImage img,File file,String format)
	{
		try
		{
			ImageIO.write(img,format,file);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return false;
		}
	}
//***********************method to read the image from the file***********************************************//

public static BufferedImage readBufferedImage(File file)
{
try
{
BufferedImage img=ImageIO.read(file);
return img;
}
catch(Exception ex)
{
System.out.println(ex);
return null;
}
}
}