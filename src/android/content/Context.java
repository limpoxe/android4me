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
package android.content;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.Theme;
import android.os.Looper;
import android.util.AttributeSet;

/**
 * @author Dmitry Skiba
 *
 */
public abstract class Context {

	public abstract AssetManager getAssets();

	public abstract Resources getResources();
	
	public abstract Context getApplicationContext();

	public abstract Theme getTheme();

	public abstract void setTheme(int themeID);
	
	public abstract Looper getMainLooper();
	
	public abstract String getPackageName();

	public abstract Object getSystemService(String name);

	public abstract SharedPreferences getSharedPreferences(String name,int mode);
	
	/////////////////////////////////// helpers
	
	public final CharSequence getText(int id) {
		return getResources().getText(id);
	}

	public final String getString(int id) {
		return getResources().getString(id);
	}

	public final String getString(int id,Object formatArgs[]) {
		return getResources().getString(id,formatArgs);
	}

	public final TypedArray obtainStyledAttributes(int attrs[]) {
		return getTheme().obtainStyledAttributes(attrs);
	}

	public final TypedArray obtainStyledAttributes(int resid,int attrs[]) {
		return getTheme().obtainStyledAttributes(resid,attrs);
	}

	public final TypedArray obtainStyledAttributes(AttributeSet set,int attrs[]) {
		return getTheme().obtainStyledAttributes(set,attrs,0,0);
	}

	public final TypedArray obtainStyledAttributes(AttributeSet set,int attrs[],int defStyleAttr,int defStyleRes) {
		return getTheme().obtainStyledAttributes(set,attrs,defStyleAttr,defStyleRes);
	}
	
	/////////////////////////////////// constants

	public static final String LAYOUT_INFLATER_SERVICE="layout_inflater";
}
