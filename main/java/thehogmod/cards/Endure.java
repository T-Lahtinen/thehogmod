package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.PoisePower;
import thehogmod.powers.ScrapArmorPower;
import thehogmod.util.CardStats;

public class Endure extends BaseCard {
    public static final String ID = makeID(Endure.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Endure() {
        super(ID, info);

        setCustomVar("e", 5, 3);
        setMagic(2);

        cardsToPreview = new Fatigue();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PoisePower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ScrapArmorPower(p, customVar("e")), customVar("e")));
        addToBot(new MakeTempCardInHandAction(new Fatigue()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Endure();
    }
}
