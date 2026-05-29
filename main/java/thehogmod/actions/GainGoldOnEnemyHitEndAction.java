package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import thehogmod.util.RecklessTracker;

import java.util.HashSet;

public class GainGoldOnEnemyHitEndAction extends AbstractGameAction {
    public GainGoldOnEnemyHitEndAction(int goldToGain) {
        this.actionType = ActionType.WAIT;
        this.amount = goldToGain;
    }

    public void update() {
        if (!RecklessTracker.drawCardsEnemiesHit.isEmpty()) {
            for (AbstractCreature abstractCreature : RecklessTracker.drawCardsEnemiesHit) {
                AbstractMonster m = (AbstractMonster) abstractCreature;
                for (int i = 0; i < this.amount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(m.hb.cX, m.hb.cY));
                }
                AbstractDungeon.player.gainGold(this.amount);
            }

            RecklessTracker.drawCardsEnemiesHit = new HashSet<>();
        }

        this.isDone = true;
    }
}
