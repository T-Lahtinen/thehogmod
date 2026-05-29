package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thehogmod.actions.ScrapNonAttacksAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class ViolentOutburst extends BaseCard {
    public static final String ID = makeID(ViolentOutburst.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    public ViolentOutburst() {
        super(ID, info);

        setMagic(2, 1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("RAGE", -0.2F, true));
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK)));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.FIREBRICK, p.hb.cX, p.hb.cY), 0.0F));
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(3));
        addToBot(new ScrapNonAttacksAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new ViolentOutburst();
    }
}
