package project6;

import processing.core.PApplet;

import java.util.Map;

public class AttractorMote extends Mote {

    double strength;
    boolean fixed;

    public AttractorMote() {

    }

    public AttractorMote(double size, double strength) {
        super(size);
        this.strength = strength;
    }

    @Override
    public void render(PApplet sketch) {
        if(strength > 0) {
            sketch.stroke(100, 20, 70);
            sketch.fill(200, 40, 140);
        } else {
            sketch.stroke(90, 20, 90);
            sketch.fill(180, 40, 180);
        }
        sketch.circle((int) x, (int) y, width/2);
    }

    @Override
    public void update(double t, Map<String, Integer> input) {
        width = (int) ((Math.sqrt(mass) + 0.5) * 10);
        if(! fixed) applyPhysics(t);

        if(mass <= 0) die();

        double dir, dist;
        for(Entity e: arena.all()) {
            if(e == this) continue;
            dir = Math.atan((y - e.getY()) / (x - e.getX())) + ((x - e.getX() > 0)? 0 : Math.PI);
            dist = Math.sqrt((y - e.getY()) * (y - e.getY()) + (x - e.getX()) * (x - e.getX()));

            e.move(dir, strength * mass * e.mass/dist);
        }
    }

    public double getStrength() { return strength; }
    public void setStrength(double strength) { this.strength = strength; }


    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
