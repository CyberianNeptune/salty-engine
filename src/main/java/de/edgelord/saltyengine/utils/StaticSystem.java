/*
 * Copyright (c) by Malte Dostal
 * Germany, 8.2018
 * All rights reserved
 */

package de.edgelord.saltyengine.utils;

import de.edgelord.saltyengine.layer.LayerCollection;
import de.edgelord.saltyengine.output.Output;
import de.edgelord.saltyengine.scene.Scene;
import testing.dummys.DummyLayerCollection;
import testing.dummys.DummyScene;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StaticSystem {

    public static String versionTag = "0.3-SNAPSHOT";
    public static double version = 0.3;
    public static String versionMode = "SNAPSHOT";
    public static String versionName = "Zeus";
    public static String gameName = "My name is Nym. Arno Nym.";

    public static long fixedTickMillis = 1;
    public static boolean paused = false;
    public static boolean withExperimentalFeatures = false;

    public static boolean inputUp = false;
    public static boolean inputDown = false;
    public static boolean inputRight = false;
    public static boolean inputLeft = false;
    public static char lastInputKey;
    /**
     * You have to add a ifn check if you use this!
     */
    public static KeyEvent lastInput;

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        StaticSystem.paused = paused;
    }

    public enum Mode {
        layerCollection, scene
    }

    public static Mode currentMode = Mode.scene;
    public static Output systemOutput = new Output(System.out);
    public static boolean drawFPS = false;

    public static Font font = new Font(Font.SERIF, 0, 15);

    public static Scene currentScene = new DummyScene();
    public static LayerCollection currentLayerCollection = new DummyLayerCollection();
}