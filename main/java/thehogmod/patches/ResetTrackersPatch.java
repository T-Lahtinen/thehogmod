package thehogmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import thehogmod.util.*;

import java.util.HashSet;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "preBattlePrep"
)
public class ResetTrackersPatch {
    @SpirePostfixPatch
    public static void resetHpLossTracker(AbstractPlayer __instance) {
        HPTracker.lastTurnHpWasLost = false;
        HPTracker.lastTurnHpWasLostAmount = 0;
        HPTracker.thisTurnHpLostAmount = 0;
        IgnitionTracker.enemiesIgnitedThisCombat = 0;
        IgnitionTracker.enemiesIgnitedThisTurn = 0;
        IgnitionTracker.heatGainedThisTurn = 0;
        RecklessTracker.recklessCardsPlayedCombat = 0;
        RecklessTracker.recklessCardsPlayedTurn = 0;
        RecklessTracker.recklessCostIncreased = 0;
        RecklessTracker.recklessCostDecreased = 0;
        RecklessTracker.drawCardsEnemiesHit = new HashSet<>();
        RecklessTracker.recklessIncreasedAttack = 0;
        ScrapTracker.scrappedThisTurn = 0;
        ScrapTracker.scrappedThisCombat = 0;
        ScrapTracker.scrapToGain = 0;
    }
}
