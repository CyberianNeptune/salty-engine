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

package de.edgelord.saltyengine.cosmetic.light;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.interfaces.Drawable;
import de.edgelord.saltyengine.cosmetic.geom.EnumShape;
import de.edgelord.saltyengine.cosmetic.geom.SaltyShape;
import de.edgelord.saltyengine.graphics.SaltyGraphics;
import de.edgelord.saltyengine.transform.Dimensions;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;
import de.edgelord.saltyengine.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientLight extends Light {

    private BufferedImage light;
    private BufferedImage coloredLight;
    private EnumShape shape;

    private float[] arcIfRoundRect;

    public GradientLight(Transform transform, Color color, EnumShape shape, float... arcIfRoundRect) {
        super(transform, color);
        this.shape = shape;
        this.arcIfRoundRect = arcIfRoundRect;
        updateLightImage();
    }

    public GradientLight(Transform transform, EnumShape shape, float... arcIfRoundRect) {
        this(transform, ColorUtil.TRANSPARENT_COLOR, shape, arcIfRoundRect);
    }

    public GradientLight(Vector2f position, Dimensions dimensions, Color color, EnumShape shape, float... arcIfRoundRect) {
        this(new Transform(position, dimensions), color, shape, arcIfRoundRect);
    }

    public GradientLight(Vector2f position, Dimensions dimensions, EnumShape shape, float... arcIfRoundRect) {
        this(new Transform(position, dimensions), shape, arcIfRoundRect);
    }

    public GradientLight(float x, float y, float width, float height, Color color, EnumShape shape, float... arcIfRoundRect) {
        this(new Transform(x, y, width, height), color, shape, arcIfRoundRect);
    }

    public GradientLight(float x, float y, float width, float height, EnumShape shape, float... arcIfRoundRect) {
        this(new Transform(x, y, width, height), shape, arcIfRoundRect);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(light, Math.round(getX()), Math.round(getY()), null);
    }

    @Override
    public void drawColorMap(Graphics2D graphics) {
        graphics.drawImage(coloredLight, Math.round(getX()), Math.round(getY()), null);
    }

    public EnumShape getShape() {
        return shape;
    }

    public void setShape(EnumShape shape) {
        this.shape = shape;
    }

    @Override
    public void setTransform(Transform transform) {
        super.setTransform(transform);
        updateLightImage();
    }

    @Override
    public void setDimensions(Dimensions dimensions) {
        super.setDimensions(dimensions);
        updateLightImage();
    }

    @Override
    public void setPosition(Vector2f position) {
        super.setPosition(position);
        updateLightImage();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        updateLightImage();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        updateLightImage();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        updateLightImage();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        updateLightImage();
    }

    public void updateLightImage() {
        light = ImageUtils.createPrimitiveGradient(shape, saltyGraphics -> {}, Game.getHost().getRenderHints(), getIntensity(), getDimensions(), arcIfRoundRect);
        coloredLight = ImageUtils.createPrimitiveGradient(shape, this::prepareGraphics, Game.getHost().getRenderHints(), getIntensity(), getColorAlpha(), getDimensions(), arcIfRoundRect);
    }
}
