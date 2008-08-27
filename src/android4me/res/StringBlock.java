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
package android4me.res;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dmitry Skiba
 * 
 * Block of strings, used in binary xml and arsc.
 * 
 * TODO:
 * - implement get()
 * - store all as one int[] chunk?
 * - hide constructor and return object from read()?
 *
 */
public class StringBlock {
	
	/**
	 * Type of string block chunk.
	 */
	public static final int CHUNK_TYPE=0x001C0001;
	
	/**
	 * Reads string block from stream.
	 * Stream must be positioned past the chunk type (at size).
	 * In case of exception object retains previous state. 
	 */
	public void read(InputStream stream) throws IOException {
		int chunkSize=ReadUtil.readInt(stream);
		int stringCount=ReadUtil.readInt(stream);
		int styleOffsetCount=ReadUtil.readInt(stream);
		/*?*/ReadUtil.readInt(stream);
		int stringsOffset=ReadUtil.readInt(stream);
		int stylesOffset=ReadUtil.readInt(stream);
		
		int[] stringOffsets=null;
		int[] strings=null;
		int[] styleOffsets=null;
		int[] styles=null;
		
		stringOffsets=ReadUtil.readIntArray(stream,stringCount);
		if (styleOffsetCount!=0) {
			styleOffsets=ReadUtil.readIntArray(stream,styleOffsetCount);
		}
		{
			int size=((stylesOffset==0)?chunkSize:stylesOffset)-stringsOffset;
			if ((size%4)!=0) {
				throw new IOException("StringBlock: string data size is not multiple of 4 ("+size+").");
			}
			strings=ReadUtil.readIntArray(stream,size/4);
		}
		if (stylesOffset!=0) {
			int size=(chunkSize-stylesOffset);
			if ((size%4)!=0) {
				throw new IOException("StringBlock: style data size is not multiple of 4 ("+size+").");
			}
			styles=ReadUtil.readIntArray(stream,size/4);
		}
		
		m_stringOffsets=stringOffsets;
		m_strings=strings;
		m_styleOffsets=styleOffsets;
		m_styles=styles;
	}
	
	/**
	 * Returns number of strings in block. 
	 */
	public int getCount() {
		return m_stringOffsets!=null?
			m_stringOffsets.length:
			0;
	}
	
	/**
	 * Returns raw string (without any styling information) at specified index.
	 * Returns null if index is invalid or object was not initialized.
	 */
	public String getRaw(int index) {
		if (m_stringOffsets==null ||
			index>=m_stringOffsets.length)
		{
			return null;
		}
		int offset=m_stringOffsets[index];
		int length=getShort(m_strings,offset);
		StringBuilder result=new StringBuilder(length);
		for (;length!=0;length-=1) {
			offset+=2;
			result.append((char)getShort(m_strings,offset));
		}
		return result.toString();
	}
	
	/**
	 * Not yet implemented. 
	 * 
	 * Returns string with style information (if any).
	 * Returns null if index is invalid or object was not initialized.
	 */
	public CharSequence get(int index) {
		return getRaw(index);
	}
	
	/**
	 * Returns string with style tags (html-like). 
	 */
	public String getHTML(int index) {
		String raw=getRaw(index);
		if (raw==null) {
			return raw;
		}
		int[] style=getStyle(index);
		if (style==null) {
			return raw;
		}
		StringBuilder html=new StringBuilder(raw.length()+32);
		int offset=0;
		while (true) {
			int i=-1;
			for (int j=0;j!=style.length;j+=3) {
				if (style[j+1]==-1) {
					continue;
				}
				if (i==-1 || style[i+1]>style[j+1]) {
					i=j;
				}
			}
			int start=((i!=-1)?style[i+1]:raw.length());
			for (int j=0;j!=style.length;j+=3) {
				int end=style[j+2];
				if (end==-1 || end>=start) {
					continue;
				}
				if (offset<=end) {
					html.append(raw,offset,end+1);
					offset=end+1;
				}
				style[j+2]=-1;
				html.append('<');
				html.append('/');
				html.append(getRaw(style[j]));
				html.append('>');
			}
			if (offset<start) {
				html.append(raw,offset,start);
				offset=start;
			}
			if (i==-1) {
				break;
			}
			html.append('<');
			html.append(getRaw(style[i]));
			html.append('>');
			style[i+1]=-1;
		}
		return html.toString();
	}
	
	///////////////////////////////////////////// implementation

	/**
	 * Returns style information - array of int triplets,
	 * where in each triplet:
	 * 	* first int is index of tag name ('b','i', etc.)
	 * 	* second int is tag start index in string
	 * 	* third int is tag end index in string
	 */
	private int[] getStyle(int index) {
		if (m_styleOffsets==null || m_styles==null ||
			index>=m_styleOffsets.length)
		{
			return null;
		}
		int offset=m_styleOffsets[index]/4;
		int style[];
		{
			int count=0;
			for (int i=offset;i<m_styles.length;++i) {
				if (m_styles[i]==-1) {
					break;
				}
				count+=1;
			}
			if (count==0 || (count%3)!=0) {
				return null;
			}
			style=new int[count];
		}
		for (int i=offset,j=0;i<m_styles.length;) {
			if (m_styles[i]==-1) {
				break;
			}
			style[j++]=m_styles[i++];
		}
		return style;
	}
	
	private static final int getShort(int[] array,int offset) {
		int value=array[offset/4];
		if ((offset%4)/2==0) {
			return (value & 0xFFFF);
		} else {
			return (value >>> 16);
		}
	}
	
	private int[] m_stringOffsets;
	private int[] m_strings;
	private int[] m_styleOffsets;
	private int[] m_styles;
}
