package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ScrapAction;
import thehogmod.actions.UnyieldingAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Unyielding extends BaseCard {
    public static final String ID = makeID(Unyielding.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    public Unyielding() {
        super(ID, info);

        setMagic(2, 1);
        setExhaust(true);

        // .loadBetaArt(this, Unyielding.class.getSimpleName() + ".png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new UnyieldingAction(p, 10));
        addToBot(new ScrapAction(magicNumber, upgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Unyielding();
    }
}
