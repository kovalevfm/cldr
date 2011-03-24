package org.unicode.cldr.unittest;

import org.unicode.cldr.test.ExampleGenerator;
import org.unicode.cldr.test.ExampleGenerator.Zoomed;
import org.unicode.cldr.unittest.TestAll.TestInfo;
import org.unicode.cldr.util.CLDRFile;
import org.unicode.cldr.util.CldrUtility;

import com.ibm.icu.dev.test.TestFmwk;

public class TestExampleGenerator extends TestFmwk {
    TestInfo info = TestAll.TestInfo.getInstance();

    public static void main(String[] args) {
        new TestExampleGenerator().run(args);
    }

    public void TestPaths() {
        showCldrFile(info.getEnglish());
    }
    
    private void showCldrFile(final CLDRFile cldrFile) {
        ExampleGenerator exampleGenerator = new ExampleGenerator(cldrFile, CldrUtility.DEFAULT_SUPPLEMENTAL_DIRECTORY);
        for (String xpath : cldrFile) {
            String value = cldrFile.getStringValue(xpath);
            checkPathValue(exampleGenerator, xpath, value);
            if (xpath.contains("count=\"one\"")) {
                String xpath2 = xpath.replace("count=\"one\"", "count=\"1\"");
                checkPathValue(exampleGenerator, xpath2, value);
            }
        }
    }

    private void checkPathValue(ExampleGenerator exampleGenerator, String xpath, String value) {
        for (Zoomed zoomed : Zoomed.values()) {
            try {
                String text = exampleGenerator.getExampleHtml(xpath, value, zoomed);
                if (text != null && text.contains("Exception")) {
                    errln("getExampleHtml\t" + zoomed + "\t" + text);
                }
            } catch (Exception e) {
                errln("getExampleHtml\t" + zoomed + "\t" + e.getMessage());
            }
        }
        try {
            String text = exampleGenerator.getHelpHtml(xpath, value);
            if (text != null && text.contains("Exception")) {
                errln("getHelpHtml\t" + text);
            }
        } catch (Exception e) {
            errln("getHelpHtml\t" + e.getMessage());
        }
    }
}
