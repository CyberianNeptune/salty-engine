package de.edgelord.saltyengine.gameobject.components;

import de.edgelord.saltyengine.core.event.CollisionEvent;
import de.edgelord.saltyengine.gameobject.GameObject;
import de.edgelord.saltyengine.gameobject.GameObjectComponent;

import java.awt.*;

public class DebugLogGameObjectStat extends GameObjectComponent {

    private FixedRate fixedRate = new FixedRate(getParent(), "de.edgelord.saltyengine.DebugLogGameObjectStat$1<tmp>", 100);

    public DebugLogGameObjectStat(GameObject parent, String name) {
        super(parent, name, CORE_COMPONENT);

        getParent().addComponent(fixedRate);
    }

    private void debugLog() {
        System.out.println("--------------------");
        System.out.println(getParent().getTag() + " stat:");
        System.out.println();

        System.out.println("-----");
        System.out.println("Transform:");
        System.out.println();
        System.out.println("x position = " + getParent().getX());
        System.out.println("y position = " + getParent().getY());
        System.out.println("width = " + getParent().getWidth() + " <exact = " + getParent().getWidthExact() + ">");
        System.out.println("height = " + getParent().getHeight() + " <exact = " + getParent().getHeightExact() + ">");

        System.out.println("-----");
        System.out.println("Components (" + getParent().getComponents().size() + "):");
        System.out.println();

        for (GameObjectComponent component : getParent().getComponents()) {
            if (component.isEnabled()) {
                System.out.println("Enabled Component:");
            } else {
                System.out.println("Disabled Component:");
            }

            System.out.println("tag = " + component.getTag());
            System.out.println("name = " + component.getName());
            System.out.println();
        }

        System.out.println();
    }

    @Override
    public void onFixedTick() {

        if (fixedRate.now()) {
            debugLog();
        }
    }

    @Override
    public void draw(Graphics2D graphics) {

        graphics.setColor(Color.RED);
        graphics.fillOval((int) (getParent().getX() - 20), (int) (getParent().getY()) - 20, 20, 20);
    }

    @Override
    public void onCollision(CollisionEvent e) {

    }

    public void setGate(int gate) {
        fixedRate.setGate(gate);
    }
}
