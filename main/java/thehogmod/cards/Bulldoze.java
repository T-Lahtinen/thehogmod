package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.BulldozeAction;
import thehogmod.actions.RecklessAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class Bulldoze extends BaseCard {
    public static final String ID = makeID(Bulldoze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            -1
    );

    public Bulldoze() {
        super(ID, info);

        setDamage(5, 2);
        setBlock(5, 2);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int energyUsed = this.energyOnUse;

        if (p.hasRelic("Chemical X")) {
            energyUsed += 2;
            p.getRelic("Chemical X").flash();
        }

        if (!this.freeToPlayOnce) {
            p.energy.use(energyUsed);
        }

        if (energyUsed > 0) {
            for (int i = 0; i < energyUsed; i++) {
                addToBot(new GainBlockAction(p, block));
                addToBot(new BulldozeAction(this, m));
            }
        }

        addToBot(new RecklessAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bulldoze();
    }
}
