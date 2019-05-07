package project6;

import java.io.InputStream;
import java.util.Scanner;

public class FileReader {

    public static Arena readFile(String filename) {
        Scanner scan;
        InputStream stream = FileReader.class.getClassLoader().getResourceAsStream( filename + ".astr156");
        if(stream == null) return new CircleArena(1000);
        scan = new Scanner(stream);

        String line;
        Mote mote = new Mote(1);
        Arena arena = new CircleArena(1000);

        while(scan.hasNext()) {
            line = scan.nextLine();

            if(line.contains("type:")) {
                if(line.contains("normal")) mote = new Mote();
                if(line.contains("hunter")) mote = new HunterMote();
                if(line.contains("floater")) mote = new FloaterMote();
                if(line.contains("attractor")) mote = new AttractorMote();
                if(line.contains("controlled")) mote = new ControlledMote();
            }

            if(line.contains("size:")) mote.setMass(Double.parseDouble(line.replace("size:", "").trim()));
            if(line.contains("x:")) mote.setX(Double.parseDouble(line.replace("x:", "").trim()));
            if(line.contains("y:")) mote.setY(Double.parseDouble(line.replace("y:", "").trim()));
            if(line.contains("velX:")) mote.setVelX(Double.parseDouble(line.replace("velX:", "").trim()));
            if(line.contains("velY:")) mote.setVelY(Double.parseDouble(line.replace("velY:", "").trim()));
            if(line.contains("strength:") && mote instanceof AttractorMote) {
                ((AttractorMote) mote).setStrength(Double.parseDouble(line.replace("strength:", "").trim()));
            }
            if(line.contains("fixed:") && mote instanceof AttractorMote) {
                ((AttractorMote) mote).setFixed(Boolean.parseBoolean(line.replace("fixed:", "").trim()));
            }


            if(line.contains("#drag:")) Entity.DRAG = (Double.parseDouble(line.replace("#drag:", "").trim()));
            if(line.contains("#floater_aggr:")) FloaterMote.RANDOM_ACTION = (Double.parseDouble(line.replace("#floater_aggr:", "").trim()));
            if(line.contains("#hunter_aggr:")) HunterMote.RANDOM_ACTION = (Double.parseDouble(line.replace("#hunter_aggr:", "").trim()));
            if(line.contains("#add#")) arena.add(mote);
            if(line.contains("#annihilate#")) arena.ANNIHILATE = true;
            if(line.contains("#width:")) {
                int size = Integer.parseInt(line.replace("#width:", "").trim());
                arena.setHeight(size);
                arena.setWidth(size);
            }
        }
        return arena;
    }
}
