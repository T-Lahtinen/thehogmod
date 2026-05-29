package thehogmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.relics.uncommon.Metronome;

import static thehogmod.TheHogMod.makeID;

public class FrightPower extends BasePower {
    public static final String POWER_ID = makeID("Fright");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public FrightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            int blockAmt = this.amount;
            if (AbstractDungeon.player.hasRelic(Metronome.ID)) {
                blockAmt += 2;
                AbstractDungeon.player.getRelic(Metronome.ID).flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(Metronome.ID)));
            }
            flash();
            addToTop(new GainBlockAction(AbstractDungeon.player, blockAmt, Settings.FAST_MODE));
        }

        return damageAmount;
    }

    @Override
    public void atEndOfRound() {
        if (((AbstractMonster) owner).getIntentBaseDmg() >= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    /*@Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && AbstractDungeon.player.hasRelic(Metronome.ID)) {
            return damage - 2.0F;
        } else {
            return damage;
        }
    }*/

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}