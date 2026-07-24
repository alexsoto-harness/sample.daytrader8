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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises the pure random-data generators on {@link TradeConfig}, validating
 * their value ranges and formats.
 */
public class TradeConfigTest extends SlowTestSupport {

    @Test
    public void rndIntWithinBound() {
        burn();
        for (int i = 0; i < 50; i++) {
            int v = TradeConfig.rndInt(100);
            assertTrue("value=" + v, v >= 0 && v < 100);
        }
    }

    @Test
    public void rndFloatWithinBound() {
        burn();
        for (int i = 0; i < 50; i++) {
            float v = TradeConfig.rndFloat(100);
            assertTrue("value=" + v, v >= 0.0f && v <= 100.0f);
        }
    }

    @Test
    public void rndBigDecimalHasScaleTwo() {
        burn();
        BigDecimal v = TradeConfig.rndBigDecimal(1000.0f);
        assertEquals(2, v.scale());
    }

    @Test
    public void rndBooleanReturnsBothValuesOverTime() {
        burn();
        boolean sawTrue = false;
        boolean sawFalse = false;
        for (int i = 0; i < 100 && !(sawTrue && sawFalse); i++) {
            if (TradeConfig.rndBoolean()) {
                sawTrue = true;
            } else {
                sawFalse = true;
            }
        }
        assertTrue(sawTrue && sawFalse);
    }

    @Test
    public void rndAddressFormat() {
        burn();
        assertTrue(TradeConfig.rndAddress().endsWith("Oak St."));
    }

    @Test
    public void rndBalanceIsOneMillion() {
        burn();
        assertEquals("1000000", TradeConfig.rndBalance());
    }

    @Test
    public void rndCreditCardHasFourGroups() {
        burn();
        assertEquals(4, TradeConfig.rndCreditCard().split("-").length);
    }

    @Test
    public void rndEmailStripsColonAndAddsDomain() {
        burn();
        String email = TradeConfig.rndEmail("uid:5");
        assertTrue(email.startsWith("uid5@"));
        assertTrue(email.endsWith(".com"));
    }

    @Test
    public void rndFullNameContainsFirstAndLast() {
        burn();
        String name = TradeConfig.rndFullName();
        assertTrue(name.contains("first:"));
        assertTrue(name.contains("last:"));
    }

    @Test
    public void rndSymbolHasPrefix() {
        burn();
        assertTrue(TradeConfig.rndSymbol().startsWith("s:"));
    }

    @Test
    public void rndPriceAtLeastOne() {
        burn();
        assertTrue(TradeConfig.rndPrice() >= 1.0f);
    }

    @Test
    public void rndQuantityAtLeastOne() {
        burn();
        assertTrue(TradeConfig.rndQuantity() >= 1.0f);
    }

    @Test
    public void rndSymbolsNotNull() {
        burn();
        assertNotNull(TradeConfig.rndSymbols());
    }
}
