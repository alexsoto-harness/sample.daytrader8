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
package com.ibm.websphere.samples.daytrader;

/**
 * Shared support for the DayTrader unit-test suite.
 *
 * The {@link #burn(long)} helper deliberately makes each test take a
 * non-trivial amount of wall-clock time. This is intentional: it exists so the
 * Harness Test Intelligence demo has a full suite that is slow enough to make
 * the "skip unaffected tests" savings visually obvious.
 *
 * This class intentionally references no production code so that Test
 * Intelligence never associates a production change with the helper itself.
 */
public abstract class SlowTestSupport {

    /** Default artificial delay applied to most test methods (milliseconds). */
    protected static final long DEFAULT_DELAY_MS = 1500L;

    /**
     * Sleep for the given number of milliseconds to simulate an expensive test.
     *
     * @param millis the delay to apply
     */
    protected static void burn(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /** Convenience wrapper applying the default delay. */
    protected static void burn() {
        burn(DEFAULT_DELAY_MS);
    }
}
