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
import android.os.Parcel;
import android.os.Parcelable;

public class Region implements Parcelable {
	
	public static final class Op extends Enum {
		public static final Op 
			DIFFERENCE	=new Op("DIFFERENCE",0),
			INTERSECT	=new Op("INTERSECT",1),
			UNION		=new Op("UNION",2),
			XOR			=new Op("XOR",3),
			REVERSE_DIFFERENCE=new Op("REVERSE_DIFFERENCE",4),
			REPLACE		=new Op("REPLACE",5);

		public static final Op[] values() {
			return VALUES;
		}
		public static Op valueOf(String name) {
			return (Op)findValue(VALUES,name);
		}

		private Op(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Op VALUES[]={
			DIFFERENCE,INTERSECT,UNION,
			XOR,REVERSE_DIFFERENCE,REPLACE
		};
	}

	public Region() {}

	public Region(Region region) {}

	public Region(Rect r) {}

	public Region(int left, int top, int right, int bottom) {}

	public void setEmpty() {}

	public boolean set(Region region) { return false; }

	public boolean set(Rect r) { return false; }

	public boolean set(int left, int top, int right, int bottom) { return false; }

	public boolean setPath(Path path, Region clip) { return false; }

	public boolean setPath(Path path) { return false; }

	public boolean isEmpty() { return false; }

	public boolean isRect() { return false; }

	public boolean isComplex() { return false; }

	public Rect getBounds() { return null; }

	public boolean getBounds(Rect r) { return false; }

	public Path getBoundaryPath() { return null; }

	public boolean getBoundaryPath(Path path) { return false; }

	public boolean contains(int i, int j) { return false; }

	public boolean quickContains(Rect r) { return false; }

	public boolean quickContains(int i, int j, int k, int l) { return false; }

	public boolean quickReject(Rect r) { return false; }

	public boolean quickReject(int i, int j, int k, int l) { return false; }

	public boolean quickReject(Region region) { return false; }

	public void translate(int dx, int dy) { }

	public void translate(int i, int j, Region region) { }

	public final boolean union(Rect r) { return false; }

	public boolean op(Rect r, Op op) { return false; }

	public boolean op(int left, int top, int right, int bottom, Op op) { return false; }

	public boolean op(Region region, Op op) { return false; }

	public boolean op(Rect rect, Region region, Op op) { return false; }

	public boolean op(Region region1, Region region2, Op op) { return false; }

	public void writeToParcel(Parcel p) {}

	Region(int ni) {}
}
