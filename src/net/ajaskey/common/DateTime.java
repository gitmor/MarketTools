
package net.ajaskey.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class encapsulates and improves the implementation of the
 * java.utils.Calendar class.
 *
 * @author Andy Askey
 *
 *         <p>
 *         PTV-Parser Copyright (c) 2015, Andy Askey. All rights reserved.
 *         </p>
 *
 *         <p>
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *         </p>
 *
 *         <p>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 *         </p>
 *
 */
public class DateTime {

  private Calendar         cal = null;
  private SimpleDateFormat sdf = null;

  /**
   * This method serves as a constructor for the class.
   *
   */
  public DateTime() {
    this.cal = Calendar.getInstance();
  }

  /**
   *
   * This method serves as a constructor for the class.
   *
   * @param c Instantiated Calendar variable
   */
  public DateTime(final Calendar c) {
    this.cal = Calendar.getInstance();
    try {
      this.cal.setTime(c.getTime());
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * This method serves as a constructor for the class.
   *
   * @param d Instantiated Date variable
   */
  public DateTime(final Date d) {
    this.cal = Calendar.getInstance();
    try {
      this.cal.setTime(d);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * This method serves as a constructor for the class.
   *
   * @param dt Instantiated DateTime variable
   */
  public DateTime(final DateTime dt) {
    this.cal = Calendar.getInstance();
    try {
      this.cal.setTime(dt.getCal().getTime());
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * This method serves as a constructor for the class.
   *
   * @param year
   * @param month
   * @param day
   */
  public DateTime(final int year, final int month, final int day) {
    this.cal = Calendar.getInstance();
    try {
      this.cal.set(year, month, day);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * This method serves as a constructor for the class.
   *
   * @param ms Milliseconds
   */
  public DateTime(final long ms) {
    this.cal = Calendar.getInstance();
    try {
      this.cal.setTimeInMillis(ms);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param value
   * @param sdf
   */
  public DateTime(String value, SimpleDateFormat sdf) {
    this.build(this, value, sdf, sdf);
  }

  public DateTime(String value, SimpleDateFormat sdf, SimpleDateFormat sdfout) {
    this.build(this, value, sdf, sdfout);
  }

  /**
   * This method serves as a constructor for the class.
   *
   * @param trim
   * @param string
   */
  public DateTime(String value, String fmt) {
    final SimpleDateFormat sdfIn = new SimpleDateFormat(fmt);
    this.build(this, value, sdfIn, sdfIn);
  }

  /**
   *
   * net.ajaskey.market.misc.add
   *
   * @param unit DATE|MONTH|YEAR
   * @param knt  Any integer
   */
  public void add(final int unit, final int knt) {
    try {
      if (unit == DateTime.DATE || unit == DateTime.MONTH || unit == DateTime.YEAR) {
        this.cal.add(unit, knt);
      }
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * net.ajaskey.market.misc.copy
   *
   * @param dt Instantiated DateTime variable
   * @return
   */
  public DateTime copy(final DateTime dt) {
    try {
      final DateTime d = new DateTime(dt.cal.getTime());
      return d;
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *
   * net.ajaskey.market.misc.format
   *
   * @return
   */
  public String format() {
    String ret = "";
    try {
      ret = this.sdf.format(this.cal.getTime());
    }
    catch (final Exception e) {
      e.printStackTrace();
      ret = "BAD-DATE-FORMAT";
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.format
   *
   * @param fmt
   * @return
   */
  public String format(final String fmt) {
    String ret = "";
    try {
      final SimpleDateFormat sdf = new SimpleDateFormat(fmt);
      ret = sdf.format(this.cal.getTime());
    }
    catch (final Exception e) {
      e.printStackTrace();
      ret = "BAD-DATE-FORMAT";
    }
    return ret;
  }

  /**
   * @return the cal
   */
  public Calendar getCal() {
    return this.cal;
  }

  /**
   *
   * net.ajaskey.market.misc.getDay
   *
   * @return
   */
  public String getDay() {
    String ret = "UNKNOWN";
    if (this.cal != null) {
      ret = this.cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.getDayOfMonth
   *
   * @return
   */
  public int getDayOfMonth() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.DAY_OF_MONTH);
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.getDayOfWeek
   *
   * @return
   */
  public int getDayOfWeek() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.DAY_OF_WEEK);
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.getDayOfYear
   *
   * @return
   */
  public int getDayOfYear() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.DAY_OF_YEAR);
    }
    return ret;
  }

  /**
   *
   * @param dt2
   * @return
   */
  public long getDeltaDays(final DateTime dt2) {
    final long s1 = dt2.getTime().getTime();
    final long s2 = this.getTime().getTime();
    // System.out.println(dt2);
    final long dd = (s2 - s1) / 86400000;
    return dd;
  }

  public long getDeltaMilliSeconds(DateTime dt2) {
    final long dt1Ms = this.cal.getTimeInMillis();
    final long dt2Ms = dt2.cal.getTimeInMillis();
    return dt2Ms - dt1Ms;
  }

  /**
   *
   * @param dt2
   * @return
   */
  public double getDeltaYears(final DateTime dt2) {
    final double dd = this.getDeltaDays(dt2);
    return dd / 365.0;
  }

  public int getHour() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.HOUR);
    }
    return ret;
  }

  public int getMinute() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.MINUTE);
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.getMonth
   *
   * @return
   */
  public String getMonth() {
    String ret = "UNKNOWN";
    if (this.cal != null) {
      ret = this.cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }
    return ret;
  }

  public int getMs() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.MILLISECOND);
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.getSdf
   *
   * @return
   */
  public SimpleDateFormat getSdf() {
    return this.sdf;
  }

  public int getSecond() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.SECOND);
    }
    return ret;
  }

  public Date getTime() {
    return this.cal.getTime();
  }

  /**
   *
   * net.ajaskey.market.misc.getYear
   *
   * @return
   */
  public int getYear() {
    int ret = -1;
    if (this.cal != null) {
      ret = this.cal.get(Calendar.YEAR);
    }
    return ret;
  }

  public int getYears(DateTime date2) {
    final int y1 = this.cal.get(DateTime.YEAR);
    final int y2 = date2.cal.get(DateTime.YEAR);
    int delta = y2 - y1;
    final int m1 = this.cal.get(DateTime.MONTH);
    final int m2 = date2.cal.get(DateTime.MONTH);
    if (m2 < m1) {
      delta--;
    }
    else if (m2 == m1) {
      final int d1 = this.cal.get(DateTime.DATE);
      final int d2 = date2.cal.get(DateTime.DATE);
      if (d2 < d1) {
        delta--;
      }
    }
    return delta;
  }

  /**
   *
   * net.ajaskey.market.misc.isEqual
   *
   * @param dt
   * @return
   */
  public boolean isEqual(final DateTime dt) {
    try {
      return DateTime.sameDate(dt, this);
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   *
   * net.ajaskey.market.misc.isGreaterThan
   *
   * @param dt
   * @return
   */
  public boolean isGreaterThan(final DateTime dt) {
    try {
      return dt.cal.before(this.cal);
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   *
   * net.ajaskey.market.misc.isGreaterThanOrEqual
   *
   * @param dt
   * @return
   */
  public boolean isGreaterThanOrEqual(final DateTime dt) {
    try {
      return this.isGreaterThan(dt) || this.isEqual(dt);
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   *
   * net.ajaskey.market.misc.isLessThan
   *
   * @param dt
   * @return
   */
  public boolean isLessThan(final DateTime dt) {
    boolean ret = false;
    try {
      ret = dt.cal.after(this.cal);
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.isLessThanOrEqual
   *
   * @param dt
   * @return
   */
  public boolean isLessThanOrEqual(final DateTime dt) {
    try {
      return this.isLessThan(dt) || this.isEqual(dt);
    }
    catch (final Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean isNull() {
    boolean ret = false;
    if (this.cal == null) {
      ret = true;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.isWeekday
   *
   * @return
   */
  public boolean isWeekday() {
    final int d = this.cal.get(Calendar.DAY_OF_WEEK);
    return d > DateTime.SUNDAY && d < DateTime.SATURDAY;
  }

  /**
   *
   * net.ajaskey.market.misc.parse
   *
   * @param src
   * @return
   */
  public DateTime parse(final String src) {
    DateTime ret = null;
    try {
      final Date d = this.sdf.parse(src);
      ret = new DateTime(d);
    }
    catch (final Exception e) {
      e.printStackTrace();
      ret = null;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.misc.parse
   *
   * @param src
   * @param fmt
   * @return
   */
  public DateTime parse(final String src, final String fmt) {
    DateTime ret = new DateTime();
    try {
      final SimpleDateFormat sdf = new SimpleDateFormat(fmt);
      final Date d = sdf.parse(src);
      ret = new DateTime(d);
    }
    catch (final Exception e) {
      e.printStackTrace();
      ret = null;
    }
    return ret;
  }

  /**
   *
   * @param dt2
   * @return
   */
  public boolean sameDate(final DateTime dt2) {
    if (dt2 == null) {
      return false;
    }
    if (this.cal.get(Calendar.YEAR) == dt2.cal.get(Calendar.YEAR)) {
      if (this.cal.get(Calendar.MONTH) == dt2.cal.get(Calendar.MONTH)) {
        if (this.cal.get(Calendar.DATE) == dt2.cal.get(Calendar.DATE)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   *
   * net.ajaskey.market.misc.set
   *
   * @param c
   */
  public void set(final Calendar c) {
    if (c != null) {
      if (this.cal == null) {
        this.cal = Calendar.getInstance();
      }
      this.cal.setTime(c.getTime());
    }
  }

  /**
   *
   * net.ajaskey.market.misc.set
   *
   * @param d
   */
  public void set(final Date d) {
    if (d != null) {
      if (this.cal == null) {
        this.cal = Calendar.getInstance();
      }
      this.cal.setTime(d);
    }
  }

  /**
   *
   * net.ajaskey.market.misc.set
   *
   * @param dt
   */
  public void set(final DateTime dt) {
    if (dt != null && dt.cal != null) {
      if (this.cal == null) {
        this.cal = Calendar.getInstance();
      }
      this.cal.setTime(dt.cal.getTime());
    }
  }

  /**
   *
   * net.ajaskey.market.misc.set
   *
   * @param year
   * @param month
   * @param day
   */
  public void set(final int year, final int month, final int day) {
    if (this.cal == null) {
      this.cal = Calendar.getInstance();
    }
    this.cal.set(year, month, day);
  }

  /**
   *
   */
  public void setFirstWorkDay() {
    final int dow = this.getDayOfWeek();
    if (dow == DateTime.SATURDAY) {
      this.add(DateTime.DATE, 2);
    }
    else if (dow == DateTime.SUNDAY) {
      this.add(DateTime.DATE, 1);
    }
  }

  /**
   *
   * net.ajaskey.market.misc.setSdf
   *
   * @param simpledateformat
   */
  public void setSdf(final SimpleDateFormat simpledateformat) {
    this.sdf = simpledateformat;
  }

  /**
   *
   * net.ajaskey.market.misc.toFullString
   *
   * @return
   */
  public String toFullString() {
    return Utils.sdfFull.format(this.cal.getTime());
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String ret = "";
    if (this.cal != null) {
      if (this.sdf == null) {
        this.setSdf(Utils.sdf);
      }
      ret = this.sdf.format(this.cal.getTime());
    }
    return ret;
  }

  /**
   * 
   * @param retThis
   * @param value
   * @param sdf
   * @param sdfout
   */
  private void build(DateTime retThis, String value, SimpleDateFormat sdf, SimpleDateFormat sdfout) {
    retThis.cal = Calendar.getInstance();
    try {
      final Date d = sdf.parse(value);
      retThis.cal.setTime(d);
      retThis.setSdf(sdfout);
    }
    catch (final Exception e) {
      retThis.cal = null;
    }
  }

  public static final int DATE       = Calendar.DATE;
  public static final int MONTH      = Calendar.MONTH;
  public static final int YEAR       = Calendar.YEAR;
  public static final int HOUR       = Calendar.HOUR;
  public static final int MINUTE     = Calendar.MINUTE;
  public static final int SECOND     = Calendar.SECOND;
  public static final int MILLSECOND = Calendar.MILLISECOND;
  public static final int JANUARY    = Calendar.JANUARY;
  public static final int FEBRUARY   = Calendar.FEBRUARY;
  public static final int MARCH      = Calendar.MARCH;
  public static final int APRIL      = Calendar.APRIL;
  public static final int MAY        = Calendar.MAY;
  public static final int JUNE       = Calendar.JUNE;
  public static final int JULY       = Calendar.JULY;
  public static final int AUGUST     = Calendar.AUGUST;
  public static final int SEPTEMBER  = Calendar.SEPTEMBER;
  public static final int OCTOBER    = Calendar.OCTOBER;
  public static final int NOVEMBER   = Calendar.NOVEMBER;
  public static final int DECEMBER   = Calendar.DECEMBER;
  public static final int SUNDAY     = Calendar.SUNDAY;
  public static final int MONDAY     = Calendar.MONDAY;
  public static final int TUESDAY    = Calendar.TUESDAY;
  public static final int WEDNESDAY  = Calendar.WEDNESDAY;
  public static final int THURSDAY   = Calendar.THURSDAY;
  public static final int FRIDAY     = Calendar.FRIDAY;
  public static final int SATURDAY   = Calendar.SATURDAY;

  /**
   *
   * net.ajaskey.market.misc.main
   *
   * @param args
   */
  public static void main(final String[] args) {
    final DateTime dt = new DateTime();
    final DateTime dt2 = new DateTime();
    dt2.add(DateTime.DATE, 1);
    System.out.println(dt);
    System.out.println(dt2);
    boolean b = dt.isLessThan(dt2);
    System.out.println("dt < dt2 : " + b);
    b = dt2.isLessThan(dt);
    System.out.println("dt2 < dt : " + b);
    b = dt.isGreaterThan(dt2);
    System.out.println("dt > dt2 : " + b);
    b = dt2.isGreaterThan(dt);
    System.out.println("dt2 > dt : " + b);
    System.out.println(dt.getSdf().toPattern());
    System.out.println(dt.getMonth());
    System.out.println(dt.getDay());
    System.out.println(dt.getYear());
    System.out.println(dt.getDayOfYear());
    System.out.println(dt.getDayOfWeek());
    System.out.println(dt.getDayOfMonth());
    final String s = dt.format("yyyy-MM-dd");
    System.out.println(s);
    dt.setSdf(Utils.sdfFull);
    System.out.println(dt);
    System.out.println(dt.getSdf().toPattern());
    System.out.println(dt.isWeekday());
    dt.set(2019, DateTime.MAY, 5);
    System.out.println(dt);
    System.out.println(dt.isWeekday());
    dt.add(DateTime.DATE, 3);
    System.out.println(dt);
    dt.add(DateTime.MONTH, -13);
    System.out.println(dt);
  }

  /**
   *
   * @param dt1
   * @param dt2
   * @return
   */
  public static boolean sameDate(final DateTime dt1, final DateTime dt2) {
    if (dt1 == null || dt2 == null) {
      return false;
    }
    if (dt1.cal.get(Calendar.YEAR) == dt2.cal.get(Calendar.YEAR)) {
      if (dt1.cal.get(Calendar.MONTH) == dt2.cal.get(Calendar.MONTH)) {
        if (dt1.cal.get(Calendar.DATE) == dt2.cal.get(Calendar.DATE)) {
          return true;
        }
      }
    }
    return false;
  }
}
