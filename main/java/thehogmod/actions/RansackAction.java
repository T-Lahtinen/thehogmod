package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thehogmod.powers.ExtraCardRewardPower;
import thehogmod.savables.BonusCardRewardSavable;

public class RansackAction extends AbstractGameAction {
    private DamageInfo info;
    private boolean isBoss = false;

    public RansackAction(AbstractCreature target, AbstractCreature source, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {

                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m.type == AbstractMonster.EnemyType.BOSS) {
                        isBoss = true;
                        break;
                    }
                }

                if (!isBoss) {
                    RewardItem reward = new RewardItem();
                    if (!reward.cards.isEmpty()) {
                        AbstractDungeon.getCurrRoom().rewards.add(reward);
                        BonusCardRewardSavable.addBonus(reward.cards);
                        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ExtraCardRewardPower(AbstractDungeon.player, 1), 1));
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
