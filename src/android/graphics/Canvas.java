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

	public void drawText(char text[], int index, int count, float x, float y, Paint paint) {}
	public void drawText(String text, float f, float f1, Paint paint) {}
	public void drawText(String text, int start, int end, float x, float y, Paint paint) {}
	public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {}
}
