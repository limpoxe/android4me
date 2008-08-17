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

import javolution.lang.Enum;

public class Canvas {

	public static final class EdgeType extends Enum {
		public static final EdgeType 
			BW	=new EdgeType("BW",0),
			AA	=new EdgeType("BW",1);
		
		public static final EdgeType[] values() {
			return VALUES;
		}
		public static EdgeType valueOf(String name) {
			return (EdgeType)findValue(VALUES,name);
		}

		private EdgeType(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final EdgeType VALUES[]={BW,AA};
	}

	public static final int MATRIX_SAVE_FLAG=1;
	public static final int CLIP_SAVE_FLAG=2;
	public static final int HAS_ALPHA_LAYER_SAVE_FLAG=4;
	public static final int FULL_COLOR_LAYER_SAVE_FLAG=8;
	public static final int CLIP_TO_LAYER_SAVE_FLAG=16;
	
	public Canvas() {}

	public Canvas(Bitmap bitmap) {}

	public void setDevice(Bitmap bitmap) {}

	public boolean isBitmapOpaque() {
		return false;
	}

	public int getBitmapWidth() {
		return 0;
	}

	public int getBitmapHeight() {
		return 0;
	}

	public int save() {
		return 0;
	}

	public int save(int i) {
		return 0;
	}

	public int saveLayer(RectF bounds, Paint paint, int saveFlags) {
		return 0;
	}

	public int saveLayer(float left, float top, float right, float bottom, Paint paint, int saveFlags) {
		return 0;
	}

	public int saveLayerAlpha(RectF bounds, int alpha, int saveFlags) {
		return 0;
	}

	public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha, int saveFlags) {
		return 0;
	}

	public void restore() {
	}

	public int getSaveCount() {
		return 0;
	}

	public void restoreToCount(int i) {
	}

	public void translate(float f, float f1) {}

	public void scale(float f, float f1) {}

	public final void scale(float sx, float sy, float px, float py) {}

	public void rotate(float f) {}

	public final void rotate(float degrees, float px, float py) {}

	public void skew(float f, float f1) {}

	public void concat(Matrix matrix) {}

	public boolean clipRect(RectF rect, Region.Op op) { return false; }

	public boolean clipRect(Rect rect, Region.Op op) { return false; }

	public boolean clipRect(RectF rectf) { return false; }

	public boolean clipRect(Rect rect) { return false; }

	public boolean clipRect(float left, float top, float right, float bottom, Region.Op op) { return false; }

	public boolean clipRect(float f, float f1, float f2, float f3) { return false; }

	public boolean clipRect(int i, int j, int k, int l) { return false; }

	public boolean clipPath(Path path, Region.Op op) { return false; }

	public boolean clipPath(Path path) { return false; }

	public boolean clipRegion(Region region, Region.Op op) { return false; }

	public boolean clipRegion(Region region) { return false; }

	public DrawFilter getDrawFilter() { return null; }

	public void setDrawFilter(DrawFilter filter) { }

	public boolean quickReject(RectF rect, EdgeType type) { return false; }

	public boolean quickReject(Path path, EdgeType type) { return false; }

	public boolean quickReject(float left, float top, float right, float bottom, EdgeType type) { return false; }

	public boolean getClipBounds(Rect bounds) { return false; }

	public final Rect getClipBounds() { return null; }

	public void getCTM(Matrix ctm) {}

	public final Matrix getCTM() { return null ;}

	public void drawRGB(int r, int g, int b) {}

	public void drawARGB(int a, int r, int g, int b) {}

	public void drawColor(int color) {}

	public void drawColor(int color, PorterDuff.Mode mode) {}

	public void drawPaint(Paint paint) {}

	public void drawPoints(float af[], int i, int j, Paint paint) {}

	public void drawPoints(float pts[], Paint paint) {}

	public void drawPoint(float f, float f1, Paint paint) {}

	public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {}

	public void drawRect(RectF rect, Paint paint) {}

	public void drawRect(Rect r, Paint paint) {}

	public void drawRect(float left, float top, float right, float bottom, Paint paint) {}

	public void drawOval(RectF oval, Paint paint) {}

	public void drawCircle(float cx, float cy, float radius, Paint paint) {}

	public void drawArc(RectF oval, float startAngle, float sweepAngle, Paint paint) {}

	public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {}

	public void drawPath(Path path, Paint paint) {}

	public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {}

	public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {}

	public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {}

	public void drawText(char text[], int index, int count, float x, float y, Paint paint) {}

	public void drawText(String s, float f, float f1, Paint paint) {}

	public void drawText(String text, int start, int end, float x, float y, Paint paint) {}

	public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {}

	public void drawPosText(char text[], int index, int count, float pos[], Paint paint) {}

	public void drawPosText(String text, float pos[], Paint paint) {}

	public void drawTextOnPath(char text[], int index, int count, Path path, float hOffset, float vOffset, Paint paint) {}

	public void drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) {}

	public void drawPicture(Picture picture) {}
}
