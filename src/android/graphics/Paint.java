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

	public static final class Join extends Enum {
		public static final Join 
			MITER	=new Join("MITER",0),
			ROUND	=new Join("ROUND",1),
			BEVEL	=new Join("BEVEL",2);
	
		public static final Join[] values() {
			return VALUES;
		}
		public static Join valueOf(String name) {
			return (Join)findValue(VALUES,name);
		}
	
		private Join(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Join VALUES[]={
			MITER,ROUND,BEVEL
		};
	}

	public static final class Cap extends Enum {
		public static final Cap 
			BUTT	=new Cap("BUTT",0),
			ROUND	=new Cap("ROUND",1),
			SQUARE	=new Cap("SQUARE",2);

		public static final Cap[] values() {
			return VALUES;
		}
		public static Cap valueOf(String name) {
			return (Cap)findValue(VALUES,name);
		}

		private Cap(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Cap VALUES[]={
			BUTT,ROUND,SQUARE
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

	public static final int ANTI_ALIAS_FLAG = 1;
	public static final int FILTER_BITMAP_FLAG = 2;
	public static final int DITHER_FLAG = 4;
	public static final int UNDERLINE_TEXT_FLAG = 8;
	public static final int STRIKE_THRU_TEXT_FLAG = 16;
	public static final int FAKE_BOLD_TEXT_FLAG = 32;
	public static final int LINEAR_TEXT_FLAG = 64;
	public static final int SUBPIXEL_TEXT_FLAG = 128;
	public static final int DEV_KERN_TEXT_FLAG = 256;


	public Paint() {}

	public Paint(int flags) {}

	public Paint(Paint paint) {}

	public void reset() {}

	public void set(Paint src) {}

	public int getFlags() { return 0; }

	public void setFlags(int i) {}

	public final boolean isAntiAlias() { return false; }

	public void setAntiAlias(boolean flag) {}

	public final boolean isDither() { return false; }

	public void setDither(boolean flag) {}

	public final boolean isLinearText() { return false; }

	public void setLinearText(boolean flag) {}

	public final boolean isSubpixelText() { return false; }

	public void setSubpixelText(boolean flag) {}

	public final boolean isUnderlineText() { return false; }

	public void setUnderlineText(boolean flag) {}

	public final boolean isStrikeThruText() { return false; }

	public void setStrikeThruText(boolean flag) {}

	public final boolean isFakeBoldText() { return false; }

	public void setFakeBoldText(boolean flag) {}

	public final boolean isFilterBitmap() { return false; }

	public void setFilterBitmap(boolean flag) {}

	public Style getStyle() { return null; }

	public void setStyle(Style style) {}

	public int getColor() { return 0; }

	public void setColor(int i) {}

	public int getAlpha() { return 0; }

	public void setAlpha(int i) {}

	public void setARGB(int a, int r, int g, int b) {}

	public float getStrokeWidth() { return 0; }

	public void setStrokeWidth(float f) {}

	public float getStrokeMiter() { return 0; }

	public void setStrokeMiter(float f) {}

	public Cap getStrokeCap() { return null; }

	public void setStrokeCap(Cap cap) {}

	public Join getStrokeJoin() { return null; }

	public void setStrokeJoin(Join join) {}

	public boolean getFillPath(Path src, Path dst) { return false; }

	public Shader getShader() { return null; }

	public Shader setShader(Shader shader) { return null; }

	public ColorFilter getColorFilter() { return null; }

	public ColorFilter setColorFilter(ColorFilter filter) { return null; }

	public Xfermode getXfermode() { return null; }

	public Xfermode setXfermode(Xfermode xfermode) { return null; }

	public PathEffect getPathEffect() { return null; }

	public PathEffect setPathEffect(PathEffect effect) { return null; }

	public MaskFilter getMaskFilter() { return null; }

	public MaskFilter setMaskFilter(MaskFilter maskfilter) { return null; }

	public Typeface getTypeface() { return null; }

	public Typeface setTypeface(Typeface typeface) { return null; }

	public Rasterizer getRasterizer() { return null; }

	public Rasterizer setRasterizer(Rasterizer rasterizer) { return null; }

	public void setShadowLayer(float f, float f1, float f2, int i) { }

	public void clearShadowLayer() {}

	public Align getTextAlign() { return null; }

	public void setTextAlign(Align align) {}

	public float getTextSize() { return 0; }

	public void setTextSize(float f) { }

	public float getTextScaleX() { return 0; }

	public void setTextScaleX(float f) { }

	public float getTextSkewX() { return 0; }

	public void setTextSkewX(float f) { }

	public float ascent() { return 0; }

	public float descent() { return 0; }

	public float getFontMetrics(FontMetrics fontmetrics) { return 0; }

	public FontMetrics getFontMetrics() { return null; }

	public int getFontMetricsInt(FontMetricsInt fontmetricsint) { return 0; }

	public FontMetricsInt getFontMetricsInt() { return null; }

	public float getFontSpacing() { return 0; }

	public float measureText(char ac[], int i, int j) { return 0; }

	public float measureText(String s, int i, int j) { return 0; }

	public float measureText(String s) { return 0; }

	public float measureText(CharSequence text, int start, int end) { return 0; }

	public int breakText(char ac[], int i, int j, float f, float af[]) { return 0; }

	public int breakText(CharSequence text, int start, int end, boolean measureForwards, float maxWidth, float measuredWidth[]) { return 0; }

	public int breakText(String s, boolean flag, float f, float af[]) { return 0; }

	public int getTextWidths(char text[], int index, int count, float widths[]) { return 0; }

	public int getTextWidths(CharSequence text, int start, int end, float widths[]) { return 0; }

	public int getTextWidths(String text, int start, int end, float widths[]) { return 0; }

	public int getTextWidths(String text, float widths[]) { return 0; }

	public void getTextPath(char text[], int index, int count, float x, float y, Path path) {}

	public void getTextPath(String text, int start, int end, float x, float y, Path path) {}


}
