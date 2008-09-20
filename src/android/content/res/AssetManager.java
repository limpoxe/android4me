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

import java.io.IOException;
import java.io.InputStream;
import android.util.TypedValue;
import android4me.res.ARSCFile;
import android4me.res.IntReader;
import android4me.res.StringBlock;
import android4me.res.ARSCFile.Asset;
import android4me.res.ARSCFile.Content;
import android4me.res.ARSCFile.Pkge;

/**
 * @author Dmitry Skiba
 * 
 * TODO:
 * 	* Theme class
 * 	* cache style(s) data location in StyleData
 *
 */
public class AssetManager {

	public AssetManager(InputStream resources,InputStream systemResources)
		throws IOException
	{
		if (resources!=null) {
			m_resources=ARSCFile.read(new IntReader(resources,false));
		}
		if (systemResources!=null) {
			m_systemResources=ARSCFile.read(new IntReader(systemResources,false));
		}
	}
	
    public TypedValue getValue(int id,boolean resolveReferences) {
    	TypedValue value=new TypedValue();
    	if (!getValue(id,value,resolveReferences)) { 
    		return null;
    	}
    	return value;
    }
    	
    public boolean getValue(int id,TypedValue outValue,boolean resolveReferences) {
    	if (!locateValue(id,m_valueLocation)) {
    		return false;
    	}
    	int[] data=m_valueLocation.content.data;
    	int offset=m_valueLocation.contentOffset;
		if (!isValue(data[offset+VALUE_IX_HEADER])) {
			return false;
		}
		int valueType=extractType(data[offset+VALUE_IX_TYPE]);
		int valueData=data[offset+VALUE_IX_DATA];
		if (valueType==TypedValue.TYPE_REFERENCE && resolveReferences) {
			return getValue(valueData,outValue,true);
		}
		outValue.type=valueType;
		outValue.data=valueData;
		outValue.assetCookie=0;
		outValue.resourceId=id;
    	outValue.changingConfigurations=m_valueLocation.content.
    		asset.flags[m_valueLocation.contentIndex];
		if (valueType==TypedValue.TYPE_STRING) {
			outValue.string=m_resources.strings.get(valueData);
		} else {
			outValue.string=null;
		}
    	return true;
    }
    
	public String getResourceName(int id) {
		return null;
	}

	public String getResourcePackageName(int id) {
		Asset asset=findAsset(id);
    	if (asset==null) {
    		return null;
    	}
    	return asset.pkge.name;
	}
	
	public String getResourceTypeName(int id) {
		Asset asset=findAsset(id);
    	if (asset==null) {
    		return null;
    	}
    	StringBlock names=asset.pkge.assetNames;
    	if (names.getCount()>asset.id) {
    		return null;
    	}
    	return names.getRaw(asset.id-1);
	}
	
	///////////////////////////////////////////// package-visible
	
	final void applyStyle(
				int themeID,
				AXmlResourceParser parser,
				int defaultStyleAttributeID,
				int styleID,
				int[] styleableIDs,
				TypedArray result)
	{
		int styleAttributeID=0;
		if (parser!=null) {
			parser.fetchAttributes(styleableIDs,result);
			styleAttributeID=parser.getStyleAttribute();
			if (styleAttributeID==0) {
				styleAttributeID=defaultStyleAttributeID;
			}
		}
		int fetchedIndex=(result.getIndexCount()!=0?0:-1);
		for (int i=0;i!=styleableIDs.length;++i) {
			if (fetchedIndex!=-1 &&
				i==result.getIndex(fetchedIndex))
			{
				int[] resultData=result.getData();
				int offset=fetchedIndex*TypedArray.VALUE_LENGTH;
				int type=resultData[offset+TypedArray.VALUE_IX_TYPE];
				if (type==TypedValue.TYPE_REFERENCE) {
					getValue(
						resultData[offset+TypedArray.VALUE_IX_DATA],
						result,i);
				}
				fetchedIndex+=1;
				continue;
			}
			int id=styleableIDs[i];
			if (getStyleValue(styleAttributeID,id,result,result.getIndexCount()) ||
				getStyleValue(styleID,id,result,result.getIndexCount()) ||
				getStyleValue(themeID,id,result,result.getIndexCount()))
			{
				result.addIndex(i);
			}
				
		}
	}
	
