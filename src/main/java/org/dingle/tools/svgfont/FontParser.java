package org.dingle.tools.svgfont;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;


public class FontParser {

    public static void parse(InputStream fontInput, File outputDir) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(fontInput);
            NodeList fontList = doc.getElementsByTagName("font");
            for (int i = 0; i < fontList.getLength(); i++) {
                Element font = (Element) fontList.item(i);
                int horizAdvX = Integer.parseInt(font.getAttribute("horiz-adv-x"));
                Element fontFace = (Element) font.getElementsByTagName("font-face").item(0);
                String fontFamily = fontFace.getAttribute("font-family");
                int unitsPerEm = Integer.parseInt(fontFace.getAttribute("units-per-em"));
                int ascent = Integer.parseInt(fontFace.getAttribute("ascent"));
                int descent = Integer.parseInt(fontFace.getAttribute("descent"));

                File fontSvgDir = new File(outputDir, fontFamily);
                fontSvgDir.mkdirs();
                NodeList glyphList = font.getElementsByTagName("glyph");
                for (int j = 0; j < glyphList.getLength(); j++) {
                    Element glyph = (Element) glyphList.item(j);
                    int glyphHorizAdvX = glyph.getAttribute("horiz-adv-x") == null ? horizAdvX : Integer.parseInt(glyph.getAttribute("horiz-adv-x"));
                    String glyphName = glyph.getAttribute("glyph-name");
                    String svgFileName = glyphName + ".svg";
                    File svgFile = new File(fontSvgDir, svgFileName);
                    String glyphContent = glyph.getAttribute("d");
                    String svgFileContent = "<?xml version=\"1.0\" standalone=\"no\"?>"
                            + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">"
                            + String.format("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" viewBox=\"0 0 %d %d\" width=\"%d\" height=\"%d\">", glyphHorizAdvX, glyphHorizAdvX, glyphHorizAdvX, glyphHorizAdvX)
                            + String.format("<path transform=\"translate(0,%d) scale(1,-1)\" d=\"%s\"/></svg>", ascent, glyphContent);
                    try (FileOutputStream fos = new FileOutputStream(svgFile)) {
                        fos.write(svgFileContent.getBytes());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void parse(File fontFile, File outputDir) {
        try (InputStream fontInput = new FileInputStream(fontFile)) {
            parse(fontInput, outputDir);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
