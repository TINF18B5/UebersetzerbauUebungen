package beispiel;

public class Krankenwagen extends Auto {
    
    public boolean warnlicht;
    public boolean sonderrechteFrei;
    public boolean sireneAn;
    public boolean hatZweiteTrage;
    
    public Krankenwagen(int geschwindigkeit, int maxGeschwindigkeit) {
        super(geschwindigkeit, maxGeschwindigkeit);
    }
}
