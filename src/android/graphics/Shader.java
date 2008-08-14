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
package android.graphics;

import javolution.lang.Enum;

public class Shader {
	
	public static final class TileMode extends Enum {

		public static final TileMode 
			CLAMP	=new TileMode("CLAMP",0),
			REPEAT	=new TileMode("REPEAT",1),
			MIRROR	=new TileMode("MIRROR",2);
		
		public static final TileMode[] values() {
			return VALUES;
		}
		public static TileMode valueOf(String name) {
			return (TileMode)findValue(VALUES,name);
		}

		private TileMode(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final TileMode VALUES[]={
			CLAMP,REPEAT,MIRROR
		};
	}

	public Shader() {}

	public boolean getLocalMatrix(Matrix localM) { return false; }

	public void setLocalMatrix(Matrix localM) {}
}
