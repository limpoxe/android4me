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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author Dmitry Skiba
 * 
 * Parser for Android's binary xml files (axml).
 * 
 * Parser follows XmlPullParser interface, but does not implement it.
 * You can implement XmlPullParser ontop of this class and contribute
 * it to Android4ME project. See http://code.google.com/p/android4me
 * 
 * TODO: 
 *  - use StringBlock
 *  - clarify interface methods, 
 *     not all behavior from XmlPullParser is supported
 *  - add more sanity checks
 *  - understand ? values
 */
public class AXMLParser {
	
	public AXMLParser(InputStream stream) {
		m_stream=stream;
		m_offset=0;
		m_started=false;
	}
	
	// See next() in XmlPullParser.
	public int next() throws IOException {
		start();
		
		m_tagType=(readInt() & 0xFF);/*other 3 bytes?*/
		/*some source length*/readInt();
		m_tagSourceLine=readInt();
		/*0xFFFFFFFF*/readInt();

		m_tagName=-1;
		m_tagAttributes=null;

		switch (m_tagType) {
			case XmlPullParser.START_DOCUMENT:
			{
				/*namespace?*/readInt();
				/*name?*/readInt();
				break;
			}
			case XmlPullParser.START_TAG:
			{
				/*0xFFFFFFFF*/readInt();
				m_tagName=readInt();
				/*flags?*/readInt();
				int attributeCount=readInt();
				/*?*/readInt();
				m_tagAttributes=new TagAttribute[attributeCount];
				for (int i=0;i!=attributeCount;++i) {
					TagAttribute attribute=new TagAttribute();
					attribute.namespace=readInt();
					attribute.name=readInt();
					attribute.valueString=readInt();
					attribute.valueType=(readInt()>>>24);/*other 3 bytes?*/
					attribute.value=readInt();
					m_tagAttributes[i]=attribute;
				}
				break;
			}
			case XmlPullParser.END_TAG:
			{
				/*0xFFFFFFFF*/readInt();
				m_tagName=readInt();
				break;
			}
			case XmlPullParser.TEXT:
			{
				m_tagName=readInt();
				/*?*/readInt();
				/*?*/readInt();
				break;
			}
			case XmlPullParser.END_DOCUMENT:
			{
				/*namespace?*/readInt();
				/*name?*/readInt();
				break;
			}
			default:
			{
				throw new IOException("Invalid tag type ("+m_tagType+").");
			}
		}
		return m_tagType;
	}
	
	// See getEventType() in XmlPullParser.
	public int getEventType() {
		return m_tagType;
	}
	
	// See getName() in XmlPullParser.
	public String getName() {
		if (m_tagName==-1) {
			return null;
		}
		return getString(m_tagName);
	}
	
	// See getLineNumber() in XmlPullParser.
	public int getLineNumber() {
		return m_tagSourceLine;
	}
	
	// See getAttributeCount() in XmlPullParser.
	public int getAttributeCount() {
		if (m_tagAttributes==null) {
			return -1;
		}
		return m_tagAttributes.length;
	}
	
	// See getAttributeNamespace() in XmlPullParser.
	public String getAttributeNamespace(int index) {
		return getString(getAttribute(index).namespace);
	}
	
	// See getAttributeName() in XmlPullParser.
	public String getAttributeName(int index) {
		return getString(getAttribute(index).name);
	}

	// Returns resource ID for attribute name.
	public int getAttributeNameResourceID(int index) {
		int resourceIndex=getAttribute(index).name;
		if (m_resourceIDs==null ||
			resourceIndex<0 || resourceIndex>=m_resourceIDs.length)
		{
			return 0;
		}
		return m_resourceIDs[resourceIndex];
	}
	
	// See TypedValue.TYPE_ values.
	public int getAttributeValueType(int index) {
		return getAttribute(index).valueType;
	}

	// Returns string value if attribute type is TypedValue.TYPE_STRING,
	//  or empty string otherwise.
	public String getAttributeValueString(int index) {
		return getString(getAttribute(index).valueString);
	}
	
	// Returns integer value for attribute.
	// Value interpretation is based on type.
	// For TypedValue.TYPE_STRING meaning is unknown.
	public int getAttributeValue(int index) {
		return getAttribute(index).value;
	}
	
	///////////////////////////////////////////// implementation
	
	private static final class TagAttribute {
		public int namespace;
		public int name;
		public int valueString;
		public int valueType;
		public int value;
	}
	
	private void start() throws IOException {
		if (m_started) {
			return;
		}
		m_started=true;
		
		int signature=readInt();
		if (signature!=0x80003) {
			throw new IOException("Invalid signature ("+signature+").");
		}
		/*chunk size*/readInt();
		
		/*chunk signature*/readInt();
		/*chunk size*/readInt();
		int stringCount=readInt();
		/*?*/readInt();
		/*?*/readInt();
		/*?*/readInt();
		/*?*/readInt();
		
		m_strings=new String[stringCount];
		{
			int offsets[]=new int[stringCount];
			for (int i=0;i!=stringCount;++i) {
				offsets[i]=readInt();
			}
			
			int baseOffset=m_offset;
			for (int c=0;c!=stringCount;++c) {
				long offset=m_offset-baseOffset;
				int index=0;
				for (;index!=stringCount;++index) {
					if (offsets[index]==offset) {
						break;
					}
				}
				if (index==stringCount) {
					throw new IOException("Invalid string offset ("+offset+").");
				}
				m_strings[index]=readString();
			}
		}
		
		// Align to 4byte boundary
		readInt(m_offset%4);
		
		/*chunk signature*/readInt();
		int resourceIDLength=readInt()-8;
		
		if (resourceIDLength<0) {
			throw new IOException("Invalid resource id length ("+resourceIDLength+").");
		}
		m_resourceIDs=new int[resourceIDLength/4];
		for (int i=0;i!=m_resourceIDs.length;++i) {
			m_resourceIDs[i]=readInt();
		}
	}

	private TagAttribute getAttribute(int index) {
		if (m_tagAttributes==null) {
			throw new IndexOutOfBoundsException("Attributes are not available.");
		}
		if (index>=m_tagAttributes.length) {
			throw new IndexOutOfBoundsException("Invalid attribute index ("+index+").");
		}
		return m_tagAttributes[index];
	}
	
	private String getString(int index) {
		if (index==-1) {
			return "";
		}
		if (index>=0 && index<m_strings.length) {
			return m_strings[index];
		} else {
			throw new IndexOutOfBoundsException("Invalid string index ("+index+").");
		}
	}
	
	private final int readInt() throws IOException {
		return readInt(4);
	}
	
	private final int readShort() throws IOException {
		return readInt(2);
	}
	
	private final String readString() throws IOException {
		int length=readShort();
		StringBuilder builder=new StringBuilder(length);
		for (int i=0;i!=length;++i) {
			builder.append((char)readShort());
		}
		readShort();
		return builder.toString();
	}
	
	private final int readInt(int length) throws IOException {
		int result=0;
		for (int i=0;i!=length;++i) {
			int b=m_stream.read();
			if (b==-1) {
				throw new EOFException();
			}
			m_offset+=1;
			result|=(b<<(i*8));
		}
		return result;		
	}
	
	/////////////////////////////////// data
		
	private InputStream m_stream;
	private int m_offset;
	
	private boolean m_started;
	private String[] m_strings;
	private int[] m_resourceIDs;
	
	private int m_tagType;
	private int m_tagSourceLine;
	private int m_tagName;
	private TagAttribute[] m_tagAttributes;
}