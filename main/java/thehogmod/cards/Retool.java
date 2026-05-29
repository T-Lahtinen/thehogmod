package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ScrapAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Retool extends BaseCard {
    public static final String ID = makeID(Retool.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    public Retool() {
        super(ID, info);

        setCostUpgrade(0);
        setMagic(2);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScrapAction(1, this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Retool();
    }
}
