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
 *  * check that asset ids starts from 1 and ends at assetNames.getCount()
 *  	(or should this check be in AssetManager?)
 *  * read multiple packages
 *  * remove Configuration and store raw values
 *  * optimize reading (read byte chunk instead of per-byte)
 *
 */
public class ARSCFile {
	
	/**
	 */
	public static class Pkge {
		public ARSCFile file;
		public int id;
		public String name;
		public StringBlock resourceNames;
		public StringBlock assetNames;
		public Asset[] assets;
	}
	
	/**
	 * 'id'-1 gives asset name index.
	 * 'flags' values are essentially changingConfiguration values.
	 * 'flags.length' is total resource count in asset.
	 */
	public static class Asset {
		public Pkge pkge;
		public int id;
		public int[] flags;
		public Content[] contents;
	}
	
	/**
	 */
	public static class Content {
		public Asset asset;
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
	
	public StringBlock strings;
	public Pkge[] pkges;
	
	/////////////////////////////////// creator
	
	public static ARSCFile read(IntReader reader) throws IOException {
		ARSCFile arsc=new ARSCFile();
		ReadUtil.readCheckType(reader,ARSC_CHUNK_TYPE);
		/*size*/reader.readInt();
		int groupCount=reader.readInt();
		
		if (groupCount!=1) {
			throw new IOException("Only one package per file is supported.");
		}
		
		arsc.strings=StringBlock.read(reader);
		arsc.pkges=new Pkge[1];
		arsc.pkges[0]=readPackage(arsc,reader);

		return arsc;
	}
	
	///////////////////////////////////////////// implementation
	
	private ARSCFile() {
	}
	
	private static Pkge readPackage(ARSCFile file,IntReader reader) throws IOException {
		Pkge pkge=new Pkge();
		pkge.file=file;
		
		ReadUtil.readCheckType(reader,PACKAGE_CHUNK_TYPE);
		/*size*/reader.skipInt();
		pkge.id=reader.readInt();
		{
			final int nameLength=128;
			StringBuilder name=new StringBuilder(16);
			int i=0;
			for (;i!=nameLength;) {
				++i;
				int ch=reader.readShort();
				if (ch==0) {
					break;
				}
				name.append((char)ch);
			}
			reader.skip((nameLength-i)*2);
			reader.skip((nameLength*2)%4);
			pkge.name=name.toString();
		}
		
		/*signature?*/reader.skipInt();
		int assetCount=reader.readInt();
		/*idNamesOffset*/reader.skipInt();
		/*idNamesCount*/reader.skipInt();
		
		pkge.assetNames=StringBlock.read(reader);
		pkge.resourceNames=StringBlock.read(reader);
		pkge.assets=new Asset[assetCount];

		ArrayList contents=new ArrayList();
		int assetsRead=0;
		Asset currentAsset=null;
		while (reader.available()!=0) {
			int chunkType=reader.readInt();
			if (chunkType!=CONTENT_CHUNK_TYPE) {
				if (currentAsset!=null) {
					currentAsset.contents=new Content[contents.size()];
					contents.toArray(currentAsset.contents);
					contents.clear();
				}
			}
			if (chunkType==ASSET_CHUNK_TYPE) {
				currentAsset=readAsset(reader,pkge);
				pkge.assets[assetsRead]=currentAsset;
				assetsRead+=1;
			} else if (chunkType==CONTENT_CHUNK_TYPE) {
				if (currentAsset==null) {
					throw new IOException("Invalid chunk sequence: content read before asset.");
				}
				contents.add(readContent(reader,currentAsset));
			} else {
				throw new IOException("Unexpected chunk type ("+chunkType+").");
			}
		}
		if (currentAsset!=null) {
			currentAsset.contents=new Content[contents.size()];
			contents.toArray(currentAsset.contents);
			contents.clear();
		}		
		if (assetsRead!=assetCount) {
			throw new IOException("Not all assets where read ("+(assetCount-assetsRead)+" left).");
		}
		return pkge;
	}
	
	private static Asset readAsset(IntReader reader,Pkge group) throws IOException {
		/*chunkSize*/reader.skipInt();
		Asset asset=new Asset();
		asset.pkge=group;
		asset.id=reader.readInt();
		int count=reader.readInt();
		asset.flags=reader.readIntArray(count);
		return asset;		
	}
	
	private static Content readContent(IntReader reader,Asset asset) throws IOException {
		int chunkSize=reader.readInt();
		Content content=new Content();
		int assetID=reader.readInt();
		if (assetID!=asset.id) {
			throw new IOException("Content id ("+assetID+") "+"doesn't match asset id ("+asset.id+").");
		}
		content.asset=asset;
		int offsetCount=reader.readInt();
		int dataOffset=reader.readInt();
		content.configuration=readConfiguration(reader);
		content.offsets=reader.readIntArray(offsetCount);
		int dataSize=(chunkSize-dataOffset);
		if ((dataSize%4)!=0) {
			throw new IOException("Content data size ("+dataSize+") is not multiple of 4.");
		}
		content.data=reader.readIntArray(dataSize/4);
		return content;
	}
	
	private static Configuration readConfiguration(IntReader reader) throws IOException {
		int size=reader.readInt();
		if (size!=0x1C) {
			throw new IOException("Bad content configuration size ("+size+").");
		}
		int[] elements=reader.readIntArray(size/4-1);
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
