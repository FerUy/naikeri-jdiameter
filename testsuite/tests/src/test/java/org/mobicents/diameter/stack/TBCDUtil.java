package org.mobicents.diameter.stack;

/**
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class TBCDUtil {

  private static final String cTBCDSymbolString = "0123456789*#abc";
  private static final char[] cTBCDSymbols = cTBCDSymbolString.toCharArray();

  public TBCDUtil() {
  }

  /*
   * This method converts a character string to a TBCD string.
   */
  public static byte[] parseTBCD(String tbcd) {
    int length = (tbcd == null ? 0:tbcd.length());
    int size = (length + 1)/2;
    byte[] buffer = new byte[size];

    for (int i=0, i1=0, i2=1; i<size; ++i, i1+=2, i2+=2) {

      char c = tbcd.charAt(i1);
      int n2 = getTBCDNibble(c, i1);
      int octet;
      int n1 = 15;
      if (i2 < length) {
        c = tbcd.charAt(i2);
        n1 = getTBCDNibble(c, i2);
      }
      octet = (n1 << 4) + n2;
      buffer[i] = (byte)(octet & 0xFF);
    }

    return buffer;
  }

  private static int getTBCDNibble(char c, int i1) {

    int n = Character.digit(c, 10);

    if (n < 0 || n > 9) {
      switch (c) {
        case '*':
          n = 10;
          break;
        case '#':
          n = 11;
          break;
        case 'a':
          n = 12;
          break;
        case 'b':
          n = 13;
          break;
        case 'c':
          n = 14;
          break;
        default:
          throw new NumberFormatException("Bad character '" + c
              + "' at position " + i1);
      }
    }
    return n;
  }

  public static String toTBCDString(byte[] tbcd) {

    int size = (tbcd == null ? 0 : tbcd.length);
    StringBuilder sb = new StringBuilder(2*size);
    for (int i=0; i<size; ++i) {
      int octet = tbcd[i];
      int n2 = (octet >> 4) & 0xF;
      int n1 = octet & 0xF;

      if (n1 == 15) {
        throw new NumberFormatException("Illegal filler in octet n=" + i);
      }
      sb.append(cTBCDSymbols[n1]);

      if (n2 == 15) {
        if (i != size-1)
          throw new NumberFormatException("Illegal filler in octet n=" + i);
      } else
        sb.append(cTBCDSymbols[n2]);
    }

    return sb.toString();
  }
}
