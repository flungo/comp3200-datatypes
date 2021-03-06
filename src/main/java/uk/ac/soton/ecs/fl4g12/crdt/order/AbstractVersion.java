/*
 * The MIT License
 *
 * Copyright 2017 Fabrizio Lungo <fl4g12@ecs.soton.ac.uk>.
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

package uk.ac.soton.ecs.fl4g12.crdt.order;

/**
 * An abstract {@linkplain Version}. To be extended by implementations {@link Version} that wish to
 * take advantage of the abstract methods provided by this class.
 *
 * @param <T> the type of the timestamp.
 * @param <V1> the type of {@linkplain Version}s which this {@linkplain Version} can perform
 *        operations with.
 * @param <V2> the specific type of {@link Version} which is returned when cloning.
 */
public abstract class AbstractVersion<T, V1 extends Version<T, V1, ?>, V2 extends V1>
    implements Version<T, V1, V2> {

  @Override
  public boolean happenedBefore(V1 version) {
    return this.compareTo(version) < 0;
  }

  @Override
  public void sync(V1 other) {
    sync(other.get());
  }

  @Override
  public boolean identical(V1 version) {
    return this.get().equals(version.get());
  }

  @Override
  public int hashCode() {
    return get().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Version other = (Version) obj;
    if (!this.get().equals(other.get())) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + get() + '}';
  }

}
