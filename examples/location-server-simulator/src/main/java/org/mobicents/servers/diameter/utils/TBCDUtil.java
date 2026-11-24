package org.mobicents.servers.diameter.utils;

/**
 * This sample code demonstrates how a character string can be converted to
 * a TBCD (Telephony Binary Coded Decimal) string and vice versa.
 *
 * @author <a href="mailto:fernando.mendioroz@gmail.com"> Fernando Mendioroz </a>
 */
public class TBCDUtil {

    private static final String cTBCDSymbolString = "0123456789*#abc";
    private static final char[] cTBCDSymbols = cTBCDSymbolString.toCharArray();

    public TBCDUtil() {
    }

    /*
     * This method converts a TBCD byte array to a character string.
     */
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
    /* Hex chars */
    private static final byte[] HEX_CHAR = new byte[]
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static Integer[] setAreaIdParams(String[] areaId, String areaType) {
        Integer[] params = new Integer[4];
        if (areaId.length > 0) {
            params[0] = Integer.valueOf(areaId[0]);
            if (areaType.equalsIgnoreCase("countryCode"))
                params[1] = params[2] = params[3] = -1;
        }
        if (areaId.length > 1) {
            params[1] = Integer.valueOf(areaId[1]);
            if (areaType.equalsIgnoreCase("plmnId"))
                params[2] = params[3] = -1;
        }
        if (areaId.length > 2) {
            String v = areaId[2];
            if (areaType.equalsIgnoreCase("locationAreaId") || areaType.equalsIgnoreCase("cellGlobalId")
                || areaType.equalsIgnoreCase("routingAreaId")) {
                params[2] = Integer.valueOf(v);
            } else if (areaType.equalsIgnoreCase("utranCellId")) {
                params[3] = Integer.valueOf(v);
            } else if (areaType.equalsIgnoreCase("eUtranCellId")) {
                params[2] = Integer.valueOf(v);
            } else if (areaType.equalsIgnoreCase("trackingAreaId")) {
                params[2] = Integer.valueOf(v);
                params[3] = -1;
            }
        }
        if (areaId.length > 3) {
            if (areaType.equalsIgnoreCase("cellGlobalId") || areaType.equalsIgnoreCase("routingAreaId")
                || areaType.equalsIgnoreCase("eUtranCellId")) {
                params[3] = Integer.valueOf(areaId[3]);
            }
        }
        return params;
    }
}
