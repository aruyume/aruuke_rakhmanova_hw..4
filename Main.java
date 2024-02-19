import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int medicHeal = 100;
    public static int[] heroesHealth = {290, 270, 250, 230, 300, 200, 190, 180};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 0, 0, 5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Piercing", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int roundNumber = 0;
    public static boolean golemAlive = true;
    public static boolean luckyAlive = true;
    public static boolean witcherAlive = true;
    public static boolean thorAlive = true;
    public static boolean bossStunned = false;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;

    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossAttacks();
        }
        heroesAttack();
        medicHeal();
        golemDefender();
        luckyChance();
        witcherRevive();
        thorStunner();
        showStatistics();
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                int damage = bossDamage;
                if (heroesAttackType[i] == "Golem") {
                    damage = damage / 5;
                }
                heroesHealth[i] = heroesHealth[i] - damage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
    }


    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth = bossHealth - damage;
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static void medicHeal() {
        int medic = 3;
        if (heroesHealth[3] <= 0) {
            return;
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && i != medic) {
                heroesHealth[i] = heroesHealth[i] + medicHeal;
                System.out.println("Medic healed " + heroesAttackType[i] + " for " + medicHeal + " hp!!!");
                break;
            }
        }
    }

    public static void golemDefender() {
        if (golemAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                    heroesHealth[i] = heroesHealth[i] - bossDamage / 5;
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
        }
    }

    public static void luckyChance() {
        if (luckyAlive && bossDamage > 0) {
            Random random = new Random();
            boolean isLucky = random.nextBoolean();
            if (isLucky) {
                System.out.println("Lucky dodged the boss's punches!!!");
            }
        }
    }

    public static void witcherRevive() {
        if (witcherAlive) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] == 0 && i != 3) {
                    heroesHealth[i] = 200;
                    witcherAlive = false;
                    System.out.println("Witcher revive " + heroesAttackType[i] + " but died!!!");
                    break;
                }
            }
        }
    }

    public static void thorStunner() {
        if (thorAlive && !bossStunned) {
            Random random = new Random();
            boolean isStunned = random.nextBoolean();
            if (isStunned) {
                bossStunned = true;
                System.out.println("Thor stunned the boss for 1 round!!!");
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: "
                + bossDamage + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: "
                    + heroesDamage[i]);
        }
    }
}