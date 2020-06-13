package net.ajaskey.market.tools.options.workbench;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import net.ajaskey.common.TextUtils;
import net.ajaskey.common.Utils;

public class OptionUtils {

  public static void getDownloads() throws IOException {

    Utils.makeDir("data");
    Utils.makeDir("data/options");

    final String ext[] = { "dat" };
    final List<File> files = Utils.getDirTree("C:\\Users\\Computer\\Downloads", ext);

    for (final File f : files) {

      if (f.getName().contains("quotedata")) {

        final List<String> data = TextUtils.readTextFile(f, true);

        String code = "";
        String s = "";
        if (data.size() > 0) {
          s = data.get(0);
          if (s.length() > 10) {
            s = s.replace("^", "");
            final String fld[] = s.split(",");
            final int idx = s.indexOf(' ');
            code = fld[0].substring(0, idx).trim().toLowerCase();
          }
        }
        if (data.size() < 10) {
          System.out.println("No Option Data Available!");
          final Path p = f.toPath();
          Files.deleteIfExists(p);
          return;
        }

        // Clear extra commas in specific data sets
        String modFirstLine = s;
        if (s.contains("PowerShares QQQ Trust,")) {
          modFirstLine = s.replace("PowerShares QQQ Trust,", "PowerShares QQQ Trust");
        }

        final String newFilename = String.format("%s-options.dat", code);
        final String ffname = String.format("D:\\dev\\Eclipse-03-2002\\workspace\\OptionsWorkbench\\data\\options\\%s", newFilename);
        try (PrintWriter pw = new PrintWriter(ffname)) {
          pw.println(modFirstLine);
          for (int i = 1; i < data.size(); i++) {
            pw.println(data.get(i));
          }
        }

        final Path p = f.toPath();
        Files.deleteIfExists(p);
        System.out.println(f.getName() + "\t" + code + "\t" + newFilename);

      }
    }
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
