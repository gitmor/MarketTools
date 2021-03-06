package net.ajaskey.market.optuma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.ajaskey.common.DateTime;

/**
 * This class...
 *
 * @author aja
 *         <p>
 *         PTV-Parser Copyright (c) 2015, Andy Askey. All rights reserved.
 *         </p>
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
public class PriceData {

  public DateTime date;

  public double    open;
  public double    high;
  public double    low;
  public double    close;
  public long      volume;
  private FormType form;
  private boolean  valid;

  public enum FormType {
    SHORT, FULL
  }

  /**
   * This method serves as a constructor for the class.
   *
   */
  public PriceData(final DateTime dt, final double o, final double h, final double l, final double c, final long v) {

    this.date = new DateTime(dt);
    this.open = o;
    this.high = h;
    this.low = l;
    this.close = c;
    this.volume = v;
    this.valid = true;
    this.form = FormType.FULL;
  }

  public PriceData(String[] fld, String fmt, int startIdx) {
    try {
      this.date = new DateTime(fld[startIdx].trim(), fmt);
      if (this.date.isNull()) {
        this.valid = false;
        return;
      }
      this.open = Double.parseDouble(fld[startIdx + 1].trim());
      this.high = Double.parseDouble(fld[startIdx + 2].trim());
      this.low = Double.parseDouble(fld[startIdx + 3].trim());
      this.close = Double.parseDouble(fld[startIdx + 4].trim());
      this.volume = Long.parseLong(fld[startIdx + 5].trim());
      this.setForm();

      this.valid = true;

    }
    catch (final Exception e) {
      e.printStackTrace();
      this.valid = false;
    }
  }

  /**
   * @return the form
   */
  public FormType getForm() {

    return this.form;
  }

  public boolean isValid() {

    // valid = this.date.isValid();

    return this.valid;
  }

  public String toShortString() {

    final String ret = String.format("%s, %.2f", this.date.format("yyyy-MM-dd"), this.close);
    return ret;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    final String ret = String.format("%s, %.2f, %.2f, %.2f, %.2f, %d", this.date.format("yyyy-MM-dd"), this.open, this.high, this.low, this.close,
        this.volume);
    return ret;
  }

  /**
   *
   * net.ajaskey.market.tools.helpers.setForm
   *
   */
  private void setForm() {

    if (this.open == this.high && this.high == this.low && this.low == this.close) {
      this.setForm(FormType.SHORT);
    }
    else {
      this.setForm(FormType.FULL);
    }
  }

  /**
   * @param form the form to set
   */
  private void setForm(final FormType form) {

    this.form = form;
  }

  /**
   *
   * @param index
   * @return
   * @throws IOException
   */
  public static List<PriceData> getData(String index) throws IOException {
    final List<PriceData> ret = new ArrayList<>();

    final String fname = PriceData.getFilename(index);

    try (BufferedReader br = new BufferedReader(new FileReader(new File(fname)))) {
      String line = "";

      line = br.readLine(); // read header

      while (line != null) {
        line = br.readLine();
        if (line != null && line.length() > 0) {
          final String fld[] = line.split(",");
          // final PriceData dd = new PriceData();
          DateTime dt = new DateTime();
          dt = dt.parse(fld[0].trim(), "yyyy-MM-dd");
          final double open = Double.parseDouble(fld[1].trim());
          final double high = Double.parseDouble(fld[2].trim());
          final double low = Double.parseDouble(fld[3].trim());
          final double close = Double.parseDouble(fld[5].trim());
          final long volume = Long.parseLong(fld[6].trim());

          final PriceData dd = new PriceData(dt, open, high, low, close, volume);

          ret.add(dd);
        }
      }
    }

    return ret;
  }

  /**
   *
   * @param prices
   * @return
   */
  public static double getLatestPrice(List<PriceData> prices) {
    try {
      return prices.get(prices.size() - 1).close;
    }
    catch (final Exception e) {
      return 0.0;
    }
  }

  public static PriceData queryDate(DateTime dt, List<PriceData> prices) {
    for (final PriceData d : prices) {
      if (d.date.isEqual(dt)) {
        return d;
      }
      else if (d.date.isGreaterThan(dt)) {
        return d;
      }
    }
    // Return last data point
    return prices.get(prices.size() - 1);
  }

  private static String getFilename(String index) {
    // TODO Auto-generated method stub
    return null;
  }

}
