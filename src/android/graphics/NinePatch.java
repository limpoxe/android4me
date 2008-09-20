/*
 * Copyright 2008 Android4ME
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.graphics;

/**
 * @author Dmitry Skiba
 *
 */
public final class NinePatch {
	
	public NinePatch(Bitmap bitmap,byte[] chunk,String name) {
		m_bitmap=bitmap;
		if (!parseChunk(chunk)) {		
			throw new IllegalArgumentException("Nine patch chunk is invalid.");
		}
	}

	public NinePatch(Bitmap bitmap) {
		this(bitmap,bitmap.getNinePatchChunk(),null);
	}
	
	public final int getWidth() {
		return m_bitmap.getWidth();
	}

	public final int getHeight() {
		return m_bitmap.getHeight();
	}
	
	public final boolean hasAlpha() {
		return m_bitmap.hasAlpha();
	}

	public final void setPaint(Paint paint) {
	}

	public final void draw(Canvas canvas,Rect location) {
		canvas.draw(
			this,
			location.left,location.top,
			location.width(),location.height());
	}

	public final void draw(Canvas canvas,RectF location) {
		draw(canvas,GraphicsUtil.convert(location));
	}

	public final void draw(Canvas canvas,Rect location,Paint paint) {
		draw(canvas,location);
	}
	
	public final static boolean isNinePatchChunk(byte[] chunk) {
		//TODO
		return chunk!=null;
	}
	
	///////////////////////////////////////////// package-visible
	
    Bitmap m_bitmap;
	
	int[] m_padding;
	int[] m_xIntervals;
	int[] m_yIntervals;
	int m_notScalableWidth;
	int m_notScalableHeight;
	
	static final int NOT_SCALABLE_INTERVAL=0x80000000;
	
	///////////////////////////////////////////// implementation
	
	private final boolean parseChunk(byte[] chunk) {
    	if (chunk==null ||
    		chunk.length<4 || (chunk.length%4)!=0)
    	{
    		return false;
    	}

    	if (chunk[0]!=0) {
    		return false;
    	}
		int xPointCount=(chunk[1] & 0xFF);
		int yPointCount=(chunk[2] & 0xFF);
		int colorCount=(chunk[3] & 0xFF);
		
		// header + 3 unknown values + 4 paddings + interval points + colors
		int npcLength=(1+3+4+xPointCount+yPointCount+colorCount);
		if (npcLength!=chunk.length/4) {
			return false;
		}
		
		int offset=4+8; // skip header + 2 unknown values
		
		m_padding=new int[4];
		for (int i=0;i!=4;++i) {
			m_padding[i]=getInt(chunk,offset);
			offset+=4;
		}
		
		offset+=4; // skip unknown value
		
		int bitmapWidth=m_bitmap.getWidth();
		int bitmapHeight=m_bitmap.getHeight();
		
		m_xIntervals=new int[xPointCount+1];
		for (int i=0;i!=xPointCount;++i) {
			int point=getInt(chunk,offset);
			if (point<0 || point>=bitmapWidth) {
				return false;
			}
			m_xIntervals[i]=point;
			offset+=4;
		}
		
		m_yIntervals=new int[yPointCount+1];
		for (int i=0;i!=yPointCount;++i) {
			int point=getInt(chunk,offset);
			if (point<0 || point>=bitmapHeight) {
				return false;
			}		
			m_yIntervals[i]=point;
			offset+=4;
		}
		
		m_notScalableWidth=createIntervals(m_xIntervals,bitmapWidth);
		m_notScalableHeight=createIntervals(m_yIntervals,bitmapHeight);

		return true;
	}
	
	private static final int createIntervals(int[] intervals,int dimension) {
		int pointCount=intervals.length-1;
		intervals[pointCount]=dimension;
		int notScalable=intervals[0];
		for (int i=pointCount;i!=0;--i) {
			intervals[i]-=intervals[i-1];
			if ((i%2)==0) {
				notScalable+=intervals[i];
				intervals[i]|=NOT_SCALABLE_INTERVAL;
			}
		}
		intervals[0]|=NOT_SCALABLE_INTERVAL;
		return notScalable;
	}
	
    private static final int getInt(byte[] bytes,int offset) {
    	return 
    		((bytes[offset++] & 0xFF)<<24) |
    		((bytes[offset++] & 0xFF)<<16) |
    		((bytes[offset++] & 0xFF)<<8 ) |
    		((bytes[offset++] & 0xFF)    );
    }	
}