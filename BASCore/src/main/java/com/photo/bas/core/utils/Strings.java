/**
 * 
 */
package com.photo.bas.core.utils;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author FengYu
 *
 */
public class Strings {
	public static final String EMPTY = "";
    private static final int OFF_ACCENT = 160;
    private static String[] SINGLECHARSTRING = new String[0x100];
    private static final String[] unicode2ascii_ = new String[376 + 1 - OFF_ACCENT];
    static {
        for (int i = 0, imax = SINGLECHARSTRING.length; i < imax; i++) {
            SINGLECHARSTRING[i] = String.valueOf((char) i);
        }
        String[] str = {"160, ,!,c,L,o,Y,|,s,XXX,(C),a,<<,-,XXX,(R),-,o,+-,2,3,XXX,u,P,.,XXX,1,o,>>,1/4,1/2,3/4,?"
                 + ",A,A,A,A,A,A,A,C,E,E,E,E,I,I,I,I,D,N,O,O,O,O,O,x,O,U,U,U,U,Y,th,ss"
                 + ",a,a,a,a,a,a,a,c,e,e,e,e,i,i,i,i,d,n,o,o,o,o,o,/,o,u,u,u,u,y,th,y",
                "338,OE,oe", "352,S,s", "376,Y"
        };

        for (int i = 0, imax = str.length; i < imax; i++) {
            StringTokenizer st = new StringTokenizer(str[i], ",");
            int x = Integer.parseInt(st.nextToken());
            while (st.hasMoreTokens()) {
                String sub = valueOf(st.nextToken());
                if ("XXX".equals(sub)) {
                    unicode2ascii_[x - OFF_ACCENT] = "";
                } else if (x <= 376) {
                    unicode2ascii_[x - OFF_ACCENT] = sub;
                }
                x++;
            }
        }
    }

    public static String formatString(String sourceString, int length) {
        if (sourceString.length() < length)
            return sourceString;
        else
            return sourceString.substring(0, length);
    }

    public static String formatStringFixLength(String sourceString, int length) {
        int len = sourceString.length();
        if (sourceString.length() < length) {
            for (int i = 0; i < length - len; i++) {
                sourceString += " ";
            }
            return sourceString;
        } else
            return sourceString.substring(0, length);
    }

