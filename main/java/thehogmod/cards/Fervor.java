package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.powers.FervorPower;
import thehogmod.util.CardStats;

public class Fervor extends BaseCard {
    public static final String ID = makeID(Fervor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Fervor() {
        super(ID, info);

        setMagic(2, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        addToBot(new DrawCardAction(1));
        addToBot(new ApplyPowerAction(p, p, new FervorPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fervor();
    }
}
