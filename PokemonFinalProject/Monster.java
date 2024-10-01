public abstract class Monster {
    protected String name;
    protected int level;
    protected int baseHP;
    protected int HP;
    protected int baseAP;
    protected int AP;
    protected int exp;
    protected Element element;

    public Monster(String name, int level, int baseHP, int baseAP, Element element) {
        this.name = name;
        this.level = level;
        this.baseHP = baseHP;
        this.baseAP = baseAP;
        this.exp = 0;
        this.element = element;
        calculateStats();
    }

    public Monster(String name, int baseHP, int baseAP, Element element) {
        this.name = name;
        this.baseHP = baseHP;
        this.baseAP = baseAP;
        this.exp = 0;
        this.element = element;
        calculateStats();
    }

    protected void calculateStats() {
        this.HP = baseHP + (baseHP * (level - 1) / 10);
        this.AP = baseAP + (baseAP * (level - 1) / 10);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getBaseHP() {
        return baseHP;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getBaseAP() {
        return baseAP;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP){
        this.AP = AP;
    }

    public Element getElement() {
        return element;
    }

    public int getExp() {
        return exp;
    }

    public void evolve(Element newElement) throws MonsterException{

    }
    public abstract void attack(Monster target) throws MonsterException;
    public abstract void specialAttack(Monster target) throws MonsterException;
    public abstract void elementalAttack(Monster target) throws MonsterException;
}
