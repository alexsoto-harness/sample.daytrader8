/**
 * (C) Copyright IBM Corporation 2015.
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
package com.ibm.websphere.samples.daytrader.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;
import com.ibm.websphere.samples.daytrader.entities.HoldingDataBean;

/**
 * Exercises {@link FinancialUtils}: gain math, holdings totals and HTML
 * rendering helpers.
 */
public class FinancialUtilsTest extends SlowTestSupport {

    private static BigDecimal bd(double v) {
        return new BigDecimal(v).setScale(FinancialUtils.SCALE, FinancialUtils.ROUND);
    }

    @Test
    public void computeGainIsCurrentMinusOpen() {
        burn();
        assertEquals(bd(50.00), FinancialUtils.computeGain(bd(150.00), bd(100.00)));
    }

    @Test
    public void computeGainCanBeNegative() {
        burn();
        assertEquals(bd(-25.00), FinancialUtils.computeGain(bd(75.00), bd(100.00)));
    }

    @Test
    public void computeGainZeroWhenEqual() {
        burn();
        assertEquals(bd(0.00), FinancialUtils.computeGain(bd(100.00), bd(100.00)));
    }

    @Test
    public void computeGainPercentReturnsZeroForZeroOpen() {
        burn();
        assertEquals(FinancialUtils.ZERO, FinancialUtils.computeGainPercent(bd(100.00), bd(0.00)));
    }

    @Test
    public void computeGainPercentPositive() {
        burn();
        BigDecimal pct = FinancialUtils.computeGainPercent(bd(200.00), bd(100.00));
        assertTrue(pct.doubleValue() > 0.0);
    }

    @Test
    public void computeGainPercentNegative() {
        burn();
        BigDecimal pct = FinancialUtils.computeGainPercent(bd(50.00), bd(100.00));
        assertTrue(pct.doubleValue() < 0.0);
    }

    @Test
    public void computeHoldingsTotalNullIsZero() {
        burn();
        assertEquals(bd(0.00), FinancialUtils.computeHoldingsTotal(null));
    }

    @Test
    public void computeHoldingsTotalEmptyIsZero() {
        burn();
        assertEquals(bd(0.00), FinancialUtils.computeHoldingsTotal(new ArrayList<HoldingDataBean>()));
    }

    @Test
    public void computeHoldingsTotalSumsQuantityTimesPrice() {
        burn();
        Collection<HoldingDataBean> holdings = new ArrayList<HoldingDataBean>();
        HoldingDataBean h1 = new HoldingDataBean();
        h1.setQuantity(10);
        h1.setPurchasePrice(bd(20.00));
        HoldingDataBean h2 = new HoldingDataBean();
        h2.setQuantity(5);
        h2.setPurchasePrice(bd(40.00));
        holdings.add(h1);
        holdings.add(h2);
        assertEquals(bd(400.00), FinancialUtils.computeHoldingsTotal(holdings));
    }

    @Test
    public void printGainHTMLUpArrowForPositive() {
        burn();
        assertTrue(FinancialUtils.printGainHTML(bd(10.00)).contains("arrowup.gif"));
    }

    @Test
    public void printGainHTMLDownArrowForNegative() {
        burn();
        assertTrue(FinancialUtils.printGainHTML(bd(-10.00)).contains("arrowdown.gif"));
    }

    @Test
    public void printChangeHTMLUpArrowForPositive() {
        burn();
        assertTrue(FinancialUtils.printChangeHTML(12.5).contains("arrowup.gif"));
    }

    @Test
    public void printChangeHTMLDownArrowForNegative() {
        burn();
        assertTrue(FinancialUtils.printChangeHTML(-12.5).contains("arrowdown.gif"));
    }

    @Test
    public void printGainPercentHTMLContainsPercent() {
        burn();
        assertTrue(FinancialUtils.printGainPercentHTML(bd(5.00)).contains("%"));
    }

    @Test
    public void printQuoteLinkContainsSymbol() {
        burn();
        assertTrue(FinancialUtils.printQuoteLink("IBM").contains("IBM"));
    }
}
