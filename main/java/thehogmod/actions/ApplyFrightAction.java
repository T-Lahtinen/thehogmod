package thehogmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.powers.FrightPower;

public class ApplyFrightAction extends AbstractGameAction {
    private AbstractCreature target = null;
    private int amount;
    private boolean isMulti = false;
    private boolean isFast = false;

    public ApplyFrightAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    public ApplyFrightAction(AbstractCreature target, int amount, boolean isFast) {
        this.target = target;
        this.amount = amount;
        this.isFast = isFast;
    }

    public ApplyFrightAction(boolean isMulti, int amount) {
        this.amount = amount;
        this.isMulti = isMulti;
    }

    public void update() {
        if (isMulti) {
            AbstractDungeon.actionManager.addToTop(new AllEnemyApplyPowerAction(AbstractDungeon.player, amount, (AbstractMonster mo) -> new FrightPower(mo, amount)));
        } else {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new FrightPower(target, amount), amount, isFast));
        }

        this.isDone = true;
    }
}
