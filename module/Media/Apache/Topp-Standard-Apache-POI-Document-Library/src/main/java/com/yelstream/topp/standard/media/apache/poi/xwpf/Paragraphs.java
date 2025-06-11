package com.yelstream.topp.standard.media.apache.poi.xwpf;

import lombok.experimental.UtilityClass;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;

import java.math.BigInteger;

/**
 * Utility addressing instances of {@link XWPFParagraph}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2019-10-27
 */
@UtilityClass
public class Paragraphs {

    public static XWPFRun createRun(XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        return run;
    }

    public static XWPFRun createRun(XWPFParagraph paragraph,
                                    String language,
                                    String text) {
        XWPFRun run = paragraph.createRun();
        run.setLang(language);
        run.setText(text);
        return run;
    }

    public static XWPFRun createRun(XWPFParagraph paragraph,
                                    String language) {
        XWPFRun run = paragraph.createRun();
        run.setLang(language);
        return run;
    }

    public static XWPFRun addBreak(XWPFParagraph paragraph) {
        XWPFRun run = Paragraphs.createRun(paragraph);
        run.addBreak();
        return run;
    }

    public static XWPFRun addNewPgNum(XWPFParagraph paragraph,
                                      String language) {
        XWPFRun run = Paragraphs.createRun(paragraph,language);
        run.getCTR().addNewPgNum();
        return run;
    }

    public static void setParagraphSpacing(XWPFParagraph paragraph) {
        CTPPr ppr=paragraph.getCTP().addNewPPr();
        CTSpacing spacing=ppr.addNewSpacing();
        spacing.setBefore(BigInteger.valueOf(0)); // No space before
        spacing.setAfter(BigInteger.valueOf(0));  // No space after
//    spacing.setLineRule(STLineSpacingRule.AUTO);
//    spacing.setLine(BigInteger.valueOf(200)); // Approx 10pt line spacing
    }
}
