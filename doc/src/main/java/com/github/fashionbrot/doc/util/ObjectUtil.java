package com.github.fashionbrot.doc.util;



import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author fashi
 */
public class ObjectUtil {

    public static final String EMPTY="";

    /**
     * trim
     * @param str str
     * @return String
     */
    public static String trim(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * 判断字符是否为空
     * @param cs cs
     * @return boolean
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符是否不为空
     * @param cs cs
     * @return boolean
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }


    /**
     * 判断集合是否为空
     * @param collection collection
     * @return boolean
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 判断集合是否不为空
     * @param collection collection
     * @return boolean
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }


    /**
     * 判断Map是否为空
     * @param map map
     * @return boolean
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 判断Map是否不为空
     * @param map map
     * @return boolean
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断objects是否为空
     * @param objects objects
     * @return boolean
     */
    public static boolean isEmpty(final Object[] objects) {
        return objects == null || objects.length==0;
    }

    /**
     * 判断objects是否不为空
     * @param objects objects
     * @return boolean
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return objects != null && objects.length > 0;
    }



    /**
     * 判断 bytes 是否不为空
     * @param bytes bytes
     * @return boolean
     */
    public static boolean isNotEmpty(final byte[] bytes) {
        return !isEmpty(bytes);
    }

    /**
     * 判断 bytes 是否为空
     * @param bytes bytes
     * @return boolean
     */
    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length==0;
    }




    /**
     * 判断 字符串是否是数字
     * @param cs cs
     * @return boolean
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * 格式化Object 为 String
     * @param object object
     * @return String
     */
    public static String formatString(final Object object){
        if (object==null){
            return EMPTY;
        }else{
            return object.toString();
        }
    }

    /**
     * 格式化String 为 Integer
     * @param str str
     * @return Integer
     */
    public static Integer parseInteger(final String str){
        return parseInteger(str,0);
    }

    /**
     * 格式化String 为 Integer
     * @param str str
     * @param defaultValue defaultValue
     * @return Integer
     */
    public static Integer parseInteger(final String str ,final Integer defaultValue){
        if (ObjectUtil.isEmpty(str)){
            return defaultValue;
        }
        try {
            return Integer.valueOf(str);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 格式String 为 Long
     * @param str str
     * @return Long
     */
    public static Long parseLong(final String str){
        return parseLong(str,0L);
    }

    /**
     * 格式String 为 Long
     * @param str str
     * @param defaultValue defaultValue
     * @return Long
     */
    public static Long parseLong(final String str ,final Long defaultValue){
        if (ObjectUtil.isEmpty(str)){
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 格式化 String 为 boolean
     * @param str str
     * @return boolean
     */
    public static boolean parseBoolean(final String str){
        return parseBoolean(str,false);
    }

    /**
     * 格式化 String 为 boolean
     * @param str str
     * @param defaultValue defaultValue
     * @return boolean
     */
    public static boolean parseBoolean(final String str,boolean defaultValue){
        if (ObjectUtil.isEmpty(str)){
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(str);
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 格式化 Object 为 Integer
     * @param object object
     * @return Integer
     */
    public static Integer formatInteger(final Object object){
        if (object==null){
            return 0;
        }else{
            String str=object.toString();
            if (!isNumeric(str)){
                return 0;
            }else{
                try {
                    return Integer.valueOf(str);
                }catch (Exception e){
                    return 0;
                }
            }
        }
    }

    /**
     * 格式化 Object 为 Long
     * @param object object
     * @return Long
     */
    public static Long formatLong(final Object object){
        if (object==null){
            return 0L;
        }else{
            String str=object.toString();
            if (!isNumeric(str)){
                return 0L;
            }else{
                try {
                    return Long.valueOf(str);
                }catch (Exception e){
                    return 0L;
                }
            }
        }
    }

    /**
     * 格式化 Object 为 Double
     * @param object object
     * @return Double
     */
    public static Double formatDouble(final Object object){
        if (object==null){
            return 0.00;
        }else{
            try {
                return Double.valueOf(object.toString());
            }catch (Exception e){
                return 0.00;
            }
        }
    }

    /**
     * 格式化 Object 为 Float
     * @param object object
     * @return Float
     */
    public static Float formatFloat(final Object object){
        if (object==null){
            return 0F;
        }else{
            try {
                return Float.valueOf(object.toString());
            }catch (Exception e){
                return 0F;
            }
        }
    }

    /**
     * 格式化 Object 为 Short
     * @param object object
     * @return Short
     */
    public static Short formatShort(final Object object){
        if (object==null){
            return 0;
        }else{
            try {
                return Short.valueOf(object.toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    /**
     * 格式化Object 为 boolean
     * @param object object
     * @return boolean
     */
    public static boolean formatBoolean(final Object object){
        if (object==null){
            return false;
        }else{
            String str=object.toString();
            try {
                return Boolean.valueOf(str);
            }catch (Exception e){
                return false;
            }
        }
    }


    /**
     * <p>Checks if a String {@code str} contains Unicode digits,
     * if yes then concatenate all the digits in {@code str} and return it as a String.</p>
     *
     * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
     *
     * <pre>
     * StringUtils.getDigits(null)  = null
     * StringUtils.getDigits("")    = ""
     * StringUtils.getDigits("abc") = ""
     * StringUtils.getDigits("1000$") = "1000"
     * StringUtils.getDigits("1123~45") = "112345"
     * StringUtils.getDigits("(541) 754-3010") = "5417543010"
     * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     *           or an empty ("") String if no digits found,
     *           or {@code null} String if {@code str} is null
     * @since 3.6
     */
    public static String getDigits(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int sz = str.length();
        final StringBuilder strDigits = new StringBuilder(sz);
        for (int i = 0; i < sz; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar);
            }
        }
        return strDigits.toString();
    }


    /**
     * <p>Truncates a String. This will turn
     * "Now is the time for all good men" into "Now is the time for".</p>
     *
     * <p>Specifically:</p>
     * <ul>
     *   <li>If {@code str} is less than {@code maxWidth} characters
     *       long, return it.</li>
     *   <li>Else truncate it to {@code substring(str, 0, maxWidth)}.</li>
     *   <li>If {@code maxWidth} is less than {@code 0}, throw an
     *       {@code IllegalArgumentException}.</li>
     *   <li>In no case will it return a String of length greater than
     *       {@code maxWidth}.</li>
     * </ul>
     *
     * <pre>
     * StringUtils.truncate(null, 0)       = null
     * StringUtils.truncate(null, 2)       = null
     * StringUtils.truncate("", 4)         = ""
     * StringUtils.truncate("abcdefg", 4)  = "abcd"
     * StringUtils.truncate("abcdefg", 6)  = "abcdef"
     * StringUtils.truncate("abcdefg", 7)  = "abcdefg"
     * StringUtils.truncate("abcdefg", 8)  = "abcdefg"
     * StringUtils.truncate("abcdefg", -1) = throws an IllegalArgumentException
     * </pre>
     *
     * @param str  the String to truncate, may be null
     * @param maxWidth  maximum length of result String, must be positive
     * @return truncated String, {@code null} if null String input
     * @since 3.5
     */
    public static String truncate(final String str, final int maxWidth) {
        return truncate(str, 0, maxWidth);
    }

    /**
     * <p>Truncates a String. This will turn
     * "Now is the time for all good men" into "is the time for all".</p>
     *
     * <p>Works like {@code truncate(String, int)}, but allows you to specify
     * a "left edge" offset.
     *
     * <p>Specifically:</p>
     * <ul>
     *   <li>If {@code str} is less than {@code maxWidth} characters
     *       long, return it.</li>
     *   <li>Else truncate it to {@code substring(str, offset, maxWidth)}.</li>
     *   <li>If {@code maxWidth} is less than {@code 0}, throw an
     *       {@code IllegalArgumentException}.</li>
     *   <li>If {@code offset} is less than {@code 0}, throw an
     *       {@code IllegalArgumentException}.</li>
     *   <li>In no case will it return a String of length greater than
     *       {@code maxWidth}.</li>
     * </ul>
     *
     * <pre>
     * StringUtils.truncate(null, 0, 0) = null
     * StringUtils.truncate(null, 2, 4) = null
     * StringUtils.truncate("", 0, 10) = ""
     * StringUtils.truncate("", 2, 10) = ""
     * StringUtils.truncate("abcdefghij", 0, 3) = "abc"
     * StringUtils.truncate("abcdefghij", 5, 6) = "fghij"
     * StringUtils.truncate("raspberry peach", 10, 15) = "peach"
     * StringUtils.truncate("abcdefghijklmno", 0, 10) = "abcdefghij"
     * StringUtils.truncate("abcdefghijklmno", -1, 10) = throws an IllegalArgumentException
     * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10) = "abcdefghij"
     * StringUtils.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 1, 10) = "bcdefghijk"
     * StringUtils.truncate("abcdefghijklmno", 2, 10) = "cdefghijkl"
     * StringUtils.truncate("abcdefghijklmno", 3, 10) = "defghijklm"
     * StringUtils.truncate("abcdefghijklmno", 4, 10) = "efghijklmn"
     * StringUtils.truncate("abcdefghijklmno", 5, 10) = "fghijklmno"
     * StringUtils.truncate("abcdefghijklmno", 5, 5) = "fghij"
     * StringUtils.truncate("abcdefghijklmno", 5, 3) = "fgh"
     * StringUtils.truncate("abcdefghijklmno", 10, 3) = "klm"
     * StringUtils.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE) = "klmno"
     * StringUtils.truncate("abcdefghijklmno", 13, 1) = "n"
     * StringUtils.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE) = "no"
     * StringUtils.truncate("abcdefghijklmno", 14, 1) = "o"
     * StringUtils.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE) = "o"
     * StringUtils.truncate("abcdefghijklmno", 15, 1) = ""
     * StringUtils.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE) = ""
     * StringUtils.truncate("abcdefghij", 3, -1) = throws an IllegalArgumentException
     * StringUtils.truncate("abcdefghij", -2, 4) = throws an IllegalArgumentException
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param offset  left edge of source String
     * @param maxWidth  maximum length of result String, must be positive
     * @return truncated String, {@code null} if null String input
     * @since 3.5
     */
    public static String truncate(final String str, final int offset, final int maxWidth) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (maxWidth < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (str == null) {
            return null;
        }
        if (offset > str.length()) {
            return EMPTY;
        }
        if (str.length() > maxWidth) {
            final int ix = offset + maxWidth > str.length() ? str.length() : offset + maxWidth;
            return str.substring(offset, ix);
        }
        return str.substring(offset);
    }


    public static byte[] getBytesUtf8(final String string) {
        return getBytes(string, Charset.forName("UTF-8"));
    }

    /**
     * Calls {@link String#getBytes(Charset)}
     *
     * @param string
     *            The string to encode (if null, return null).
     * @param charset
     *            The {@link Charset} to encode the <code>String</code>
     * @return the encoded bytes
     */
    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    public static String newStringUsAscii(final byte[] bytes) {
        return newString(bytes, Charset.forName("UTF-8"));
    }


    private static String newString(final byte[] bytes, final Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }


    public static boolean nullSafeEquals( Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            return arrayEquals(o1, o2);
        }
        return false;
    }

    /**
     * Compare the given arrays with {@code Arrays.equals}, performing an equality
     * check based on the array elements rather than the array reference.
     * @param o1 first array to compare
     * @param o2 second array to compare
     * @return whether the given objects are equal
     * @see #nullSafeEquals(Object, Object)
     * @see Arrays#equals
     */
    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }

//    public static void main(String[] args) {
//        Charset us_ascii = Charset.forName("UTF-8");
//        System.out.println(us_ascii.displayName());
//    }

}
