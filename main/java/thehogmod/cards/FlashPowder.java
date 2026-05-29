package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import thehogmod.actions.IgniteAction;
import thehogmod.powers.HeatPower;
import thehogmod.powers.IntensityPower;
import thehogmod.powers.OiledPower;
import thehogmod.util.CardStats;

public class FlashPowder extends BaseCard {
    public static final String ID = makeID(FlashPowder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    public FlashPowder() {
        super(ID, info);

        setMagic(0);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            addToBot(new IgniteAction(m, true));
        } else {
            addToBot(new IgniteAction(m));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int heatAmt = 0;
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) heatAmt = heat.amount;

        this.baseMagicNumber = heatAmt;

        if (heatAmt > 0 && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
        }

        this.magicNumber = heatAmt;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;

        updateDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        int heatAmt = 0;
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) heatAmt = heat.amount;

        this.baseMagicNumber = heatAmt;
        boolean modified = false;

        if (heatAmt > 0 && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
            modified = true;
        }

        int shown = heatAmt;

        if (mo != null && mo.hasPower(OiledPower.POWER_ID)) {
            shown = heatAmt + (heatAmt / 2);
            modified = true;
        }

        if (heatAmt > 0 && mo != null && mo.hasPower(IntangiblePower.POWER_ID)) {
            shown = 1;
            modified = true;
        }

        this.magicNumber = shown;
        this.isMagicNumberModified = modified;

        updateDescription();
    }

    private void updateDescription() {
        this.rawDescription = upgraded
                ? cardStrings.EXTENDED_DESCRIPTION[1]
                : cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = upgraded
                ? cardStrings.UPGRADE_DESCRIPTION
                : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlashPowder();
    }
}
