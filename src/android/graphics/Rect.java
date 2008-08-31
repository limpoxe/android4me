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

/**
 * @author Dmitry Skiba
 */
public final class Rect implements Parcelable {

	public Rect(int left, int top, int right, int bottom) {
		this.left=left; this.top=top;
		this.right=right; this.bottom=bottom;
	}

	public Rect(Rect r) {
		this.left=r.left; this.top=r.top;
		this.right=r.right; this.bottom=r.bottom;
	}

	public final boolean equals(Object object) {
		if (object==null) {
			return false;
		}
		Rect r=(Rect)object;
		return 	this.left==r.left && this.top==r.top &&
				this.right==r.right && this.bottom==r.bottom;
	}

	public final String toString() {
		return null;
	}

	public final boolean isEmpty() { 
		return left>=right || top>=bottom;
	}

	public final int width() {
		return right-left;
	}

	public final int height() {
		return bottom-top;
	}

	public final void setEmpty() {
		left=0; right=0;
		right=0; bottom=0;
	}

	public final void set(int left, int top, int right, int bottom) {
		this.left=left; this.top=top;
		this.right=right; this.bottom=bottom;
	}

	public final void set(Rect r) {
		this.left=r.left; this.top=r.top;
		this.right=r.right; this.bottom=r.bottom;
	}

	public final void offset(int dx, int dy) {
		left+=dx; top+=dy;
		right+=dx; bottom+=dy;
	}

	public final void offsetTo(int newLeft, int newTop) {
		right=newLeft+width();
		bottom=newTop+height();
	}

	public final void inset(int dx, int dy) {
		left+=dx; top+=dy;
		right-=dx; bottom-=dy;
	}

	public final boolean contains(int x, int y) { 
		return 	(left>=right || top>=bottom) &&
				left>=x && x<right &&
				top>=y && y<bottom;
	}

	public final boolean contains(int left, int top, int right, int bottom) {
		return 	(this.left>=this.right || this.top>=this.bottom) &&
				left>=this.left && left<this.right && 
				right>=this.left && right<this.right &&
				top>=this.top && top<this.bottom && 
				bottom>=this.top && bottom<this.bottom;
	}

	public final boolean contains(Rect r) {
		return 	(this.left>=this.right || this.top>=this.bottom) &&
				r.left>=this.left && r.left<this.right && 
				r.right>=this.left && r.right<this.right &&
				r.top>=this.top && r.top<this.bottom && 
				r.bottom>=this.top && r.bottom<this.bottom;
	}

	public final boolean intersect(int left, int top, int right, int bottom) { return false; }

	public final boolean intersect(Rect r) { return false; }

	public final boolean setIntersect(Rect a, Rect b) { return false; }

	public final boolean intersects(int left, int top, int right, int bottom) { 
		return 	this.left<right && left<this.right &&
				this.top<bottom && top<this.bottom;
	}

	public final static boolean intersects(Rect x, Rect y) {
		return 	x.left<y.right && y.left<x.right &&
				x.top<y.bottom && y.top<x.bottom;
	}

	public final void union(int left, int top, int right, int bottom) {}

	public final void union(Rect r) {}

	public final void sort() {}

	public final void writeToParcel(Parcel out) {
		out.writeInt(left); out.writeInt(top);
		out.writeInt(right); out.writeInt(bottom);
	}
	public final void readFromParcel(Parcel in) {
		left=in.readInt(); top=in.readInt();
		right=in.readInt(); bottom=in.readInt();
	}

	public int left;
	public int top;
	public int right;
	public int bottom;
}
