package beispiel;

import java.util.*;

public abstract class Auto {
    
    private static final Random random = new Random(0x815);
    
    public final int maxGeschwindigkeit;
    public final String kennzeichen;
    public int geschwindigkeit;
    
    public Auto(int geschwindigkeit, int maxGeschwindigkeit) {
        
        this.geschwindigkeit = geschwindigkeit;
        this.maxGeschwindigkeit = maxGeschwindigkeit;
        this.kennzeichen = String.format("%s%s %s%s-%s",
                (char) ('A' + random.nextInt(26)),
                (char) ('A' + random.nextInt(26)),
                (char) ('A' + random.nextInt(26)),
                (char) ('A' + random.nextInt(26)),
                Integer.toString(random.nextInt(10000), 10));
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "kennzeichen='" + kennzeichen + '\'' + ", geschwindigkeit=" + geschwindigkeit + '}';
    }
}
