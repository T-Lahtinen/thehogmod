package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.IncreaseCostAction;
import thehogmod.util.CardStats;

public class Fatigue extends BaseCard {
    public static final String ID = makeID(Fatigue.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            2
    );

    public Fatigue() {
        super(ID, info);

        setCostUpgrade(1);
        setExhaust(true);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new IncreaseCostAction(this.uuid, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fatigue();
    }
}
