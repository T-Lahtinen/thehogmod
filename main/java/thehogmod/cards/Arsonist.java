package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.powers.ArsonistPower;
import thehogmod.util.CardStats;

public class Arsonist extends BaseCard {
    public static final String ID = makeID(Arsonist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Arsonist() {
        super(ID, info);

        setMagic(6, 4);

        cardsToPreview = new FlashPowder();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ArsonistPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Arsonist();
    }
}
