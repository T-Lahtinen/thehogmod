package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import static thehogmod.TheHogMod.makeID;

public class ScrapTopCardsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private boolean opened = false;

    public ScrapTopCardsAction(int choicesAmount) {
        this.amount = choicesAmount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            if (!opened) {
                CardGroup choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                    choices.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                }

                if (choices.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (choices.size() == 1) {
                    addToTop(new ScrapAction(choices.getTopCard(), true));
                    this.isDone = true;
                    return;
                }

                AbstractDungeon.gridSelectScreen.open(
                        choices,
                        1,
                        TEXT[0],
                        false
                );

                opened = true;
                tickDuration();
                return;
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                AbstractCard selected = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                exhaustCard(selected);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.isDone = true;
            }

            tickDuration();
        }
    }

    private void exhaustCard(AbstractCard card) {
        addToTop(new ScrapAction(card, true));
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ScrapTopCardAction"));
        TEXT = uiStrings.TEXT;
    }
}
