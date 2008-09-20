/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2006 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package java.lang;

import java.lang.CharSequence;

import java.io.IOException;

/**
 * <p> This class is equivalent to <code>java.lang.Appendable</code> 
 *     and is moved (refactored) to the <code>java.lang</code> system
 *     package for applications targetting the J2SE 5.0+ run-time.</p>
 *     
 * <p> This class has two additional methods, 
 *     as in J2ME String *is not a* CharSequence.</p>
 * 
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 2.1, September 1, 2008
 */
public interface Appendable {

    /**
     * Appends the specified character. 
     *
     * @param  c the character to append.
     * @return <code>this</code>
     */
    Appendable append(char c) throws IOException;

    /**
     * Appends the specified string.
     *
     * @param  string the string to append.
     * @return <code>this</code>
     */
    Appendable append(String string) throws IOException;
    
    /**
     * Appends a subsequence of the specified character sequence.
     *
     * @param  string the string to append.
     * @param  start the index of the first character to append.
     * @param  end the index after the last character to append.
     * @return <code>this</code>
     */
    Appendable append(String string, int start, int end) throws IOException;
    
    /**
     * Appends the specified character sequence. 
     *
     * @param  csq the character sequence to append.
     * @return <code>this</code>
     */
    Appendable append(CharSequence csq) throws IOException;

    /**
     * Appends a subsequence of the specified character sequence. 
     *
     * @param  csq the character sequence to append.
     * @param  start the index of the first character to append.
     * @param  end the index after the last character to append.
     * @return <code>this</code>
     */
    Appendable append(CharSequence csq, int start, int end) throws IOException;

}