package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.powers.IntensityPower;
import thehogmod.util.IgnitionTracker;

public class ScorchAction extends AbstractGameAction {
    public ScorchAction(int gainAmount) {
        setValues(AbstractDungeon.player, AbstractDungeon.player, gainAmount);
    }

    @Override
    public void update() {
        if (IgnitionTracker.heatGainedThisTurn > 0) {
            addToTop(new ApplyPowerAction(this.source, this.source, new IntensityPower(this.source, this.amount), this.amount));
        }

        this.isDone = true;
    }
}
