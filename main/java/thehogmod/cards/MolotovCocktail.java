package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import thehogmod.actions.CustomPotionBounceEffect;
import thehogmod.actions.GainHeatAction;
import thehogmod.actions.IgniteAction;
import thehogmod.character.TheHog;
import thehogmod.powers.HeatPower;
import thehogmod.powers.IntensityPower;
import thehogmod.powers.OiledPower;
import thehogmod.util.CardStats;

public class MolotovCocktail extends BaseCard {
    public static final String ID = makeID(MolotovCocktail.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public MolotovCocktail() {
        super(ID, info);

        setCustomVar("e", 6, 3);
        setMagic(0);
        setExhaust(true);

        // TheHog.loadBetaArt(this, MolotovCocktail.class.getSimpleName() + ".png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(customVar("e")));

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new VFXAction(new CustomPotionBounceEffect(p.hb.cX, p.hb.cY + 50.0F, monster.hb.cX, this.hb.cY, Color.FIREBRICK), 0.4F));
            }
        }

        addToBot(new IgniteAction(true));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        int heatAmt = customVar("e");
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) heatAmt += heat.amount;

        this.baseMagicNumber = heatAmt;

        if (heatAmt >= customVar("e") && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
        }

        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(IntangiblePower.POWER_ID)) {
                heatAmt = 1;
                break;
            }
        }

        this.magicNumber = heatAmt;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;

        updateDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        int heatAmt = customVar("e");
        AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
        if (heat != null) heatAmt += heat.amount;

        this.baseMagicNumber = heatAmt;
        boolean modified = false;

        if (heatAmt >= customVar("e") && AbstractDungeon.player.hasPower(IntensityPower.POWER_ID)) {
            heatAmt += AbstractDungeon.player.getPower(IntensityPower.POWER_ID).amount;
            modified = true;
        }

        int shown = heatAmt;

        int monsterCount = 0;
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.hasPower(OiledPower.POWER_ID)) {
                monsterCount++;
            }

            if (!m.isDeadOrEscaped() && m.hasPower(IntangiblePower.POWER_ID)) {
                shown = 1;
                monsterCount = 0;
                modified = true;
                break;
            }
        }

        if (monsterCount == 1) {
            shown = heatAmt + (heatAmt / 2);
            modified = true;
        }

        this.magicNumber = shown;
        this.isMagicNumberModified = modified;

        updateDescription();
    }

    private void updateDescription() {
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new MolotovCocktail();
    }
}
