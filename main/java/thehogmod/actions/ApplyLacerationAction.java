package thehogmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.powers.LacerationPower;
import thehogmod.relics.uncommon.ButcherKnife;

public class ApplyLacerationAction extends AbstractGameAction {
    private AbstractCreature target = null;
    private final int amount;
    private boolean isMulti = false;
    private boolean isFast = false;

    public ApplyLacerationAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    public ApplyLacerationAction(AbstractCreature target, int amount, boolean isFast) {
        this.target = target;
        this.amount = amount;
        this.isFast = isFast;
    }

    public ApplyLacerationAction(boolean isMulti, int amount) {
        this.amount = amount;
        this.isMulti = isMulti;
    }

    public void update() {
        int lacerationAmount = this.amount;
        if (AbstractDungeon.player.hasRelic(ButcherKnife.ID)) {
            lacerationAmount++;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(ButcherKnife.ID)));
            AbstractDungeon.player.getRelic(ButcherKnife.ID).flash();
        }
        if (isMulti) {
            int finalLacerationAmount = lacerationAmount;
            AbstractDungeon.actionManager.addToTop(new AllEnemyApplyPowerAction(AbstractDungeon.player, lacerationAmount, (AbstractMonster mo) -> new LacerationPower(mo, finalLacerationAmount)));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new LacerationPower(target, lacerationAmount), lacerationAmount, this.isFast));
        }

        this.isDone = true;
    }
}
