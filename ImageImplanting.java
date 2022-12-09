import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
public class ImageImplanting
{
static ArrayList zeropixellist=new ArrayList();

//**************************************************************************************************************



//**************************************************************************************************************

public static void setPixelValue(BufferedImage img,PixelData pd)
{
	int x=pd.x;
	int y=pd.y;
	int a=pd.a;
	int r=pd.r;
	int g=pd.g;
	int b=pd.b;
	int count=pd.count;
	int newrgb=ImageUtilities.rgb(a,r,g,b);
	img.setRGB(x,y,newrgb);
			
}

//**************************************************************************************************************

public static PixelData getZeroPixels(BufferedImage img)
{
zeropixellist.clear();
int width=img.getWidth();
int height=img.getHeight();
PixelData max=new PixelData(0,0,0,0,0,0,0);
for(int y=0;y<height;y++)
for(int x=width-1;x>=0;x--)
{
int rgb=img.getRGB(x,y);
int r=ImageUtilities.red(rgb);
int g=ImageUtilities.green(rgb);
int b=ImageUtilities.blue(rgb);
if(r==0 && g==0 && b==0)
{
PixelData pd=getNonZeroNeighbourCount(img,x,y);
zeropixellist.add(pd);
if(pd.count>max.count);
	max=pd;
}
}
return max;
}

public static int isPixelNonZero(BufferedImage matrix,int i,int j)
{
int rgb=matrix.getRGB(i,j);
int r=ImageUtilities.red(rgb);
int g=ImageUtilities.green(rgb);
int b=ImageUtilities.blue(rgb);
if(r==0 && g==0 && b==0)
	return 0;
return 1;
}		
public static PixelData getNonZeroNeighbourCount(BufferedImage matrix,int i,int j)
{
if(isPixelNonZero(matrix,i,j)==1)
return null;
PixelData pd;
int count=0;
int rcount=0;
int gcount=0;
int bcount=0;
int acount=0;
if(i>0)
{
if(isPixelNonZero(matrix,i-1,j)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i-1,j));
rcount+=ImageUtilities.red(matrix.getRGB(i-1,j));
gcount+=ImageUtilities.green(matrix.getRGB(i-1,j));
bcount+=ImageUtilities.blue(matrix.getRGB(i-1,j));
count++;
}
}
if(j>0)
{
if(isPixelNonZero(matrix,i,j-1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i,j-1));
rcount+=ImageUtilities.red(matrix.getRGB(i,j-1));
gcount+=ImageUtilities.green(matrix.getRGB(i,j-1));
bcount+=ImageUtilities.blue(matrix.getRGB(i,j-1));
count++;
}
}

if(i<matrix.getHeight()-1)
{
if(isPixelNonZero(matrix,i+1,j+1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i+1,j+1));
rcount+=ImageUtilities.red(matrix.getRGB(i+1,j+1));
gcount+=ImageUtilities.green(matrix.getRGB(i+1,j+1));
bcount+=ImageUtilities.blue(matrix.getRGB(i+1,j+1));
count++;
}
if(isPixelNonZero(matrix,i+1,j)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i+1,j));
rcount+=ImageUtilities.red(matrix.getRGB(i+1,j));
gcount+=ImageUtilities.green(matrix.getRGB(i+1,j));
bcount+=ImageUtilities.blue(matrix.getRGB(i+1,j));
count++;
}
}

if(j<matrix.getWidth()-1)
{
if(isPixelNonZero(matrix,i-1,j+1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i-1,j+1));
rcount+=ImageUtilities.red(matrix.getRGB(i-1,j+1));
gcount+=ImageUtilities.green(matrix.getRGB(i-1,j+1));
bcount+=ImageUtilities.blue(matrix.getRGB(i-1,j+1));
count++;
}

if(isPixelNonZero(matrix,i,j+1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i,j+1));
rcount+=ImageUtilities.red(matrix.getRGB(i,j+1));
gcount+=ImageUtilities.green(matrix.getRGB(i,j+1));
bcount+=ImageUtilities.blue(matrix.getRGB(i,j+1));
count++;
}
}

if(i>0 && j>0)
{
if(isPixelNonZero(matrix,i-1,j-1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i-1,j-1));
rcount+=ImageUtilities.red(matrix.getRGB(i-1,j-1));
gcount+=ImageUtilities.green(matrix.getRGB(i-1,j-1));
bcount+=ImageUtilities.blue(matrix.getRGB(i-1,j-1));
count++;
}
}

if(i<matrix.getHeight()-1 && j<matrix.getWidth()-1)
{
if(isPixelNonZero(matrix,i+1,j+1)==1)
{
acount+=ImageUtilities.alpha(matrix.getRGB(i+1,j+1));
rcount+=ImageUtilities.red(matrix.getRGB(i+1,j+1));
gcount+=ImageUtilities.green(matrix.getRGB(i+1,j+1));
bcount+=ImageUtilities.blue(matrix.getRGB(i+1,j+1));
count++;
}
}
if(count!=0)
	 pd=new PixelData(i,j,acount/count,rcount/count,gcount/count,bcount/count,count);
else 
	pd=new PixelData(i,j,0,0,0,0,0);
return pd;
}


}