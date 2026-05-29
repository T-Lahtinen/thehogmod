package thehogmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.savables.BonusCardRewardSavable;

@SpirePatch(clz = AbstractDungeon.class, method = "nextRoomTransitionStart")
public class ResetBonusRewardsPatch {
    @SpirePostfixPatch
    public static void reset() {
        BonusCardRewardSavable.reset();
        RestoreBonusRewardAfterLoadPatch.didRestore = false;
    }
}