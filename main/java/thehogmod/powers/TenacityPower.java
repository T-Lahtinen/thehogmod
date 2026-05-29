package thehogmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static thehogmod.TheHogMod.makeID;

public class TenacityPower extends BasePower {
    public static final String POWER_ID = makeID("Tenacity");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TenacityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onRecklessCostChange(AbstractCard card, int newCost, boolean costIncreased) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && costIncreased) {
            flash();
            addToBot(new GainBlockAction(owner, amount, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}