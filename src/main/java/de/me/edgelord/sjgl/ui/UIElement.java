package de.me.edgelord.sjgl.ui;

import de.me.edgelord.sjgl.gameobject.GameObject;
import de.me.edgelord.sjgl.location.Coordinates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class UIElement extends GameObject {

    public UIElement(Coordinates coordinates, int width, int height) {
        super(coordinates, width, height);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void onCollision(GameObject other) {
    }

    @Override
    public void onFixedTick() {

    }

    @Override
    public void onTick() {
    }

    public abstract void draw(Graphics2D graphics);

    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void keyTyped(KeyEvent e);
}