package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.actions.ApplyLacerationToRandomEnemyAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class FesteringWounds extends BaseCard {
    public static final String ID = makeID(FesteringWounds.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public FesteringWounds() {
        super(ID, info);

        setBlock(7, 2);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (upgraded) {
            addToBot(new ApplyLacerationAction(true, magicNumber));
        } else {
            addToBot(new ApplyLacerationToRandomEnemyAction(magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FesteringWounds();
    }
}
