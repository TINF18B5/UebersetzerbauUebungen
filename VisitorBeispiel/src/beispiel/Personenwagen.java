package beispiel;

import java.util.*;

public class Personenwagen extends Auto {
    
    public Date letzteHU = new Date();
    
    public Personenwagen(int geschwindigkeit, int maxGeschwindigkeit) {
        super(geschwindigkeit, maxGeschwindigkeit);
    }
}
