package thehogmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import thehogmod.rewards.CustomCardRewardItem;
import thehogmod.rewards.SavedCardData;
import thehogmod.savables.BonusCardRewardSavable;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(clz = AbstractDungeon.class, method = "update")
public class RestoreBonusRewardAfterLoadPatch {
    public static boolean didRestore = false;

    @SpirePostfixPatch
    public static void restoreBonusRewards() {
        if (didRestore) return;

        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null) {
            for (List<SavedCardData> saved : BonusCardRewardSavable.savedBonusRewards) {
                List<AbstractCard> cards = new ArrayList<>();

                for (SavedCardData entry : saved) {
                    AbstractCard c = CardLibrary.getCard(entry.cardID).makeCopy();
                    if (entry.upgraded) c.upgrade();
                    cards.add(c);
                }

                RewardItem bonus = new CustomCardRewardItem(cards);
                AbstractDungeon.getCurrRoom().rewards.add(bonus);
                AbstractDungeon.combatRewardScreen.rewards.add(bonus);
            }

            AbstractDungeon.combatRewardScreen.positionRewards();
            didRestore = true;
        }
    }
}
