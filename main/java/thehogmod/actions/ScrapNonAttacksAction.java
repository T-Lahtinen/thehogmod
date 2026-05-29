package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ScrapNonAttacksAction extends AbstractGameAction {
    public ScrapNonAttacksAction() {
        this.setValues(target, AbstractDungeon.player);
        this.actionType = ActionType.EXHAUST;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToScrap = new ArrayList<>();
        Iterator<AbstractCard> var2 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var2.hasNext()) {
            c = var2.next();
            if (c.type != AbstractCard.CardType.ATTACK) {
                cardsToScrap.add(c);
            }
        }

        var2 = cardsToScrap.iterator();

        while(var2.hasNext()) {
            c = var2.next();
            addToTop(new ScrapAction(c, true));
        }

        this.isDone = true;
    }
}