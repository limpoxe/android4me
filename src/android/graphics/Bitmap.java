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
import java.io.OutputStream;

import javolution.lang.Enum;

public final class Bitmap implements Parcelable {
	
	public static final class CompressFormat extends Enum {
		public static final CompressFormat 
			JPEG	=new CompressFormat("JPEG",0),
			PNG		=new CompressFormat("PNG",1),
			ZLIB	=new CompressFormat("ZLIB",2);
		
		public static final CompressFormat[] values() {
			return VALUES;
		}
		public static CompressFormat valueOf(String name) {
			return (CompressFormat)findValue(VALUES,name);
		}

		private CompressFormat(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final CompressFormat VALUES[]={
			JPEG,PNG,ZLIB
		};
	}

	public static final class Config extends Enum {
		public static final Config 
			RGB_565		=new Config("RGB_565",0),
			ARGB_4444	=new Config("ARGB_4444",1),
			ARGB_8888	=new Config("ARGB_8888",2);
		
		public static final Config[] values() {
			return VALUES;
		}
		public static Config valueOf(String name) {
			return (Config)findValue(VALUES,name);
		}

		private Config(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Config VALUES[]={
			RGB_565,ARGB_4444,ARGB_8888
		};
	}

	public static Bitmap createBitmap(int width, int height, boolean hasAlpha) {
		return null;
	}

	public int width() {
		return 0;
	}

	public int height() {
		return 0;
	}

	private static void checkXYSign(int x, int y) {}

	private static void checkWidthHeight(int width, int height) {}

	public Bitmap copy(Config config, boolean isMutable) {
		return null;
	}

	public static Bitmap createBitmap(Bitmap src) {
		return null;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height) {
		return null;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter) {
		return null;
	}

	public static Bitmap createBitmap(int width, int height, Config config) {
		return null;
	}

	public static Bitmap createBitmap(int colors[], int width, int height, Config config) {
		return null;
	}

	public byte[] getNinePatchChunk() {
		return null;
	}

	public boolean compress(CompressFormat format, int quality, OutputStream stream) {
		return false;
	}

	public final boolean isMutable() {
		return false;
	}

	public final int getWidth() {
		return 0;
	}

	public final int getHeight() {
		return 0;
	}

	public final boolean hasAlpha() {
		return false;
	}

	public void eraseColor(int c) {
		
	}

	public int getPixel(int x, int y) {
		return 0;
	}

	public void getPixels(int pixels[], int offset, int stride, int x, int y, int width, int height) {
		
	}

	public void setPixel(int x, int y, int color) {
		
	}

	public void setPixels(int pixels[], int offset, int stride, int x, int y, int width, int height) {
		
	}

	public boolean hasMipMap() {
		return false;
	}

	public void buildMipMap(boolean flag) {
	}

	public void removeMipMap() {
	}

	public void writeToParcel(Parcel p) {}

	public Bitmap extractAlpha() {
		return null;
	}

	public Bitmap extractAlpha(Paint paint, int offsetXY[]) {
		return null;
	}
}
