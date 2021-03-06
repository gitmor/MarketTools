
package net.ajaskey.market.tools.quandl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import net.ajaskey.common.DateTime;
import net.ajaskey.common.Utils;
import net.ajaskey.market.optuma.OptumaCommon;
import net.ajaskey.market.optuma.PriceData;

/**
 * This class...
 *
 * @author ajask_000
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
public class ProcessEIA {

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

  private static DocumentBuilderFactory dbFactory = null;
  private static DocumentBuilder        dBuilder  = null;

  /**
   * net.ajaskey.market.tools.main
   *
   * @param args
   * @throws ParserConfigurationException
   */
  public static void main(final String[] args) throws ParserConfigurationException {

    final String apiKey = "5083132038aeb07288f19e6313b85532";

    // String outName = "C:/Users/ajask_000/Documents/Market Analyst 8/CSV
    // Data/EIA/.csv";
    final String gasURL = "http://api.eia.gov/series/?api_key=" + apiKey + "&series_id=PET.WGFUPUS2.W&out=xml";
    final String keroURL = "http://api.eia.gov/series/?api_key=" + apiKey + "&series_id=PET.WKJUPUS2.W&out=xml";

    ProcessEIA.dbFactory = DocumentBuilderFactory.newInstance();

    final List<PriceData> gas = ProcessEIA.getData(gasURL);
    ProcessEIA.writeList(gas, "gasoline_demand");

    final List<PriceData> kero = ProcessEIA.getData(keroURL);
    ProcessEIA.writeList(kero, "kerosene_demand");

  }

  private static List<PriceData> getData(final String url) {

    final List<PriceData> ret = new ArrayList<>();

    String resp;
    try {
      ProcessEIA.dBuilder = ProcessEIA.dbFactory.newDocumentBuilder();

      System.out.println("Processing : " + url);

      resp = Utils.getFromUrl(url);
      // System.out.println(resp);

      final Document doc = ProcessEIA.dBuilder.parse(new InputSource(new StringReader(resp)));

      doc.getDocumentElement().normalize();

      final NodeList nResp = doc.getElementsByTagName("row");
      for (int knt = 0; knt < nResp.getLength(); knt++) {
        final Node nodeResp = nResp.item(knt);
        if (nodeResp.getNodeType() == Node.ELEMENT_NODE) {
          final NodeList nrList = nodeResp.getChildNodes();
          // Calendar cal = null;
          DateTime dt = null;
          for (int cnt = 0; cnt < nrList.getLength(); cnt++) {
            final Node nr = nrList.item(cnt);
            if (nr.getNodeType() == Node.ELEMENT_NODE) {
              final String s = nr.getNodeName();
              if (s.contains("date")) {
                // System.out.println(nr.getNodeName() + " " + nr.getTextContent());
                final Date date = ProcessEIA.sdf.parse(nr.getTextContent().trim());
                // cal = Calendar.getInstance();
                // cal.setTime(date);
                dt = new DateTime(date);

              }
              else if (s.contains("value")) {
                // System.out.println(nr.getNodeName() + " " + nr.getTextContent());
                if (dt != null) {
                  final double c = Double.parseDouble(nr.getTextContent().trim());
                  final PriceData d = new PriceData(dt, c, c, c, c, 0);
                  dt = null;
                  ret.add(d);
                  // System.out.println(d.toShortString());
                }
              }
            }
          }
        }
      }

    }
    catch (final Exception e) {
      ret.clear();
      e.printStackTrace();
    }

    return ret;
  }

  private static void writeList(final List<PriceData> list, final String fname) {

    Collections.reverse(list);
    try (PrintWriter pw = new PrintWriter(OptumaCommon.optumaPath + "\\Quandl\\" + fname + ".csv")) {
      for (final PriceData price : list) {

        pw.printf("%s,%.2f%n", price.date.format("yyyy-MM-dd"), price.close);
      }
      System.out.println(list.get(list.size() - 1).date);

    }
    catch (final FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
