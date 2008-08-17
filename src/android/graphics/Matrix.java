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

public class Matrix {
	
	// is this needed?
	public static final class ScaleToFit extends Enum {

		public static final ScaleToFit 
			FILL	=new ScaleToFit("FILL",0),
			START	=new ScaleToFit("START",1),
			CENTER	=new ScaleToFit("CENTER",2),
			END		=new ScaleToFit("END",3);

		public static final ScaleToFit[] values() {
			return VALUES;
		}
		public static ScaleToFit valueOf(String name) {
			return (ScaleToFit)findValue(VALUES,name);
		}

		private ScaleToFit(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final ScaleToFit VALUES[]={
			FILL,START,CENTER,END
		};
	}

	public static final int 
		MSCALE_X	=0,
		MSKEW_X		=1,
		MTRANS_X	=2,
		MSKEW_Y		=3,
		MSCALE_Y	=4,
		MTRANS_Y	=5,
		MPERSP_0	=6,
		MPERSP_1	=7,
		MPERSP_2	=8;
	
	public Matrix() {}

	public Matrix(Matrix src) {}

	public boolean isIdentity() {return false;}

	// always true, as we don't support skew & rotation? 
	public boolean rectStaysRect() {return true;}

	public void set(Matrix src) {}

	public boolean equals(Object obj) {return false;}

	public void reset() {}

	// IGNORE (-ScaleAnimation, -ScaleLayout, -ImageView's ScaleType.CENTER_CROP,
	// -Gallery
	//public void setScale(float sx, float sy, float px, float py) {}
	//public void setScale(float sx, float sy) {}

	// not used
	//public boolean preScale(float sx, float sy, float px, float py) {return false;}
	//public boolean preScale(float sx, float sy) {return false;}
	//public boolean postScale(float sx, float sy, float px, float py) {return false;}
	//public boolean postScale(float sx, float sy) {return false;}

	// IGNORE (-RotateAnimation)
	//public void setRotate(float degrees, float px, float py) {}
	//public void setRotate(float degrees) {}

	// not used
	//public boolean preRotate(float degrees, float px, float py) {return false;}
	//public boolean preRotate(float degrees) {return false;}
	//public boolean postRotate(float degrees, float px, float py) {return false;}
	//public boolean postRotate(float degrees) {return false;}
	
	// not used
	//public void setSinCos(float sinValue, float cosValue, float px, float py) {}
	//public void setSinCos(float sinValue, float cosValue) {}

	// not used
	//public void setSkew(float kx, float ky, float px, float py) {}
	//public void setSkew(float kx, float ky) {}

	// not used
	//public boolean preSkew(float kx, float ky, float px, float py) {return false;}
	//public boolean preSkew(float kx, float ky) {return false;}
	//public boolean postSkew(float kx, float ky, float px, float py) {return false;}
	//public boolean postSkew(float kx, float ky) {return false;}
	
	public boolean setConcat(Matrix a, Matrix b) {return false;}
	public boolean preConcat(Matrix other) {return false;}
	public boolean postConcat(Matrix other) {return false;}

	public void setTranslate(float dx, float dy) {}
	public boolean preTranslate(float dx, float dy) {return false;}
	public boolean postTranslate(float dx, float dy) {return false;}

	// IGNORE (used in ImageView)
	//public boolean setRectToRect(RectF src, RectF dst, ScaleToFit stf) {return false;}
	
	// not used
	//public boolean setPolyToPoly(float src[], int srcIndex, float dst[], int dstIndex, int pointCount) {return false;}
	//public float mapRadius(float radius) {return 0;}
	//public boolean invert(Matrix inverse) {return false;}

	public void mapPoints(float dst[], int dstIndex, float src[], int srcIndex, int pointCount) {}
	public void mapPoints(float dst[], float src[]) {}
	public void mapPoints(float pts[]) {}

	public void mapVectors(float dst[], int dstIndex, float src[], int srcIndex, int vectorCount) {}
	public void mapVectors(float dst[], float src[]) {}
	public void mapVectors(float vecs[]) {}

	public boolean mapRect(RectF dst, RectF src) {return false;}
	public boolean mapRect(RectF rect) {return false;}

	public void getValues(float values[]) {}
	public void setValues(float values[]) {}

	public String toString() {return null;}
}
