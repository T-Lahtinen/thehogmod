package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ScrapAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Pulverize extends BaseCard {
    public static final String ID = makeID(Pulverize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Pulverize() {
        super(ID, info);

        setDamage(5, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, damage);
        addToBot(new DamageAction(m, info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ScrapAction(1, m, info, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pulverize();
    }
}
