package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.powers.ScrapArmorPower;

public class MultiplyScrapArmorAction extends AbstractGameAction {
    private final int multiplyBy;

    public MultiplyScrapArmorAction(int multiplyBy) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.EXHAUST;
        this.multiplyBy = multiplyBy;
    }

    public void update() {
        if (this.source.hasPower(ScrapArmorPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(this.source, this.source, new ScrapArmorPower(this.source, this.source.getPower(ScrapArmorPower.POWER_ID).amount * this.multiplyBy), this.source.getPower(ScrapArmorPower.POWER_ID).amount * this.multiplyBy));
        }

        this.isDone = true;
    }
}