    public static boolean isEmpty(String value) {
        if (value == null || value.length() == 0 || isNull(value)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isNull(String value) {
        if (value.equals("null") || value.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isEmptyAfterTrim(String value) {
        if (value == null || value.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String printCovertTable() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < unicode2ascii_.length; i++) {
            sb.append((i + 160) + new Character((char) (i + 160)).toString()
                    + " - " + unicode2ascii_[i] + "   ");
            if ((i + 1) % 5 == 0)
                sb.append("\n");
        }
        return sb.toString();
    }

    static public String replace(String originalValue, String oldSegment,
            String newSegment) {
        if (Strings.isEmpty(originalValue))
            return "";
        String newValue = "";
        int i = originalValue.indexOf(oldSegment, 0);
        int lastpos = 0;
        while (i != -1) {
            newValue += originalValue.substring(lastpos, i) + newSegment;
            lastpos = i + oldSegment.length();
            i = originalValue.indexOf(oldSegment, lastpos);
        }
        newValue += originalValue.substring(lastpos); 
        return newValue;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] split(String str, char x) {
        Vector v = new Vector();
        String str1 = new String();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == x) {
                v.add(str1);
                str1 = new String();
            } else {
                str1 += str.charAt(i);
            }
        }
        v.add(str1);
        String array[];
        array = new String[v.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = new String((String) v.elementAt(i));
        }
        return array;
    }

	public static String removeTrailingZeros(String str, Boolean isNumber) {
		if (str == null) {
			return null;
		}

		if(isNumber) {
			if(str.indexOf(".") == -1) {
				return str;
			}
		}
		char[] chars = str.toCharArray();
		int length, index;
		length = str.length();
		index = length - 1;
		for (; index >= 0; index--) {
			if (chars[index] != '0') {
				break;
			}
		}
		
		String myStr = (index == length - 1) ? str : str.substring(0, index + 1);
		if (myStr.endsWith(".")) {
			myStr = myStr.substring(0, myStr.length() - 1);
		}
		return myStr;
	}
	
	public static String removeTrailingZeros(Double d) {
		if(d != null) {
			return removeTrailingZeros(d.toString(), true);
		}
		return "";
	}
	
    public static String toASCII7(String txt) {
        for (int i = 0, imax = txt.length(); i < imax; i++) {
            char ch = txt.charAt(i);
            if (ch >= 128) {
                String[] u2a = unicode2ascii_;
                final int END = OFF_ACCENT + u2a.length;
                StringBuffer sb = new StringBuffer(txt.length());
                for (int j = 0, jmax = txt.length(); j < jmax; j++) {
                    ch = txt.charAt(j);
                    if (ch < 128)
                        sb.append(ch);
                    else if (OFF_ACCENT <= ch && ch < END
                            && u2a[ch - OFF_ACCENT] != null) {
                        sb.append(u2a[ch - OFF_ACCENT]);
                    }
                }
                txt = sb.toString();
                break;
            }
        }
        return txt;
    }

    public static String valueOf(String str) {
        String s;
        if (str == null)
            s = null;
        else if (str.length() == 0)
            s = "";
        else if (str.length() == 1 && str.charAt(0) < SINGLECHARSTRING.length)
            s = SINGLECHARSTRING[str.charAt(0)];
        else
            s = str;
        return s;
    }
    
	/**
     * Filter the specified string for characters that are senstive to HTML
     * interpreters, returning the string with these characters replaced by
     * the corresponding character entities.
     *
     * @param value The string to be filtered and returned
     */
    public static String filterResponseString(String value) {
        if ((value == null) || (value.length() == 0)) {
            return value;
        }

        StringBuffer result = null;
        String filtered = null;

        for (int i = 0; i < value.length(); i++) {
            filtered = null;

            switch (value.charAt(i)) {
            case '<':
                filtered = "&lt;";

                break;

            case '>':
                filtered = "&gt;";

                break;

            case '&':
                filtered = "&amp;";

                break;

            case '"':
                filtered = "&quot;";

                break;

            case '\'':
                filtered = "&#39;";

                break;
            }

            if (result == null) {
                if (filtered != null) {
                    result = new StringBuffer(value.length() + 50);

                    if (i > 0) {
                        result.append(value.substring(0, i));
                    }

                    result.append(filtered);
                }
            } else {
                if (filtered == null) {
                    result.append(value.charAt(i));
                } else {
                    result.append(filtered);
                }
            }
        }

        return (result == null) ? value : result.toString();
    }
    
	public static String firstCharUpperCase(String s) {
		if (s == null || "".equals(s)) {
			return ("");
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
    
	public static String firstCharLowerCase(String s) {
		if (s == null || "".equals(s)) {
			return ("");
		}
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
    public static StringBuffer append(StringBuffer sb, String...str) {
    	if(sb == null) sb = new StringBuffer();
    	if(str != null) {
	    	for (int i = 0; i < str.length; i++) {
				sb.append(str[i]);
			}
    	}
    	return sb;
    }
    public static StringBuffer appendWithBlank(StringBuffer sb, String...str) {
    	return append(" ", sb, str);
    }
    public static StringBuffer append(String delimiter, StringBuffer sb, String...str) {
    	if(sb == null) sb = new StringBuffer();
    	if(str != null) {
	    	for (int i = 0; i < str.length; i++) {
	    		String s = str[i];
				if(sb.length() > 0 && !Strings.isEmpty(s)) {
	    			sb.append(delimiter);
	    		}
				sb.append(s);
			}
    	}
    	return sb;
    }
	
    private Strings() { }
    
	public static void main(String[] args) {

		String decimalString = "0100";
		String numberString = "1.010";

		System.out.println(removeTrailingZeros("Decimal==>" + decimalString,true));
		System.out.println(removeTrailingZeros("number==>" + numberString,true));

		// System.out.println(removeLeadingZeros(trailingZerosRemovedStr1));
		// System.out.println(removeLeadingZeros(trailingZerosRemovedStr2));
	}
}
