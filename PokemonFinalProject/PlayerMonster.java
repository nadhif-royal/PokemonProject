import javax.swing.JOptionPane;

public class PlayerMonster extends Monster {
    private int level;
    private int maxHP;
    private int xpThreshold;
    private HomeBase homeBase;
    private int evoP = 5;

    public PlayerMonster(String name, int level, HomeBase homeBase) {
        super(name, MonsterDex.getMonsterData(name).getBaseHP(), MonsterDex.getMonsterData(name).getBaseAP(), MonsterDex.getMonsterData(name).getElement());
        this.level = level;
        this.exp = 0;
        this.maxHP = HP;
        this.xpThreshold = level * 100;
        this.homeBase = homeBase;
    }

    public PlayerMonster(String name, int level, int baseHP, int baseAP, Element element, HomeBase homeBase){
        super(name, level, baseHP, baseAP, element);
        this.level = level;
        calculateStats();
        this.maxHP = HP;
        this.xpThreshold = level * 100;
        this.homeBase = homeBase;
    }

    public PlayerMonster(String name, int level, int exp, int HP, int AP, Element element, int maxHP, int baseHP, int baseAP, int evoP, HomeBase homeBase) {
        super(name, baseHP, baseAP, element);
        this.level = level;
        this.exp = exp;
        this.HP = HP;
        this.AP = AP;
        this.evoP = evoP;
        this.maxHP = maxHP;
        this.xpThreshold = level * 100;
        this.homeBase = homeBase;
    }

    public HomeBase getHomeBase() {
        return homeBase;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getExp() {
        return exp;
    }

    public int getEvoP() {
        return evoP;
    }

    public void setXpThreshold(int xpThreshold) {
        this.xpThreshold = xpThreshold;
    }

    public void addExp(int exp) {
        this.exp += exp;
        if (this.exp >= xpThreshold) {
            levelUp();
        }
    }

    public void levelUp() {
        this.exp -= xpThreshold;
        this.level++;
        this.evoP++;
        this.setXpThreshold(level * 100);
        this.setMaxHP(this.getMaxHP() + (baseHP/10));
        this.setHP(this.getMaxHP());
        this.setAP(this.getAP() + (baseAP/10));
        JOptionPane.showMessageDialog(null, this.getName() + " has leveled up to level " + this.getLevel() + "!");
    }

    public void heal(int value) {
        this.HP += value;
        if(this.HP > maxHP){
            this.HP = maxHP;
        }
        JOptionPane.showMessageDialog(null, this.name + " healed for " + value + " HP.");
    }

    public void heal() {
        this.HP = maxHP;
    }

    public void evolve(Element newElement) throws MonsterException {
        if (this.evoP >= 1){
            if (this.element.getEvolutionElements().contains(newElement)) {
                this.element = newElement;
                JOptionPane.showMessageDialog(null, this.name + " has evolved to " + newElement + " element!");
                this.evoP--;
            } else {
                throw new MonsterException(this.name + " cannot evolve to " + newElement + " element!");
            }
        } else {
            JOptionPane.showMessageDialog(null, this.name + " not enough energy");
        }

    }

    @Override
    public void attack(Monster target) throws MonsterException {
        int damage = this.AP;
        target.HP -= damage;
        JOptionPane.showMessageDialog(null, this.name + " attacked " + target.getName() + " for " + damage + " Damage.");
    }

    @Override
    public void specialAttack(Monster target) throws MonsterException {
        int damage = this.AP * 2;
        target.HP -= damage;
        this.HP -= this.AP / 2;
        JOptionPane.showMessageDialog(null, this.name + " attacked " + target.getName() + " with " + damage + " Special Damage.\n" +
                this.name + " receives " + (this.AP / 2) + " damage.");
    }

    @Override
    public void elementalAttack(Monster target) throws MonsterException {
        int damage;
        if (this.element.isEffectiveAgainst(target.getElement())) {
            damage = (int) (this.AP * 1.5);
            JOptionPane.showMessageDialog(null, "Great!");
        } else if(this.element.isWeakAgainst(target.getElement())){
            damage = this.AP / 2;
            JOptionPane.showMessageDialog(null, "Great!");
        } else {
            damage = this.AP;
        }
        target.HP -= damage;
        JOptionPane.showMessageDialog(null, this.name + " attacked " + target.getName() + " with " + damage + " Elemental Damage.");
    }

    public void elementalAttack(Monster target, int value, Element element) throws MonsterException {
        int damage = value;
        Element element2 = element;
        if (element2.isEffectiveAgainst(target.getElement())) {
            damage *= 1.5;
            JOptionPane.showMessageDialog(null, "Great!");
        } else if(element2.isWeakAgainst(target.getElement())){
            damage /= 2;
            JOptionPane.showMessageDialog(null, "Great!");
        }
        target.HP -= damage;
        JOptionPane.showMessageDialog(null, this.name + " attacked " + target.getName() + " with " + damage + " Elemental Damage.");
    }

    @Override
    public String toString() {
        return "PlayerMonster{" +
                "name='" + getName() + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", HP=" + getHP() +
                ", AP=" + getAP() +
                ", element=" + getElement() +
                '}';
    }
}
