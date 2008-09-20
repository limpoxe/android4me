/*
 * Copyright 2008 Android4ME
 *
 * Licensed under the Apache License,Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.graphics;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;
import javolution.lang.Enum;
import android4me.res.IntReader;

/**
 * @author Dmitry Skiba
 *
 */
public final class Bitmap {

	public static final class Config extends Enum {
		public static final Config 
			ARGB_8888	=new Config("ARGB_8888",2);
		
		public static final Config[] values() {
			return VALUES;
		}
		public static final Config valueOf(String name) {
			return (Config)findValue(VALUES,name);
		}

		private Config(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Config VALUES[]={
			ARGB_8888
		};
	}
	
	/////////////////////////////////// methods
	
	public final static Bitmap createBitmap(Bitmap src) {
		//TODO
		return null;
	}

	public final static Bitmap createBitmap(Bitmap source,int x,int y,int width,int height) {
		//TODO
		return null;
	}

	public final static Bitmap createBitmap(int width,int height,Config config) {
		Bitmap bitmap=new Bitmap();
		bitmap.m_image=Image.createImage(width,height);
		bitmap.m_width=width;
		bitmap.m_height=height;
		return bitmap;
	}

	public final static Bitmap createBitmap(int colors[],int width,int height,Config config) {
		//TODO
		return null;
	}

	public final Bitmap copy(Config config,boolean isMutable) {
		//TODO
		return null;
	}
	
	public final int getWidth() {
		return m_width;
	}

	public final int getHeight() {
		return m_height;
	}

	public final byte[] getNinePatchChunk() {
		return m_ninePatchChunk;
	}

	public final boolean isMutable() {
		//TODO
		return false;
	}

	public final boolean hasAlpha() {
		//TODO
		return false;
	}

	public final void eraseColor(int c) {
		//TODO
	}

	public final int getPixel(int x,int y) {
		//TODO
		return 0;
	}

	public final void getPixels(int pixels[],int offset,int stride,int x,int y,int width,int height) {
		//TODO
	}

	public final void setPixel(int x,int y,int color) {
		//TODO
	}

	public final void setPixels(int pixels[],int offset,int stride,int x,int y,int width,int height) {
		//TODO
	}
	
	public final void recycle() {
	}
	
	///////////////////////////////////////////// package-visible
	
	// rename!
	final int[] getData() {
		if (m_imageData==null) {
			m_imageData=new int[m_width*m_height];
			m_image.getRGB(m_imageData,0,m_width,0,0,m_width,m_height);
			m_image=null;
		}
		return m_imageData;
	}
	
	static final Bitmap decodeFile(String name,BitmapFactory.Options options) {
		Image image;
		try {
			image=Image.createImage(name);
		}
		catch (IOException e) {
			return null;
		}
		Bitmap bitmap=new Bitmap();
		bitmap.m_width=image.getWidth();
		bitmap.m_height=image.getHeight();
		bitmap.m_image=image;
		bitmap.m_ninePatchChunk=loadNinePatchChunk(name);
		
		return bitmap;
	}
	
	///////////////////////////////////////////// implementation
	
	private Bitmap() {
	}
	
	private static final byte[] loadNinePatchChunk(String name) {
		InputStream stream=name.getClass().getResourceAsStream(name);
		if (stream==null) {
			return null;
		}
		try {
			IntReader reader=new IntReader(stream,true);
			// check PNG signature
			if (reader.readInt()!=0x89504e47 ||
				reader.readInt()!=0x0D0A1A0A)
			{
				return null;
			}
			while (true) {
				int size=reader.readInt();
				int type=reader.readInt();
				// check for nine patch chunk type (npTc) 
				if (type!=0x6E705463) {
					reader.skip(size+4/*crc*/);
					continue;
				}
				return reader.readByteArray(size);				
			}
		}
		catch (IOException e) {
			return null;
		}
	}
	
	/////////////////////////////////// data
	
	private Image m_image;
	private int	[] m_imageData;
	
	private int m_width;
	private int m_height;
	private byte[] m_ninePatchChunk;
	
	private boolean m_hasAlpha;	
}
