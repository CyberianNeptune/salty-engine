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

package de.edgelord.saltyengine.components.animation;

import de.edgelord.saltyengine.core.Component;
import de.edgelord.saltyengine.core.animation.Keyframe;
import de.edgelord.saltyengine.core.animation.LinearKeyframeAnimation;
import de.edgelord.saltyengine.core.event.CollisionEvent;
import de.edgelord.saltyengine.gameobject.Components;
import de.edgelord.saltyengine.gameobject.GameObject;
import de.edgelord.saltyengine.graphics.SaltyGraphics;

/**
 * Animates the basic state of its {@link #parent}.
 * That includes rotation, width, height, x position and y position.
 * <p>
 * NOTE: Take care of the hitbox of the {@link #parent}, these animation won't change it, you have to do that manually
 * if necessary!
 */
public class BasicGameObjectAnimation extends Component<GameObject> {

    private boolean recalculateOnNextStep = true;
    private LinearKeyframeAnimation animation = new LinearKeyframeAnimation();
    private boolean loop = false;

    private Control control;

    public BasicGameObjectAnimation(GameObject parent, String name, Control control) {
        super(parent, name, Components.ANIMATION_COMPONENT);

        this.control = control;
    }

    public void init() {
        animation.calculateAnimation();
    }

    public void startOver() {
        recalculateOnNextStep = true;
    }

    public void start() {
        enable();
    }

    public void pause() {
        disable();
    }

    public void stop() {
        recalculateOnNextStep = true;
        disable();
    }

    @Override
    public void onFixedTick() {

        if (recalculateOnNextStep) {
            animation.calculateAnimation();
            recalculateOnNextStep = false;
        }

        if (animation.animationEnded()) {
            if (isLoop()) {
                animation.restart();
            } else {
                remove();
            }
        }

        float delta = animation.nextDelta();

        switch (control) {

            case WIDTH:
                getParent().setWidth(getParent().getWidth() + delta);
                break;
            case HEIGHT:
                getParent().setHeight(getParent().getHeight() + delta);
                break;
            case X_POS:
                getParent().moveX(delta);
                break;
            case Y_POS:
                getParent().moveY(delta);
                break;

            case ROTATION:
                getParent().getTransform().setRotationDegrees(getParent().getTransform().getRotationDegrees() + delta);
                break;
        }
    }

    @Override
    public void draw(SaltyGraphics saltyGraphics) {

    }

    @Override
    public void onCollision(CollisionEvent e) {

    }

    public void addKeyFrame(Keyframe keyframe) {
        animation.add(keyframe);
    }

    public void addKeyFrame(int timing, float value) {
        animation.add(timing, value);
    }

    public enum Control {
        WIDTH,
        HEIGHT,
        X_POS,
        Y_POS,
        ROTATION
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
