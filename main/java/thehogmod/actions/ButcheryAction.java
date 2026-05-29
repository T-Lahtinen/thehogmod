package thehogmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class ButcheryAction extends AbstractGameAction {
    private int lacerationAmt;
    private DamageInfo info;

    public ButcheryAction(DamageInfo damageInfo, int lacerationAmt) {
        this.info = damageInfo;
        this.lacerationAmt = lacerationAmt;
    }

    @Override
    public void update() {
        AbstractCreature mo = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (mo != null) {
            addToBot(new VFXAction(new ViolentAttackEffect(mo.hb.cX, mo.hb.cY, Color.MAROON)));

            for (int in = 0; in < 5; ++in) {
                addToBot(new VFXAction(new StarBounceEffect(mo.hb.cX, mo.hb.cY)));
            }

            addToBot(new DamageAction(mo, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            addToBot(new ApplyLacerationAction(mo, this.lacerationAmt, true));
            if (Settings.FAST_MODE) {
                addToBot(new WaitAction(0.1F));
            } else {
                addToBot(new WaitAction(0.25F));
            }
        }

        this.isDone = true;
    }
}
