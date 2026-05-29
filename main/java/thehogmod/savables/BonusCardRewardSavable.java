package thehogmod.savables;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thehogmod.patches.RestoreBonusRewardAfterLoadPatch;
import thehogmod.rewards.SavedCardData;

import java.util.ArrayList;
import java.util.List;

public class BonusCardRewardSavable implements CustomSavable<List<List<SavedCardData>>> {
    public static List<List<SavedCardData>> savedBonusRewards = new ArrayList<>();

    @Override
    public List<List<SavedCardData>> onSave() {
        return savedBonusRewards;
    }

    @Override
    public void onLoad(List<List<SavedCardData>> data) {
        RestoreBonusRewardAfterLoadPatch.didRestore = false;
        savedBonusRewards = (data != null) ? data : new ArrayList<>();
    }

    public static void addBonus(List<AbstractCard> cards) {
        List<SavedCardData> entry = new ArrayList<>();
        for (AbstractCard c : cards) {
            entry.add(new SavedCardData(c.cardID, c.upgraded));
        }
        savedBonusRewards.add(entry);
    }

    public static void reset() {
        savedBonusRewards.clear();
    }
}
