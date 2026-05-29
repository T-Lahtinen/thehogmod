package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ScrapStatusCardsAction extends AbstractGameAction {
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                if (Settings.FAST_MODE) {
                    addToTop(new ScrapAction(c, true, false, Settings.ACTION_DUR_XFAST));
                } else {
                    addToTop(new ScrapAction(c, true));
                }
            }
        }

        this.isDone = true;
    }
}
