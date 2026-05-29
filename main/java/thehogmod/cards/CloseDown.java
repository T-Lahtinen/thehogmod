package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class CloseDown extends BaseCard {
    public static final String ID = makeID(CloseDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public CloseDown() {
        super(ID, info);

        setBlock(9, 3);
        setMagic(1);

        cardsToPreview = new Fatigue();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(new Fatigue()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CloseDown();
    }
}
