package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.powers.HeatPower;
import thehogmod.util.CardStats;
import thehogmod.vfx.FirePunchEffect;

public class FlamingFist extends BaseCard {
    public static final String ID = makeID(FlamingFist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public FlamingFist() {
        super(ID, info);

        setDamage(7, 3);
        setMagic(3, 1);

        cardsToPreview = new FlashPowder();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        if (m != null) {
            int heatAmount = 0;
            AbstractPower heat = AbstractDungeon.player.getPower(HeatPower.POWER_ID);
            if (heat != null) heatAmount += heat.amount;
            addToBot(new VFXAction(new FirePunchEffect(m.hb.cX, m.hb.cY, heatAmount), 0.2F));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractCard c = new FlashPowder();
        if (upgraded) {
            c.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(c));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(damageUpgrade);
            this.upgradeMagicNumber(magicUpgrade);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlamingFist();
    }
}
