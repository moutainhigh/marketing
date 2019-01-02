/*
StringTools: Miscellaneous static methods for dealing with Strings.

copyright (c) 1997-2006 Roedy Green, Canadian Mind Products
may be copied and used freely for any purpose but military.
Roedy Green
Canadian Mind Products
#101 - 2536 Wark Street
Victoria, BC Canada
V8T 4G8
tel: (250) 361-9093
mailto:roedyg@mindprod.com
http://mindprod.com

version history

version 1.5 2005-07-14 split off from Misc, allow for compilation with old compiler.

version 1.6 2006-10-15 add condense method.

vernion 1.7 2006-03-04 format with IntelliJ and prepare Javadoc
*/

package com.oristartech.marketing.components.util;

import java.awt.Color;
import java.text.MessageFormat;

/**
 * Miscellaneous static methods for dealing with Strings.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.7,  2006-03-04
 * @since 2003 May 15
 */

public class StringUtil {

    // ------------------------------ FIELDS ------------------------------

    /**
     * true if you want extra debugging output and test code
     */
    static final boolean DEBUGGING = true;

    // -------------------------- STATIC METHODS --------------------------
    
    /**
	 * 方法用途和描述:使用StringBuilder连接多个字符串
	 * 方法的实现逻辑描述：
	 * @param sb
	 * @param strings
	 * @return
	 */
	public static StringBuilder concat(StringBuilder sb, Object... strings) {
		if (sb == null) {
			sb = new StringBuilder();
		}
		if (strings == null) {
			return sb;
		}
		for (Object string : strings) {
			if (string != null) {
				sb.append(string.toString());
			}
		}
		return sb;
	}

	/**
	 * 方法用途和描述:使用StringBuilder连接多个字符串
	 * 方法的实现逻辑描述：
	 * @param strings
	 * @return
	 */
	public static StringBuilder concat(Object... strings) {
		return concat(null, strings);
	}
	
    /**
	* 方法用途和描述: 格式化带有占位符的信息
	* @param msg 需要格式化的信息
	* @param parm 用来替换占位符的对象
	* @return
	* @author 张宪新 新增日期：2008-7-2
	* @since CEReport version(2.1)
	*/
	public static String msgFormat(String msg, Object parm){
		MessageFormat form = new MessageFormat(msg);
		return form.format(parm);
	}
	
