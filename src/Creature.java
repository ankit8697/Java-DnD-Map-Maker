public class Creature {
    boolean isPlayer;
    int hp;
    int maxHP;
    int initiativeBonus;
    int walkSpeed;
    int swimSpeed;
    int flySpeed;
    int burrowSpeed;
    int climbSpeed;
    int[] defaultSpeeds;
    String creatureType;
    int[] attackDistances;


    public Creature(boolean isPlayer,
                    int hp,
                    int initiativeBonus,
                    int walkSpeed,
                    int swimSpeed,
                    int flySpeed,
                    int burrowSpeed,
                    int climbSpeed,
                    String creatureType,
                    int[] attackDistances) {
        this.isPlayer = isPlayer;
        this.hp = hp;
        this.maxHP = hp;
        this.initiativeBonus = initiativeBonus;
        this.walkSpeed = walkSpeed;
        this.swimSpeed = swimSpeed;
        this.flySpeed = flySpeed;
        this.burrowSpeed = burrowSpeed;
        this.climbSpeed = climbSpeed;
        this.creatureType = creatureType;
        this.attackDistances = attackDistances;
    }

    public int[] getDefaultSpeeds() {
        return defaultSpeeds;
    }

    public void setDefaultSpeeds(int[] defaultSpeeds) {
        this.defaultSpeeds = defaultSpeeds;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public void incrementHP(int delta) {
        this.hp += delta;
        if (this.hp > this.maxHP) this.hp = this.maxHP;
    }

    public void decrementHP(int delta) {
        this.hp -= delta;
        if (this.hp < 0) this.hp = 0;
    }

    public int getInitiativeBonus() {
        return initiativeBonus;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public int getSwimSpeed() {
        return swimSpeed;
    }

    public void setSwimSpeed(int swimSpeed) {
        this.swimSpeed = swimSpeed;
    }

    public int getClimbSpeed() {
        return climbSpeed;
    }

    public void setClimbSpeed(int climbSpeed) {
        this.climbSpeed = climbSpeed;
    }

    public int getBurrowSpeed() {
        return burrowSpeed;
    }

    public void setBurrowSpeed(int burrowSpeed) {
        this.burrowSpeed = burrowSpeed;
    }

    public int getFlySpeed() {
        return flySpeed;
    }

    public void setFlySpeed(int flySpeed) {
        this.flySpeed = flySpeed;
    }

    public void setAllSpeeds(int speed) {
        this.burrowSpeed = speed;
        this.climbSpeed = speed;
        this.flySpeed = speed;
        this.swimSpeed = speed;
        this.walkSpeed = speed;
    }

    public void incrementAllSpeeds(int delta) {
        this.burrowSpeed += delta;
        this.climbSpeed += delta;
        this.flySpeed += delta;
        this.swimSpeed += delta;
        this.walkSpeed += delta;
    }

    public void decrementAllSpeeds(int delta) {
        this.incrementAllSpeeds(- delta);
    }

    public String getCreatureType() {
        return creatureType;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int[] getAttackDistances() {
        return attackDistances;
    }

    public void setAttackDistances(int[] attackDistances) {
        this.attackDistances = attackDistances;
    }
}