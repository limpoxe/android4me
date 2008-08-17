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

public class PorterDuff extends Xfermode {

	public static final class Mode extends Enum {
		public static final Mode 
			CLEAR		=new Mode("CLEAR",0),
			SRC			=new Mode("SRC",1),
			DST			=new Mode("DST",2),
			SRC_OVER	=new Mode("SRC_OVER",3),
			DST_OVER	=new Mode("DST_OVER",4),
			SRC_IN		=new Mode("SRC_IN",5),
			DST_IN		=new Mode("DST_IN",6),
			SRC_OUT		=new Mode("SRC_OUT",7),
			DST_OUT		=new Mode("DST_OUT",8),
			SRC_ATOP	=new Mode("SRC_ATOP",9),
			DST_ATOP	=new Mode("DST_ATOP",10),
			XOR			=new Mode("XOR",11),
			DARKEN		=new Mode("DARKEN",12),
			LIGHTEN		=new Mode("LIGHTEN",13);

		public static final Mode[] values() {
			return VALUES;
		}
		public static Mode valueOf(String name) {
			return (Mode)findValue(VALUES,name);
		}

		private Mode(String name,int ordinal) {
			super(name,ordinal);
		}
		private static final Mode VALUES[]={
			CLEAR,SRC,DST,SRC_OVER,DST_OVER,
			SRC_IN,DST_IN,SRC_OUT,DST_OUT,
			SRC_ATOP,DST_ATOP,XOR,DARKEN,LIGHTEN
		};
	}
}
