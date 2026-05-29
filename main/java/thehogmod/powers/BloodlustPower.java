package thehogmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehogmod.TheHogMod.makeID;

public class BloodlustPower extends BasePower {
    public static final String POWER_ID = makeID("Bloodlust");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BloodlustPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}