package thehogmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehogmod.TheHogMod.makeID;

public class RazorhidePower extends BasePower {
    public static final String POWER_ID = makeID("Razorhide");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RazorhidePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }
}