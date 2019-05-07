package project6;

import processing.core.*;

import java.util.Map;

public class Mote extends Entity {


    public Mote() {
        
    }

    public Mote(double size) {
        super((int) size, (int) size, (int) size);
        // Mass is going to be very important for this one
        setMass(size);

    }

    @Override
    public void render(PApplet sketch) {
        sketch.stroke(100, 70, 20);
        sketch.fill(200, 140, 40);
        sketch.circle((int) x, (int) y, width/2);
    }

    @Override
    protected void update(double t, Map<String, Integer> inputMap) {
        width = (int) ((Math.sqrt(mass) + 0.5) * 10);
        applyPhysics(t);

        if(mass <= 0) die();
    }

    @Override
    public boolean intersectsWith(Entity e) {
        // Motes treat everything circularly.
        // (Basically, because I'm abusing the entity framework, but shhh)
        if(e == this) return false;
        double dist = Math.sqrt((e.getX() - x) * (e.getX() - x) + (e.getY() - y) * (e.getY() - y));
        return dist <= (width/4.0 + e.width/4.0);
    }

    @Override
    public void intersect(Entity e) {

        // We interact differently with different Entities...
        // instanceof isn't a good practice keyword, but haters gonna hate

        if(e instanceof Mote) {
            if(e.getMass() < mass && e.mass > 0) {
                double m = e.mass/4+1;
                mass += m;
                e.setMass(e.mass - m);

                // Repel!
                velX += e.velX * (m/mass);
                velY += e.velY * (m/mass);
                e.velX -= e.velX * (m/mass);
                e.velY -= e.velY * (m/mass);
            }
        }
    }

    @Override
    protected void onBirth() {
        //Nothing to be done here
    }

    @Override
    protected void onDeath() {
        //Nothing to be done here
    }

    @Override
    boolean isAlive() {
        return super.isAlive() && mass > 0;
    }

    // Motes move by ejecting small bits of mass.
    protected void eject(double dir) {

        double m = (Math.random() * mass)/32 + 2;
        dir += Math.random()/10 - Math.random()/10;

        Mote ejectee = new Mote(m);
        ejectee.x = x + (width/4.0+Math.sqrt(m)*10/4.0+2) * Math.cos(dir);
        ejectee.y = y + (width/4.0+Math.sqrt(m)*10/4.0+2) * Math.sin(dir);

        ejectee.move(dir, 10);
//        ejectee.update(1, null);
        move(dir+Math.PI, m*10);
        mass -= m;
        add(ejectee);
    }
}
