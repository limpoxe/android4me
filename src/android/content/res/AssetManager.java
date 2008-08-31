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

import java.io.FileInputStream;
import java.io.IOException;
import android.util.TypedValue;
import android4me.res.ARSCFile;

public class AssetManager {

    public static final int
    	ACCESS_UNKNOWN		=0,
    	ACCESS_RANDOM		=1,
    	ACCESS_STREAMING	=2,
    	ACCESS_BUFFER		=3;
	
    public void openFile(String arscFile) throws IOException {
    	m_file=ARSCFile.read(new FileInputStream(arscFile));
    }
    
    public TypedValue getValue(int id) {
    	if (!checkID(id)) {
    		return null;
    	}
    	int assetID=extractAssetID(id);
    	if (assetID>m_file.assets.length) {
    		return null;
    	}
    	ARSCFile.Asset asset=m_file.assets[assetID-1];
    	int index=extractResourceIndex(id);
    	if (index>=asset.flags.length) {
    		return null;
    	}
    	ARSCFile.Content content;
    	{
    		int i=0;
    		for (;i!=asset.contents.length;++i) {
    			if (asset.contents[i].offsets[index]!=-1) {
    				break;
    			}
    		}
	    	if (i==asset.contents.length) {
	    		return null;
	    	}
	    	content=asset.contents[i];
    	}
    	TypedValue value=new TypedValue();
    	value.resourceId=id;
    	value.changingConfigurations=asset.flags[index];
    	{
    		int offset=content.offsets[index]/4;
    		if ((content.data[offset] & 0xFF)!=8) {
    			// bag resource
    			return null;
    		}
    		//content.data[offset+1] is index of resource id in stringIDs 
    		value.type=(content.data[offset+2]>>>24);
    		value.data=content.data[offset+3];
    		if (value.type==TypedValue.TYPE_STRING) {
    			value.string=m_file.stringValues.get(value.data);
    		}
    	}
    	return value;
    }
    
    
    ///////////////////////////////////////////// implementation
    
    private boolean checkID(int id) {
    	if (m_file==null) {
    		return false;
    	}
    	if (extractPackageID(id)!=m_file.packageID) {
    		return false;
    	}
    	return true;
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
    
    private ARSCFile m_file;
}
