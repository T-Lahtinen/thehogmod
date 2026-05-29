package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.vfx.MangleEffect;

public class Mangle extends BaseCard {
    public static final String ID = makeID(Mangle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -1
    );

    public Mangle() {
        super(ID, info);

        setMagic(3, 2);

        setExhaust(true);
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
            addToBot(new VFXAction(new BorderLongFlashEffect(Color.RED, true)));
            addToBot(new VFXAction(new MangleEffect(m.hb.cX, m.hb.cY, Color.RED), 0.3F));
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.MAROON, m.hb.cX, m.hb.cY), 0.0F));

            for (int i = 0; i < energyUsed; i++) {
                addToBot(new ApplyLacerationAction(m, magicNumber));
                addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Mangle();
    }
}
