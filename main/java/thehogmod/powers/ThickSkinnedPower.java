package thehogmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehogmod.TheHogMod.makeID;

public class ThickSkinnedPower extends BasePower {
    public static final String POWER_ID = makeID("ThickSkinned");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ThickSkinnedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = this.DESCRIPTIONS[1] + this.amount + this.DESCRIPTIONS[2];
        } else {
            this.description = this.DESCRIPTIONS[0];
        }
    }
}