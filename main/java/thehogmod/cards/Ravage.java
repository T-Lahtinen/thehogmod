package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Ravage extends BaseCard {
    public static final String ID = makeID(Ravage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public Ravage() {
        super(ID, info);

        setDamage(16, 6);
        setMagic(16, 6);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, false)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void triggerOnManualScrap() {
        AbstractCard c = this.makeStatEquivalentCopy();
        c.baseDamage += magicNumber;
        addToTop(new MakeTempCardInDiscardAction(c, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ravage();
    }
}
