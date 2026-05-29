package thehogmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thehogmod.patches.EnumPatch;
import thehogmod.powers.LacerationPower;

public class HemorrhageAction extends AbstractGameAction {
    public HemorrhageAction(AbstractMonster m) {
        this.target = m;
    }

    public void update() {
        if (this.target.hasPower(LacerationPower.POWER_ID)) {
            addToTop(new VFXAction(AbstractDungeon.player, new VerticalAuraEffect(Color.MAROON, this.target.hb.cX, this.target.hb.cY), 0.0F));
            addToTop(new LoseHPAction(this.target, AbstractDungeon.player, this.target.getPower(LacerationPower.POWER_ID).amount, EnumPatch.HOG_LACERATION));
        }

        this.isDone = true;
    }
}
