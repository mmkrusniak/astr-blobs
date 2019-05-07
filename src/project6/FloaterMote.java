package project6;

import processing.core.PApplet;

import java.util.Map;

public class FloaterMote extends Mote {
    public static double RANDOM_ACTION = 0.00005;

    public FloaterMote() {
        super();
    }
    public FloaterMote(int size) {
        super(size);
    }

    @Override
    public void render(PApplet sketch) {
        sketch.stroke(120, 60, 20);
        sketch.fill(240, 120, 40);
        sketch.circle((int) x, (int) y, width/2);
    }

    @Override
    protected void update(double t, Map<String, Integer> inputMap) {
        super.update(t, inputMap);
        if(mass > 12 && Math.random() / mass < RANDOM_ACTION) eject(Math.random() * Math.PI * 2);
    }

    // Floater motes eject other floater motes, which is different
    @Override
    protected void eject(double dir) {

        double m = (Math.random() * mass)/32 + 2;
        dir += Math.random()/10 - Math.random()/10;

        Mote ejectee;
        if(Math.random() < 0.3) ejectee = new FloaterMote((int) m);
        else ejectee = new Mote(m);
        ejectee.x = x + (width/4.0+Math.sqrt(m)*10/4.0+2) * Math.cos(dir);
        ejectee.y = y + (width/4.0+Math.sqrt(m)*10/4.0+2) * Math.sin(dir);

        ejectee.move(dir, 10);
//        ejectee.update(1, null);
        move(dir+Math.PI, m*10);
        mass -= m;
        add(ejectee);
    }
}
