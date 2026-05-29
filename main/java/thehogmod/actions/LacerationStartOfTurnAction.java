package thehogmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import thehogmod.powers.LacerationPower;
import thehogmod.vfx.GiantCustomTextEffect;

import static thehogmod.TheHogMod.makeID;

public class LacerationStartOfTurnAction extends AbstractGameAction {
    private AbstractCreature target;
    private int amount;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("LacerationStartOfTurnAction"));
    private static final String[] TEXT = uiStrings.TEXT;

    public LacerationStartOfTurnAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    public void update() {
        if (target.currentHealth <= amount && target.hasPower(LacerationPower.POWER_ID)) {
            target.getPower(LacerationPower.POWER_ID).flash();
            addToTop(new SFXAction("BELL", MathUtils.random(-0.45F, -0.55F)));
            addToTop(new WaitAction(0.2F));
            addToTop(new VFXAction(new GiantCustomTextEffect(target.hb.cX, target.hb.cY, TEXT[0])));
            addToTop(new InstantKillAction(target));
        }

        this.isDone = true;
    }
}