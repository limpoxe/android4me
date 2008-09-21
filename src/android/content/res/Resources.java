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
package android.content.res;

import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.util.AttributeSet;

/**
 * @author Dmitry Skiba
 *
 */
public class Resources {
	
	/////////////////////////////////// Theme
	
	public final class Theme {
		public void applyStyle(int resid, boolean force) {
		}

		public void setTo(Theme other) {
		}

		public TypedArray obtainStyledAttributes(int[] styleable) {
			return null;
		}

		public TypedArray obtainStyledAttributes(int style,int[] styleable) {
			TypedArray result=getTypedArray(styleable.length);
			m_assetManager.applyStyle(m_theme,null,0,style,styleable,result);
			return result;
		}

		public TypedArray obtainStyledAttributes(AttributeSet set, int attrs[], int defStyleAttr, int defStyleRes)
		{
			return null;
		}
//			int len = attrs.length;
//			TypedArray array = getCachedStyledAttributes(len);
//			XmlBlock.Parser parser = (XmlBlock.Parser)set;
//			AssetManager.applyStyle(mTheme, defStyleAttr, defStyleRes, parser == null ? 0 : parser.mParseState, attrs, array.mData, array.mIndices);
//			array.mRsrcs = attrs;
//			array.mXml = parser;
//			return array;
//		}
//
//		public boolean resolveAttribute(int resid, TypedValue outValue, boolean resolveRefs)
//		{
//			boolean got = mAssets.getThemeValue(mTheme, resid, outValue, resolveRefs);
//			return got;
//		}
//
//		public void dump(int priority, String tag, String prefix)
//		{
//			AssetManager.dumpTheme(mTheme, priority, tag, prefix);
//		}
		int m_theme;
	}
	
	/////////////////////////////////// NotFoundException
	
	public static class NotFoundException extends RuntimeException {
		public NotFoundException() {
		}

		public NotFoundException(String message) {
			super(message);
		}
	}
	
	///////////////////////////////////////////// methods
	
	public Resources(AssetManager assetManager,DisplayMetrics displayMetrics) {
		m_assetManager=assetManager;
		m_displayMetrics=displayMetrics;
	}
	
	public AssetManager getAssets() {
		return m_assetManager;
	}
	
	public DisplayMetrics getDisplayMetrics() {
		return m_displayMetrics;
	}
	
 	public void getValue(int id,TypedValue outValue,boolean resolveReferences)
 		throws NotFoundException
 	{
		if (!m_assetManager.getValue(id,outValue,resolveReferences)) {
			throw new NotFoundException("Value 0x"+Integer.toHexString(id)+" not found.");
		}
 	}
	
	public int getColor(int id)
		throws NotFoundException
	{
		getValue(id,m_cachedValue,true);
		if (m_cachedValue.type>=TypedValue.TYPE_FIRST_COLOR_INT &&
			m_cachedValue.type<=TypedValue.TYPE_LAST_COLOR_INT)
		{
			return m_cachedValue.data;
		}
		if (m_cachedValue.type!=TypedValue.TYPE_STRING) {
			throwBadValueTypeException(m_cachedValue,"color");
			return -1;
		}
		//TODO: implement coercion of string to ColorStateList
		return 0;
	}
	
	public String getString(int id)
		throws NotFoundException
	{
		getValue(id,m_cachedValue,true);
		if (m_cachedValue.type!=TypedValue.TYPE_STRING) {
			throwBadValueTypeException(m_cachedValue,"string");
			return null;
		}
		return m_cachedValue.string.toString();
	}

	public String getString(int id,Object formatArgs)
		throws NotFoundException
	{
		//TODO
		return null;
	}
	
	public CharSequence getText(int id)
		throws NotFoundException
	{
		getValue(id,m_cachedValue,true);
		if (m_cachedValue.type!=TypedValue.TYPE_STRING) {
			throwBadValueTypeException(m_cachedValue,"text");
			return null;
		}
		return m_cachedValue.string;
	}
	
	public int getInteger(int id)
		throws NotFoundException
	{
		getValue(id,m_cachedValue,true);
		if (m_cachedValue.type>=TypedValue.TYPE_FIRST_INT &&
			m_cachedValue.type<=TypedValue.TYPE_LAST_INT)
		{
			return m_cachedValue.data;
		}
		throwBadValueTypeException(m_cachedValue,"integer");
		return -1;				
	}
	
	public XmlResourceParser getLayout(int id)
		throws NotFoundException
	{
		//TODO:!
		return null;
	}

	public Drawable getDrawable(int id)
		throws NotFoundException
	{
		//TODO:!
		return null;
	}
	
	public Theme newTheme() {
		return new Theme();
	}
	
	
	public String getResourceName(int id)
		throws NotFoundException
	{
		return null;
	}

	public String getResourcePackageName(int id)
		throws NotFoundException
	{
		return null;
	}
	
	public String getResourceTypeName(int id)
		throws NotFoundException
	{
		return null;
	}
	
	public TypedArray obtainAttributes(AttributeSet set,int[] styleableIDs) {
		return obtainStyledAttributes(
			0,
			(AXmlResourceParser)set,
			0,
			0,
			styleableIDs);
	}
	
	public TypedArray obtainStyledAttributes(
			int themeID,
			AXmlResourceParser parser,
			int defaultStyleAttributeID,
			int styleID,
			int[] styleableIDs)
	{
		TypedArray result=getTypedArray(styleableIDs.length);
		m_assetManager.applyStyle(
			themeID,
			parser,
			defaultStyleAttributeID,
			styleID,
			styleableIDs,
			result);
		return result;
	}
	
	///////////////////////////////////////////// package-visible
	
	final CharSequence loadText(int cookie,int data) {
		return m_assetManager.loadText(cookie,data);
	}
	
	final String loadString(int cookie,int data) {
		return m_assetManager.loadString(cookie,data);
	}
	
    ColorStateList loadColorStateList(TypedValue value,int id)
		throws NotFoundException
	{
		//TODO implement
		return null;
	}
	
	Drawable loadDrawable(TypedValue value,int id)
		throws NotFoundException
	{
		//TODO implement
		return null;
	}

	///////////////////////////////////////////// implementation

	private final TypedArray getTypedArray(int length) {
		return new TypedArray(length,this);
	}
	
	private final void throwBadValueTypeException(TypedValue value,String expectedType)
		throws NotFoundException
	{
		throw new NotFoundException(
			"Value "+value.resourceId+" has type "+value.type+
			" and not '"+expectedType+"'."
		); 
	}
	
	/////////////////////////////////// data
	
	private DisplayMetrics m_displayMetrics;
	private AssetManager m_assetManager;
	
	private TypedValue m_cachedValue=new TypedValue();
}
