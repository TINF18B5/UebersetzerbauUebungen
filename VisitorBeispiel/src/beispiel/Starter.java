package beispiel;

import java.util.*;

public class Starter {
    
    public static void main(String[] args) {
        final List<Auto> autos = new ArrayList<>();
        autos.add(new Krankenwagen(50, 120));
        autos.add(new Personenwagen(30, 150));
        autos.add(new Rennauto(180, 250));
        final Personenwagen zuSchnellesAuto = new Personenwagen(200, 200);
        autos.add(zuSchnellesAuto);
        
        
        //Wir sind eine Behörde und haben den Auftrag, diese Autos zu überprüfen
        // 1) Alle Autos herausziehen, die zu schnell sind
        // 1.1) Stadt, 50 km/h außer es ist ein Krankenwagen mit Sonderrechten
        // 1.2) Land, 70 km/h außer es ist ein Krankenwagen mit Sirene an
        for(Auto auto : autos) {
            if(auto.geschwindigkeit > 50 && !(auto instanceof Krankenwagen)){
                System.out.println("Zu Schnell: " + auto);
            }
        }
        
        // Wir sind von einer Werkstatt und wählen als PR Gag zufällig Leute aus.
        // Wenn das Fahrzeug HU benötigt (= letzte HU 2 Jahre her) oder einen Boxenstop
        // und es kein Krankenwagen ist, dann gewinnen die Fahrzeugführer eine Wartung bei uns.
        // 2) Die Besitzer welcher Fahrzeuge sind die Gewinner?
        
        // Wir besitzen die Fahrzeuge in der Liste und wollen sie möglichst günstig versichern.
        // Welche Versicherung ist die günstigste für uns?
        // 3) Gesamt-Versicherungskosten der Autos vergleichen
        // 3.1) Agentur A: (Krankenwagen, 0), (PersonenWagen, 3), (Rennwagen 5)
        // 3.2) Agentur B: (Krankenwagen, 0), (PersonenWagen, 4), (Rennwagen, 4)
        // 3.3) Agentur C: (Krankenwagen, 3), (PersonenWagen, 3), (Rennwagen, 3)
        // 3.4) Agentur D:
        //      PersonenWagen -> 1 wenn MaxSpeed < 150, sonst 4
        //      KrankenWagen  -> 1 wenn Zweite Trage, sonst 2
        //      RennWagen     -> 3 wenn MaxSpeed > 250, sonst 6
    }
}
