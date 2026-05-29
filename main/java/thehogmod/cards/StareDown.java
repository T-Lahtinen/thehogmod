package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ForTheEyesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class StareDown extends BaseCard {
    public static final String ID = makeID(StareDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    public StareDown() {
        super(ID, info);

        setMagic(2, 1);
        setCustomVar("e", 1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!Settings.FAST_MODE) {
            addToBot(new VFXAction(new RoomTintEffect(Color.BLACK.cpy(), 0.8F)));
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.2F));
            addToBot(new SFXAction("INTIMIDATE"));
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.2F));
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.0F));
            addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.BLACK.cpy()), 0.0F, true));
        } else {
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.2F));
            addToBot(new SFXAction("INTIMIDATE"));
            addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.0F));
            addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.BLACK.cpy()), 0.0F, true));
        }

        addToBot(new ApplyFrightAction(m, magicNumber));
        addToBot(new ForTheEyesAction(customVar("e"), m));
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new StareDown();
    }
}
