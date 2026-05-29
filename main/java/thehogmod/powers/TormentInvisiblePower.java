package thehogmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thehogmod.TheHogMod.makeID;

public class TormentInvisiblePower extends BasePower implements InvisiblePower {
    public static final String POWER_ID = makeID("TormentInvisible");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TormentInvisiblePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && mo.hasPower(TormentPower.POWER_ID)) {
                    AbstractPower tormentPower = mo.getPower(TormentPower.POWER_ID);
                    tormentPower.flash();
                    addToBot(new DamageAction(mo, new DamageInfo(null, tormentPower.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                }
            }
        }
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}