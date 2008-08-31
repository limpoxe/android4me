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
import java.util.ArrayList;

/**
 * @author Dmitry Skiba
 * 
 * This class represents content of arsc file.
 * 
 * Content presented without any modification except for Configuration,
 * where I decided to do parsing, because its original values are packed
 * in ints.
 * 
 * TODO:
 * 	* content -> asset ?
 *
 */
public class ARSCFile {
	
	/**
	 * 'id'-1 gives asset name index.
	 * 'flags' values are essentially changingConfiguration values.
	 * 'flags.length' is total resource count in asset.
	 */
	public static class Asset {
		public int id;
		public int[] flags;
		public Content[] contents;
	}
	
	/**
	 */
	public static class Content {
		public int assetID;
		public Configuration configuration;
		public int[] offsets;
		public int[] data;
	}

	/**
	 * int values are interpreted according to 
	 *  android.content.res.Configuration.
	 */
	public static class Configuration {
		public String language;
		public String country;
		public int orientation;
		public int touchscreen;
		public int keyboard;
		public int keyboardHidden;
		public int navigation;
		public int screenWidth;
		public int screenHeight;
	}
	
	/////////////////////////////////// data
	
	public int packageID;
	public String packageName;
	public StringBlock stringValues;
	public StringBlock stringIDs;
	public StringBlock assetNames;
	public Asset[] assets;
	
	/////////////////////////////////// creator
	
	public static ARSCFile read(InputStream stream) throws IOException {
		ARSCFile arsc=new ARSCFile();
		ReadUtil.readCheckType(stream,ARSC_CHUNK_TYPE);
		/*size*/ReadUtil.readInt(stream);
		/*package count?*/ReadUtil.readInt(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.stringValues=new StringBlock();
		arsc.stringValues.read(stream);
		
		ReadUtil.readCheckType(stream,PACKAGE_CHUNK_TYPE);
		/*size*/ReadUtil.readInt(stream);
		arsc.packageID=ReadUtil.readInt(stream);
		{
			final int nameLength=128;
			StringBuilder name=new StringBuilder(16);
			int i=0;
			for (;i!=nameLength;) {
				++i;
				int ch=ReadUtil.readShort(stream);
				if (ch==0) {
					break;
				}
				name.append((char)ch);
			}
			stream.skip((nameLength-i)*2);
			stream.skip((nameLength*2)%4);
			arsc.packageName=name.toString();
		}
		
		/*signature?*/ReadUtil.readInt(stream);
		/*assetNameCount*/ReadUtil.readInt(stream);
		/*stringIDOffset*/ReadUtil.readInt(stream);
		/*stringIDCount*/ReadUtil.readInt(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.assetNames=new StringBlock();
		arsc.assetNames.read(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.stringIDs=new StringBlock();
		arsc.stringIDs.read(stream);
		
		ArrayList assets=new ArrayList();
		ArrayList contents=new ArrayList();
		while (stream.available()!=0) {
			int chunkType=ReadUtil.readInt(stream);
			int chunkSize=ReadUtil.readInt(stream);
			if (chunkType==ASSET_CHUNK_TYPE) {
				assets.add(readasset(stream));
			} else if (chunkType==CONTENT_CHUNK_TYPE) {
				contents.add(readContent(stream,chunkSize));
			} else {
				throw new IOException("Unexpected chunk type ("+chunkType+").");
			}
		}
		
		arsc.assets=new Asset[assets.size()];
		int contentLeft=contents.size();
		for (int i=0;i!=assets.size();++i) {
			Asset asset=(Asset)assets.get(i);
			int contentCount=0;
			for (int j=0;j!=contents.size();++j) {
				Content content=(Content)contents.get(j);
				if (content.assetID==asset.id) {
					contentCount+=1;
				}
			}
			asset.contents=new Content[contentCount];
			for (int j=0,k=0;j!=contents.size();++j) {
				Content content=(Content)contents.get(j);
				if (content.assetID==asset.id) {
					asset.contents[k++]=content;
					contentLeft-=1;
				}
			}
			arsc.assets[i]=asset;
		}
		if (contentLeft!=0) {
			throw new IOException("Problem in mapping contents to assets ("+contentLeft+" left).");
		}
		
		return arsc;
	}
	
	///////////////////////////////////////////// implementation
	
	private ARSCFile() {
	}
	
	private static Asset readasset(InputStream stream) throws IOException {
		Asset asset=new Asset();
		asset.id=ReadUtil.readInt(stream);
		int count=ReadUtil.readInt(stream);
		asset.flags=ReadUtil.readIntArray(stream,count);
		return asset;		
	}
	
	private static Content readContent(InputStream stream,int chunkSize) throws IOException {
		Content content=new Content();
		content.assetID=ReadUtil.readInt(stream);
		int offsetCount=ReadUtil.readInt(stream);
		int dataOffset=ReadUtil.readInt(stream);
		content.configuration=readConfiguration(stream);
		content.offsets=ReadUtil.readIntArray(stream,offsetCount);
		int dataSize=(chunkSize-dataOffset);
		if ((dataSize%4)!=0) {
			throw new IOException("Content data size ("+dataSize+") is not multiple of 4.");
		}
		content.data=ReadUtil.readIntArray(stream,dataSize/4);
		return content;
	}
	
	private static Configuration readConfiguration(InputStream stream) throws IOException {
		int size=ReadUtil.readInt(stream);
		if (size!=0x1C) {
			throw new IOException("Bad content configuration size ("+size+").");
		}
		int[] elements=ReadUtil.readIntArray(stream,size/4-1);
		Configuration configuration=new Configuration();
		for (int i=0;i!=elements.length;++i) {
			int element=elements[i];
			switch (i) {
				case 1:
				{
					if (element==0) {
						break;
					}
					StringBuilder s2=new StringBuilder(2);
					if ((element & 0xFFFF)!=0) {
						s2.append((char)((element    ) & 0xFF));
						s2.append((char)((element>>8 ) & 0xFF));
						configuration.language=s2.toString();
					}
					if ((element>>>16)!=0) {
						s2.setLength(0);
						s2.append((char)((element>>16) & 0xFF));
						s2.append((char)((element>>24) & 0xFF));
						configuration.country=s2.toString();
					}
					break;
				}
				case 2:
				{
					configuration.orientation=(element & 0xFF);
					configuration.touchscreen=((element>>8) & 0xFF);
					break;
				}
				case 3:
				{
					configuration.keyboard=(element & 0xFF);
					configuration.navigation=((element>>8) & 0xFF);
					configuration.keyboardHidden=((element>>16) & 0xFF);
					break;
				}
				case 4:
				{
					configuration.screenWidth=(element & 0xFFFF);
					configuration.screenHeight=(element>>>16);
					break;
				}
			}
		}
		return configuration;		
	}
	
	private static final int 
		ARSC_CHUNK_TYPE		=0x000C0002,
		PACKAGE_CHUNK_TYPE	=0x011C0200,
		ASSET_CHUNK_TYPE	=0x00100202,
		CONTENT_CHUNK_TYPE	=0x00300201;
	
}
