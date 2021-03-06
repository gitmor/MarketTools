package net.ajaskey.market.tools.options.workbench;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import net.ajaskey.common.Utils;

public class OptionStatistics {

  private long   putVol;
  private long   putOi;
  private long   callVol;
  private long   callOi;
  private double vdollarsPut;
  private double vdollarsCall;
  private double dollarsPut;
  private double dollarsCall;
  private double rdollarsPut;  // retail OI:Vol less than 200
  private double rdollarsCall; // retail OI:Vol less than 200
  private String header;

  private Option opt;

  /**
   *
   */
  public OptionStatistics() {
    this.putVol = 0L;
    this.putOi = 0L;
    this.callVol = 0L;
    this.callOi = 0L;
    this.vdollarsPut = 0.0;
    this.vdollarsCall = 0.0;
    this.dollarsPut = 0.0;
    this.dollarsCall = 0.0;
    this.rdollarsPut = 0.0;
    this.rdollarsCall = 0.0;
    this.header = "Combined Index Data :";
  }

  public OptionStatistics(Option optIn) {
    this.opt = optIn;
    this.putVol = 0L;
    this.putOi = 0L;
    this.callVol = 0L;
    this.callOi = 0L;
    this.vdollarsPut = 0.0;
    this.vdollarsCall = 0.0;
    this.dollarsPut = 0.0;
    this.dollarsCall = 0.0;
    this.header = optIn.sCode;
    this.calcStats();
  }

  /**
   *
   * @param option
   */
  public void addToStats(Option option) {

    this.header += String.format(" %s", option.sCode);
    for (final OptionData od : option.optList) {
      if (od.type.equalsIgnoreCase("PUT")) {
        this.putVol += od.volume;
        this.putOi += od.openInterest;
        final double vdp = od.lastPrice * (double) od.volume;
        final double dp = od.lastPrice * (double) od.openInterest;
        this.vdollarsPut += vdp;
        this.dollarsPut += dp;
        if (od.openInterest < OptionStatistics.retailOiLevel && od.volume < OptionStatistics.retailVolLevel) {
          final double rdp = od.lastPrice * (double) od.volume;
          this.rdollarsPut += rdp;
        }
      }
      else if (od.type.equalsIgnoreCase("CALL")) {
        this.callVol += od.volume;
        this.callOi += od.openInterest;
        final double vdc = od.lastPrice * (double) od.volume;
        final double dc = od.lastPrice * (double) od.openInterest;
        this.vdollarsCall += vdc;
        this.dollarsCall += dc;
        if (od.openInterest < OptionStatistics.retailOiLevel && od.volume < OptionStatistics.retailVolLevel) {
          final double rdp = od.lastPrice * (double) od.volume;
          this.rdollarsCall += rdp;
        }
      }
    }

  }

  public double getCallOi() {
    return this.callOi;
  }

  public double getCallVol() {
    return this.callVol;
  }

  public double getDollarsCall() {
    return this.dollarsCall;
  }

  public double getDollarsPut() {
    return this.dollarsPut;
  }

  public double getPutOi() {
    return this.putOi;
  }

  public double getPutVol() {
    return this.putVol;
  }

  @Override
  public String toString() {

    final int fldLen = 13;

    if (Utils.df == null) {
      Utils.df = new DecimalFormat("#,###,##0.00", Utils.decimalFormatSymbols);
    }

    String ret = String.format("%s%n", this.header.toUpperCase(), " ", "");

    ret += String.format("%n  Put Volume       : %s%n", Utils.lfmt(this.putVol, fldLen));
    ret += String.format("  Put OI           : %s%n", Utils.lfmt(this.putOi, fldLen));

    ret += String.format("%n  Call Volume      : %s%n", Utils.lfmt(this.callVol, fldLen));
    ret += String.format("  Call OI          : %s%n", Utils.lfmt(this.callOi, fldLen));

    ret += String.format("%n  P/C Volume       : %s%n", Utils.fmt((double) this.putVol / (double) this.callVol, fldLen));
    ret += String.format("  P/C OI           : %s%n", Utils.fmt((double) this.putOi / (double) this.callOi, fldLen));

    ret += String.format("%n  Vol Put Dollars  : %s%n", Utils.lfmt((long) this.vdollarsPut, fldLen));
    ret += String.format("  Vol Call Dollars : %s%n", Utils.lfmt((long) this.vdollarsCall, fldLen));
    final String vdollarPC = Utils.fmt(this.vdollarsPut / this.vdollarsCall, fldLen);
    ret += String.format("  Vol P/C Dollars  : %s%n", vdollarPC);

    ret += String.format("%n  Ret Put Dollars  : %s%n", Utils.lfmt((long) this.rdollarsPut, fldLen));
    ret += String.format("  Ret Call Dollars : %s%n", Utils.lfmt((long) this.rdollarsCall, fldLen));
    final String rdollarPC = Utils.fmt(this.rdollarsPut / this.rdollarsCall, fldLen);
    ret += String.format("  P/C Dollars      : %s%n", rdollarPC);

    ret += String.format("%n  OI Put Dollars   : %s%n", Utils.lfmt((long) this.dollarsPut, fldLen));
    ret += String.format("  OI Call Dollars  : %s%n", Utils.lfmt((long) this.dollarsCall, fldLen));
    final String dollarPC = Utils.fmt(this.dollarsPut / this.dollarsCall, fldLen);
    ret += String.format("  OI P/C Dollars   : %s%n", dollarPC);

    ret += String.format("%nRet = A guess at Retail%nVol = Today's Trading");

    return ret;
  }