	/**
	 * 判断是否全为数字
	 * @param str
	 * @return
	 */
	public static boolean isAllNumbers(String str){
		for (int i = 0; i < str.length(); i++) {
			char pw = str.charAt(i);
			if (pw < 48 || pw > 57) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断是否全为字母
	 * @param str
	 * @return
	 */
	public static boolean isAllLetters(String str){
		for (int i = 0; i < str.length(); i++) {
			char pw = str.charAt(i);
			if (pw < 65 || pw > 90) {
				if (pw < 97 || pw > 122) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	* 方法用途和描述: 判断字符是否为数字
	* @param c
	* @return
	*/
	public static boolean isNumber(char c){
		return (c > 48 && c < 57);
	}
	
	/**
	* 方法用途和描述: 判断字符是否为字母
	* @param c
	* @return
	*/
	public static boolean isLetter(char c){
		return (c > 65 && c < 90) || (c > 97 && c <122) ;
	}
	
	/**
	* 方法用途和描述: 判断字符串是否由数字跟字母组成
	* @param str
	* @return
	*/
	public static boolean isNumberAndLettersComprise(String str){
		int numberCount = 0;
		int letterCount = 0;
		for (int i = 0; i < str.length(); i++) {
			char pw = str.charAt(i);
			//第一步，判断其必须为数字或者字母
			if (pw < 48 || pw > 57) {
				if (pw < 65 || pw > 90) {
					if (pw < 97 || pw > 122) {
						return false;
					}
				}
			}
			
			//第二步，不能全部为数字
			if (pw >= 48 && pw <= 57) {
				numberCount++;//计算数字的个数	
			}
			
			//第三步，不能全部为字母
			if ((pw >= 65 && pw <= 90) || (pw >= 97 && pw <= 122)) {
				letterCount++;//计算字母的个数
			}
		}
		if (numberCount == str.length() || letterCount == str.length()) {
			return false;
		}
		return true;
	}
	
	/*  -------------------------- ---- -------------------------- */
    
	
    /**
     * makeshift system beep if awt.Toolkit.beep is not available. Works also in
     * JDK 1.02.
     */
    public static void beep() {
		System.out.print("\007");
		System.out.flush();
	} // end beep

    /**
     * Convert String to canonical standard form. null -> "". Trims lead trail
     * blanks.
     *
     * @param s String to be converted.
     *
     * @return String in canonical form.
     */
    public static String canonical(String s) {
		if (s == null) {
			return "";
		} else {
			return s.trim();
		}
	} // end canonical

    /**
     * Collapse multiple spaces in string down to a single space. Remove lead
     * and trailing spaces.
     *
     * @param s String to strip of blanks.
     *
     * @return String with all blanks, lead/trail/embedded removed.
     */
    public static String condense(String s) {
		if (s == null) {
			return null;
		}
		s = s.trim();
		if (s.indexOf("  ") < 0) {
			return s;
		}
		int len = s.length();
		// have to use StringBuffer for JDK 1.1
		StringBuffer b = new StringBuffer(len - 1);
		boolean suppressSpaces = false;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == ' ') {
				if (suppressSpaces) {
					// subsequent space
				} else {
					// first space
					b.append(c);
					suppressSpaces = true;
				}
			} else {
				// was not a space
				b.append(c);
				suppressSpaces = false;
			}
		} // end for
		return b.toString();
	} // end condense

    /**
     * count of how many leading characters there are on a string matching a
     * given character. It does not remove them.
     *
     * @param text text with possible leading characters, possibly empty, but not
     *             null.
     * @param c    the leading character of interest, usually ' ' or '\n'
     *
     * @return count of leading matching characters, possibly 0.
     */
    public static int countLeading(String text, char c) {
		// need defined outside the for loop.
		int count;
		for (count = 0; count < text.length() && text.charAt(count) == c; count++) {
		}
		return count;
	}

    /**
     * count of how many trailing characters there are on a string matching a
     * given character. It does not remove them.
     *
     * @param text text with possible trailing characters, possibly empty, but not
     *             null.
     * @param c    the trailing character of interest, usually ' ' or '\n'
     *
     * @return count of trailing matching characters, possibly 0.
     */
    public static int countTrailing(String text, char c) {
		int length = text.length();
		// need defined outside the for loop.
		int count;
		for (count = 0; count < length && text.charAt(length - 1 - count) == c; count++) {
		}
		return count;
	}

    /**
	 * Is this string empty?
	 * @param s String to be tested for emptiness.
	 * @return true if the string is null or equal to the "" null string. or just blanks
	 * @deprecated 
	 * @see {@link BlankUtil#isBlank(String)}
	 */
	@Deprecated
	public static boolean isEmpty(String s) {
		return (s == null) || s.trim().length() == 0;
	} // end isEmpty

    /**
     * Ensure the string contains only legal characters
     *
     * @param candidate  string to test.
     * @param legalChars characters than are legal for candidate.
     *
     * @return true if candidate is formed only of chars from the legal set.
     */
    public static boolean isLegal(String candidate, String legalChars) {
		for (int i = 0; i < candidate.length(); i++) {
			if (legalChars.indexOf(candidate.charAt(i)) < 0) {
				return false;
			}
		}
		return true;
	}

    /**
     * Check if char is plain ASCII lower case.
     *
     * @param c char to check
     *
     * @return true if char is in range a..z
     *
     * @see Character#isLowerCase(char)
     */
    public static boolean isUnaccentedLowerCase(char c) {
		return 'a' <= c && c <= 'z';
	} // isUnaccentedLowerCase

    /**
     * Check if char is plain ASCII upper case.
     *
     * @param c char to check.
     *
     * @return true if char is in range A..Z.
     *
     * @see Character#isUpperCase(char)
     */
    public static boolean isUnaccentedUpperCase(char c) {
		return 'A' <= c && c <= 'Z';
	} // end isUnaccentedUpperCase

    /**
     * Pads the string out to the given length by applying blanks on the left.
     *
     * @param s      String to be padded/chopped.
     * @param newLen length of new String desired.
     * @param chop   true if Strings longer than newLen should be truncated to newLen
     *               chars.
     *
     * @return String padded on left/chopped to the desired length.
     */
    public static String leftPad(String s, int newLen, boolean chop) {
		int grow = newLen - s.length();
		if (grow <= 0) {
			if (chop) {
				return s.substring(0, newLen);
			} else {
				return s;
			}
		} else if (grow <= 30) {
			return "                              ".substring(0, grow) + s;
		} else {
			return rep(' ', grow) + s;
		}
	} // end leftPad

    /**
     * convert a String to a long. The routine is very forgiving. It ignores
     * invalid chars, lead trail, embedded spaces, decimal points etc. Dash is
     * treated as a minus sign.
     *
     * @param numStr String to be parsed.
     *
     * @return long value of String with junk characters stripped.
     *
     * @throws NumberFormatException if the number is too big to fit in a long.
     */
    public static long parseDirtyLong(String numStr) {
		numStr = numStr.trim();
		// strip commas, spaces, decimals + etc
		StringBuffer b = new StringBuffer(numStr.length());
		boolean negative = false;
		for (int i = 0; i < numStr.length(); i++) {
			char c = numStr.charAt(i);
			if (c == '-') {
				negative = true;
			} else if ('0' <= c && c <= '9') {
				b.append(c);
			}
		} // end for
		numStr = b.toString();
		if (numStr.length() == 0) {
			return 0;
		}
		long num = Long.parseLong(numStr);
		if (negative) {
			num = -num;
		}
		return num;
	} // end parseDirtyLong

    /**
     * convert a String into long pennies. It ignores invalid chars, lead trail,
     * embedded spaces. Dash is treated as a minus sign. 0 or 2 decimal places
     * are permitted.
     *
     * @param numStr String to be parsed.
     *
     * @return long pennies.
     *
     * @throws NumberFormatException if the number is too big to fit in a long.
     */
    public static long parseLongPennies(String numStr) {
		numStr = numStr.trim();
		// strip commas, spaces, + etc
		StringBuffer b = new StringBuffer(numStr.length());
		boolean negative = false;
		int decpl = -1;
		for (int i = 0; i < numStr.length(); i++) {
			char c = numStr.charAt(i);
			switch (c) {
			case '-':
				negative = true;
				break;

			case '.':
				if (decpl == -1) {
					decpl = 0;
				} else {
					throw new NumberFormatException(
							"more than one decimal point");
				}
				break;

			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (decpl != -1) {
					decpl++;
				}
				b.append(c);
				break;

			default:
				// ignore junk chars
				break;
			} // end switch
		} // end for
		if (numStr.length() != b.length()) {
			numStr = b.toString();
		}

		if (numStr.length() == 0) {
			return 0;
		}
		
		long num = Long.parseLong(numStr);
		if (decpl == -1 || decpl == 0) {
			num *= 100;
		} else if (decpl == 2) { /* it is fine as is */
			
		} else {
			throw new NumberFormatException("wrong number of decimal places.");
		}

		if (negative) {
			num = -num;
		}
		return num;
	} // end parseLongPennies

    /**
     * Print dollar currency, stored internally as scaled int. convert pennies
     * to a string with a decorative decimal point.
     *
     * @param pennies long amount in pennies.
     *
     * @return amount with decorative decimal point, but no lead $.
     */
    public static String penniesToString(long pennies) {
		boolean negative;
		if (pennies < 0) {
			pennies = -pennies;
			negative = true;
		} else {
			negative = false;
		}
		String s = Long.toString(pennies);
		int len = s.length();
		switch (len) {
		case 1:
			s = "0.0" + s;
			break;
		case 2:
			s = "0." + s;
			break;
		default:
			s = s.substring(0, len - 2) + "." + s.substring(len - 2, len);
			break;
		} // end switch
		if (negative) {
			s = "-" + s;
		}
		return s;
	} // end penniesToString

    /**
     * Extracts a number from a string, returns 0 if malformed.
     *
     * @param s String containing the integer.
     *
     * @return binary integer.
     */
    public static int pluck(String s) {
		int result = 0;
		try {
			result = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			// leave result at 0
		}
		return result;
	} // end pluck

    /**
     * used to prepare SQL string literals by doubling each embedded ' and
     * wrapping in ' at each end. Further quoting is required to use the results
     * in Java String literals. If you use PreparedStatement, then this method
     * is not needed. The ' quoting is automatically handled for you.
     *
     * @param sql Raw SQL string literal
     *
     * @return sql String literal enclosed in '
     */
    public static String quoteSQL(String sql) {
		StringBuffer sb = new StringBuffer(sql.length() + 5);
		sb.append('\'');
		for (int i = 0; i < sql.length(); i++) {
			char c = sql.charAt(i);
			if (c == '\'') {
				sb.append("\'\'");
			} else {
				sb.append(c);
			}
		}
		sb.append('\'');
		return sb.toString();
	}

    /**
     * Produce a String of a given repeating character.
     *
     * @param c     the character to repeat
     * @param count the number of times to repeat
     *
     * @return String, e.g. rep('*',4) returns "****"
     */
    public static String rep(char c, int count) {
		char[] s = new char[count];
		for (int i = 0; i < count; i++) {
			s[i] = c;
		}
		return new String(s).intern();
	} // end rep

    /**
     * Pads the string out to the given length by applying blanks on the right.
     *
     * @param s      String to be padded/chopped.
     * @param newLen length of new String desired.
     * @param chop   true if Strings longer than newLen should be truncated to newLen
     *               chars.
     *
     * @return String padded on left/chopped to the desired length.
     */
    public static String rightPad(String s, int newLen, boolean chop) {
		int grow = newLen - s.length();
		if (grow <= 0) {
			if (chop) {
				return s.substring(0, newLen);
			} else {
				return s;
			}
		} else if (grow <= 30) {
			return s + "                              ".substring(0, grow);
		} else {
			return s + rep(' ', grow);
		}
	} // end rightPad

    /**
     * Remove all spaces from a String.
     *
     * @param s String to strip of blanks.
     *
     * @return String with all blanks, lead/trail/embedded removed.
     */
    public static String squish(String s) {
		if (s == null) {
			return null;
		}
		s = s.trim();
		if (s.indexOf(' ') < 0) {
			return s;
		}
		int len = s.length();
		// have to use StringBuffer for JDK 1.1
		StringBuffer b = new StringBuffer(len - 1);
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				b.append(c);
			}
		} // end for
		return b.toString();
	} // end squish

    /**
     * convert to Book Title case, with first letter of each word capitalised.
     * e.g. "handbook to HIGHER consciousness" -> "Handbook to Higher
     * Consciousness" e.g. "THE HISTORY OF THE U.S.A." -> "The History of the
     * U.S.A." e.g. "THE HISTORY OF THE USA" -> "The History of the Usa" (sorry
     * about that.) Don't confuse this with Character.isTitleCase which concerns
     * ligatures.
     *
     * @param s String to convert. May be any mixture of case.
     *
     * @return String with each word capitalised, except embedded words "the"
     *         "of" "to"
     */
    public static String toBookTitleCase( String s ){
        char[] ca = s.toCharArray();
        // Track if we changed anything so that
        // we can avoid creating a duplicate String
        // object if the String is already in Title case.
        boolean changed = false;
        boolean capitalise = true;
        boolean firstCap = true;
        for ( int i = 0; i < ca.length; i++ ){
            char oldLetter = ca[ i ];
            if ( oldLetter <= '/'
                 || ':' <= oldLetter && oldLetter <= '?'
                 || ']' <= oldLetter && oldLetter <= '`' )
                {
                /* whitespace, control chars or punctuation */
                /* Next normal char should be capitalised */
                capitalise = true;
                }
            else
                {
                if ( capitalise && !firstCap )
                    {
                    // might be the_ of_ or to_
                    capitalise =
                            ! ( s.substring( i, Math.min( i + 4, s.length() ) )
                                    .equalsIgnoreCase( "the " )
                                || s.substring( i,
                                                Math.min( i + 3, s.length() ) )
                                    .equalsIgnoreCase( "of " )
                                || s.substring( i,
                                                Math.min( i + 3, s.length() ) )
                                    .equalsIgnoreCase( "to " ) );
                    } // end if
                char newLetter =
                        capitalise
                        ? Character.toUpperCase( oldLetter )
                        : Character.toLowerCase( oldLetter );
                ca[ i ] = newLetter;
                changed |= ( newLetter != oldLetter );
                capitalise = false;
                firstCap = false;
                } // end if
            } // end for
        if ( changed ){
            s = new String( ca );
        }
        return s;
    } // end toBookTitleCase

    /**
	 * Convert int to hex with lead zeroes
	 * 
	 * @param h
	 *            number you want to convert to hex
	 * 
	 * @return 0x followed by unsigned hex 8-digit representation
	 * 
	 * @see #toString(Color)
	 */
	public static String toHexString(int h) {
		String s = Integer.toHexString(h);
		if (s.length() < 8) { // pad on left with zeros
			s = "00000000".substring(0, 8 - s.length()) + s;
		}
		return "0x" + s;
	}

	/**
	 * Convert an integer to a String, with left zeroes.
	 * 
	 * @param i the integer to be converted
	 * @param len the length of the resulting string. Warning. It will chop the
	 *            result on the left if it is too long.
	 * 
	 * @return String representation of the int e.g. 007
	 */
	public static String toLZ(int i, int len) {
		// Since String is final, we could not add this method there.
		String s = Integer.toString(i);
		if (s.length() > len) { /* return rightmost len chars */
			return s.substring(s.length() - len);
		} else if (s.length() < len)
		// pad on left with zeros
		{
			return "000000000000000000000000000000".substring(0, len
					- s.length())
					+ s;
		} else {
			return s;
		}
	} // end toLZ

    /**
     * Get #ffffff html hex number for a colour
     *
     * @param c Color object whose html colour number you want as a string
     *
     * @return # followed by 6 hex digits
     *
     * @see #toHexString(int)
     */
    public static String toString( Color c ){
		String s = Integer.toHexString(c.getRGB() & 0xffffff);
		if (s.length() < 6) { // pad on left with zeros
			s = "000000".substring(0, 6 - s.length()) + s;
		}
		return '#' + s;
	}

    /**
     * Removes white space from beginning this string.
     * <p/>
     * All characters that have codes less than or equal to
     * <code>'&#92;u0020'</code> (the space character) are considered to be
     * white space.
     *
     * @param s String to process. As always the original in unchanged.
     *
     * @return this string, with leading white space removed
     */
    public static String trimLeading( String s ){
		if (s == null) {
			return null;
		}
		int len = s.length();
		int st = 0;
		while ((st < len) && (s.charAt(st) <= ' ')) {
			st++;
		}
		return (st > 0) ? s.substring(st, len) : s;
	} // end trimLeading

    
    /**
     * Removes white space from end this string.
     * <p/>
     * All characters that have codes less than or equal to
     * <code>'&#92;u0020'</code> (the space character) are considered to be
     * white space.
     *
     * @param s String to process. As always the original in unchanged.
     *
     * @return this string, with trailing white space removed
     */
    public static String trimTrailing(String s) {
		if (s == null) {
			return null;
		}
		int len = s.length();
		int origLen = len;
		while ((len > 0) && (s.charAt(len - 1) <= ' ')) {
			len--;
		}
		return (len != origLen) ? s.substring(0, len) : s;
	} // end trimTrailing

    
    // --------------------------- CONSTRUCTORS ---------------------------

    /**
     * StringUtil contains only static methods.
     */
    private StringUtil(){
    	
    }

    // --------------------------- main() method ---------------------------

    /**
     * Test harness, used in debugging
     *
     * @param args not used
     */
    public static void main( String[] args ){
        if ( DEBUGGING ){
            System.out.println("1、"+ condense( "   this  is   spaced.  " ) );
            System.out.println("2、"+ trimLeading( "   trim" ) );
            System.out.println("3、"+ toString( Color.red ) );
            System.out.println("4、"+ toHexString( -3 ) );
            System.out.println("5、"+ toHexString( 3 ) );
            System.out.println("6、"+ countLeading( "none", ' ' ) );
            System.out.println("7、"+ countLeading( "*one***", '*' ) );
            System.out.println("8、"+ countLeading( "\n\ntw\n\n\no\n\n\n\n", '\n' ) );
            System.out.println("9、"+ countTrailing( "none", ' ' ) );
            System.out.println("10、"+ countTrailing( "***one*", '*' ) );
            System.out.println("11、"+ countTrailing( "\n\n\n\nt\n\n\n\nwo\n\n", '\n' ) );

            System.out.println("12、"+ quoteSQL( "Judy's Place" ) );
            System.out.println("13、"+ parseLongPennies( "$5.00" ) );
            System.out.println("14、"+ parseLongPennies( "$50" ) );
            System.out.println("15、"+ parseLongPennies( "50" ) );
            System.out.println("16、"+ parseLongPennies( "$50-" ) );

            System.out.println("17、"+ penniesToString( 0 ) );
            System.out.println("18、"+ penniesToString( -1 ) );
            System.out.println("19、"+ penniesToString( 20 ) );
            System.out.println("20、"+ penniesToString( 302 ) );
            System.out.println("21、"+ penniesToString( -100000 ) );
            System.out.println("22、"+ toBookTitleCase("handbook to HIGHER consciousness" ) );
            System.out.println("23、"+ toBookTitleCase( "THE HISTORY OF THE U.S.A." ) );
            System.out.println("24、"+ toBookTitleCase( "THE HISTORY OF THE USA" ) );

            System.out.println("25、"+ rightPad( "abc", 6, true ) + "*" );
            System.out.println("26、"+ rightPad( "abc", 2, true ) + "*" );
            System.out.println("27、"+ rightPad( "abc", 2, false ) + "*" );
            System.out.println("28、"+ rightPad( "abc", 3, true ) + "*" );
            System.out.println("29、"+ rightPad( "abc", 3, false ) + "*" );
            System.out.println("30、"+ rightPad( "abc", 0, true ) + "*" );
            System.out.println("31、"+ rightPad( "abc", 20, true ) + "*" );
            System.out.println("32、"+ rightPad( "abc", 29, true ) + "*" );
            System.out.println("33、"+ rightPad( "abc", 30, true ) + "*" );
            System.out.println("34、"+ rightPad( "abc", 31, true ) + "*" );
            System.out.println("35、"+ rightPad( "abc", 40, true ) + "*" );
            }
        } // end main
} // end class StringTools
