import java.util.Arrays;
import java.util.List;

public enum Element {
    FIRE, ICE, WIND, EARTH, WATER;

    public boolean isEffectiveAgainst(Element other) {
        switch (this) {
            case FIRE:
                return other == ICE;
            case ICE:
                return other == WIND;
            case WIND:
                return other == EARTH;
            case EARTH:
                return other == WATER;
            case WATER:
                return other == FIRE;
            default:
                return false;
        }
    }

    public boolean isWeakAgainst(Element other) {
        switch (this) {
            case FIRE:
                return other == WATER;
            case ICE:
                return other == FIRE;
            case WIND:
                return other == ICE;
            case EARTH:
                return other == WIND;
            case WATER:
                return other == EARTH;
            default:
                return false;
        }
    }


    public List<Element> getEvolutionElements() {
        switch (this) {
            case FIRE:
                return Arrays.asList(EARTH, WIND);
            case ICE:
                return Arrays.asList(WATER, EARTH);
            case WIND:
                return Arrays.asList(FIRE, ICE);
            case EARTH:
                return Arrays.asList(ICE, FIRE);
            case WATER:
                return Arrays.asList(WIND, EARTH);
            default:
                return Arrays.asList();
        }
    }
}