  public String toSumString() {
    final int fldLen = 13;

    if (Utils.df == null) {
      Utils.df = new DecimalFormat("#,###,##0.00", Utils.decimalFormatSymbols);
    }

    String ret = String.format("%s -- %s -- %s%n", this.header.toUpperCase(), " ", " ");

    ret += String.format("%n  Put Volume       : %s%n", Utils.lfmt(this.putVol, fldLen));
    ret += String.format("  Put OI           : %s%n", Utils.lfmt(this.putOi, fldLen));

    ret += String.format("%n  Call Volume      : %s%n", Utils.lfmt(this.callVol, fldLen));
    ret += String.format("  Call OI          : %s%n", Utils.lfmt(this.callOi, fldLen));

    final double pc = (double) this.putVol / (double) this.callVol;
    final double pcoi = (double) this.putOi / (double) this.callOi;
    ret += String.format("%n  P/C              : %s%n", Utils.fmt(pc, fldLen));
    ret += String.format("  P/C OI           : %s%n", Utils.fmt(pcoi, fldLen));

    ret += String.format("%n  Vol Put Dollars  : %s%n", Utils.lfmt((long) this.vdollarsPut, fldLen));
    ret += String.format("  Vol Call Dollars : %s%n", Utils.lfmt((long) this.vdollarsCall, fldLen));
    final String vdollarPC = Utils.fmt(this.vdollarsPut / this.vdollarsCall, fldLen);
    ret += String.format("  Vol Dollar P/C   : %s%n", vdollarPC);

    ret += String.format("%n  Put Dollars      : %s%n", Utils.lfmt((long) this.dollarsPut, fldLen));
    ret += String.format("  Call Dollars     : %s%n", Utils.lfmt((long) this.dollarsCall, fldLen));
    final String dollarPC = Utils.fmt(this.dollarsPut / this.dollarsCall, fldLen);
    ret += String.format("  Dollar P/C       : %s%n", dollarPC);

    ret += String.format("%n  Ret Put Dollars  : %s%n", Utils.lfmt((long) this.rdollarsPut, fldLen));
    ret += String.format("  Ret Call Dollars : %s%n", Utils.lfmt((long) this.rdollarsCall, fldLen));
    final String rdollarPC = Utils.fmt(this.rdollarsPut / this.rdollarsCall, fldLen);
    ret += String.format("  P/C Dollars      : %s%n", rdollarPC);

    return ret;
  }

  /**
   *
   */
  private void calcStats() {

    for (final OptionData od : this.opt.optList) {
      if (od.type.equals("PUT")) {
        this.putVol += od.volume;
        this.putOi += od.openInterest;
      }
      else if (od.type.equals("CALL")) {
        this.callVol += od.volume;
        this.callOi += od.openInterest;
      }
    }

    for (final OptionData od : this.opt.optList) {
      if (od.type.equals("PUT")) {

        this.vdollarsPut += od.lastPrice * (double) od.volume;

        this.dollarsPut += od.lastPrice * (double) od.openInterest;

        if (od.openInterest <= OptionStatistics.retailOiLevel && od.volume <= OptionStatistics.retailVolLevel) {
          final double rdp = od.lastPrice * (double) od.volume;
          this.rdollarsPut += rdp;
        }

      }
      else if (od.type.equals("CALL")) {

        this.vdollarsCall += od.lastPrice * (double) od.volume;

        this.dollarsCall += od.lastPrice * (double) od.openInterest;

        if (od.openInterest <= OptionStatistics.retailOiLevel && od.volume <= OptionStatistics.retailVolLevel) {
          final double rdp = od.lastPrice * (double) od.volume;
          this.rdollarsCall += rdp;
        }

      }
    }
  }

  private static int retailVolLevel = 35;

  private static int retailOiLevel = 60;

  /**
   *
   * @param args
   * @throws FileNotFoundException
   */
  public static void main(String[] args) throws FileNotFoundException {

//    final Option opt = new Option();
//
//    opt.processJson("SPY");
//
//    final OptionStats os = new OptionStats(opt);
//    System.out.println(os);
  }
}
