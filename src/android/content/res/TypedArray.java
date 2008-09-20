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
import android.util.TypedValue;
import android4me.res.StringBlock;

/**
 * @author Dmitry Skiba
 * 
 */
public final class TypedArray {

	public TypedArray(int length,Resources resources) {
		m_data=new int[length*VALUE_LENGTH];
		m_indices=new int[length];
		m_resources=resources;
	}
	
	public final int length() {
		return m_data.length/VALUE_LENGTH;
	}

	public final int getIndexCount() {
		return m_indexCount;
	}

	public final int getIndex(int at) {
		return m_indices[at];
	}

	public final Resources getResources() {
		return null;
	}

	public final CharSequence getText(int index) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type==TypedValue.TYPE_STRING) {
			return loadText(
				m_data[index+VALUE_IX_ASSET_COOKIE],
				data);
		}
		return TypedValue.coerceToString(type,data);
	}
	
	public final CharSequence[] getTextArray(int index) {
		//TODO: implement
		return null;
	}
	
	public final String getString(int index) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type==TypedValue.TYPE_STRING) {
			return loadString(
				m_data[index+VALUE_IX_ASSET_COOKIE],
				data);
		}
		return TypedValue.coerceToString(type,data);
	}
	
	
	public final boolean getBoolean(int index,boolean defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data!=0;
		}
		return defaultValue;
	}

	public final int getInt(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data;
		}
		//TODO: try to convert
		return defaultValue;
	}

	public final int getInteger(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data;
		}
		//TODO: ?
		return defaultValue;
	}
	
	public final float getFloat(int index,float defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type==TypedValue.TYPE_FLOAT) {
			return Float.intBitsToFloat(data);
		}
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data;
		}
		return defaultValue;
	}

	public final int getColor(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data;
		}
		if (type==TypedValue.TYPE_STRING) {
			//TODO: convert
		}
		return defaultValue;
	}

	public final float getDimension(int index,float defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if(type==TypedValue.TYPE_DIMENSION) {
			return TypedValue.complexToDimension(
				data,
				m_resources.getDisplayMetrics());
		}
		return defaultValue;
	}

	public final int getDimensionPixelOffset(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if(type==TypedValue.TYPE_DIMENSION) {
			return TypedValue.complexToDimensionPixelOffset(
				data,
				m_resources.getDisplayMetrics());
		}
		return defaultValue;
	}

	public final int getDimensionPixelSize(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if(type==TypedValue.TYPE_DIMENSION) {
			return TypedValue.complexToDimensionPixelSize(
				data,
				m_resources.getDisplayMetrics());
		}
		return defaultValue;
	}

	public final int getLayoutDimension(int index,String name) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type>=TypedValue.TYPE_FIRST_INT &&
			type<=TypedValue.TYPE_LAST_INT)
		{
			return data;
		}
		if (type==TypedValue.TYPE_DIMENSION) {
			return TypedValue.complexToDimensionPixelSize(
				data,
				m_resources.getDisplayMetrics());
		}
		throw new RuntimeException(name+" has invalid resource type ("+type+").");
	}

	public final float getFraction(int index,int base,int pbase,float defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		int data=m_data[index+VALUE_IX_DATA];
		if (type==TypedValue.TYPE_FRACTION) {
			return TypedValue.complexToFraction(data,base,pbase);
		}
		return defaultValue;
	}

	public final int getResourceId(int index,int defaultValue) {
		index*=VALUE_LENGTH;
		int type=m_data[index+VALUE_IX_TYPE];
		if (type!=TypedValue.TYPE_NULL) {
			return m_data[index+VALUE_IX_RESOURCE_ID];
		}
		return defaultValue;
	}

	public final ColorStateList getColorStateList(int index) {
		//TODO: implement
		return null;
	}
	
	public final Drawable getDrawable(int index) {
		//TODO: implement
		return null;
	}

	public final boolean getValue(int index,TypedValue outValue) {
		if (!loadValue(index)) {
			return false;
		}
		m_value.setTo(outValue);
		return true;
	}

	public final boolean hasValue(int index) {
		index*=VALUE_LENGTH;
		return m_data[index+VALUE_IX_TYPE]!=TypedValue.TYPE_NULL;
	}

	public final TypedValue peekValue(int index) {
		return loadValue(index)?m_value:null;
	}

	public final String getPositionDescription() {
		return "somewhere in the universe";
	}

	public final void recycle() {
	}

	///////////////////////////////////////////// package-visible
	
	final int[] getData() {
		return m_data;
	}
	
	final void setStrings(StringBlock strings) {
		m_strings=strings;
	}

	final void addIndex(int index) {
		m_indices[m_indexCount]=index;
		m_indexCount++;
	}

	final void reset() {
		m_indexCount=0;
	}

	final void addValue(
			int index,
			int type,int data,int assetCookie,int resourceID,int changingConfigurations)
	{
		setValueAtOffset(
			m_data,m_indexCount*VALUE_LENGTH,
			type,data,assetCookie,resourceID,changingConfigurations);
		addIndex(index);
	}

	final void setValueAt(
			int valueIndex,
			int type,int data,int assetCookie,int resourceID,int changingConfigurations)
	{
		setValueAtOffset(
			m_data,valueIndex*VALUE_LENGTH,
			type,data,assetCookie,resourceID,changingConfigurations);
	}

	private final static void setValueAtOffset(
			int[] data,int dataOffset,
			int valueType,int valueData,int assetCookie,int resourceID,int changingConfigurations)
	{
		data[dataOffset++]=valueType;
		data[dataOffset++]=valueData;
		data[dataOffset++]=assetCookie;
		data[dataOffset++]=resourceID;
		data[dataOffset++]=changingConfigurations;
	}

	final static int
		VALUE_IX_TYPE			=0,
		VALUE_IX_DATA			=1,
		VALUE_IX_ASSET_COOKIE	=2,
		VALUE_IX_RESOURCE_ID	=3,
		VALUE_IX_CHANGING_CONFIGURATIONS=4,
		VALUE_LENGTH=			5;

	///////////////////////////////////////////// implementation
	
	private final CharSequence loadText(int cookie,int data) {
		if (cookie==-1) {
			if (m_strings!=null) {
				return m_strings.get(data);
			}
		} else {
			if (m_resources!=null) {
				return m_resources.loadText(cookie,data);
			}
		}
		return null;
	}

	private final String loadString(int cookie,int data) {
		if (cookie==-1) {
			if (m_strings!=null) {
				return m_strings.getRaw(data);
			}
		} else {
			if (m_resources!=null) {
				return m_resources.loadString(cookie,data);
			}
		}
		return null;
	}
	
	private final boolean loadValue(int index) {
		return loadValueAtOffset(index*VALUE_LENGTH);
	}
	
	private final boolean loadValueAtOffset(int offset) {
		int type=m_data[offset+VALUE_IX_TYPE];
		if (type==TypedValue.TYPE_NULL) {
			return false;
		}
		if (m_value==null) {
			m_value=new TypedValue();
		}
		m_value.type=type;
		m_value.data=m_data[offset+VALUE_IX_DATA];
		m_value.assetCookie=m_data[offset+VALUE_IX_ASSET_COOKIE];
		m_value.resourceId=m_data[offset+VALUE_IX_RESOURCE_ID];
		m_value.changingConfigurations=m_data[offset+VALUE_IX_CHANGING_CONFIGURATIONS];
		if (type==TypedValue.TYPE_STRING) {
			m_value.string=loadText(m_value.assetCookie,m_value.data);
		} else {
			m_value.string=null;
		}
		return true;
	}
	
	private int[] m_data;
	private int[] m_indices;
	private int m_indexCount;

	private StringBlock m_strings;
	private Resources m_resources;
	
	private TypedValue m_value;
}
