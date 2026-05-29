package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.RecklessAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class RiskyManeuver extends BaseCard {
    public static final String ID = makeID(RiskyManeuver.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public RiskyManeuver() {
        super(ID, info);

        setMagic(1, 1);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RecklessAction(this));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RiskyManeuver();
    }
}
