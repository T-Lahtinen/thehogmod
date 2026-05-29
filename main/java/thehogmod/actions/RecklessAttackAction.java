package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.util.RecklessTracker;

public class RecklessAttackAction extends AbstractGameAction {
    private final AbstractCard sourceCard;
    private DamageInfo info;

    public RecklessAttackAction(AbstractMonster target, DamageInfo damageInfo, AbstractCard sourceCard) {
        setValues(target, damageInfo);
        this.info = damageInfo;
        this.sourceCard = sourceCard;
    }

    @Override
    public void update() {
        int repeatTimes = RecklessTracker.recklessIncreasedAttack;
        for (int i = 0; i < repeatTimes; i++) {
            addToBot(new GainBlockAction(AbstractDungeon.player, sourceCard.block));
            addToBot(new DamageAction(this.target, this.info, AttackEffect.SLASH_HEAVY));
        }

        RecklessTracker.recklessIncreasedAttack = 0;
        this.isDone = true;
    }
}
