package project6;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Arena {

    public boolean ANNIHILATE = false;

    private List<Entity> entities;
    private List<Entity> toRemove;
    private List<Entity> toAdd;

    protected int width; // in arena-inches
    protected int height; // in arena-inches

    public Arena(int width, int height) {

        this.entities =  new ArrayList<>();
        this.toRemove = new ArrayList<>();
        this.toAdd = new ArrayList<>();

        // An arbitrary size default
        this.width = width;
        this.height = height;
    }

    public void updateAll(double t, Map<String, Integer> inputMap) {
        for(Entity e: toRemove) entities.remove(e);
        entities.addAll(toAdd);
        toRemove.clear();
        toAdd.clear();

        for(Entity e: entities) {
            e.update(t, inputMap);
            constrain(e);
        }
        for(Entity e: entities) for(Entity o: entities) {
            if(e.intersectsWith(o)) e.intersect(o);
        }
    }


    public void renderAll(PApplet sketch) {
        for(Entity e: entities) {
            e.render(sketch);
        }
    }

    public void add(Entity e) {
        toAdd.add(e);
        e.setArena(this);
    }

    public Iterable<Entity> all() {
        return entities;
    }

    public Entity random() {
        return entities.get((int) (Math.random() * entities.size()));
    }

    public void remove(Entity e) {
        // We tend to remove things while traversing the list of entities
        // which can cause concurrency problems.
        // It's easier to maintain a list of entities to remove and
        // get rid of them whenever it's convenient.

        toRemove.add(e);
    }

    abstract void constrain(Entity e);


    public int getWidth() {return width; }
    public void setWidth(int width) {this.width = width; }
    public int getHeight() {return height; }
    public void setHeight(int height) {this.height = height; }
}
