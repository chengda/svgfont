package org.dingle.tools.svgfont;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FontParserTest {
    @Test
    public void testOne() {
        try (InputStream fontInput = getClass().getResourceAsStream("/iconfont.svg")) {
            File outputDir = new File("./svgoutput");
            FontParser.parse(fontInput, outputDir);
            Assert.assertEquals(outputDir.list().length, 1);
            File fontFamilyOuputDir = new File(outputDir, "joys-icon");
            Assert.assertEquals(fontFamilyOuputDir.list().length, 3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void clean() {
        File outputDir = new File("./svgoutput");
        File fontFamilyOuputDir = new File(outputDir, "joys-icon");
        for (File file : fontFamilyOuputDir.listFiles()) {
            file.delete();
        }
        for (File file : outputDir.listFiles()) {
            file.delete();
        }
        outputDir.delete();
    }
}
