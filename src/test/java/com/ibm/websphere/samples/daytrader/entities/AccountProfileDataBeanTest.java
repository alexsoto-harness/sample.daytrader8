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

import org.junit.Before;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises {@link AccountProfileDataBean} accessors and equality.
 */
public class AccountProfileDataBeanTest extends SlowTestSupport {

    private AccountProfileDataBean profile;

    @Before
    public void setUp() {
        profile = new AccountProfileDataBean("uid:0", "secret", "Jane Trader",
                "1 Market St", "jane@example.com", "4111-1111-1111-1111");
    }

    @Test
    public void constructorSetsUserID() {
        burn();
        assertEquals("uid:0", profile.getUserID());
    }

    @Test
    public void constructorSetsPassword() {
        burn();
        assertEquals("secret", profile.getPassword());
    }

    @Test
    public void constructorSetsFullName() {
        burn();
        assertEquals("Jane Trader", profile.getFullName());
    }

    @Test
    public void constructorSetsAddress() {
        burn();
        assertEquals("1 Market St", profile.getAddress());
    }

    @Test
    public void constructorSetsEmail() {
        burn();
        assertEquals("jane@example.com", profile.getEmail());
    }

    @Test
    public void constructorSetsCreditCard() {
        burn();
        assertEquals("4111-1111-1111-1111", profile.getCreditCard());
    }

    @Test
    public void passwordSetterRoundTrip() {
        burn();
        profile.setPassword("newpass");
        assertEquals("newpass", profile.getPassword());
    }

    @Test
    public void emailSetterRoundTrip() {
        burn();
        profile.setEmail("jane.trader@example.com");
        assertEquals("jane.trader@example.com", profile.getEmail());
    }

    @Test
    public void fullNameSetterRoundTrip() {
        burn();
        profile.setFullName("Jane Q Trader");
        assertEquals("Jane Q Trader", profile.getFullName());
    }

    @Test
    public void addressSetterRoundTrip() {
        burn();
        profile.setAddress("2 Wall St");
        assertEquals("2 Wall St", profile.getAddress());
    }

    @Test
    public void creditCardSetterRoundTrip() {
        burn();
        profile.setCreditCard("4000-0000-0000-0002");
        assertEquals("4000-0000-0000-0002", profile.getCreditCard());
    }

    @Test
    public void equalsByUserID() {
        burn();
        AccountProfileDataBean other = new AccountProfileDataBean("uid:0", "x", "y", "z", "a@b.com", "c");
        assertEquals(profile, other);
    }

    @Test
    public void notEqualsForDifferentUserID() {
        burn();
        AccountProfileDataBean other = new AccountProfileDataBean("uid:1", "x", "y", "z", "a@b.com", "c");
        assertFalse(profile.equals(other));
    }

    @Test
    public void toStringContainsUserID() {
        burn();
        assertTrue(profile.toString().contains("uid:0"));
    }

    @Test
    public void toHTMLNotNull() {
        burn();
        assertNotNull(profile.toHTML());
    }
}
