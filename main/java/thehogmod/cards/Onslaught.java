package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Onslaught extends BaseCard {
    public static final String ID = makeID(Onslaught.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    public Onslaught() {
        super(ID, info);

        setDamage(8, 2);
        setMagic(2, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ApplyFrightAction(true, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Onslaught();
    }
}
