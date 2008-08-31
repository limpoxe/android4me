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

public final class Bitmap {

	public static final class Config extends Enum {
		public static final Config 
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
			ARGB_8888
		};
	}

	public static Bitmap createBitmap(int width, int height, boolean hasAlpha) {
		return null;
	}

	public static Bitmap createBitmap(Bitmap src) {
		return null;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height) {
		return null;
	}

	public static Bitmap createBitmap(int width, int height, Config config) {
		return null;
	}

	public static Bitmap createBitmap(int colors[], int width, int height, Config config) {
		return null;
	}

	public Bitmap copy(Config config, boolean isMutable) {
		return null;
	}
	
	public int getWidth() {
		return 0;
	}

	public int getHeight() {
		return 0;
	}

	public byte[] getNinePatchChunk() {
		return null;
	}

	public final boolean isMutable() {
		return false;
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
}
