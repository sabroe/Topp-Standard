/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.media.apache.poi.xwpf;

import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.math.BigInteger;

/**
 * Document margin.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-07
 */
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder")
public class Margin {
    private final BigInteger top;
    private final BigInteger bottom;
    private final BigInteger left;
    private final BigInteger right;

    public void apply(XWPFDocument document) {
        CTSectPr sectPr=document.getDocument().getBody().addNewSectPr();
        CTPageMar pageMargin=sectPr.addNewPgMar();
        apply(pageMargin);
    }

    public void apply(CTPageMar pageMargin) {
        pageMargin.setTop(top);
        pageMargin.setBottom(bottom);
        pageMargin.setLeft(left);
        pageMargin.setRight(right);
    }

    public static Margin of(long top,
                            long bottom,
                            long left,
                            long right) {
        return new Margin(BigInteger.valueOf(top),
                BigInteger.valueOf(bottom),
                BigInteger.valueOf(left),
                BigInteger.valueOf(right));
    }
}
