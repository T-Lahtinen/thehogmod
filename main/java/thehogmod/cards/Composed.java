package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.ComposedPower;
import thehogmod.powers.PoisePower;
import thehogmod.util.CardStats;
import thehogmod.vfx.BorromeanRingEffect;

public class Composed extends BaseCard {
    public static final String ID = makeID(Composed.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Composed() {
        super(ID, info);

        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p != null) {
            addToBot(new SFXAction("TINGSHA"));
            addToBot(new VFXAction(new BorromeanRingEffect(p.hb.cX, p.hb.cY, Color.SKY, Color.YELLOW, Color.PINK)));
        }

        if (upgraded) {
            addToBot(new ApplyPowerAction(p, p, new PoisePower(p, magicNumber), magicNumber));
        }

        addToBot(new ApplyPowerAction(p, p, new ComposedPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Composed();
    }
}
