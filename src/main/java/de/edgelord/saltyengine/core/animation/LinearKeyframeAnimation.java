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

package de.edgelord.saltyengine.core.animation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LinearKeyframeAnimation {

    private List<Keyframe> keyframes;
    // This List only contains relative Values!
    private HashMap<Integer, Float> animation = new HashMap<>();
    private int end = 0;

    private int currentFrame = -1;

    public LinearKeyframeAnimation(List<Keyframe> keyframes) {
        this.keyframes = keyframes;

        add(0, 0);
    }

    public LinearKeyframeAnimation() {
        this(new ArrayList<>());
    }

    public List<Keyframe> getKeyframes() {
        return keyframes;
    }

    public void setKeyframes(List<Keyframe> keyframes) {
        this.keyframes = keyframes;
    }

    /**
     * Important: This method only returns a delta value!
     *
     * @return the next delta-step of the linear keyframe animation
     */
    public float nextDelta() {

        if (!animationEnded()) {

            currentFrame++;
            return animation.get(currentFrame);
        } else {
            // If there is a request out of the available timeline, there should be no delta returned so 0
            return 0f;
        }
    }

    public void calculateAnimation() {

        currentFrame = 0;

        // Sort the list by the timing of the keyframes
        keyframes.sort(Comparator.comparingInt(Keyframe::getTiming));

        end = keyframes.get(keyframes.size() - 1).getTiming();

        int index = 0;

        while (index < keyframes.size() - 1) {

            int i = 0;
            int duration = keyframes.get(index + 1).getTiming() - keyframes.get(index).getTiming();
            float step = (keyframes.get(index + 1).getKey() - keyframes.get(index).getKey()) / duration;

            while (i < duration) {

                // System.out.println("timing: " + (i + keyframes.get(index).getTiming()) + " value: " + step);
                animation.put(i + keyframes.get(index).getTiming(), step);
                i++;
            }

            index++;
        }
    }

    public boolean animationEnded() {
        return !(currentFrame < end - 1);
    }

    public void add(Keyframe keyframe) {
        keyframes.add(keyframe);
    }

    public void add(int timing, float value) {
        keyframes.add(new Keyframe(timing, value));
    }

    public void remove(Keyframe keyframe) {
        keyframes.remove(keyframe);
    }

    public void removeByTiming(int timing) {

        keyframes.removeIf(keyframe -> keyframe.getTiming() == timing);
    }

    public void removeByKey(float key) {
        keyframes.removeIf(keyframe -> keyframe.getKey() == key);
    }

    public void restart() {
        currentFrame = -1;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
