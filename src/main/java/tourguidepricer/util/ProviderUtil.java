package tourguidepricer.util;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

@Repository
public class ProviderUtil {

    public String getProviderName(String apiKey, int adults) {
        int multiple = ThreadLocalRandom.current().nextInt(1, 10);
        switch (multiple) {
            case 1:
                return "Holiday Travels";
            case 2:
                return "Enterprize Ventures Limited";
            case 3:
                return "Sunny Days";
            case 4:
                return "FlyAway Trips";
            case 5:
                return "United Partners Vacations";
            case 6:
                return "Dream Trips";
            case 7:
                return "Live Free";
            case 8:
                return "Dancing Waves Cruselines and Partners";
            case 9:
                return "AdventureCo";
            default:
                return "Cure-Your-Blues";
        }
    }
}
