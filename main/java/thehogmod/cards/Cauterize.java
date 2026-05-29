package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.powers.CauterizePower;
import thehogmod.util.CardStats;

public class Cauterize extends BaseCard {
    public static final String ID = makeID(Cauterize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public Cauterize() {
        super(ID, info);

        setMagic(7, 3);
        setExhaust(true);

        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyFrightAction(m, 3));
        addToBot(new ApplyPowerAction(m, p, new CauterizePower(m, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cauterize();
    }
}
