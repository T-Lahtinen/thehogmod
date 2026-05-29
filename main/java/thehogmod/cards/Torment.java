package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.powers.TormentInvisiblePower;
import thehogmod.powers.TormentPower;
import thehogmod.util.CardStats;

public class Torment extends BaseCard {
    public static final String ID = makeID(Torment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public Torment() {
        super(ID, info);

        setMagic(2, 1);
        setCustomVar("e", 3, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyFrightAction(m, magicNumber));
        addToBot(new ApplyPowerAction(m, p, new TormentPower(m, customVar("e")), customVar("e")));
        addToBot(new ApplyPowerAction(p, p, new TormentInvisiblePower(p, customVar("e")), customVar("e")));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Torment();
    }
}
