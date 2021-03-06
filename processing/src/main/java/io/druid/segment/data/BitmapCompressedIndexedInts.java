/*
 * Druid - a distributed column store.
 * Copyright 2012 - 2015 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.druid.segment.data;

import com.google.common.collect.Ordering;
import com.metamx.collections.bitmap.ImmutableBitmap;
import org.roaringbitmap.IntIterator;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Iterator;

/**
 */
public class BitmapCompressedIndexedInts implements IndexedInts, Comparable<ImmutableBitmap>
{
  private static Ordering<ImmutableBitmap> comparator = new Ordering<ImmutableBitmap>()
  {
    @Override
    public int compare(
        ImmutableBitmap set, ImmutableBitmap set1
    )
    {
      if (set.size() == 0 && set1.size() == 0) {
        return 0;
      }
      if (set.size() == 0) {
        return -1;
      }
      if (set1.size() == 0) {
        return 1;
      }
      return set.compareTo(set1);
    }
  }.nullsFirst();

  private final ImmutableBitmap immutableBitmap;

  public BitmapCompressedIndexedInts(ImmutableBitmap immutableBitmap)
  {
    this.immutableBitmap = immutableBitmap;
  }

  @Override
  public int compareTo(@Nullable ImmutableBitmap otherBitmap)
  {
    return comparator.compare(immutableBitmap, otherBitmap);
  }

  @Override
  public int size()
  {
    return immutableBitmap.size();
  }

  @Override
  public int get(int index)
  {
    throw new UnsupportedOperationException("This is really slow, so it's just not supported.");
  }

  public ImmutableBitmap getImmutableBitmap()
  {
    return immutableBitmap;
  }

  @Override
  public Iterator<Integer> iterator()
  {
    return new Iterator<Integer>()
    {
      IntIterator baseIterator = immutableBitmap.iterator();

      @Override
      public boolean hasNext()
      {
        return baseIterator.hasNext();
      }

      @Override
      public Integer next()
      {
        return baseIterator.next();
      }

      @Override
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public void fill(int index, int[] toFill)
  {
    throw new UnsupportedOperationException("fill not supported");
  }

  @Override
  public void close() throws IOException
  {

  }
}
