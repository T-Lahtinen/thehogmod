package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Heatstroke extends BaseCard {
    public static final String ID = makeID(Heatstroke.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    public Heatstroke() {
        super(ID, info);

        setBlock(10, 3);
        setMagic(1, 1);

        cardsToPreview = new FlashPowder();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new GainHeatAction(5));
        addToBot(new MakeTempCardInHandAction(new FlashPowder(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Heatstroke();
    }
}
