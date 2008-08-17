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

public class Path {
	
	public static final class Direction extends Enum {
		public static final Direction 
			CW			=new Direction("CW",0),
			CCW			=new Direction("CCW",1);

		public static final Direction[] values() {
			return VALUES;
		}
		public static Direction valueOf(String name) {
			return (Direction)findValue(VALUES,name);
		}

		private Direction(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Direction VALUES[]={
			CW,CCW
		};
	}

	public static final class FillType extends Enum {
		public static final FillType 
			WINDING		=new FillType("WINDING",0),
			EVEN_ODD	=new FillType("EVEN_ODD",1);
		
		public static final FillType[] values() {
			return VALUES;
		}
		public static FillType valueOf(String name) {
			return (FillType)findValue(VALUES,name);
		}

		private FillType(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final FillType VALUES[]={
			WINDING,EVEN_ODD
		};
	}



	public Path() {}

	public Path(Path src) {}

	public void reset() {}

	public void set(Path src) {}

	public FillType getFillType() { return null; }

	public void setFillType(FillType ft) {}

	public boolean isEmpty() { return false; }

	public boolean isRect(RectF rect) { return false; }

	public void computeBounds(RectF bounds, boolean exact) {}

	public void incReserve(int extraPtCount) {}

	public void moveTo(float x, float y) {}

	public void rMoveTo(float dx, float dy) {}

	public void lineTo(float x, float y) {}

	public void rLineTo(float dx, float dy) {}

	public void quadTo(float x1, float y1, float x2, float y2) {}

	public void rQuadTo(float dx1, float dy1, float dx2, float dy2) {}

	public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {}

	public void rCubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {}

	public void arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo) {}

	public void arcTo(RectF oval, float startAngle, float sweepAngle) {}

	public void close() {}

	public void addRect(RectF rect, Direction dir) {}

	public void addRect(float left, float top, float right, float bottom, Direction dir) {}

	public void addOval(RectF oval, Direction dir) {}

	public void addCircle(float x, float y, float radius, Direction dir) {}

	public void addArc(RectF oval, float startAngle, float sweepAngle) {}

	public void addRoundRect(RectF rect, float rx, float ry, Direction dir) {}

	public void addRoundRect(RectF rect, float radii[], Direction dir) {}

	public void addPath(Path src, float dx, float dy) {}

	public void addPath(Path src) {}

	public void addPath(Path src, Matrix matrix) {}

	public void offset(float dx, float dy, Path dst) {}

	public void offset(float dx, float dy) {}

	public void transform(Matrix matrix, Path dst) {}

	public void transform(Matrix matrix) {}
}
