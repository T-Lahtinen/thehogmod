
package thehogmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.util.*;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfTurnOrbs"
)
public class StartOfTurnResetPatch {

    @SpirePostfixPatch
    public static void afterApplyStartOfTurnOrbs(AbstractPlayer __instance) {
        RecklessTracker.recklessCardsPlayedTurn = 0;
        RecklessTracker.recklessDecreaseCost = false;
        ScrapTracker.scrappedThisTurn = 0;
        IgnitionTracker.enemiesIgnitedThisTurn = 0;
        IgnitionTracker.heatGainedThisTurn = 0;

        if (HPTracker.lastTurnHpWasLost) {
            HPTracker.lastTurnHpWasLostAmount = HPTracker.thisTurnHpLostAmount;
            HPTracker.lastTurnHpWasLost = false;
            HPTracker.thisTurnHpLostAmount = 0;
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof StartOfTurnListener) {
                ((StartOfTurnListener)c).onStartOfTurn();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof StartOfTurnListener) {
                ((StartOfTurnListener)c).onStartOfTurn();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof StartOfTurnListener) {
                ((StartOfTurnListener)c).onStartOfTurn();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof StartOfTurnListener) {
                ((StartOfTurnListener)c).onStartOfTurn();
            }
        }
    }
}

