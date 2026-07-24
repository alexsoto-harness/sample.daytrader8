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

import org.junit.Before;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises {@link QuoteDataBean} accessors, constructors and equality.
 */
public class QuoteDataBeanTest extends SlowTestSupport {

    private QuoteDataBean quote;

    @Before
    public void setUp() {
        quote = new QuoteDataBean("IBM", "IBM Corp", 1000.0, new BigDecimal("120.00"),
                new BigDecimal("118.00"), new BigDecimal("115.00"), new BigDecimal("122.00"), 2.0);
    }

    @Test
    public void constructorSetsSymbol() {
        burn();
        assertEquals("IBM", quote.getSymbol());
    }

    @Test
    public void constructorSetsCompanyName() {
        burn();
        assertEquals("IBM Corp", quote.getCompanyName());
    }

    @Test
    public void constructorSetsPrice() {
        burn();
        assertEquals(new BigDecimal("120.00"), quote.getPrice());
    }

    @Test
    public void constructorSetsOpen() {
        burn();
        assertEquals(new BigDecimal("118.00"), quote.getOpen());
    }

    @Test
    public void constructorSetsLow() {
        burn();
        assertEquals(new BigDecimal("115.00"), quote.getLow());
    }

    @Test
    public void constructorSetsHigh() {
        burn();
        assertEquals(new BigDecimal("122.00"), quote.getHigh());
    }

    @Test
    public void constructorSetsVolume() {
        burn();
        assertEquals(1000.0, quote.getVolume(), 0.001);
    }

    @Test
    public void constructorSetsChange() {
        burn();
        assertEquals(2.0, quote.getChange(), 0.001);
    }

    @Test
    public void settersRoundTrip() {
        burn();
        quote.setPrice(new BigDecimal("99.99"));
        quote.setChange(-3.5);
        assertEquals(new BigDecimal("99.99"), quote.getPrice());
        assertEquals(-3.5, quote.getChange(), 0.001);
    }

    @Test
    public void symbolOnlyConstructor() {
        burn();
        QuoteDataBean zero = new QuoteDataBean("AAPL");
        assertEquals("AAPL", zero.getSymbol());
    }

    @Test
    public void equalsBySymbol() {
        burn();
        QuoteDataBean other = new QuoteDataBean("IBM");
        assertEquals(quote, other);
    }

    @Test
    public void notEqualsForDifferentSymbol() {
        burn();
        assertFalse(quote.equals(new QuoteDataBean("MSFT")));
    }

    @Test
    public void hashCodeConsistentWithSymbol() {
        burn();
        assertEquals(new QuoteDataBean("IBM").hashCode(), quote.hashCode());
    }

    @Test
    public void toStringContainsSymbol() {
        burn();
        assertTrue(quote.toString().contains("IBM"));
    }

    @Test
    public void toHTMLContainsSymbol() {
        burn();
        assertTrue(quote.toHTML().contains("IBM"));
    }

    @Test
    public void toHTMLNotNull() {
        burn();
        assertNotNull(quote.toHTML());
    }
}
