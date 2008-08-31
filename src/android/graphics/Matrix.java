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

// Matrix supports only translation.
public class Matrix {

	public Matrix() {}
	public Matrix(Matrix src) {}

	public boolean isIdentity() {return false;}

	public boolean rectStaysRect() {
		return true;
	}

	public void set(Matrix src) {}
	public void reset() {}

	public boolean setConcat(Matrix a, Matrix b) {return false;}
	public boolean preConcat(Matrix other) {return false;}
	public boolean postConcat(Matrix other) {return false;}

	public void setTranslate(float dx, float dy) {}
	public boolean preTranslate(float dx, float dy) {return false;}
	public boolean postTranslate(float dx, float dy) {return false;}

	public void mapPoints(float dst[], int dstIndex, float src[], int srcIndex, int pointCount) {}
	public void mapPoints(float dst[], float src[]) {}
	public void mapPoints(float pts[]) {}

	public void mapVectors(float dst[], int dstIndex, float src[], int srcIndex, int vectorCount) {}
	public void mapVectors(float dst[], float src[]) {}
	public void mapVectors(float vecs[]) {}

	public boolean mapRect(RectF dst, RectF src) {return false;}
	public boolean mapRect(RectF rect) {return false;}

	public boolean equals(Object obj) {return false;}

	public String toString() {return null;}
}
