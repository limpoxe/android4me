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
	 * 'id'-1 gives folder name index.
	 * 'flags' values meaning is unknown.
	 * 'flags.length' is total resource count in folder.
	 * 'flags' value of -1 means resource is not available 
	 * 		in current configuration.
	 */
	public static class Folder {
		public int id;
		public int[] flags; // changingConfigurations
		public Content[] contents;
	}
	
	/**
	 */
	public static class Content {
		public int folderID;
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
	
	public String packageName;
	public StringBlock stringValues;
	public StringBlock stringIDs;
	public StringBlock folderNames;
	public Folder[] folders;
	
	/////////////////////////////////// creator
	
	@SuppressWarnings("unchecked")
	public static ARSCFile read(InputStream stream) throws IOException {
		ARSCFile arsc=new ARSCFile();
		ReadUtil.readCheckType(stream,ARSC_CHUNK_TYPE);
		/*size*/ReadUtil.readInt(stream);
		/*?*/ReadUtil.readInt(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.stringValues=new StringBlock();
		arsc.stringValues.read(stream);
		
		ReadUtil.readCheckType(stream,PACKAGE_CHUNK_TYPE);
		/*size*/ReadUtil.readInt(stream);
		/*some offset?*/ReadUtil.readInt(stream);
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
		/*folderNameCount*/ReadUtil.readInt(stream);
		/*stringIDOffset*/ReadUtil.readInt(stream);
		/*stringIDCount*/ReadUtil.readInt(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.folderNames=new StringBlock();
		arsc.folderNames.read(stream);
		
		ReadUtil.readCheckType(stream,StringBlock.CHUNK_TYPE);
		arsc.stringIDs=new StringBlock();
		arsc.stringIDs.read(stream);
		
		ArrayList folders=new ArrayList();
		ArrayList contents=new ArrayList();
		while (stream.available()!=0) {
			int chunkType=ReadUtil.readInt(stream);
			int chunkSize=ReadUtil.readInt(stream);
			if (chunkType==FOLDER_CHUNK_TYPE) {
				folders.add(readFolder(stream));
			} else if (chunkType==CONTENT_CHUNK_TYPE) {
				contents.add(readContent(stream,chunkSize));
			} else {
				throw new IOException("ARSCFile: unexpected chunk type ("+chunkType+").");
			}
		}
		
		arsc.folders=new Folder[folders.size()];
		int contentLeft=contents.size();
		for (int i=0;i!=folders.size();++i) {
			Folder folder=(Folder)folders.get(i);
			int contentCount=0;
			for (int j=0;j!=contents.size();++j) {
				Content content=(Content)contents.get(j);
				if (content.folderID==folder.id) {
					contentCount+=1;
				}
			}
			folder.contents=new Content[contentCount];
			for (int j=0,k=0;j!=contents.size();++j) {
				Content content=(Content)contents.get(j);
				if (content.folderID==folder.id) {
					folder.contents[k++]=content;
					contentLeft-=1;
				}
			}
			arsc.folders[i]=folder;
		}
		if (contentLeft!=0) {
			throw new IOException("ARSCFile: problem in mapping contents to folders ("+contentLeft+" left).");
		}
		
		return arsc;
	}
	
	///////////////////////////////////////////// implementation
	
	private ARSCFile() {
	}
	
	private static Folder readFolder(InputStream stream) throws IOException {
		Folder folder=new Folder();
		folder.id=ReadUtil.readInt(stream);
		int flagCount=ReadUtil.readInt(stream);
		folder.flags=ReadUtil.readIntArray(stream,flagCount);
		return folder;		
	}
	
	private static Content readContent(InputStream stream,int chunkSize) throws IOException {
		Content content=new Content();
		content.folderID=ReadUtil.readInt(stream);
		int offsetCount=ReadUtil.readInt(stream);
		int dataOffset=ReadUtil.readInt(stream);
		content.configuration=readConfiguration(stream);
		content.offsets=ReadUtil.readIntArray(stream,offsetCount);
		int dataSize=(chunkSize-dataOffset);
		if ((dataSize%4)!=0) {
			throw new IOException("ARSCFile: content data size ("+dataSize+") is not multiple of 4.");
		}
		content.data=ReadUtil.readIntArray(stream,dataSize/4);
		return content;
	}
	
	private static Configuration readConfiguration(InputStream stream) throws IOException {
		int size=ReadUtil.readInt(stream);
		if (size!=0x1C) {
			throw new IOException("ARSCFile: bad content configuration size ("+size+").");
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
		FOLDER_CHUNK_TYPE	=0x00100202,
		CONTENT_CHUNK_TYPE	=0x00300201;
	
}
