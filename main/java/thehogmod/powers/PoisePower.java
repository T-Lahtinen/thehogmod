package thehogmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.util.CustomTags;

import static thehogmod.TheHogMod.makeID;

public class PoisePower extends BasePower {
    public static final String POWER_ID = makeID("Poise");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PoisePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    private boolean cardIsReckless(AbstractCard c) {
        return c.hasTag(CustomTags.RECKLESS) || (AbstractDungeon.player.hasPower(FrenzyPower.POWER_ID) && c.type == AbstractCard.CardType.ATTACK);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return (cardIsReckless(card) && type == DamageInfo.DamageType.NORMAL) ? damage + (float)this.amount : damage;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (cardIsReckless(card)) {
            return (blockAmount += (float)this.amount) < 0.0F ? 0.0F : blockAmount;
        }

        return blockAmount;
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (cardIsReckless(usedCard) && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            if (this.amount == 1 && owner.hasPower(FrenzyPower.POWER_ID)) {
                AbstractPower frenzy = owner.getPower(FrenzyPower.POWER_ID);
                frenzy.flash();
                addToTop(new ApplyPowerAction(owner, owner, new PoisePower(owner, frenzy.amount), frenzy.amount));
            }

            addToTop(new ReducePowerAction(owner, owner, this, 1));

            if (AbstractDungeon.player.hasPower(RushOfBloodPower.POWER_ID)) {
                flash();
                addToBot(new ApplyLacerationAction(true, AbstractDungeon.player.getPower(RushOfBloodPower.POWER_ID).amount));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}