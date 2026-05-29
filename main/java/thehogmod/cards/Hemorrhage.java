package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.HemorrhageAction;
import thehogmod.character.TheHog;
import thehogmod.powers.LacerationPower;
import thehogmod.util.CardStats;

public class Hemorrhage extends BaseCard {
    public static final String ID = makeID(Hemorrhage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    public Hemorrhage() {
        super(ID, info);

        setMagic(6, 4);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new LacerationPower(m, magicNumber), magicNumber));
        addToBot(new HemorrhageAction(m));
        // addToBot(new ApplyPowerAction(m, p, new HemorrhagePower(m, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hemorrhage();
    }
}
