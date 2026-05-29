package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ScrapHandAction extends AbstractGameAction {
    public ScrapHandAction() {
        this.setValues(target, AbstractDungeon.player);
        this.actionType = ActionType.EXHAUST;
    }

    public void update() {
        Iterator<AbstractCard> var2 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = var2.next();
            addToTop(new ScrapAction(c, true));
        }

        this.isDone = true;
    }
}
