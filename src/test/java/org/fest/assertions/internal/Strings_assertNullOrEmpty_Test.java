/*
 * Created on Dec 26, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.assertions.internal;

import static junit.framework.Assert.assertEquals;

import static org.fest.assertions.test.TestFailures.expectedAssertionErrorNotThrown;
import static org.fest.test.ExpectedException.none;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.fest.assertions.description.Description;
import org.fest.assertions.error.ErrorMessageFactory;
import org.fest.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link Strings#assertNullOrEmpty(Description, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Strings_assertNullOrEmpty_Test {
  @Rule
  public ExpectedException thrown = none();

  private Failures failures;
  private Strings strings;

  @Before
  public void setUp() {
    failures = spy(new Failures());
    strings = new Strings();
    strings.failures = failures;
  }

  @Test
  public void should_pass_if_actual_is_null() {
    strings.assertNullOrEmpty(mock(Description.class), null);
  }

  @Test
  public void should_pass_if_actual_is_empty() {
    strings.assertNullOrEmpty(mock(Description.class), "");
  }

  @Test
  public void should_fail_if_actual_is_not_null_and_is_not_empty() {
    Description description = new TestDescription("Testing");
    try {
      strings.assertNullOrEmpty(description, "Yoda");
    } catch (AssertionError e) {
      assertEquals("[Testing] expecting:\n null or empty but was:<'Yoda'>", e.getMessage());
      verify(failures).failure(same(description), any(ErrorMessageFactory.class));
      return;
    }
    throw expectedAssertionErrorNotThrown();
  }
}
