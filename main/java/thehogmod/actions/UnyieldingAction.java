package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnyieldingAction extends AbstractGameAction {
    public UnyieldingAction(AbstractCreature source, int amount) {
        this.setValues(this.target, source, amount);
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        int toAdd = this.amount - AbstractDungeon.player.hand.size();
        if (toAdd > 0) {
            for (int i = 0; i < toAdd; i++) {
                addToTop(new SFXAction("CARD_OBTAIN"));
                addToTop(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), false));
            }
        }

        this.isDone = true;
    }
}
