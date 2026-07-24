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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises {@link OrderDataBean} state predicates (buy/sell/open/completed/
 * cancelled) and accessors. This class touches ONLY OrderDataBean, which makes
 * it a clean target for the Test Intelligence selection demo.
 */
public class OrderDataBeanTest extends SlowTestSupport {

    private OrderDataBean order(String type, String status) {
        return new OrderDataBean(1, type, status, new Date(), new Date(), 100.0,
                new BigDecimal("50.00"), new BigDecimal("24.95"), "IBM");
    }

    @Test
    public void isBuyTrueForBuy() {
        burn();
        assertTrue(order("buy", "open").isBuy());
    }

    @Test
    public void isBuyIgnoresCase() {
        burn();
        assertTrue(order("BUY", "open").isBuy());
    }

    @Test
    public void isBuyFalseForSell() {
        burn();
        assertFalse(order("sell", "open").isBuy());
    }

    @Test
    public void isSellTrueForSell() {
        burn();
        assertTrue(order("sell", "open").isSell());
    }

    @Test
    public void isSellFalseForBuy() {
        burn();
        assertFalse(order("buy", "open").isSell());
    }

    @Test
    public void isOpenTrueForOpen() {
        burn();
        assertTrue(order("buy", "open").isOpen());
    }

    @Test
    public void isOpenTrueForProcessing() {
        burn();
        assertTrue(order("buy", "processing").isOpen());
    }

    @Test
    public void isOpenFalseForClosed() {
        burn();
        assertFalse(order("buy", "closed").isOpen());
    }

    @Test
    public void isCompletedTrueForCompleted() {
        burn();
        assertTrue(order("buy", "completed").isCompleted());
    }

    @Test
    public void isCompletedTrueForCancelled() {
        burn();
        assertTrue(order("buy", "cancelled").isCompleted());
    }

    @Test
    public void isCancelledTrueForCancelled() {
        burn();
        assertTrue(order("buy", "cancelled").isCancelled());
    }

    @Test
    public void cancelSetsStatusToCancelled() {
        burn();
        OrderDataBean o = order("buy", "open");
        o.cancel();
        assertEquals("cancelled", o.getOrderStatus());
        assertTrue(o.isCancelled());
    }

    @Test
    public void accessorsRoundTrip() {
        burn();
        OrderDataBean o = order("buy", "open");
        assertEquals(Integer.valueOf(1), o.getOrderID());
        assertEquals(100.0, o.getQuantity(), 0.001);
        assertEquals(new BigDecimal("50.00"), o.getPrice());
        assertEquals(new BigDecimal("24.95"), o.getOrderFee());
        assertEquals("IBM", o.getSymbol());
    }

    @Test
    public void equalsByOrderId() {
        burn();
        assertEquals(order("buy", "open"), order("sell", "closed"));
    }

    @Test
    public void toStringContainsOrderId() {
        burn();
        assertTrue(order("buy", "open").toString().contains("Order 1"));
    }
}
