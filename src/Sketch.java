import processing.core.PApplet;
import project6.*;
import g4p_controls.*;

import java.util.HashMap;
import java.util.Map;

public class Sketch extends PApplet {

    Arena arena;
    Mote b;
    GTextField text;

    // Unfortunately due to the way the input map works this is an int
    private int mouseClicked = 0;

    public void setup() {
        arena = new CircleArena(1000);

        text = new GTextField(this, 5, 5, 150, 20);
        text.setFocus(true);
    }

    public void settings() {
        fullScreen();
//        size(500, 500);
    }

    public void draw() {
        background(0, 0, 0);
        arena.updateAll(0.4, input());
        arena.renderAll(this);
    }

    public Map<String, Integer> input() {
        Map<String, Integer> result = new HashMap<>();
        result.put("mouseX", mouseX-width/2);
        result.put("mouseY", mouseY-height/2);
        result.put("mouseClick", mouseClicked);
        if(mouseClicked == 1) mouseClicked = 0; // Once action per click, please
        return result;
    }

    public void mousePressed() {
        mouseClicked = 1;
    }

    public void keyPressed() {
        if(key == '/') text.setFocus(true);
    }

    public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) {
        if(event == GEvent.ENTERED) {
            String text = textcontrol.getText();
            if(text.charAt(0) == '/') text = text.substring(1);
            System.out.println(text);
            if(Sketch.class.getClassLoader().getResource(text + ".astr156") == null) return;
            arena = FileReader.readFile(text);
        }
    }
}
