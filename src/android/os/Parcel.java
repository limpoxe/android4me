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
package android.os;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public final class Parcel {
	
	public static final Parcel obtain() { return null; }

	public final void recycle() {}

	public final int dataSize() { return 0; }

	public final int dataAvail() { return 0; }

	public final int dataPosition() { return 0; }

	public final int dataCapacity() { return 0; }

	public final void setDataSize(int i) {}

	public final void setDataPosition(int i) {}

	public final void setDataCapacity(int i) {}

	public final byte[] marshall() { return null; }

	public final void unmarshall(byte data[], int i, int j) {}

	public final void appendFrom(Parcel parcel, int i, int j) {}

	public final void writeByteArray(byte b[]) {}

	public final void writeByteArray(byte b[], int offset, int len) {}

	public final void writeInt(int i) {}

	public final void writeLong(long l) {}

	public final void writeFloat(float f) {}

	public final void writeDouble(double d) {}

	public final void writeString(String s) {}

//	public final void writeStrongBinder(IBinder ibinder) {}

//	public final void writeStrongInterface(IInterface val) { return null; }

//	public final void writeFileDescriptor(FileDescriptor filedescriptor);

	public final void writeByte(byte val) {}

	public final void writeMap(Map val) {}

//	public final void writeBundle(Bundle val) {}

	public final void writeList(List val) {}

	public final void writeArray(Object val[]) {}

//	public final void writeSparseArray(SparseArray val) {}

	public final void writeBooleanArray(boolean val[]) {}

	public final boolean[] createBooleanArray() { return null; }

	public final void readBooleanArray(boolean val[]) {}

	public final void writeCharArray(char val[]) {}

	public final char[] createCharArray() { return null; }

	public final void readCharArray(char val[]) {}

	public final void writeIntArray(int val[]) {}

	public final int[] createIntArray() { return null; }

	public final void readIntArray(int val[]) {}

	public final void writeLongArray(long val[]) {}

	public final long[] createLongArray() { return null; }

	public final void readLongArray(long val[]) {}

	public final void writeFloatArray(float val[]) {}

	public final float[] createFloatArray() { return null; }

	public final void readFloatArray(float val[]) {}

	public final void writeDoubleArray(double val[]) {}

	public final double[] createDoubleArray() { return null; }

	public final void readDoubleArray(double val[]) {}

	public final void writeStringArray(String val[]) {}

	public final String[] createStringArray() { return null; }

	public final void readStringArray(String val[]) {}

	public final void writeTypedList(List val) {}

	public final void writeStringList(List val) {}

	public final void writeTypedArray(Parcelable val[]) {}

	public final void writeValue(Object v) {}

	public final void writeParcelable(Parcelable p) {}

	public final void writeSerializable(Serializable s) {}

	public final void writeException(Exception e) {}

	public final void writeNoException() {}

	public final void readException() {}

	public final int readInt() { return 0; }

	public final long readLong() { return 0; }

	public final float readFloat() { return 0; }

	public final double readDouble() { return 0; }

	public final String readString() { return null; }

//	public final IBinder readStrongBinder();

//	public final ParcelFileDescriptor readFileDescriptor() { return null; }

	public final byte readByte() { return 0; }

//	public final void readMap(Map outVal, ClassLoader loader) { return null; }
//
//	public final void readList(List outVal, ClassLoader loader) { return null; }

//	public final HashMap readHashMap(ClassLoader loader) { return null; }
//
//	public final Bundle readBundle() { return null; }

	public final byte[] createByteArray() { return null; }

	public final void readByteArray(byte val[]) {}

	public final String[] readStringArray() { return null; }

//	public final ArrayList readArrayList(ClassLoader loader) { return null; }
//
//	public final Object[] readArray(ClassLoader loader) { return null; }
//
//	public final SparseArray readSparseArray(ClassLoader loader) { return null; }

//	public final ArrayList createTypedArrayList(Parcelable.Creator c) { return null; }

	public final void readTypedList(List list, Parcelable.Creator c) {}

//	public final ArrayList createStringArrayList() { return null; }

	public final void readStringList(List list) {}

	public final Object[] createTypedArray(Parcelable.Creator c) { return null; }

//	public final void readTypedArray(Object val[], Parcelable.Creator c) { return null; }

	public final Object[] readTypedArray(Parcelable.Creator c) { return null; }

//	public final void writeParcelableArray(Parcelable value[]) { return null; }

//	public final Object readValue(ClassLoader loader) { return null; }
//
//	public final Object readParcelable(ClassLoader loader) { return null; }
//
//	public final Parcelable[] readParcelableArray(ClassLoader loader) { return null; }

	public final Serializable readSerializable() { return null; }
}
