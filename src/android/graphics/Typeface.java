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

public class Typeface {

	public int getStyle() {
		return 0;
	}

	public final boolean isBold() {
		return false;
	}

	public final boolean isItalic() {
		return false;
	}

	public static Typeface create(String familyName, int style) {
		return null;
	}

	public static Typeface create(Typeface family, int style) {
		return null;
	}

	public static Typeface defaultFromStyle(int style) {
		return null;
	}

//	public static Typeface createFromAsset(AssetManager mgr, String path) {
//		return null;
//	}

	public static final Typeface 
		DEFAULT				=new Typeface(),
		DEFAULT_BOLD		=new Typeface(),
		DEFAULT_ITALIC		=new Typeface(),
		DEFAULT_BOLD_ITALIC	=new Typeface(),
		SANS_SERIF			=new Typeface(),
		SERIF				=new Typeface(),
		MONOSPACE			=new Typeface();

	public static final int 
		NORMAL 				=0,
		BOLD 				=1,
		ITALIC 				=2,
		BOLD_ITALIC 		=3;
}
