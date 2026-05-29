package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import thehogmod.powers.BasePower;
import thehogmod.powers.HeatPower;
import thehogmod.relics.BaseRelic;
import thehogmod.util.IgnitionTracker;


public class GainHeatAction extends AbstractGameAction {
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean isFast = false;

    public GainHeatAction(int amount) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
    }

    public GainHeatAction(int amount, boolean isFast) {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.isFast = isFast;
    }

    public void update() {
        if (!this.isFast) {
            addToTop(new VFXAction(p, new InflameEffect(p), 0.5F));
        } else {
            addToTop(new VFXAction(p, new InflameEffect(p), 0.2F));
        }
        int heatToGain = amount;

        addToTop(new ApplyPowerAction(p, p, new HeatPower(p, heatToGain), heatToGain));
        IgnitionTracker.heatGainedThisTurn += heatToGain;

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower power : m.powers) {
                if (power instanceof BasePower) {
                    ((BasePower) power).onPlayerGainedHeat(heatToGain);
                }
            }
        }

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) {
                ((BasePower) power).onPlayerGainedHeat(heatToGain);
            }
        }

        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) {
                ((BaseRelic) relic).onPlayerGainedHeat(heatToGain);
            }
        }


        this.isDone = true;
    }
}
