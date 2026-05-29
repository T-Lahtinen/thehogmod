package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.MultiplyScrapArmorAction;
import thehogmod.actions.ScrapHandAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Cleanse extends BaseCard {
    public static final String ID = makeID(Cleanse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Cleanse() {
        super(ID, info);

        setCostUpgrade(1);
        setMagic(1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScrapHandAction());
        addToBot(new MultiplyScrapArmorAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Cleanse();
    }
}
