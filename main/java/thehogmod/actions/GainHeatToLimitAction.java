package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehogmod.powers.HeatPower;

public class GainHeatToLimitAction extends AbstractGameAction {
    public GainHeatToLimitAction(AbstractCreature source, int heatAmt) {
        setValues(source, source, heatAmt);
    }

    @Override
    public void update() {
        int targetHeat = this.amount;
        int currentHeat = 0;

        AbstractPower heat = source.getPower(HeatPower.POWER_ID);
        if (heat != null) {
            currentHeat = heat.amount;
        }

        if (currentHeat < targetHeat) {
            int toGain = targetHeat - currentHeat;
            addToTop(new GainHeatAction(toGain));
        }

        this.isDone = true;
    }
}
