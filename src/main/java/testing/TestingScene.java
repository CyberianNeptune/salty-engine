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

package testing;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.WindowClosingHooks;
import de.edgelord.saltyengine.cosmetic.geom.EnumShape;
import de.edgelord.saltyengine.cosmetic.light.GradientLight;
import de.edgelord.saltyengine.cosmetic.light.Light;
import de.edgelord.saltyengine.cosmetic.light.LightSystem;
import de.edgelord.saltyengine.cosmetic.light.StaticLightSystem;
import de.edgelord.saltyengine.factory.ImageFactory;
import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.io.LanguageManager;
import de.edgelord.saltyengine.resource.InnerResource;
import de.edgelord.saltyengine.scene.Scene;
import de.edgelord.saltyengine.io.serialization.Serializer;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.ui.elements.FloatingLabel;
import de.edgelord.saltyengine.ui.elements.RoundedTextBox;
import de.edgelord.saltyengine.utils.ColorUtil;
import de.edgelord.saltyengine.utils.SaltySystem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestingScene extends Scene {

    private Light light = new GradientLight(0, 0, 300, 300, ColorUtil.GOLD, EnumShape.OVAL);

    public TestingScene(String foo, Integer bar) {
        setFriction(0.005f);

        try {
            LanguageManager.init("res/lyrics", SaltySystem.defaultResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addLight();
        initForcesTest();
        initUITest();
        addUI();

        disableGravity();

        WindowClosingHooks.addShutdownHook(Game.getHost()::takeScreenshot);
    }

    private void addLight() {
        setLightSystem(new LightSystem(new Color(0, 0, 0, 225)));
        light.setColor(Color.blue);
        getLightSystem().addLight(light);
        getLightSystem().addLight(new GradientLight(new Transform(0, 0, 200, 200), EnumShape.ROUND_RECTANGLE));
    }

    private void initUITest() {
        FloatingLabel floatingLabel = new FloatingLabel("Use WASD or the arrow keys to move the red bird!", new Vector2f(0, 25));
        floatingLabel.centreOnXAxis(true);
        floatingLabel.setForegroundColor(Color.black);
        floatingLabel.setFont(floatingLabel.getFont().deriveFont(20f));

        getUI().addElement(floatingLabel);
    }

    @Override
    public void onFixedTick() {
        light.positionByCentre(Input.getCursorPosition());
        super.onFixedTick();
    }

    private void addUI() {

        SaltySystem.defaultFont = SaltySystem.defaultFont.deriveFont(20f);

        PauseButton pauseButton = new PauseButton();
        RoundedTextBox textBox = new RoundedTextBox(LanguageManager.getText("textBox"), new Vector2f(10, 600), 1180, 100, new Vector2f(25, 50));
        textBox.setFont(textBox.getFont().deriveFont(18f));

        getUI().addElement(pauseButton);
        getUI().addElement(textBox);
    }

    private void initPhysicsTest() {

        final ImageFactory imageFactory = new ImageFactory(new InnerResource());
        final BufferedImage birdSpritesheet = imageFactory.getImageResource("res/pictures/spritesheets/bird_spritesheet.png");

        final Bird bird1_1 = new Bird(birdSpritesheet, 1, 1);
        final Bird bird3_1 = new Bird(birdSpritesheet, 3, 1);
        final Bird bird1_3 = new Bird(birdSpritesheet, 1, 3);
        final Bird bird3_3 = new Bird(birdSpritesheet, 3, 3);

        bird1_1.setTag("de.edgelord.saltyengine.testing.bird1_1");
        bird3_1.setTag("de.edgelord.saltyengine.testing.bird3_1");
        bird1_3.setTag("de.edgelord.saltyengine.testing.bird1_3");
        bird3_3.setTag("de.edgelord.saltyengine.testing.bird3_3");

        addGameObject(bird1_1);
        addGameObject(bird1_3);
        addGameObject(bird3_1);
        addGameObject(bird3_3);
    }

    public void initForcesTest() {

        final ImageFactory imageFactory = new ImageFactory(new InnerResource());
        final BufferedImage birdSpritesheet = imageFactory.getImageResource("res/pictures/spritesheets/bird_spritesheet.png");

        final Bird upperBird = new Bird(birdSpritesheet, 2, 2);
        final Bird bottomBird = new Bird(birdSpritesheet, 3, 4);
        final BirdPlayer player = new BirdPlayer(new Vector2f(0, 100), SaltySystem.defaultImageFactory.getOptimizedImageResource("res/pictures/spritesheets/bird_spritesheet_player.png"));

        addGameObject(bottomBird);
        addGameObject(upperBird);
        addGameObject(player);

        Serializer.add(player);
        Serializer.add(bottomBird);
        try {
            Serializer.doDeserialization();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSampleScene() {

        final ImageFactory imageFactory = new ImageFactory(new InnerResource());

        final BirdPlayer player = new BirdPlayer(new Vector2f(0, 0), SaltySystem.defaultImageFactory.getOptimizedImageResource("res/pictures/spritesheets/bird_spritesheet_player.png"));

        addGameObject(player);

        final BufferedImage birdSpritesheet = imageFactory.getImageResource("res/pictures/spritesheets/bird_spritesheet.png");

        // Adding all those cute birds to the Scene

        int index = 0;
        int xPos = 0;
        int yPos = 0;

        while (index < 16) {

            addGameObject(new Bird(birdSpritesheet, xPos, yPos));

            if (index == 7) {

                xPos++;
                yPos = 0;
            } else {

                yPos++;
            }

            index++;
        }
    }
}
