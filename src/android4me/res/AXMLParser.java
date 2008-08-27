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
 *	* clarify interface methods, 
 *     not all behavior from XmlPullParser is supported
 * 	* understand ? values
 */
public class AXMLParser {
	
	public AXMLParser(InputStream stream) {
		m_stream=stream;
		m_started=false;
	}
	
	// See next() in XmlPullParser.
	public int next() throws IOException {
		start();
		
		m_tagType=(ReadUtil.readInt(m_stream) & 0xFF);/*other 3 bytes?*/
		/*some source length*/ReadUtil.readInt(m_stream);
		m_tagSourceLine=ReadUtil.readInt(m_stream);
		/*0xFFFFFFFF*/ReadUtil.readInt(m_stream);

		m_tagName=-1;
		m_tagAttributes=null;

		switch (m_tagType) {
			case XmlPullParser.START_DOCUMENT:
			{
				/*namespace?*/ReadUtil.readInt(m_stream);
				/*name?*/ReadUtil.readInt(m_stream);
				break;
			}
			case XmlPullParser.START_TAG:
			{
				/*0xFFFFFFFF*/ReadUtil.readInt(m_stream);
				m_tagName=ReadUtil.readInt(m_stream);
				/*flags?*/ReadUtil.readInt(m_stream);
				int attributeCount=ReadUtil.readInt(m_stream);
				/*?*/ReadUtil.readInt(m_stream);
				m_tagAttributes=new TagAttribute[attributeCount];
				for (int i=0;i!=attributeCount;++i) {
					TagAttribute attribute=new TagAttribute();
					attribute.namespace=ReadUtil.readInt(m_stream);
					attribute.name=ReadUtil.readInt(m_stream);
					attribute.valueString=ReadUtil.readInt(m_stream);
					attribute.valueType=(ReadUtil.readInt(m_stream)>>>24);/*other 3 bytes?*/
					attribute.value=ReadUtil.readInt(m_stream);
					m_tagAttributes[i]=attribute;
				}
				break;
			}
			case XmlPullParser.END_TAG:
			{
				/*0xFFFFFFFF*/ReadUtil.readInt(m_stream);
				m_tagName=ReadUtil.readInt(m_stream);
				break;
			}
			case XmlPullParser.TEXT:
			{
				m_tagName=ReadUtil.readInt(m_stream);
				/*?*/ReadUtil.readInt(m_stream);
				/*?*/ReadUtil.readInt(m_stream);
				break;
			}
			case XmlPullParser.END_DOCUMENT:
			{
				/*namespace?*/ReadUtil.readInt(m_stream);
				/*name?*/ReadUtil.readInt(m_stream);
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
		
		ReadUtil.readCheckType(m_stream,AXML_CHUNK_TYPE);
		/*chunk size*/ReadUtil.readInt(m_stream);
		
		ReadUtil.readCheckType(m_stream,StringBlock.CHUNK_TYPE);
		m_strings=new StringBlock();
		m_strings.read(m_stream);
		
		ReadUtil.readCheckType(m_stream,RESOURCEIDS_CHUNK_TYPE);
		int chunkSize=ReadUtil.readInt(m_stream);
		if (chunkSize<8 || (chunkSize%4)!=0) {
			throw new IOException("Invalid resource ids size ("+chunkSize+").");
		}
		m_resourceIDs=ReadUtil.readIntArray(m_stream,chunkSize/4-2);
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
		return m_strings.getRaw(index);
	}
	
	/////////////////////////////////// data
		
	private InputStream m_stream;
	
	private boolean m_started;
	private StringBlock m_strings;
	private int[] m_resourceIDs;
	
	private int m_tagType;
	private int m_tagSourceLine;
	private int m_tagName;
	private TagAttribute[] m_tagAttributes;
	
	private static final int 
		AXML_CHUNK_TYPE			=0x00080003,
		RESOURCEIDS_CHUNK_TYPE	=0x00080180;
}