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

public class RectF {

	public RectF() {}

	public RectF(float left, float top, float right, float bottom) {}

	public RectF(RectF r) {}

	public RectF(Rect r) {}

	public String toString() { return null; }

	public final boolean isEmpty() { return false; }

	public final float width() { return 0; }

	public final float height() { return 0; }

	public final float centerX() { return 0; }

	public final float centerY() { return 0; }

	public void setEmpty() {}

	public void set(float left, float top, float right, float bottom) {}

	public void set(RectF src) {}

	public void set(Rect src) {}

	public void offset(float dx, float dy) {}

	public void offsetTo(float newLeft, float newTop) {}

	public void inset(float dx, float dy) {}

	public boolean contains(float x, float y) { return false; }

	public boolean contains(float left, float top, float right, float bottom) { return false; }

	public boolean contains(RectF r) { return false; }

	public boolean intersect(float left, float top, float right, float bottom) { return false; }

	public boolean intersect(RectF r) { return false; }

	public boolean setIntersect(RectF a, RectF b) { return false; }

	public boolean intersects(float left, float top, float right, float bottom) { return false; }

	public static boolean intersects(RectF a, RectF b) { return false; }

	public void round(Rect dst) {}

	public void roundOut(Rect dst) {}

	public void union(float left, float top, float right, float bottom) {}

	public void union(RectF r) {}

	public void sort() {}

	public float left;
	public float top;
	public float right;
	public float bottom;

}
