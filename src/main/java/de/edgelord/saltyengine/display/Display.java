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

package de.edgelord.saltyengine.display;

import de.edgelord.saltyengine.core.interfaces.MouseInputHandler;
import de.edgelord.saltyengine.utils.SaltySystem;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Display extends JFrame {

    private String windowTitle;
    private DisplayRatio displayRatio;
    private boolean closeRequested = false;
    private MouseInputHandler displayMouseHandler = null;

    public Display(DisplayRatio displayRatio, String windowTitle) {

        setSize((int) displayRatio.getCurrentDimensions().getWidth(), (int) displayRatio.getCurrentDimensions().getHeight());
        this.windowTitle = windowTitle;
        this.displayRatio = displayRatio;
    }

    public void create() {

        setTitle(windowTitle + " [Salty Engine " + SaltySystem.versionTag + "]");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                displayRatio.setWidth(e.getComponent().getWidth());
            }
        });
        addWindowListener(new NativeDisplayListener());
    }

    public boolean isCloseRequested() {
        return closeRequested;
    }

    public void setCloseRequested(boolean closeRequested) {
        this.closeRequested = closeRequested;
    }

    public MouseInputHandler getDisplayMouseHandler() {
        return displayMouseHandler;
    }

    public void setDisplayMouseHandler(MouseInputHandler displayMouseHandler) {
        this.displayMouseHandler = displayMouseHandler;
    }

    public DisplayRatio getDisplayRatio() {
        return displayRatio;
    }

    public void setDisplayRatio(DisplayRatio displayRatio) {
        this.displayRatio = displayRatio;
    }
}
