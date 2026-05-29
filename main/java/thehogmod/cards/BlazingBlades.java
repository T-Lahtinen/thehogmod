package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.GainHeatAction;
import thehogmod.character.TheHog;
import thehogmod.powers.BlazingBladesPower;
import thehogmod.util.CardStats;

public class BlazingBlades extends BaseCard {
    public static final String ID = makeID(BlazingBlades.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public BlazingBlades() {
        super(ID, info);

        setMagic(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHeatAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new BlazingBladesPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BlazingBlades();
    }
}
