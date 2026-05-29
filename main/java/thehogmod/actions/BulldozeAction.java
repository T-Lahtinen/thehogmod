package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class BulldozeAction extends AbstractGameAction {
    private AbstractCard card;

    public BulldozeAction(AbstractCard card, AbstractCreature target) {
        this.card = card;
        this.target = target;
    }

    public void update() {
        // this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null && (!this.target.isDying || this.target.currentHealth > 0)) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
            this.addToTop(new VFXAction(new ClashEffect(this.target.hb.cX, this.target.hb.cY), 0.1F));
        }

        this.isDone = true;
    }
}
