package project6;

import processing.core.PApplet;

import java.util.Map;

/**
 * Created by commandm on 2/16/17.
 * Anything that moves according to set physics is an Entity.
 * Entities can update themselves every tick and render themselves.
 * They can also interact with Entities with which they intersect.
 */

public abstract class Entity {

    //Motion components:
    protected double x; //X position, in pixels
    protected double y; //Y position, in pixels
    protected double r; //Rotation, in radians
    protected double velX; //X velocity, in pixels per tick.
    protected double velY; //Y velocity, in pixels per tick.
    protected double velR; //Rotational velocity, in degrees per tick.
    protected double accX; //X acceleration, in pixels per tick per tick.
    protected double accY; //Y acceleration, in pixels per tick per tick.
    protected double accR; //Rotational acceleration, in degrees per tick per tick.

    public static double DRAG = 0; //The amount that an Entity naturally slows down each tick, per unit of velocity.
    private static double RDRAG = 0.5;

    //Size components:
    protected int width;
    protected int height;
    protected double mass = 1;

    //Entities can also be destroyed:
    protected double health = 1;

    // Other things
    protected Arena arena;

    //ID management
    protected int uuid = -1;

    Entity() {
        // Empty constructor.
    }

    Entity(int width, int height, int health) {
        this.width = width;
        this.height = height;
        this.health = health;
    }

    void applyDrag(double t) {

        velX -= DRAG * velX * t;
        velY -= DRAG * velY * t;
        velR -= RDRAG * velR * t;
    }

    void applyPhysics(double t) {
        r %= 6.28;

        applyDrag(t);

        velX += accX * t;
        velY += accY * t;
        velR += accR * t;

        x += velX * t;
        y += velY * t;
        r += velR * t;


    }

    double component(double x, double y, double dir) {
        return x/Math.cos(dir) + y/Math.cos(dir);
    }

    void repel(Entity e) {
        double rposX = x - e.getX();
        double rposY = y  - e.getY();

//        //Lay down the law for impossibly direct collisions
//        double velX = (Math.abs(this.velX) < 1)? this.velX+1:this.velX;
//        double velY = (Math.abs(this.velY) < 1)? this.velY+1:this.velY;
//        if(rposX == 0) rposX = 1;
//        if(rposY == 0) rposY = 1;


        double collisionAngle = Math.atan(rposY/rposX) + ((rposX < 0)? Math.PI : 0);
        double compoundVel = component(velX, velY, collisionAngle);
        double forceAngle = ((velX > 0)? Math.PI:0) + Math.atan(velY/velX);
        double reflectAngle = 2*collisionAngle-forceAngle;

//        move(reflectAngle,compoundVel * (1-percentMass));
        e.move( reflectAngle + Math.PI,compoundVel*mass);
    }

    void accelerate(double direction, double magnitude) {
        accX += magnitude * Math.cos(direction) / mass;
        accY += magnitude * Math.sin(direction) / mass;
    }

    void move(double direction, double magnitude) {
        velX += magnitude * Math.cos(direction) / mass;
        velY += magnitude * Math.sin(direction) / mass;
    }

    boolean isAlive() {
        if(health <= 0 || arena == null) return false;
        return true;
    }

    public abstract void render(PApplet sketch);
    protected abstract void update(double t, Map<String, Integer> inputMap);
    public abstract boolean intersectsWith(Entity e);
    public abstract void intersect(Entity e);
    protected abstract void onBirth();
    protected abstract void onDeath();

    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }
    public double getVelX() { return velX; }
    public double getVelY() { return velY; }
    public double getVelR() { return velR; }
    public double getAccX() { return accX; }
    public double getAccY() { return accY; }
    public double getAccR() { return accR; }
    public double getHealth() { return health; }
    public double getMass() { return mass; }
    public int getUUID() { return uuid; }
    public int getWidth() { return width; }
    public int getHeight() { return height;}
    public Arena getField() { return arena; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setR(double r) { this.r = r; }
    public void setVelX(double velX) { this.velX = velX; }
    public void setVelY(double velY) { this.velY = velY; }
    public void setVelR(double velR) { this.velR = velR; }
    public void setAccX(double accX) { this.accX = accX; }
    public void setAccY(double accY) { this.accY = accY; }
    public void setAccR(double accR) { this.accR = accR; }
    public void setHealth(double health) { this.health = health;}
    public void setWidth(int width) { this.width = width;}
    public void setHeight(int height) { this.height = height;}
    public void setMass(double mass) { this.mass = mass;}
    public Arena getArena() { return arena; }
    public void setArena(Arena arena) { this.arena = arena; }

    protected void setUUID(int uuid) {
        if(isAlive()) throw new IllegalStateException("Cannot change UUID of live entity");
        else this.uuid = uuid;
    }


    public final void die() {
        onDeath();
        arena.remove(this);
    }

    public final void appear(int uuid) {
        this.uuid = uuid;
        onBirth();
    }

    public void damage(double amount) {health -= amount;}
    public void heal(double amount) {health += amount;}
    public void add(Entity e) {arena.add(e);}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
