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

public class Paint {
	
	public static class FontMetricsInt {
		public int top;
		public int ascent;
		public int descent;
		public int bottom;
		public int leading;
	}

	public static class FontMetrics {
		public float top;
		public float ascent;
		public float descent;
		public float bottom;
		public float leading;
	}

	public static final class Align extends Enum {
		public static final Align 
			LEFT	=new Align("LEFT",0),
			CENTER	=new Align("CENTER",1),
			RIGHT	=new Align("RIGHT",2);
		
		public static final Align[] values() {
			return VALUES;
		}
		public static Align valueOf(String name) {
			return (Align)findValue(VALUES,name);
		}

		private Align(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Align VALUES[]={
			LEFT,CENTER,RIGHT
		};
	}

	public static final class Style extends Enum {
		public static final Style 
			FILL			=new Style("FILL",0),
			STROKE			=new Style("STROKE",1),
			FILL_AND_STROKE	=new Style("FILL_AND_STROKE",2);
		
		public static final Style[] values() {
			return VALUES;
		}
		public static Style valueOf(String name) {
			return (Style)findValue(VALUES,name);
		}

		private Style(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Style VALUES[]={
			FILL,STROKE,FILL_AND_STROKE
		};
	}

	public Paint() {}
	public Paint(Paint paint) {}

	public void reset() {}

	public void set(Paint src) {}

	public Style getStyle() { return null; }
	public void setStyle(Style style) {}

	public int getColor() { return 0; }
	public void setColor(int i) {}

	public int getAlpha() { return 0; }
	public void setAlpha(int i) {}

	public void setARGB(int a, int r, int g, int b) {}

	public float getStrokeWidth() { return 0; }
	public void setStrokeWidth(float f) {}

	public Typeface getTypeface() { return null; }
	public Typeface setTypeface(Typeface typeface) { return null; }

	public Align getTextAlign() { return null; }
	public void setTextAlign(Align align) {}

	public float getTextSize() { return 0; }
	public void setTextSize(float f) { }

	public float ascent() { return 0; }
	public float descent() { return 0; }

	public float getFontMetrics(FontMetrics fontmetrics) { return 0; }
	public FontMetrics getFontMetrics() { return null; }
	public int getFontMetricsInt(FontMetricsInt fontmetricsint) { return 0; }
	public FontMetricsInt getFontMetricsInt() { return null; }

	public float measureText(char text[], int index, int count) { return 0; }
	public float measureText(String text, int i, int j) { return 0; }
	public float measureText(String text) { return 0; }
	public float measureText(CharSequence text, int start, int end) { return 0; }

	public int breakText(char ac[], int i, int j, float f, float af[]) { return 0; }
	public int breakText(CharSequence text, int start, int end, boolean measureForwards, float maxWidth, float measuredWidth[]) { return 0; }
	public int breakText(String s, boolean flag, float f, float af[]) { return 0; }

	public int getTextWidths(char text[], int index, int count, float widths[]) { return 0; }
	public int getTextWidths(CharSequence text, int start, int end, float widths[]) { return 0; }
	public int getTextWidths(String text, int start, int end, float widths[]) { return 0; }
	public int getTextWidths(String text, float widths[]) { return 0; }
}
