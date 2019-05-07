package project6;

import processing.core.PApplet;

public class CircleArena extends Arena {


    public CircleArena(int width) {
        super(width, width);
    }

    @Override
    public void renderAll(PApplet sketch) {
        sketch.translate(sketch.width/2, sketch.height/2);
        sketch.scale(sketch.height/((float) width));
        sketch.fill(0, 0, 0);
        if(ANNIHILATE) sketch.stroke(200, 0, 0);
        else sketch.stroke(100, 100, 100);
        sketch.circle(0, 0, width);
        super.renderAll(sketch);
        sketch.scale(((float) width)/sketch.height);
        sketch.translate(-sketch.width/2, -sketch.height/2);
    }

    @Override
    void constrain(Entity e) {
        if(! e.isAlive()) {
            // Important talk about the difference between e.die() and remove(e):
            //  in this framework I've made, e.die() is the nicer way to kill things
            //  because it lets them run whatever they want before politely removing themselves.
            //  remove(e) deletes the entity instantly, stopping it from updating or rendering
            //  but not necessarily taking it out of the picture.
            e.die();
            return;
        }

        // Valid positions are within the circle centered on (width/2, width/2)
        // of radius width/2 as well

        // One of my favorite hacks: square root is inefficient to calculate,
        // so take the square of both sides: multiplication and addition are easy
        if(e.getX() * e.getX() + e.getY() * e.getY() > (width-e.getWidth()/2.0) * (width-e.getWidth()/2.0) / 4.0) {
            if(ANNIHILATE) {
                e.die();
                return;
            }

            double aWall = Math.atan(e.getY()/e.getX()) + (e.getX()<0? Math.PI:0);

            e.setX((width/2.0-e.getWidth()/4.0) * Math.cos(aWall));
            e.setY((width/2.0-e.getWidth()/4.0) * Math.sin(aWall));

            double aColl = Math.atan(e.getVelY()/e.getVelX()) + (e.getVelX()<0? Math.PI:0);
            double bounceAngle = aColl - 2*(aColl - aWall);


            double totalSpeed = Math.sqrt(e.getVelX() * e.getVelX() + e.getVelY() * e.getVelY());

            e.setVelX(-totalSpeed * Math.cos(bounceAngle));
            e.setVelY(-totalSpeed * Math.sin(bounceAngle));

            // Give it an extra update to get out of the border
//            e.update(1, null);
        }
    }
}
