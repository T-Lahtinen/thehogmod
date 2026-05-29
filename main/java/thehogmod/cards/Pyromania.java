package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatAction;
import thehogmod.actions.ModifyMagicNumberAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Pyromania extends BaseCard {
    public static final String ID = makeID(Pyromania.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Pyromania() {
        super(ID, info);

        setMagic(6, 2);

        cardsToPreview = new FlashPowder();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        addToBot(new ModifyMagicNumberAction(this.uuid, -1));
        addToBot(new MakeTempCardInHandAction(new FlashPowder(), 2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pyromania();
    }
}
