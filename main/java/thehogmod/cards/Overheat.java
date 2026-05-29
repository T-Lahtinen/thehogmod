package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import thehogmod.actions.PandemoniumAction;
import thehogmod.character.TheHog;
import thehogmod.powers.HeatPower;
import thehogmod.powers.IntensityPower;
import thehogmod.powers.OiledPower;
import thehogmod.util.CardStats;

public class Overheat extends BaseCard {
    public static final String ID = makeID(Overheat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    public Overheat() {
        super(ID, info);

        setCustomVar("e", 4, 2);
        setMagic(0);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(HeatPower.POWER_ID)) {
            for (int i=0; i < customVar("e"); i++) {
                addToBot(new PandemoniumAction());
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int heatAmt = 0;
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) {
            heatAmt += heat.amount;
        }

        this.baseMagicNumber = heatAmt;
        if (heatAmt >= 0 && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
        }

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (heatAmt > 1 && !m.isDeadOrEscaped() && m.hasPower(IntangiblePower.POWER_ID)) {
                heatAmt = 1;
                break;
            }
        }

        this.magicNumber = heatAmt;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
        this.updateDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int heatAmt = 0;
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) {
            heatAmt += heat.amount;
        }

        this.baseMagicNumber = heatAmt;
        boolean modified = false;
        if (heatAmt >= 0 && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
            modified = true;
        }

        int shown = heatAmt;
        int monsterCount = 0;

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(OiledPower.POWER_ID)) {
                ++monsterCount;
            }

            if (heatAmt > 1 && !m.isDeadOrEscaped() && m.hasPower(IntangiblePower.POWER_ID)) {
                shown = 1;
                monsterCount = 0;
                modified = true;
                break;
            }
        }

        if (monsterCount == 1) {
            shown = heatAmt + heatAmt / 2;
            modified = true;
        }

        this.magicNumber = shown;
        this.isMagicNumberModified = modified;
        this.updateDescription();
    }

    private void updateDescription() {
        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = this.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Overheat();
    }
}
