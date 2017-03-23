/*
 * The MIT License
 *
 * Copyright 2017 Fabrizio Lungo <fl4g12@ecs.soton.ac.uk>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uk.ac.soton.ecs.fl4g12.crdt.datatypes.convergent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import uk.ac.soton.ecs.fl4g12.crdt.delivery.StatefulUpdatable;
import uk.ac.soton.ecs.fl4g12.crdt.order.VersionVector;

/**
 * Tests to ensure that two {@linkplain StatefulUpdatable} {@linkplain Set}s converge under various
 * operations.
 *
 * @param <E> the type of values stored in the {@link Set}.
 * @param <K> the type of identifier used to identify nodes.
 * @param <T> the type of the timestamp within the {@link VersionVector}
 * @param <U> the type of snapshot made from this state.
 * @param <S> the type of {@link Set} being tested.
 */
public abstract class SetConvergenceTest<E, K, T extends Comparable<T>, U extends SetState<E, K, T>, S extends Set<E> & StatefulUpdatable<K, T, U>>
    extends GrowableSetConvergenceTest<E, K, T, U, S> {

  private static final Logger LOGGER = Logger.getLogger(SetConvergenceTest.class.getName());

  /**
   * Test update with a local remove.
   *
   * @throws Exception if the test fails.
   */
  @Test
  public void testUpdate_LocalRemove() throws Exception {
    LOGGER.log(Level.INFO, "testUpdate_LocalRemove: Test update with a local remove.");

    final S set1 = getSet();
    final S set2 = getSet();

    // Setup the initial states
    final HashSet<E> initial = new HashSet<>(Arrays.asList(getElement(0), getElement(1)));
    set1.addAll(initial);
    set2.update(set1.snapshot());

    final HashSet<E> mutated = new HashSet<>(Arrays.asList(getElement(1)));

    assertTrue("The version vectors should be identical to start with",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical to start with", set1.snapshot().getState(),
        set2.snapshot().getState());
    assertEquals("set1 should have the initial state", initial, set1.snapshot().getState());
    assertEquals("set2 should have the initial state", initial, set2.snapshot().getState());

    set1.remove(getElement(0));
    assertTrue("set2 should have happenedBefore set1",
        set2.getVersion().happenedBefore(set1.getVersion()));

    set1.update(set2.snapshot());
    assertTrue("set2 should have happenedBefore set1",
        set2.getVersion().happenedBefore(set1.getVersion()));
    assertEquals("set1 should have the mutated state", mutated, set1.snapshot().getState());
    assertEquals("set2 should have the initial state", initial, set2.snapshot().getState());

    set2.update(set1.snapshot());
    assertTrue("The version vectors should be identical after bi-directional update",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical after bi-directional update",
        set1.snapshot().getState(), set2.snapshot().getState());
    assertEquals("set1 should have the mutated state", mutated, set1.snapshot().getState());
    assertEquals("set2 should have the mutated state", mutated, set2.snapshot().getState());
  }

  /**
   * Test update with a remote remove.
   *
   * @throws Exception if the test fails.
   */
  @Test
  public void testUpdate_RemoteRemove() throws Exception {
    LOGGER.log(Level.INFO, "testUpdate_RemoteRemove: Test update with a remote remove.");

    final S set1 = getSet();
    final S set2 = getSet();

    // Setup the initial states
    final HashSet<E> initial = new HashSet<>(Arrays.asList(getElement(0), getElement(1)));
    set1.addAll(initial);
    set2.update(set1.snapshot());

    final HashSet<E> mutated = new HashSet<>(Arrays.asList(getElement(0)));

    assertTrue("The version vectors should be identical to start with",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical to start with", set1.snapshot().getState(),
        set2.snapshot().getState());
    assertEquals("set1 should have the initial state", initial, set1.snapshot().getState());
    assertEquals("set2 should have the initial state", initial, set2.snapshot().getState());

    set2.remove(getElement(1));
    assertTrue("set1 should have happenedBefore set2",
        set1.getVersion().happenedBefore(set2.getVersion()));

    set1.update(set2.snapshot());
    assertTrue("The version vectors should be identical after update",
        set2.getVersion().identical(set1.getVersion()));
    assertEquals("set1 should have the mutated state", mutated, set1.snapshot().getState());
    assertEquals("set2 should have the mutated state", mutated, set2.snapshot().getState());

    set2.update(set1.snapshot());
    assertTrue("The version vectors should be identical after bi-directional update",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical after bi-directional update",
        set1.snapshot().getState(), set2.snapshot().getState());
    assertEquals("set1 should have the mutated state", mutated, set1.snapshot().getState());
    assertEquals("set2 should have the mutated state", mutated, set2.snapshot().getState());
  }

  /**
   * Test update with concurrent removals.
   *
   * @throws Exception if the test fails.
   */
  @Test
  public void testUpdate_BothRemove() throws Exception {
    LOGGER.log(Level.INFO, "testUpdate_BothRemove: Test update with concurrent removals.");

    final S set1 = getSet();
    final S set2 = getSet();

    // Setup the initial states
    final HashSet<E> initial =
        new HashSet<>(Arrays.asList(getElement(0), getElement(1), getElement(2)));
    set1.addAll(initial);
    set2.update(set1.snapshot());

    final HashSet<E> removed1 = new HashSet<>(Arrays.asList(getElement(0), getElement(2)));
    final HashSet<E> removed2 = new HashSet<>(Arrays.asList(getElement(2)));

    assertTrue("The version vectors should be identical to start with",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical to start with", set1.snapshot().getState(),
        set2.snapshot().getState());
    assertEquals("set1 should have seen 0 elements", initial, set1.snapshot().getState());
    assertEquals("set2 should have seen 0 elements", initial, set2.snapshot().getState());

    set1.remove(getElement(0));
    set2.remove(getElement(1));
    assertTrue("set1 should be concurrent with set2",
        set1.getVersion().concurrentWith(set2.getVersion()));

    set1.update(set2.snapshot());
    assertTrue("set2 should have happenedBefore set1",
        set2.getVersion().happenedBefore(set1.getVersion()));
    assertEquals("set1 should have seen 2 elements", removed2, set1.snapshot().getState());
    assertEquals("set2 should have seen 1 element", removed1, set2.snapshot().getState());

    set2.update(set1.snapshot());
    assertTrue("The sets should be identical after bi-directional update",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical after bi-directional update",
        set1.snapshot().getState(), set2.snapshot().getState());
    assertEquals("set1 should have seen 2 elements", removed2, set1.snapshot().getState());
    assertEquals("set2 should have seen 2 elements", removed2, set2.snapshot().getState());
  }

  /**
   * Test update with concurrent removals of the same item.
   *
   * @throws Exception if the test fails.
   */
  @Test
  public void testUpdate_BothRemove_Same() throws Exception {
    LOGGER.log(Level.INFO,
        "testUpdate_BothRemove_Same: Test update with concurrent removals of the same item.");

    final S set1 = getSet();
    final S set2 = getSet();

    // Setup the initial states
    final HashSet<E> initial = new HashSet<>(Arrays.asList(getElement(0), getElement(1)));
    set1.addAll(initial);
    set2.update(set1.snapshot());

    final HashSet<E> removed = new HashSet<>(Arrays.asList(getElement(0)));

    assertTrue("The version vectors should be identical to start with",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical to start with", set1.snapshot().getState(),
        set2.snapshot().getState());
    assertEquals("set1 should have seen 0 elements", initial, set1.snapshot().getState());
    assertEquals("set2 should have seen 0 elements", initial, set2.snapshot().getState());

    set1.remove(getElement(1));
    set2.remove(getElement(1));
    assertTrue("set1 should be concurrent with set2",
        set1.getVersion().concurrentWith(set2.getVersion()));

    set1.update(set2.snapshot());
    assertTrue("set2 should have happenedBefore set1",
        set2.getVersion().happenedBefore(set1.getVersion()));
    assertEquals("set1 should have seen 2 elements", removed, set1.snapshot().getState());
    assertEquals("set2 should have seen 1 element", removed, set2.snapshot().getState());

    set2.update(set1.snapshot());
    assertTrue("The sets should be identical after bi-directional update",
        set1.getVersion().identical(set2.getVersion()));
    assertEquals("The sets should be identical after bi-directional update",
        set1.snapshot().getState(), set2.snapshot().getState());
    assertEquals("set1 should have seen 2 elements", removed, set1.snapshot().getState());
    assertEquals("set2 should have seen 2 elements", removed, set2.snapshot().getState());
  }

  // TODO: test remove, removeAll, retain, retainAll and clear - other inter leavings.

}