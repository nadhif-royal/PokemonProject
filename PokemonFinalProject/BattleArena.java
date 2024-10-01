public interface BattleArena {
    void startBattle(PlayerMonster playerMonster, WildMonster wildMonster) throws MonsterException;
    void useItem(PlayerMonster playerMonster, Item item, WildMonster wildMonster, HomeBase homeBase) throws MonsterException;
    boolean runAttempt();
    boolean attemptCapture(WildMonster wildMonster, HomeBase homeBase);
}
