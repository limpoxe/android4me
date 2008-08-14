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
package android.util;

public final class Log {

	private Log() {}

	public static int v(String tag, String msg) { return 0; }

	public static int v(String tag, String msg, Throwable tr) { return 0; }

	public static int d(String tag, String msg) { return 0; }

	public static int d(String tag, String msg, Throwable tr) { return 0; }

	public static int i(String tag, String msg) { return 0; }

	public static int i(String tag, String msg, Throwable tr) { return 0; }

	public static int w(String tag, String msg) { return 0; }

	public static int w(String tag, String msg, Throwable tr) { return 0; }

	public static boolean isLoggable(String s, int j) { return true; }

	public static int w(String tag, Throwable tr) { return 0; }

	public static int e(String tag, String msg) { return 0; }

	public static int e(String tag, String msg, Throwable tr) { return 0; }

	public static String getStackTraceString(Throwable tr) { return null; }

	public static int println(int j, String s, String s1) { return 0; }

	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;
	public static final int ASSERT = 7;
}
