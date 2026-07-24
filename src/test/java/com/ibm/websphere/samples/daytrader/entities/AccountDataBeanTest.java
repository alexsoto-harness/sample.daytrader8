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

import javax.ejb.EJBException;

import org.junit.Before;
import org.junit.Test;

import com.ibm.websphere.samples.daytrader.SlowTestSupport;

/**
 * Exercises {@link AccountDataBean} accessors plus the login / logout behavior.
 */
public class AccountDataBeanTest extends SlowTestSupport {

    private AccountDataBean account;

    @Before
    public void setUp() {
        account = new AccountDataBean(100, 3, 2, new Date(), new Date(),
                new BigDecimal("1000.00"), new BigDecimal("800.00"), "uid:0");
    }

    @Test
    public void constructorSetsAccountID() {
        burn();
        assertEquals(Integer.valueOf(100), account.getAccountID());
    }

    @Test
    public void constructorSetsLoginCount() {
        burn();
        assertEquals(3, account.getLoginCount());
    }

    @Test
    public void constructorSetsLogoutCount() {
        burn();
        assertEquals(2, account.getLogoutCount());
    }

    @Test
    public void constructorSetsBalance() {
        burn();
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    public void constructorSetsOpenBalance() {
        burn();
        assertEquals(new BigDecimal("800.00"), account.getOpenBalance());
    }

    @Test
    public void constructorSetsProfileID() {
        burn();
        assertEquals("uid:0", account.getProfileID());
    }

    @Test
    public void logoutIncrementsLogoutCount() {
        burn();
        account.logout();
        assertEquals(3, account.getLogoutCount());
    }

    @Test
    public void loginIncrementsLoginCountWithCorrectPassword() {
        burn();
        AccountProfileDataBean profile = new AccountProfileDataBean("uid:0", "pw", "n", "a", "e@x.com", "c");
        account.setProfile(profile);
        account.login("pw");
        assertEquals(4, account.getLoginCount());
        assertNotNull(account.getLastLogin());
    }

    @Test(expected = EJBException.class)
    public void loginWithWrongPasswordThrows() {
        burn();
        AccountProfileDataBean profile = new AccountProfileDataBean("uid:0", "pw", "n", "a", "e@x.com", "c");
        account.setProfile(profile);
        account.login("wrong");
    }

    @Test(expected = EJBException.class)
    public void loginWithNullProfileThrows() {
        burn();
        account.login("pw");
    }

    @Test
    public void balanceSetterRoundTrip() {
        burn();
        account.setBalance(new BigDecimal("1234.56"));
        assertEquals(new BigDecimal("1234.56"), account.getBalance());
    }

    @Test
    public void equalsByAccountID() {
        burn();
        AccountDataBean other = new AccountDataBean(100, 0, 0, new Date(), new Date(),
                BigDecimal.ZERO, BigDecimal.ZERO, "uid:9");
        assertEquals(account, other);
    }

    @Test
    public void notEqualsForDifferentAccountID() {
        burn();
        AccountDataBean other = new AccountDataBean(200, 0, 0, new Date(), new Date(),
                BigDecimal.ZERO, BigDecimal.ZERO, "uid:0");
        assertFalse(account.equals(other));
    }

    @Test
    public void toStringContainsAccountID() {
        burn();
        assertTrue(account.toString().contains("100"));
    }

    @Test
    public void toHTMLNotNull() {
        burn();
        assertNotNull(account.toHTML());
    }
}
