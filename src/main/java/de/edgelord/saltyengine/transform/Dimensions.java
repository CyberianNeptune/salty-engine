/*
 * This software was published under the MIT License.
 * The full LICENSE file can be found here: https://github.com/edgelord314/salty-enigne/tree/master/LICENSE
 *
 * Copyright (c) since 2018 by the Salty Engine developers,
 * Maintained by Malte Dostal
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package de.edgelord.saltyengine.transform;

import java.util.Random;

public class Dimensions {

    private float width, height;

    public Dimensions(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dimensions) {
            Dimensions other = (Dimensions) obj;
            return other.getWidth() == getWidth() && other.getHeight() == getHeight();
        } else {
            return false;
        }
    }

    public static Dimensions zero() {
        return new Dimensions(0, 0);
    }

    public static Dimensions max() {
        return new Dimensions(Float.MAX_VALUE, Float.MAX_VALUE);
    }

    public static Dimensions min() {
        return new Dimensions(Float.MIN_VALUE, Float.MIN_VALUE);
    }

    public static Dimensions random(int min, int max) {
        Random random = new Random();
        return new Dimensions(random.nextInt(max + min) - min, random.nextInt(max + min) - min);
    }

    public static Dimensions one() {
        return new Dimensions(1, 1);
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * Returns a new <code>Dimensions</code> with the same {@link #width} and {@link #height} as this one.
     *
     * @return a "copy" of this <code>Dimensions</code>
     */
    @Override
    protected Object clone() {
        return new Dimensions(width, height);
    }
}
