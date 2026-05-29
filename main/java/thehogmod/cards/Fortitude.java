package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ScrapAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Fortitude extends BaseCard {
    public static final String ID = makeID(Fortitude.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Fortitude() {
        super(ID, info);

        setBlock(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ScrapAction(1, this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fortitude();
    }
}
