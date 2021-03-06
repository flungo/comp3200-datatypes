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

package uk.ac.soton.ecs.fl4g12.crdt.util;

/**
 * Interface for performing arithmetic on objects of the given type. Objects should be treated as
 * immutable and arguments should not be modified.
 *
 * @param <T> the type which this object provides arithmetic operations on.
 */
public interface Arithmetic<T> {

  /**
   * Get a zero value for the arithmetic type.
   *
   * @return a zero value for the arithmetic type.
   */
  T getZero();

  /**
   * Get the unit value for the arithmetic type.
   *
   * @return the unit value for the arithmetic type.
   */
  T getUnit();

  /**
   * Add the given elements.
   *
   * @param elements the elements to add.
   * @return the sum of all the provided elements.
   */
  T add(T... elements);

  /**
   * Add the given elements.
   *
   * @param elements the elements to add.
   * @return the sum of all the provided elements.
   */
  T add(Iterable<T> elements);

  /**
   * Subtract the given elements from the given value.
   *
   * @param value the value to subtract the elements from.
   * @param elements the elements to subtract.
   * @return the value with all the elements subtracted from it.
   */
  T sub(T value, T... elements);

  /**
   * Subtract the given elements from the given value.
   *
   * @param value the value to subtract the elements from.
   * @param elements the elements to subtract.
   * @return the value with all the elements subtracted from it.
   */
  T sub(T value, Iterable<T> elements);

}
