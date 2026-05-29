package thehogmod.util;

import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.HashSet;
import java.util.Set;

public class RecklessTracker {
    public static int recklessCardsPlayedCombat = 0;
    public static int recklessCardsPlayedTurn = 0;
    public static int recklessCostIncreased = 0;
    public static int recklessCostDecreased = 0;
    public static Set<AbstractCreature> drawCardsEnemiesHit = new HashSet<>();
    public static boolean recklessDecreaseCost = false;
    public static int recklessIncreasedAttack = 0;
}
