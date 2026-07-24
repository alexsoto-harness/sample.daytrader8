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
package com.ibm.websphere.samples.daytrader.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises {@link HoldingDataBean} accessors, the quote relationship and
 * equality semantics.
 */
public class HoldingDataBeanTest extends SlowTestSupport {

    private HoldingDataBean holding;
    private Date purchaseDate;

    @Before
    public void setUp() {
        purchaseDate = new Date();
        holding = new HoldingDataBean(42, 100.0, new BigDecimal("25.00"), purchaseDate, "IBM");
    }

    @Test
    public void constructorSetsHoldingID() {
        burn();
        assertEquals(Integer.valueOf(42), holding.getHoldingID());
    }

    @Test
    public void constructorSetsQuantity() {
        burn();
        assertEquals(100.0, holding.getQuantity(), 0.001);
    }

    @Test
    public void constructorSetsPurchasePrice() {
        burn();
        assertEquals(new BigDecimal("25.00"), holding.getPurchasePrice());
    }

    @Test
    public void constructorSetsPurchaseDate() {
        burn();
        assertEquals(purchaseDate, holding.getPurchaseDate());
    }

    @Test
    public void quoteIDFallsBackToTransientField() {
        burn();
        assertEquals("IBM", holding.getQuoteID());
    }

    @Test
    public void quoteIDPrefersAssociatedQuote() {
        burn();
        holding.setQuote(new QuoteDataBean("AAPL"));
        assertEquals("AAPL", holding.getQuoteID());
    }

    @Test
    public void quantitySetterRoundTrip() {
        burn();
        holding.setQuantity(250.0);
        assertEquals(250.0, holding.getQuantity(), 0.001);
    }

    @Test
    public void purchasePriceSetterRoundTrip() {
        burn();
        holding.setPurchasePrice(new BigDecimal("33.33"));
        assertEquals(new BigDecimal("33.33"), holding.getPurchasePrice());
    }

    @Test
    public void quoteAssociationRoundTrip() {
        burn();
        QuoteDataBean q = new QuoteDataBean("MSFT");
        holding.setQuote(q);
        assertEquals(q, holding.getQuote());
    }

    @Test
    public void equalsByHoldingID() {
        burn();
        HoldingDataBean other = new HoldingDataBean(42, 1.0, BigDecimal.ONE, new Date(), "MSFT");
        assertEquals(holding, other);
    }

    @Test
    public void notEqualsForDifferentHoldingID() {
        burn();
        HoldingDataBean other = new HoldingDataBean(99, 1.0, BigDecimal.ONE, new Date(), "IBM");
        assertFalse(holding.equals(other));
    }

    @Test
    public void hashCodeConsistentWithHoldingID() {
        burn();
        HoldingDataBean other = new HoldingDataBean(42, 1.0, BigDecimal.ONE, new Date(), "IBM");
        assertEquals(other.hashCode(), holding.hashCode());
    }

    @Test
    public void toStringContainsHoldingID() {
        burn();
        assertTrue(holding.toString().contains("42"));
    }

    @Test
    public void toHTMLNotNull() {
        burn();
        assertNotNull(holding.toHTML());
    }
}
