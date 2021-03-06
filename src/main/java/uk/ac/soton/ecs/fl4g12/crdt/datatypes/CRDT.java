/*
 * The MIT License
 *
 * Copyright 2016 Fabrizio Lungo <fl4g12@ecs.soton.ac.uk>.
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

package uk.ac.soton.ecs.fl4g12.crdt.datatypes;

import uk.ac.soton.ecs.fl4g12.crdt.datatypes.commutative.CmRDT;
import uk.ac.soton.ecs.fl4g12.crdt.datatypes.convergent.CvRDT;
import uk.ac.soton.ecs.fl4g12.crdt.delivery.Updatable;
import uk.ac.soton.ecs.fl4g12.crdt.delivery.UpdateMessage;

/**
 * Interface for Conflict-free Replicated Data Types. This interface should not be extended directly
 * and the {@link CvRDT} and {@link CmRDT} should be used to determine the semantic of how the
 * datatype works.
 *
 * @param <K> the type of identifier used to identify nodes.
 * @param <M> the type of updates which this object can be updated by.
 * @see CvRDT for the interface to be used with Convergent Replicated Data Types.
 * @see CmRDT for the interface to be used with Commutative Replicated Data Types.
 */
public interface CRDT<K, M extends UpdateMessage<K, ?>> extends Updatable<K, M> {

}