	final CharSequence loadText(int cookie,int data) {
		Pkge pkge=findPackage(cookie);
		if (pkge==null) {
			return null;
		}
		return pkge.file.strings.get(data);
	}
	
	final String loadString(int cookie,int data) {
		Pkge pkge=findPackage(cookie);
		if (pkge==null) {
			return null;
		}
		return pkge.file.strings.getRaw(data);
	}
	
    ///////////////////////////////////////////// helper classes
	
	private static final class ValueLocation {
		public Content content;
		public int contentOffset;
		public int contentIndex;
	}
	
	private static final class StyleData {
		public StyleData parent;
		public int[] data;
		public int dataOffset;
	}
	
	///////////////////////////////////////////// implementation
	
	private void getValue(int id,TypedArray array,int arrayIndex) {
		ValueLocation location=new ValueLocation();
		while (true) { 
			if (!locateValue(id,location)) {
				return;
			}
			int[] contentData=location.content.data;
			int contentOffset=location.contentOffset;
			if (!isValue(contentData[contentOffset+VALUE_IX_HEADER])) {
				return;
			}
			int type=extractType(contentData[contentOffset+VALUE_IX_TYPE]);
			int data=contentData[contentOffset+VALUE_IX_DATA];
			int assetCookie=(type==TypedValue.TYPE_STRING)?
				location.content.asset.pkge.id:
				0;
			int changingConfigurations=location.content.
				asset.flags[location.contentIndex];
			array.setValueAt(
				arrayIndex,
				type,data,assetCookie,id,changingConfigurations);
			if (type!=TypedValue.TYPE_REFERENCE || data==id) {
				break;
			}
			id=data;
		}
	}
	
	private final boolean getStyleValue(int style,int id,TypedArray result,int resultIndex) {
		if (style==0) {
			return false;
		}
		ValueLocation location=new ValueLocation();
		if (!locateValue(style,location) ||
			!isBagValue(location.content.data[location.contentOffset]))
		{
			return false;
		}
		return getStyleValue(location,id,result,resultIndex);		
	}
	
	private final boolean getStyleValue(ValueLocation location,int id,TypedArray result,int resultIndex) {
		int[] data=location.content.data;
		int offset=location.contentOffset;
		int parent=data[offset+BAG_IX_PARENT];
		int count=data[offset+BAG_IX_VALUE_COUNT];
		offset+=BAG_HEADER_LENGTH;
		for (;count!=0;--count,offset+=BAGVALUE_LENGTH) {
			if (id!=data[offset+BAGVALUE_IX_ID]) {
				continue;
			}
			int valueType=extractType(data[offset+BAGVALUE_IX_TYPE]);
			int valueData=data[offset+BAGVALUE_IX_DATA];
			if (valueType==TypedValue.TYPE_REFERENCE) {
				getValue(valueData,result,resultIndex);
				return true;
			}
			int assetCookie=(valueType==TypedValue.TYPE_STRING)?
				location.content.asset.pkge.id:
				0;
			int changingConfigurations=location.content.
				asset.flags[location.contentIndex];
			result.setValueAt(
				resultIndex,
				valueType,valueData,
				assetCookie,id,changingConfigurations);
			return true;
		}
		return getStyleValue(parent,id,result,resultIndex);
	}
	
