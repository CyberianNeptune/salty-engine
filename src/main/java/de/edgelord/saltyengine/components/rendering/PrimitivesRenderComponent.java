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

package de.edgelord.saltyengine.components.rendering;

import de.edgelord.saltyengine.core.Component;
import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.interfaces.Drawable;
import de.edgelord.saltyengine.core.stereotypes.ComponentParent;
import de.edgelord.saltyengine.gameobject.Components;
import de.edgelord.saltyengine.graphics.SaltyGraphics;
import de.edgelord.saltyengine.transform.Dimensions;
import de.edgelord.saltyengine.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PrimitivesRenderComponent extends RenderComponent {

    // The color with which the Component should render
    private Color color = Color.black;

    // Whether the Component should only DRAW (e.g. graphics.drawRectangle()) or FILL it (e.g. graphics.fillRectangle())
    private boolean fill = true;

    // The stroke (like a pen) with which the component should DRAW the primitives
    private float lineWidth = 3f;

    private BufferedImage primitiveImage;

    private Drawable primitiveDraw;

    /**
     * The default super constructor for gameObjectComponent, which takes in the parent GameObject and the
     * name, used as an id, for fishing specific Components out of a list
     *
     * @param parent the parent of the Component, so where to take the e.g. the Coordinate info from
     * @param name   the id-name for this Component
     * @see Component
     */
    public PrimitivesRenderComponent(final ComponentParent parent, final String name) {
        super(parent, name, Components.PRIMITIVES_RENDER_COMPONENT);
        updateImage();
    }

    /**
     * Any classes extending PrimitivesRenderComponent has to override this method
     * for drawing e.g. a primitives like a Rectangle in RectangleRender
     *
     * @param saltyGraphics the SaltyGraphics to which the component should DRAW
     * @see Component
     */
    @Override
    public void draw(SaltyGraphics saltyGraphics) {
        saltyGraphics.drawImage(primitiveImage, getParent().getPosition());
    }

    /**
     * Sets the color and stroke of the given Graphics2D to the ones set UP in this class
     * So the user can always control how the component should render the primitives.
     *
     * @param saltyGraphics the SaltyGraphics which should be prepared
     * @see Graphics2D
     */
    protected void setUpGraphics(final SaltyGraphics saltyGraphics) {

        saltyGraphics.setColor(getColor());
        saltyGraphics.setStroke(new BasicStroke(lineWidth));
    }

    public abstract void updateImageData();

    public void updateImage() {
        updateImageData();
        primitiveImage = ImageUtils.createPrimitiveImage(saltyGraphics -> {
            setUpGraphics(saltyGraphics);
            primitiveDraw.draw(saltyGraphics);
        }, new Dimensions(getParent().getWidth() + (lineWidth * 2), getParent().getHeight() + (lineWidth * 2)), Game.getHost().getRenderHints());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
        updateImage();
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(final boolean fill) {
        this.fill = fill;
        updateImage();
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(final float width) {
        this.lineWidth = width;
        updateImage();
    }

    protected Drawable getPrimitiveDraw() {
        return primitiveDraw;
    }

    protected void setPrimitiveDraw(Drawable primitiveDraw) {
        this.primitiveDraw = primitiveDraw;
    }
}
