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

/**
 * @author Dmitry Skiba
 *
 */
public final class Typeface {

	public final int getFamily() {
		return m_family;
	}
	
	public final int getStyle() {
		return m_style;
	}

	public final boolean isBold() {
		return 0!=(getStyle() & BOLD);
	}

	public final boolean isItalic() {
		return 0!=(getStyle() & ITALIC);
	}

	public final static Typeface create(String familyName,int style) {
		//TODO:
		return null;
	}

	public final static Typeface create(Typeface family,int style) {
		return new Typeface(style,family.getFamily());
	}

	public final static Typeface defaultFromStyle(int style) {
		switch (style) {
			case BOLD:			return DEFAULT_BOLD;
			case ITALIC:		return DEFAULT_ITALIC;
			case BOLD_ITALIC:	return DEFAULT_BOLD_ITALIC;
			default:			return DEFAULT;
		}
	}

	public static final int 
		NORMAL 				=0x00,
		BOLD 				=0x01,
		ITALIC 				=0x02,
		BOLD_ITALIC 		=0x03;

	public static final int
		FAMILY_DEFAULT		=0,
		FAMILY_SANS_SERIF	=1,
		FAMILY_SERIF		=2,
		FAMILY_MONOSPACE	=3;
	
	public static final Typeface 
		DEFAULT				=new Typeface(NORMAL,FAMILY_DEFAULT),
		DEFAULT_BOLD		=new Typeface(BOLD,FAMILY_DEFAULT),
		DEFAULT_ITALIC		=new Typeface(ITALIC,FAMILY_DEFAULT),
		DEFAULT_BOLD_ITALIC	=new Typeface(BOLD_ITALIC,FAMILY_DEFAULT),
		SANS_SERIF			=new Typeface(NORMAL,FAMILY_SANS_SERIF),
		SERIF				=new Typeface(NORMAL,FAMILY_SERIF),
		MONOSPACE			=new Typeface(NORMAL,FAMILY_MONOSPACE);
	
	///////////////////////////////////////////// implementation
	
	private Typeface(int style,int family) {
		m_style=style;
		m_family=family;
	}
	
	private int m_family;
	private int m_style;
}
