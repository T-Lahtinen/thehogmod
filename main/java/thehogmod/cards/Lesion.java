package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Lesion extends BaseCard {
    public static final String ID = makeID(Lesion.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public Lesion() {
        super(ID, info);

        setMagic(6, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyLacerationAction(m, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lesion();
    }
}
