package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.PoisePower;
import thehogmod.powers.UnravelPower;
import thehogmod.util.CardStats;

public class FirmGrip extends BaseCard {
    public static final String ID = makeID(FirmGrip.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public FirmGrip() {
        super(ID, info);

        setCustomVar("e", 2, 2);
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PoisePower(p, customVar("e")), customVar("e")));
        addToBot(new ApplyPowerAction(p, p, new UnravelPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FirmGrip();
    }
}
