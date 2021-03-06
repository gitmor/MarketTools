
package net.ajaskey.market.tools.fred;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.ajaskey.common.DateTime;

/**
 * This class...
 *
 * @author ajask
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
public class DateValue {

  public DateTime date;

  public double  value;
  public boolean valid;

  /**
   * This method serves as a constructor for the class.
   *
   * @param dv
   */
  public DateValue(final DateValue dv) {

    this.date = dv.date;
    this.value = dv.value;
    this.valid = true;
  }

  /**
   * This method serves as a constructor for the class.
   *
   */
  public DateValue(final String str, final int fptr) {

    this.valid = false;
    try {
      if (str.trim().length() > 10) {
        final String fld[] = str.trim().split(",");
        if (fld.length > 1) {
          final Date dat = DateValue.sdf.parse(fld[0].trim());
          this.date = new DateTime(dat);
          final String field = fld[fptr].replaceAll("\"", "").replaceAll(",", "").trim();
          this.value = Double.parseDouble(field);
          this.valid = true;
        }
      }
    }
    catch (final Exception e) {
      this.date = null;
      this.value = 0.0;
      this.valid = false;
    }
  }

  @Override
  public String toString() {

    final String ret = String.format("%s\t%.2f", this.date, this.value);
    return ret;
  }

  public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
}
