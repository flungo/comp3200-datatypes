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

package uk.ac.soton.ecs.fl4g12.crdt.datatypes.commutative;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import uk.ac.soton.ecs.fl4g12.crdt.datatypes.GrowableUpdatableSetAbstractTest;
import uk.ac.soton.ecs.fl4g12.crdt.delivery.VersionedUpdatable;
import uk.ac.soton.ecs.fl4g12.crdt.order.VersionVector;

/**
 * Tests of the commutativity of growable {@link VersionedUpdatable} {@link Set} implementations.
 *
 * @param <E> the type of values stored in the {@link Set}.
 * @param <K> the type of identifier used to identify nodes.
 * @param <T> the type of the timestamp within the {@link VersionVector}
 * @param <U> the type of snapshot made from this state.
 * @param <S> the type of {@link VersionedUpdatable} based {@link Set} being tested.
 */
public abstract class GrowableCommutativeSetAbstractTest<E, K, T extends Comparable<T>, U extends GrowOnlySetUpdateMessage<E, K, T>, S extends Set<E> & VersionedUpdatable<K, T, U>>
    extends GrowableUpdatableSetAbstractTest<E, K, T, U, S> {

  public static <E, K, T extends Comparable<T>, U extends GrowOnlySetUpdateMessage<E, K, T>> void assertElementsMatch(
      Set<E> elements, U updateMessage) {
    assertEquals("Update element set should consist only of the new element(s)", elements,
        updateMessage.getElements());
  }

  @Override
  protected void assertAdd(S set, E element, U updateMessage) {
    assertElementsMatch(new HashSet<>(Arrays.asList(element)), updateMessage);
  }

  @Override
  protected void assertAddAll_Single(S set, E element, U updateMessage) {
    assertElementsMatch(new HashSet<>(Arrays.asList(element)), updateMessage);
  }

  @Override
  protected void assertAddAll_Multiple(S set, Set<E> elements, U updateMessage) {
    assertElementsMatch(elements, updateMessage);
  }

  @Override
  protected void assertAddAll_Overlap(S set, Set<E> elements, Set<E> newElements, U updateMessage) {
    assertElementsMatch(newElements, updateMessage);
  }
}