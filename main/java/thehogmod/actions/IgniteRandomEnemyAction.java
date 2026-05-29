package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IgniteRandomEnemyAction extends AbstractGameAction {
    public IgniteRandomEnemyAction() {
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new IgniteAction(this.target));
        }

        this.isDone = true;
    }
}
