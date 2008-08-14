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

import android.os.Parcel;
import android.os.Parcelable;

public final class Rect implements Parcelable {
	public Rect() {}

	public Rect(int left, int top, int right, int bottom) {}

	public Rect(Rect r) {}

	public boolean equals(Object obj) { return false; }

	public String toString() { return null; }

	public final boolean isEmpty() { return false; }

	public final int width() { return 0; }

	public int height() { return 0; }

	public void setEmpty() {}

	public void set(int left, int top, int right, int bottom) {}

	public void set(Rect src) {}

	public void offset(int dx, int dy) {}

	public void offsetTo(int newLeft, int newTop) {}

	public void inset(int dx, int dy) {}

	public boolean contains(int x, int y) { return false; }

	public boolean contains(int left, int top, int right, int bottom) { return false; }

	public boolean contains(Rect r) { return false; }

	public boolean intersect(int left, int top, int right, int bottom) { return false; }

	public boolean intersect(Rect r) { return false; }

	public boolean setIntersect(Rect a, Rect b) { return false; }

	public boolean intersects(int left, int top, int right, int bottom) { return false; }

	public static boolean intersects(Rect a, Rect b) { return false; }

	public void union(int left, int top, int right, int bottom) {}

	public void union(Rect r) {}

	public void sort() {}

	public void writeToParcel(Parcel out) {}

	public void readFromParcel(Parcel in) {}

	public int left;
	public int top;
	public int right;
	public int bottom;
}
