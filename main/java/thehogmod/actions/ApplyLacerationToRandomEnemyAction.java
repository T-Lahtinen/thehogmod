package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ApplyLacerationToRandomEnemyAction extends AbstractGameAction {
    public ApplyLacerationToRandomEnemyAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.DEBUFF;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            addToTop(new ApplyLacerationAction(this.target, this.amount));
        }

        this.isDone = true;
    }
}
