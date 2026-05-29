package thehogmod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.FrenzyPower;
import thehogmod.powers.PoisePower;
import thehogmod.util.CardStats;
import thehogmod.vfx.FrenzyEffect;

public class Frenzy extends BaseCard {
    public static final String ID = makeID(Frenzy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public Frenzy() {
        super(ID, info);

        setMagic(3, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        addToBot(new VFXAction(p, new FrenzyEffect(p.hb.cX, p.hb.cY), 1.0F));
        addToBot(new ApplyPowerAction(p, p, new PoisePower(p, 5), 5));
        addToBot(new ApplyPowerAction(p, p, new FrenzyPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Frenzy();
    }
}