	private final boolean locateValue(int id,ValueLocation location) {
		Asset asset=findAsset(m_resources,id);
		if (asset!=null) {
			//location.resources=m_resources;
		} else {
			asset=findAsset(m_systemResources,id);
			if (asset!=null) {
				//location.resources=m_systemResources;
			}
		}
		if (asset==null) {
			return false;
		}
		location.content=findContent(asset,id);
    	if (location.content==null) {
    		return false;
    	}
    	int index=extractResourceIndex(id);
    	location.contentOffset=location.content.offsets[index]/4;
		return true;
	}
    
    private final Asset findAsset(int id) {
    	Asset asset=findAsset(m_resources,id);
    	if (asset==null) {
    		asset=findAsset(m_systemResources,id);
    	}
    	return asset;
    }
    
    private final Pkge findPackage(int packageID) {
    	Pkge pkge=findPackage(m_resources,packageID);
    	if (pkge==null) {
    		pkge=findPackage(m_systemResources,packageID);
    	}
    	return pkge;
    }
    
    private static final Pkge findPackage(ARSCFile resources,int packageID) {
    	if (resources==null) {
    		return null;
    	}
    	for (int i=0;i!=resources.pkges.length;++i) {
    		Pkge pkge=resources.pkges[i];
    		if (pkge.id==packageID) {
    			return pkge;
    		}
    	}
    	return null;
    }
    
    private static final Asset findAsset(ARSCFile resources,int id) {
    	if (resources==null) {
    		return null;
    	}
    	int packageID=extractPackageID(id);
    	for (int i=0;i!=resources.pkges.length;++i) {
    		Pkge pkge=resources.pkges[i];
    		if (pkge.id==packageID) {
    			int assetID=extractAssetID(id);
    			Asset asset;
    			if (assetID<=pkge.assets.length) {
    				asset=pkge.assets[assetID-1];
    				if (asset.id==assetID) {
    					return asset;
    				}
    			}
    			for (int j=0;j!=pkge.assets.length;++i) {
    				asset=pkge.assets[j];
    				if (asset.id==assetID) {
    					return asset;
    				}
    			}
    			break;    			
    		}
    	}
    	return null;
    }
    
    private final Content findContent(Asset asset,int id) {
    	int index=extractResourceIndex(id);
    	if (index>=asset.flags.length) {
    		return null;
    	}
		for (int i=0;i!=asset.contents.length;++i) {
			Content content=asset.contents[i];
			int offset=content.offsets[index];
			if (offset==-1) {
				continue;
			}
			offset/=4;
			if (offset>=content.data.length) {
				return null;
			}
			return content;
		}
		return null;
    }
    
    private final Content findContent(int id) {
    	Asset asset=findAsset(id);
    	if (asset==null) {
    		return null;
    	}
    	return findContent(asset,id);
    }

	private static final boolean isBagValue(int type) {
		return (type & 0xFF)==0x10;
	}
	private static final boolean isValue(int type) {
		return (type & 0xFF)==0x8;
	}
    
	private static final int extractType(int header) {
		return header>>>24;
	}
	
    private static final int extractPackageID(int id) {
    	return (id>>>24);
    }
    private static final int extractAssetID(int id) {
    	return (id>>16) & 0xFF;
    }
    private static final int extractResourceIndex(int id) {
    	return (id & 0xFFFF);
    }
    
    private ARSCFile m_resources;
    private ARSCFile m_systemResources;
    
    private ValueLocation m_valueLocation=new ValueLocation();

    private static final int
		VALUE_IX_HEADER			=0,
		VALUE_IX_RESOURCE_NAME	=1,
		VALUE_IX_TYPE			=2,
		VALUE_IX_DATA			=3,
		
		BAG_IX_HEADER			=0,
		BAG_IX_RESOURCE_NAME	=1,
		BAG_IX_PARENT			=2,
		BAG_IX_VALUE_COUNT		=3,
		BAG_HEADER_LENGTH		=4,
		
		BAGVALUE_IX_ID			=0,
		BAGVALUE_IX_TYPE		=1,
		BAGVALUE_IX_DATA		=2,
		BAGVALUE_LENGTH			=3;
}
