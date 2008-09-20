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

import javax.microedition.lcdui.Graphics;

/**
 * @author Dmitry Skiba
 *
 */
public class Canvas {

	public Canvas() {}
	public Canvas(Bitmap bitmap) {}

	public void setBitmap(Bitmap bitmap) {}

	public boolean isOpaque() {	return true; }

	public int getWidth() {	return 0; }
	public int getHeight() { return 0; }

	public int save() {	return 0; }
	public void restore() {}
	public int getSaveCount() { return 0; }
	public void restoreToCount(int i) {}

	public void translate(float f, float f1) {}
	public void concat(Matrix matrix) {}

	public boolean clipRect(RectF rectf) { return false; }
	public boolean clipRect(Rect rect) { return false; }
	public boolean clipRect(float f, float f1, float f2, float f3) { return false; }
	public boolean clipRect(int i, int j, int k, int l) { return false; }

	public boolean getClipBounds(Rect bounds) { return false; }
	public final Rect getClipBounds() { return null; }

	public void setMatrix(Matrix matrix) {}
	public void getMatrix(Matrix matrix) {}
	public final Matrix getMatrix() { return null ;}

	public void drawRGB(int r, int g, int b) {}
	public void drawColor(int color) {}

	public void drawPoint(float f, float f1, Paint paint) {}

	public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {}

	public void drawRect(RectF rect, Paint paint) {}
	public void drawRect(Rect r, Paint paint) {}
	public void drawRect(float left, float top, float right, float bottom, Paint paint) {}

	public void drawOval(RectF oval, Paint paint) {}
	public void drawCircle(float cx, float cy, float radius, Paint paint) {}
	public void drawArc(RectF oval, float startAngle, float sweepAngle, Paint paint) {}

	public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {}

	public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {}
	public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {}
	public void drawBitmap(int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, Paint paint) {}

	public void drawText(char text[], int index, int count, float x, float y, Paint paint) {}
	public void drawText(String text, float f, float f1, Paint paint) {}
	public void drawText(String text, int start, int end, float x, float y, Paint paint) {}
	public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {}
	
	///////////////////////////////////////////// package-visible
	
	public final void draw(NinePatch np,int x,int y,int width,int height) {
    	if (width<=np.m_notScalableWidth || height<=np.m_notScalableHeight) {
    		return;
    	}

		int bitmapWidth=np.m_bitmap.getWidth();

		int bitmapScalableWidth=(bitmapWidth-np.m_notScalableWidth);
    	int scalableWidth=(width-np.m_notScalableWidth);
    	int xStep=(bitmapScalableWidth/scalableWidth);
    	int xStepFraction=(bitmapScalableWidth%scalableWidth);
    	
    	int bitmapScalableHeight=(np.m_bitmap.getHeight()-np.m_notScalableHeight);
    	int scalableHeight=(height-np.m_notScalableHeight);
    	int yStep=(bitmapScalableHeight/scalableHeight-1)*bitmapWidth;
    	int yStepFraction=(bitmapScalableHeight%scalableHeight);
    	int yIntervalStep=(bitmapScalableHeight==scalableHeight)?
    		1:
    		bitmapScalableHeight/scalableHeight;
    	
    	int[] bitmapData=np.m_bitmap.getData();
    	int bitmapOffset=0;

		int[] buffer=getBuffer(width);
    	int bufferOffset=0;
		int maxBufferHeight=buffer.length/width;

    	int[] xIntervals=np.m_xIntervals;
    	int[] yIntervals=np.m_yIntervals;
    	
		int usedBufferHeight=0;
		int yStepAccumulator=scalableHeight;
		for (int i=0;i!=yIntervals.length;++i) {
    		int yInterval=yIntervals[i];
    		int yIntervalNotScalable=(yInterval & NinePatch.NOT_SCALABLE_INTERVAL);
    		yInterval&=~NinePatch.NOT_SCALABLE_INTERVAL;
    		int yPreviousInterval=-1;
	    	for (;yInterval!=0;) {
	    		if (yPreviousInterval==yInterval && bufferOffset!=0) {
					System.arraycopy(
						buffer,bufferOffset-width,
						buffer,bufferOffset,
						width);
					bitmapOffset+=bitmapWidth;
					bufferOffset+=width;
	    		} else {
		    		int xStepAccumulator=scalableWidth;
		    		for (int j=0;j!=xIntervals.length;++j) {
		    			int xInterval=xIntervals[j];
		    			if (0!=(xInterval & NinePatch.NOT_SCALABLE_INTERVAL)) {
		    				xInterval&=~NinePatch.NOT_SCALABLE_INTERVAL;
		    				if (xInterval>=5) {
		    					System.arraycopy(
		    						bitmapData,bitmapOffset,
		    						buffer,bufferOffset,
		    						xInterval);
		    					bitmapOffset+=xInterval;
		    					bufferOffset+=xInterval;
		    				} else {
				        		for (;xInterval!=0;) {
				        			buffer[bufferOffset]=bitmapData[bitmapOffset];
				        			bufferOffset+=1;
				        			bitmapOffset+=1;
				        			xInterval-=1;
				        		}
		    				}
		    			} else {
			        		for (;xInterval!=0;) {
			        			buffer[bufferOffset]=bitmapData[bitmapOffset];
			        			bufferOffset+=1;
			        			bitmapOffset+=xStep;
			        			xInterval-=xStep;
			        			xStepAccumulator-=xStepFraction;
			        			if (xStepAccumulator<=0) {
			        				bitmapOffset+=1;
			        				xInterval-=1;
			        				xStepAccumulator+=scalableWidth;
			        			}
			        		}
		    			}
		    		}
	    		}
	    		yPreviousInterval=yInterval;
	    		if (yIntervalNotScalable==0) {
		    		bitmapOffset+=yStep;
		    		yInterval-=yIntervalStep;
		    		yStepAccumulator-=yStepFraction;
		    		if (yStepAccumulator<=0) {
		    			bitmapOffset+=bitmapWidth;
		    			yInterval-=1;
		    			yStepAccumulator+=scalableHeight;
		    		}
	    		} else {
	    			yInterval-=1;
	    		}
		    	usedBufferHeight+=1;
		    	if (usedBufferHeight==maxBufferHeight) {
		    		m_graphics.drawRGB(
		    			buffer,0,width,
		    			x,y,
		    			width,usedBufferHeight,
		    			true);
		    		y+=usedBufferHeight;
		    		usedBufferHeight=0;
		    		bufferOffset=0;
		    	}
	    	}
    	}
		if (usedBufferHeight!=0) {
			m_graphics.drawRGB(
    			buffer,0,width,
    			x,y,
    			width,usedBufferHeight,
    			true);
		}
	}
		
	///////////////////////////////////////////// implementation
	
	private final int[] getBuffer(int minimumSize) {
		if (minimumSize>m_buffer.length) {
			m_buffer=new int[minimumSize];
		}
		return m_buffer;
	}
	
	/////////////////////////////////// data
	
	private Graphics m_graphics;
	private int[] m_buffer=new int[4*1024];
}
