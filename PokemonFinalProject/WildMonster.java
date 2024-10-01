import javax.swing.*;

public class WildMonster extends Monster {
    public WildMonster(String name, int level) {
        super(name, level, MonsterDex.getMonsterData(name).getBaseHP(), MonsterDex.getMonsterData(name).getBaseAP(), MonsterDex.getMonsterData(name).getElement());
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
        JOptionPane.showMessageDialog(null, this.name + " attacked " + target.getName() + " with " + damage + " Special Attack Damage.\n" +
                this.name + " receives " + (this.AP / 2) + " damage.");
    }

    @Override
    public void elementalAttack(Monster target) throws MonsterException {
        int damage;
        if (this.element.isEffectiveAgainst(target.getElement())) {
            damage = (int) (this.AP * 1.5);
            JOptionPane.showMessageDialog(null, "Enemy is Better");
        } else if(this.element.isWeakAgainst(target.getElement())){
            damage = this.AP / 2;
            JOptionPane.showMessageDialog(null, "Enemy is Better");
        } else {
            damage = this.AP;
        }
        target.HP -= damage;
        JOptionPane.showMessageDialog(null, this.name + " attack " + target.getName() + " with " + damage + " Elemental Damage.");
    }

    @Override
    public String toString() {
        return "WildMonster{" +
                "name='" + getName() + '\'' +
                ", HP=" + getHP() +
                ", AP=" + getAP() +
                ", element=" + getElement() +
                '}';
    }
}
