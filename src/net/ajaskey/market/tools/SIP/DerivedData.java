
package net.ajaskey.market.tools.SIP;

/**
 * This class...
 *
 * @author Andy
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
public class DerivedData {

  public double qoqGrowth;
  public double seqGrowth;

  public double yoyGrowth;

  public double ttm;

  private QuarterlyData qd;

  /**
   * This method serves as a constructor for the class.
   *
   */
  public DerivedData() {

    this.qoqGrowth = 0.0;
    this.seqGrowth = 0.0;
    this.yoyGrowth = 0.0;
    this.ttm = 0.0;
  }

  /**
   * net.ajaskey.market.tools.SIP.calculate
   *
   */
  public void calculate(final QuarterlyData qdata) {

    this.qd = qdata;
    this.setQoQGrowth();
    this.setSeqGrowth();
    this.setYoyGrowth();
    this.setTtmEps();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    String ret = DerivedData.TAB + DerivedData.TAB + "Derived ==>" + DerivedData.NL;
    ret += DerivedData.TAB + DerivedData.TAB + DerivedData.TAB + String.format("Seq Growth : %5.2f%%", this.seqGrowth) + DerivedData.NL;
    ret += DerivedData.TAB + DerivedData.TAB + DerivedData.TAB + String.format("Q/Q Growth : %15.2f%%", this.qoqGrowth) + DerivedData.NL;
    ret += DerivedData.TAB + DerivedData.TAB + DerivedData.TAB + String.format("Y/Y Growth : %15.2f%%", this.yoyGrowth) + DerivedData.NL;
    ret += DerivedData.TAB + DerivedData.TAB + DerivedData.TAB + "12m Total  : " + QuarterlyData.fmt(this.ttm) + " (avg= "
        + QuarterlyData.fmt(this.ttm / 4.0, 1) + ")" + DerivedData.NL;
    return ret;
  }

  /**
   * net.ajaskey.market.tools.SIP.setYoYGrowth
   *
   */
  private void setQoQGrowth() {

    double qtr1;
    double qtr5;
    if (this.qd.q1 != 0.0) {
      qtr1 = this.qd.q1;
      qtr5 = this.qd.q5;
    }
    else {
      qtr1 = this.qd.q2;
      qtr5 = this.qd.q6;
    }
    if (qtr5 != 0.0) {
      this.qoqGrowth = (qtr1 - qtr5) / Math.abs(qtr5) * 100.0;
    }
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.setSeqGrowth
   *
   */
  private void setSeqGrowth() {

    double qtr1;
    double qtr2;
    if (this.qd.q1 != 0.0) {
      qtr1 = this.qd.q1;
      qtr2 = this.qd.q2;
    }
    else {
      qtr1 = this.qd.q2;
      qtr2 = this.qd.q3;
    }
    if (qtr2 != 0.0) {
      this.seqGrowth = (qtr1 - qtr2) / Math.abs(qtr2) * 100.0;
    }
  }

  /**
   * net.ajaskey.market.tools.SIP.setTtmEps
   *
   */
  private void setTtmEps() {

    final double e1 = this.qd.getTtm();
    this.ttm = e1;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.setYoyGrowth
   *
   */
  private void setYoyGrowth() {

    final double yr1 = this.qd.q1 + this.qd.q2 + this.qd.q3 + this.qd.q4;
    final double yr2 = this.qd.q5 + this.qd.q6 + this.qd.q7 + this.qd.q8;

    if (yr2 != 0.0) {
      this.yoyGrowth = (yr1 - yr2) / Math.abs(yr2) * 100.0;
    }
  }

  final private static String NL = "\n";

  final private static String TAB = "\t";

  final private static double MAX_PE = 275.0;

  /**
   * net.ajaskey.market.tools.SIP.calc52weekHigh
   *
   * @param cd
   * @return
   */
  public static double calc52weekHighPercent(final CompanyData cd) {

    final double d = (cd.high52wk - cd.lastPrice) / cd.high52wk * 100.0;
    return d;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcCurrAssets
   *
   * @param cd
   * @return
   */
  public static double calcCurrAssets(final CompanyData cd) {

    final double ca = cd.bsd.cash.getMostRecent() + cd.bsd.acctReceiveable.getMostRecent() + cd.bsd.stInvestments.getMostRecent()
        + cd.bsd.inventory.getMostRecent() + cd.bsd.otherAssets.getMostRecent();
    return ca;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcCurrentRatio
   *
   * @param cd
   * @return
   */
  public static double calcCurrentRatio(final CompanyData cd) {

    double cr = 0.0;
    if (cd.bsd.currLiab.getMostRecent() > 0.0) {
      cr = cd.bsd.currentAssets.getMostRecent() / cd.bsd.currLiab.getMostRecent();
    }
    else if (cd.sumCurrLiab > 0.0) {
      cr = cd.sumCurrAssets / cd.sumCurrLiab;
    }
    return cr;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcCurrLiabilities
   *
   * @param cd
   * @return
   */
  public static double calcCurrLiabilities(final CompanyData cd) {

    final double cl = cd.bsd.acctPayable.getMostRecent() + cd.bsd.stDebt.getMostRecent() + cd.bsd.otherCurrLiab.getMostRecent();
    return cl;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcDebtToCash
   *
   * @param bsd
   * @return
   */
  public static double calcDebtToCash(final BalanceSheetData bsd) {

    double ret = 0.0;
    final double c = bsd.cash.getMostRecent();
    if (c > 0.0) {
      ret = (bsd.ltDebt.getMostRecent() + bsd.stDebt.getMostRecent()) / c;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcDebtToEquity
   *
   * @param bsd
   * @return
   */
  public static double calcDebtToEquity(final BalanceSheetData bsd) {

    double ret = 0.0;
    final double e = bsd.equity.getMostRecent();
    if (e > 0.0) {
      ret = bsd.ltDebt.getMostRecent() / e;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcDividendYield
   *
   * @param id
   * @param lPrice
   * @return
   */
  public static double calcDividendYield(final CompanyData cd) {

    double ret = 0.0;
    if (cd.lastPrice > 0.0) {
      final double d1 = cd.id.dividend.getTtm();
      ret = d1 / cd.lastPrice * 100.0;
    }
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcEarningsYield
   *
   * @param id
   * @param lPrice
   * @return
   */
  public static double calcEarningsYield(final CompanyData cd) {

    double ret = 0.0;
    if (cd.lastPrice > 0.0) {
      final double e1 = cd.id.epsDilCont.getTtm();
      ret = e1 / cd.lastPrice * 100.0;
    }
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcFreeCashFlow
   *
   * @param cd
   * @return
   */
  public static double calcFreeCashFlow(final CompanyData cd) {

    final double ret = cd.cashData.cashFromOps.getTtm() - cd.cashData.capEx.getTtm() - cd.id.dividend.getTtm() * cd.shares.getTtmAvg();
    return ret;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcInterestRate
   *
   * @param id
   * @return
   */
  public static double calcInterestRate(final IncomeData id) {

    double ret = 0.0;
    final double i1 = id.totalInterest.getTtm();
    final double s1 = id.sales.getTtm();
    if (s1 > 0.0) {
      ret = i1 / s1 * 100.0;
    }
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcDebtToCash
   *
   * @param cd
   * @return
   */
  public static double calcMarketCap(final CompanyData cd) {

    final double ret = cd.lastPrice * cd.shares.getTtmAvg();
    return ret;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcNetCashFlow
   *
   * @param cd
   * @return
   */
  public static double calcNetCashFlow(final CompanyData cd) {

    final double ncf = cd.cashData.cashFromOps.getTtm() + cd.cashData.cashFromFin.getTtm();
    return ncf;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcNetMargin
   *
   * @param id
   * @return
   */
  public static double calcNetMargin(final IncomeData id) {

    double ret = 0.0;
    final double n12 = id.netIncome.getTtm();
    final double s12 = id.sales.getTtm();
    if (s12 > 0.0) {
      ret = n12 / s12 * 100.0;
    }
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcOpMargin
   *
   * @param id
   * @return
   */
  public static double calcOpMargin(final IncomeData id) {

    double ret = 0.0;
    final double g12 = id.grossOpIncome.getTtm();
    final double s12 = id.sales.getTtm();
    if (s12 > 0.0) {
      ret = g12 / s12 * 100.0;
    }
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcPE
   *
   * @param id
   * @param price
   * @return
   */
  public static double calcPE(final IncomeData id, final double price) {

    double ret = DerivedData.MAX_PE;
    final double e12 = id.epsDilCont.getTtm();
    if (e12 > 0.0) {
      ret = price / e12;
    }
    ret = Math.min(DerivedData.MAX_PE, ret);
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcPSales
   *
   * @param id
   * @param lPrice
   * @return
   */
  public static double calcPSales(final CompanyData cd) {

    double ret = DerivedData.MAX_PE;
    final double s12 = cd.id.sales.getTtm();
    if (s12 > 0.0) {
      ret = cd.lastPrice / s12;
    }
    ret = Math.min(DerivedData.MAX_PE, ret);
    return ret;

  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcRoe
   *
   * @param cd
   * @return
   */
  public static double calcRoe(final CompanyData cd) {

    double ret = 0.0;
    final double n1 = cd.id.netIncome.getTtm();
    final double e1 = cd.bsd.equity.getMostRecent();
    if (e1 > 0.0) {
      ret = n1 / e1 * 100.0;
    }
    return ret;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcShareChange
   *
   * @param cd
   * @return
   */
  public static double calcShareChange(final CompanyData cd) {

    double sc = 0.0;
    if (cd.shares.q1 > 0.0) {
      sc = cd.shares.q1 - cd.shares.q5;
    }
    else {
      sc = cd.shares.q2 - cd.shares.q6;
    }
    return sc;
  }

  public static double calcStDebtToOpIncome(final CompanyData cd) {

    double ret = 0.0;
    final double g = cd.id.grossOpIncome.getTtm();
    if (g != 0.0) {
      ret = cd.bsd.stDebt.q1 / g;
    }
    return ret;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcTaxRate
   *
   * @param id
   * @return
   */
  public static double calcTaxRate(final IncomeData id) {

    double ret = 0.0;
    double t1 = id.incomeTax.q1;
    double s1 = id.sales.q1;
    if (t1 == 0.0) {
      t1 = id.incomeTax.q2;
      s1 = id.sales.q2;
    }
    if (s1 > 0.0) {
      ret = t1 / s1 * 100.0;
    }
    return ret;

  }

  /**
   * net.ajaskey.market.tools.SIP.calcTotalCashFlow
   *
   * @param cd
   * @return
   */
  public static double calcTotalCashFlow(final CompanyData cd) {

    final double tcf = cd.cashData.cashFromOps.getTtm() + cd.cashData.cashFromFin.getTtm() + cd.cashData.cashFromInv.getTtm();
    return tcf;
  }

  /**
   * net.ajaskey.market.tools.SIP.calcWorkingCapital
   *
   * @param cd
   * @return
   */
  public static double calcWorkingCapital(final CompanyData cd) {

    double wc = 0.0;
    if (cd.bsd.currentAssets.getMostRecent() > 0.0) {
      wc = cd.bsd.currentAssets.getMostRecent() - cd.bsd.currLiab.getMostRecent();
    }
    else {
      wc = cd.sumCurrAssets - cd.sumCurrLiab;
    }

    return wc;
  }

  /**
   *
   * net.ajaskey.market.tools.SIP.calcWorkingCashFlow
   *
   * @param cd
   * @return
   */
  public static double calcWorkingCashFlow(final CompanyData cd) {

    final double ret = cd.id.pretaxIncome.getTtm() - cd.cashData.capEx.getTtm() - cd.id.dividend.getTtm() * cd.shares.getTtmAvg();
    return ret;
  }

}
