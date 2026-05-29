package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.HogEnergizedPower;
import thehogmod.powers.PoisePower;
import thehogmod.util.CardStats;

public class ThinkTwice extends BaseCard {
    public static final String ID = makeID(ThinkTwice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public ThinkTwice() {
        super(ID, info);

        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PoisePower(p, 2), 2));
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new HogEnergizedPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ThinkTwice();
    }
}
