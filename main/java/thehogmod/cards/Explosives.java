package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatToLimitAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Explosives extends BaseCard {
    public static final String ID = makeID(Explosives.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    public Explosives() {
        super(ID, info);

        setMagic(2, 1);

        cardsToPreview = new FlashPowder();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatToLimitAction(p, 6));
        addToBot(new MakeTempCardInHandAction(new FlashPowder(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Explosives();
    }
}
