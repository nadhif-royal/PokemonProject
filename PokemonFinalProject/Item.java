public class Item {
    private String name;
    private String effect;
    private int value;
    private Element element;

    public Item(String name, String effect, int value) {
        this.name = name;
        this.effect = effect;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getValue() {
        return value;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }


}

class ElementStone extends Item {

    public ElementStone(String name, Element element, int value) {
        super(name, "elemental", value);
        setElement(element);
    }
}