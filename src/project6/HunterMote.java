package project6;

import processing.core.PApplet;

import java.util.Map;

public class HunterMote extends Mote {

    public static double RANDOM_ACTION = 0.01;

    public HunterMote(int size) {
        super(size);
    }
    public HunterMote() {
        super();
    }
    Mote target;


    @Override
    public void render(PApplet sketch) {
        sketch.stroke(40, 40, 120);
        sketch.fill(80, 80, 240);
        sketch.circle((int) x, (int) y, width/2);



        if(target != null) {
            double relX = target.x - x;
            double relY = target.y - y;
            double target_dir = Math.atan(relY/relX) + (relX<0? Math.PI:0);
            System.out.println(target_dir);
            sketch.line((int) x, (int) y, (float) (x + (width/2+5) * Math.cos(target_dir)), (float) (y + (width/2+5) * Math.sin(target_dir)));
        }
    }

    @Override
    protected void update(double t, Map<String, Integer> inputMap) {
        super.update(t, inputMap);

        if(target == null || ! target.isAlive() || Math.random() < RANDOM_ACTION / 1000) {
            Entity e = arena.random();
            // brute force, would refactor this for sure
            while(! (e instanceof Mote)) e = arena.random();
            target = (Mote) e;
        }
        if(mass > 12 && Math.random() < RANDOM_ACTION) {
            double relX = target.x - x;
            double relY = target.y - y;
            double target_dir = Math.atan(relY/relX) + (relX<0? Math.PI:0);
            eject(target_dir + ((target.mass > mass)? 0:Math.PI));
        }
    }
}
