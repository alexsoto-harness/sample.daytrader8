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
package com.ibm.websphere.samples.daytrader.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;
import com.ibm.websphere.samples.daytrader.entities.QuoteDataBean;

/**
 * Exercises {@link MarketSummaryDataBean}, including the cached gain-percent
 * computation delegated to FinancialUtils.
 */
public class MarketSummaryDataBeanTest extends SlowTestSupport {

    private MarketSummaryDataBean summary;

    private static QuoteDataBean quote(String sym, double change) {
        return new QuoteDataBean(sym, sym + " Inc", 100.0, new BigDecimal("10.00"),
                new BigDecimal("9.00"), new BigDecimal("8.00"), new BigDecimal("11.00"), change);
    }

    @Before
    public void setUp() {
        Collection<QuoteDataBean> gainers = new ArrayList<QuoteDataBean>();
        Collection<QuoteDataBean> losers = new ArrayList<QuoteDataBean>();
        gainers.add(quote("IBM", 2.0));
        losers.add(quote("MSFT", -2.0));
        summary = new MarketSummaryDataBean(new BigDecimal("150.00"), new BigDecimal("100.00"),
                50000.0, gainers, losers);
    }

    @Test
    public void constructorSetsTSIA() {
        burn();
        assertEquals(new BigDecimal("150.00"), summary.getTSIA());
    }

    @Test
    public void constructorSetsOpenTSIA() {
        burn();
        assertEquals(new BigDecimal("100.00"), summary.getOpenTSIA());
    }

    @Test
    public void constructorSetsVolume() {
        burn();
        assertEquals(50000.0, summary.getVolume(), 0.001);
    }

    @Test
    public void constructorSetsSummaryDate() {
        burn();
        assertNotNull(summary.getSummaryDate());
    }

    @Test
    public void gainPercentPositiveWhenTSIAAboveOpen() {
        burn();
        assertTrue(summary.getGainPercent().doubleValue() > 0.0);
    }

    @Test
    public void gainPercentNegativeWhenTSIABelowOpen() {
        burn();
        MarketSummaryDataBean down = new MarketSummaryDataBean(new BigDecimal("80.00"),
                new BigDecimal("100.00"), 1.0, new ArrayList<QuoteDataBean>(), new ArrayList<QuoteDataBean>());
        assertTrue(down.getGainPercent().doubleValue() < 0.0);
    }

    @Test
    public void gainPercentIsCached() {
        burn();
        BigDecimal first = summary.getGainPercent();
        BigDecimal second = summary.getGainPercent();
        assertEquals(first, second);
    }

    @Test
    public void topGainersRoundTrip() {
        burn();
        assertEquals(1, summary.getTopGainers().size());
    }

    @Test
    public void topLosersRoundTrip() {
        burn();
        assertEquals(1, summary.getTopLosers().size());
    }

    @Test
    public void volumeSetterRoundTrip() {
        burn();
        summary.setVolume(12345.0);
        assertEquals(12345.0, summary.getVolume(), 0.001);
    }

    @Test
    public void tsiaSetterRoundTrip() {
        burn();
        summary.setTSIA(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("200.00"), summary.getTSIA());
    }

    @Test
    public void toStringContainsTSIALabel() {
        burn();
        assertTrue(summary.toString().contains("TSIA"));
    }

    @Test
    public void toHTMLNotNull() {
        burn();
        assertNotNull(summary.toHTML());
    }
}
