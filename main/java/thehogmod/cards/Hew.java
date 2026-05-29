package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ScrapDiscardPileAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Hew extends BaseCard {
    public static final String ID = makeID(Hew.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public Hew() {
        super(ID, info);

        setDamage(9, 3);

        // TheHog.loadBetaArt(this, Hew.class.getSimpleName() + ".png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ScrapDiscardPileAction(p));
        // addToBot(new ScrapTopCardAction());
        // addToBot(new ApplyPowerAction(p, p, new HewPower(p), 1));
        // addToBot(new ScrapTopCardsAction(3));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hew();
    }
}
