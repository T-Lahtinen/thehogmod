package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.RecklessAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class Subterfuge extends BaseCard {
    public static final String ID = makeID(Subterfuge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );


    public Subterfuge() {
        super(ID, info);

        setCostUpgrade(0);
        setExhaust(true);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RecklessAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Subterfuge();
    }
}
