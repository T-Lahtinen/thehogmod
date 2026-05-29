package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.powers.IntensityPower;
import thehogmod.util.CardStats;

public class HotHog extends BaseCard {
    public static final String ID = makeID(HotHog.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public HotHog() {
        super(ID, info);

        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new IntensityPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HotHog();
    }
}
