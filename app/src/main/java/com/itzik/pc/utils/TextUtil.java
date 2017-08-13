package com.itzik.pc.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

/**
 * Created on 4/3/2017.
 */

public class TextUtil
{
    public static boolean isEmpty(String text)
    {
        return text == null || text.length() == 0;
    }

    /**
     * Capitalize the first letter of string.
     * @param theString to change.
     * @return new string with first letter capitalized
     */
    @NonNull
    static String capitalize(String theString)
    {
        if (theString == null || theString.isEmpty())
        {
            return "";
        }

        char first = theString.charAt(0);
        if (Character.isUpperCase(first))
        {
            return theString;
        }

        return Character.toUpperCase(first) + theString.substring(1);
    }

    public static String encode(String stringToEncode)
    {
        return Base64.encodeToString(stringToEncode.getBytes(), Base64.DEFAULT);
    }

    public static String decode(String stringToDecode)
    {
        return new String(Base64.decode(stringToDecode.getBytes(), Base64.DEFAULT));
    }
}
