package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.RushOfBloodPower;
import thehogmod.util.CardStats;

public class RushOfBlood extends BaseCard {
    public static final String ID = makeID(RushOfBlood.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public RushOfBlood() {
        super(ID, info);

        setMagic(2, 1);
        // TheHog.loadBetaArt(this, HarvesterOfPain.class.getSimpleName() + ".png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RushOfBloodPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RushOfBlood();
    }
}
