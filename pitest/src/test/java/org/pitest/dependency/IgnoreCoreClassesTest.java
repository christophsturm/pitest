/*
 * Copyright 2011 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.dependency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.net.SocketFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.pitest.dependency.DependencyAccess.Member;
import org.pitest.dependency.DependencyExtractorTest.Foo;
import org.powermock.core.classloader.annotations.PowerMockIgnore;

public class IgnoreCoreClassesTest {

  private IgnoreCoreClasses testee;

  @Before
  public void setUp() {
    this.testee = new IgnoreCoreClasses();
  }

  @Test
  public void shouldIgnoreJavaLangClasses() {
    assertIgnored(Integer.class);
  }

  @Test
  public void shouldIgnoreLegecyJUnitClasses() {
    assertIgnored(junit.framework.Assert.class);
  }

  @Test
  public void shouldIgnoreJUnitClasses() {
    assertIgnored(JUnit4.class);
  }

  @Test
  public void shouldIgnoreMockito() {
    assertIgnored(Mockito.class);
  }

  @Test
  public void shouldIgnorePowerMock() {
    assertIgnored(PowerMockIgnore.class);
  }

  private void assertIgnored(final Class<?> clazz) {
    assertFalse(this.testee.apply(makeAccessFor(clazz)));
  }

  @Test
  public void shouldIgnoreJavaX() {
    assertFalse(this.testee.apply(makeAccessFor(SocketFactory.class)));
  }

  @Test
  public void shouldNotIgnoreOtherPackages() {
    assertTrue(this.testee.apply(makeAccessFor(Foo.class)));
  }

  private DependencyAccess makeAccessFor(final Class<?> clazz) {
    return new DependencyAccess(new Member("foo", 
        "()V"), new Member(clazz.getName(), "()V"));
  }

}
