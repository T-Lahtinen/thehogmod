package thehogmod.rewards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.List;


public class CustomCardRewardItem extends RewardItem {
    public CustomCardRewardItem(List<AbstractCard> prebuiltCards) {
        this.hb = new Hitbox(460.0F * Settings.xScale, 90.0F * Settings.yScale);
        this.flashTimer = 0.0F;
        this.isDone = false;
        this.ignoreReward = false;
        this.redText = false;
        // this.reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.type = RewardType.CARD;
        // this.isBoss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
        this.cards = (java.util.ArrayList<AbstractCard>) prebuiltCards;
        this.text = TEXT[2];
    }
}
