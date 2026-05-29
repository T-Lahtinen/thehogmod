package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static thehogmod.TheHogMod.makeID;

public class ScrapDiscardPileAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public ScrapDiscardPileAction(AbstractCreature source) {
        this.p = AbstractDungeon.player;
        this.setValues((AbstractCreature)null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.discardPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (this.p.discardPile.size() == 1) {
                    AbstractCard tmp = this.p.discardPile.getTopCard();
                    addToTop(new ScrapAction(tmp, true));
                }

                if (this.p.discardPile.group.size() > this.amount) {
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, TEXT[0], false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new ScrapAction(c, true));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ScrapTopCardAction"));
        TEXT = uiStrings.TEXT;
    }
}
