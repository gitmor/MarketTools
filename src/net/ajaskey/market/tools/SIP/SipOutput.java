package net.ajaskey.market.tools.SIP;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class SipOutput {

  private final static String digitfmt = String.format("#,###,##0.");

  private final static DecimalFormat dfmt = new DecimalFormat("#,###");

  private final static DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

  public static String buildArray(String desc, double[] values, int len, int digits) {
    String ret = desc;
    for (final double value : values) {
      final String sfmt = SipOutput.fmt(value, len, digits);
      ret += String.format(" %s", sfmt);
    }
    return ret;
  }

  public static String buildArray(String desc, double[] values, int len, int digits, int slide) {
    String ret = desc;
    for (int i = slide; i < values.length; i++) {
      final String sfmt = SipOutput.fmt(values[i], len, digits);
      ret += String.format(" %s", sfmt);
    }
    return ret;
  }

  /**
   *
   * @param d
   * @param len
   * @param decimal
   * @return
   */
  public static String fmt(final double d, final int len, int decimal) {

    String digfmt = SipOutput.digitfmt;
    if (decimal > 0) {
      for (int i = 0; i < decimal; i++) {
        digfmt += "0";
      }
    }
    else {
      final String tmp = SipOutput.digitfmt.substring(0, SipOutput.digitfmt.length() - 1);
      digfmt = tmp;
    }

    final DecimalFormat df = new DecimalFormat(digfmt, SipOutput.decimalFormatSymbols);

    final String sfmt = String.format("%%%ds", len);
    return String.format(sfmt, df.format(d));
  }

  /**
   *
   * @param i
   * @param len
   * @return
   */
  public static String ifmt(final int i, final int len) {

    return SipOutput.ifmt((long) i, len);
  }

  /**
   *
   * @param i
   * @param len
   * @return
   */
  public static String ifmt(final long i, final int len) {

    final String s = SipOutput.dfmt.format(i);
    final String sfmt = String.format("%%%ds", len);

    return String.format(sfmt, s);
  }

  public static String SipHeader(String ticker, String name, String exch, String sec, String ind) {
    return String.format("%-10s\t%-40s\t%-20s\t%-40s\t%-1s%n", ticker, name, exch, sec, ind);
  }

}
