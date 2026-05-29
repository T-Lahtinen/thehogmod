package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Repulse extends BaseCard {
    public static final String ID = makeID(Repulse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public Repulse() {
        super(ID, info);

        setBlock(13, 3);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyFrightAction(true, magicNumber));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Repulse();
    }
}
