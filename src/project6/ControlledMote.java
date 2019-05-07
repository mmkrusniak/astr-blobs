package project6;

import processing.core.PApplet;

import java.util.Map;

public class ControlledMote extends Mote {


    public ControlledMote() {

    }

    public ControlledMote(int size) {
        super(size);
    }


    @Override
    public void render(PApplet sketch) {
        sketch.stroke(20, 100, 70);
        sketch.fill(40, 200, 140);
        sketch.circle((int) x, (int) y, width/2);
    }

    @Override
    public void update(double t, Map<String, Integer> input) {
        super.update(t, input);


        if(input != null && input.get("mouseClick") == 1) {
            double relX = input.get("mouseX") - x;
            double relY = input.get("mouseY") - y;

            double dir;
            if (relX > 0) dir = Math.atan(relY/relX);
            else  dir = Math.PI + Math.atan(relY/relX);

            eject(dir);
        }
    }
}
