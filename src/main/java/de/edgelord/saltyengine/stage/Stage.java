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

package de.edgelord.saltyengine.stage;

import de.edgelord.saltyengine.core.Engine;
import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.interfaces.MouseInputHandler;
import de.edgelord.saltyengine.graphics.SaltyGraphics;
import de.edgelord.saltyengine.utils.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.RenderingHints.*;

public class Stage extends JPanel {

    private final Container container;
    private final Engine engine;
    private NativeStageMouseListener nativeMouseListener = null;
    private NativeStageMouseMotionListener nativeMouseMotionListener = null;
    private NativeStageMouseWheelListener nativeMouseWheelListener = null;

    private float currentScale = 1f;

    private float lastFps = 0f;
    private int ticks = 0;
    private int fpsRefreshGate = 25;

    private boolean highQuality = true;
    private RenderingHints renderingHints;

    public Stage(final Container container, final Engine engine) {
        this(container, engine, 0, 0, container.getWidth(), container.getHeight());
    }

    public Stage(final Container container, final Engine engine, int x, int y, int width, int height) {
        this.container = container;
        this.engine = engine;

        init(x, y, width, height);
    }

    protected void initNativeMouseListener() {

        nativeMouseListener = new NativeStageMouseListener(null);

        nativeMouseMotionListener = new NativeStageMouseMotionListener(null);

        nativeMouseWheelListener = new NativeStageMouseWheelListener(null);

        addMouseListener(nativeMouseListener);
        addMouseMotionListener(nativeMouseMotionListener);
        addMouseWheelListener(nativeMouseWheelListener);
    }

    protected void init(int x, int y, int width, int height) {

        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        container.add(this);
        setIgnoreRepaint(true);
        setFocusable(false);

        if (highQuality) {
            renderingHints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        } else {
            renderingHints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
            putToRenderHints(KEY_RENDERING, VALUE_RENDER_SPEED);
            putToRenderHints(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_SPEED);
            putToRenderHints(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_SPEED);
        }

        initNativeMouseListener();
    }

    public void putToRenderHints(Key key, Object value) {
        renderingHints.put(key, value);
    }

    protected void paintComponent(Graphics graphics) {
        ticks++;
        final Graphics2D graphics2D = (Graphics2D) graphics;
        renderToGraphics(graphics2D);
    }

    private void renderToGraphics(Graphics2D graphics2D) {
        graphics2D.setClip(0, 0, getWidth(), getWidth());

        graphics2D.setRenderingHints(renderingHints);
        graphics2D.scale(currentScale, currentScale);

        Game.getCamera().setViewToGraphics(graphics2D);

        SaltyGraphics saltyGraphics = new SaltyGraphics(graphics2D);

        engine.render(saltyGraphics);

        if (Game.isDrawFPS()) {
            if (ticks == fpsRefreshGate) {
                lastFps = Time.getFPS();
                ticks = 0;
            }

            saltyGraphics.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            saltyGraphics.setColor(Color.RED);
            String fps = String.valueOf(Math.round(lastFps));
            saltyGraphics.drawText("FPS: " + fps, 0, (float) saltyGraphics.getFontMetrics().getStringBounds(fps, saltyGraphics.getGraphics2D()).getHeight());
        }
    }

    public BufferedImage renderToImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = image.createGraphics();
        renderToGraphics(graphics2D);
        graphics2D.dispose();

        return image;
    }

    public void scaleTo(float scale) {
        this.currentScale = scale;
    }

    public void setMouseHandler(final MouseInputHandler mouseHandler) {
        this.nativeMouseListener.setMouseHandler(mouseHandler);
        this.nativeMouseMotionListener.setMouseHandler(mouseHandler);
        this.nativeMouseWheelListener.setMouseHandler(mouseHandler);
    }

    public boolean isHighQuality() {
        return highQuality;
    }

    public void setHighQuality(final boolean highQuality) {
        this.highQuality = highQuality;
    }

    public RenderingHints getRenderHints() {
        return renderingHints;
    }
}
