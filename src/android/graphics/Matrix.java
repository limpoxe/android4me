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

/**
 * @author Dmitry Skiba
 * 
 * Very limited matrix class - supports only translation.
 */
public class Matrix {

	public Matrix() {
	}
	public Matrix(Matrix src) {
		m_dx=src.m_dx;
		m_dy=src.m_dy;		
	}

	public boolean isIdentity() {
		return false;
	}

	public boolean rectStaysRect() {
		return true;
	}

	public void set(Matrix src) {
		m_dx=src.m_dx;
		m_dy=src.m_dy;
	}
	public void reset() {
		m_dx=0;
		m_dy=0;
	}

	public boolean setConcat(Matrix a,Matrix b) {
		m_dx=a.m_dx+b.m_dx;
		m_dy=a.m_dx+b.m_dy;
		return true;
	}
	public boolean preConcat(Matrix other) {
		m_dx+=other.m_dx;
		m_dy+=other.m_dy;
		return true;
	}
	public boolean postConcat(Matrix other) {
		m_dx+=other.m_dx;
		m_dy+=other.m_dy;
		return true;
	}

	public void setTranslate(float dx,float dy) {
		m_dx=dx;
		m_dy=dy;
	}
	public boolean preTranslate(float dx,float dy) {
		m_dx+=dx;
		m_dy+=dy;
		return true;
	}
	public boolean postTranslate(float dx,float dy) {
		m_dx+=dx;
		m_dy+=dy;
		return true;
	}

	public void mapPoints(float dst[],int dstIndex,float src[],int srcIndex,int pointCount) {
		for (;pointCount>0;--pointCount) {
			dst[dstIndex++]=src[srcIndex++]+m_dx;
			dst[dstIndex++]=src[srcIndex++]+m_dy;
		}
	}
	public void mapPoints(float dst[],float src[]) {
		for (int i=0;i!=src.length;) {
			dst[i]=src[i++]+m_dx;
			dst[i]=src[i++]+m_dy;
		}
	}
	public void mapPoints(float pts[]) {
		for (int i=0;i!=pts.length;) {
			pts[i]=pts[i++]+m_dx;
			pts[i]=pts[i++]+m_dy;
		}		
	}

	public void mapVectors(float dst[],int dstIndex,float src[],int srcIndex,int vectorCount) {
		
	}
	public void mapVectors(float dst[],float src[]) {
		
	}
	public void mapVectors(float vecs[]) {
		
	}

	public boolean mapRect(RectF dst,RectF src) {
		return false;
	}
	public boolean mapRect(RectF rect) {return false;}

	public boolean equals(Object obj) {
		return false;
	}

	public String toString() {
		return "Matrix[dx:"+m_dx+",dy:"+m_dy+"]";
	}
	
	/////////////////////////////////// data
	
	float m_dx;
	float m_dy;
}
