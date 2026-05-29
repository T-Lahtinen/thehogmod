package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;
import thehogmod.character.TheHog;
import thehogmod.powers.FatiguePower;
import thehogmod.util.CardStats;

public class Delirium extends BaseCard {
    public static final String ID = makeID(Delirium.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Delirium() {
        super(ID, info);

        setCostUpgrade(0);
        setMagic(2);

        cardsToPreview = new Fatigue();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DrawPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new FatiguePower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Delirium();
    }
}
