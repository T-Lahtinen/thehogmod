package thehogmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static thehogmod.TheHogMod.makeID;

public class FerocityPower extends BasePower {
    public static final String POWER_ID = makeID("Ferocity");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int strengthAmt;

    public FerocityPower(AbstractCreature owner, int strengthAmt) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 5);

        this.strengthAmt = strengthAmt;
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.strengthAmt += stackAmount;
        this.updateDescription();
    }

    public void onRecklessCostChange(AbstractCard card, int newCost, boolean costIncreased) {
        if (costIncreased) {
            --this.amount;
            if (this.amount == 0) {
                this.flash();
                this.amount = 5;
                this.addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, this.strengthAmt), this.strengthAmt));
            }

            this.updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.strengthAmt + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.strengthAmt + DESCRIPTIONS[2];
        }
    }
}