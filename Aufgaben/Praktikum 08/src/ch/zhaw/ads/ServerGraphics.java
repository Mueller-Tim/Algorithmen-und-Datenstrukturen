package ch.zhaw.ads;

import java.awt.Color;

public class ServerGraphics {

  private StringBuffer b;
  private static ServerGraphics theGraphics;

  public static ServerGraphics instance() {
    if (theGraphics == null) {
      theGraphics = new ServerGraphics();
    }
    return theGraphics;
  }

  public ServerGraphics() {
    clear();
    theGraphics = this;
  }

  public void clear() {
    b = new StringBuffer();
  }

  public String getTrace() {
    return new String(b);
  }

  private double round(double d) {
    return Math.round(d * 10000) / 10000.0;
  }

  public void drawLine(double x1, double y1, double x2, double y2) {
    b.append("<line x1=\"");
    b.append(round(x1));
    b.append("\" y1=\"");
    b.append(round(y1));
    b.append("\" x2=\"");
    b.append(round(x2));
    b.append("\" y2=\"");
    b.append(round(y2));
    b.append("\"/>\n");
  }

  public void drawRect(double x, double y, double w, double h) {
    b.append("<rect x=\"");
    b.append(round(x));
    b.append("\" y=\"");
    b.append(round(y));
    b.append("\" ");
    b.append("width=\"");
    b.append(round(w));
    b.append("\" height=\"");
    b.append(round(h));
    b.append("\" style=\"draw\" />\n");
  }

  public void fillRect(double x, double y, double w, double h) {
    b.append("<rect x=\"");
    b.append(round(x));
    b.append("\" y=\"");
    b.append(round(y));
    b.append("\" ");
    b.append("width=\"");
    b.append(round(w));
    b.append("\" height=\"");
    b.append(round(h));
    b.append("\" style=\"fill\" />\n");
  }

  public void setColor(Color c) {
    b.append("<color red=\"");
    b.append(c.getRed());
    b.append("\" green=\"");
    b.append(c.getGreen());
    b.append("\" blue=\"");
    b.append(c.getBlue());
    b.append("\"/>\n");
  }

  public void drawPath(String from, String to, boolean line) {
    double scale = 11;
    double xh0 = from.charAt(0) - '0';
    double yh0 = from.charAt(2) - '0';
    double xh1 = to.charAt(0) - '0';
    double yh1 = to.charAt(2) - '0';
    double x0 = Math.min(xh0, xh1) / scale;
    double y0 = Math.min(yh0, yh1) / scale;
    double x1 = Math.max(xh0, xh1) / scale;
    double y1 = Math.max(yh0, yh1) / scale;
    double w = 1 / scale;
    if (line) {
      drawLine(x0 + w / 2, y0 + w / 2, x1 + w / 2, y1 + w / 2);
    } else {
      if (Math.abs(y0 - y1) < 1E-10) {
        fillRect(x0, y0, x1 - x0 + w, w);
      } else {
        fillRect(x0, y0, w, y1 - y0 + w);
      }
    }
  }
}
