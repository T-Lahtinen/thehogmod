package thehogmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import thehogmod.cards.*;
import thehogmod.powers.BasePower;
import thehogmod.relics.BaseRelic;
import thehogmod.util.CustomTags;
import thehogmod.util.RecklessCostListener;
import thehogmod.util.RecklessTracker;

import java.util.Objects;

import static thehogmod.TheHogMod.makeID;

public class RecklessAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private final AbstractPlayer p = AbstractDungeon.player;
    private final Array<AbstractCard> cardsToProcess = new Array<>();
    private final AbstractCard sourceCard;
    private AbstractMonster targetEnemy = null;
    private Array<AbstractCard> cardsIncreasedCost;
    int extraEffectAmount = 0;
    private DamageInfo info;

    public RecklessAction(AbstractCard sourceCard) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.sourceCard = sourceCard;
        this.cardsIncreasedCost = new Array<>();

        processReckless(sourceCard);
    }

    public RecklessAction(AbstractCard sourceCard, int extraEffectAmount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.sourceCard = sourceCard;
        this.extraEffectAmount = extraEffectAmount;
        this.cardsIncreasedCost = new Array<>();

        processReckless(sourceCard);
    }

    private void processReckless(AbstractCard sourceCard) {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }

        addToTop(new SFXAction("RAGE"));
        addToTop(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.MAROON, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));

        for (AbstractCard c : p.hand.group) {
            if (c != sourceCard && !c.hasTag(CustomTags.STUBBORN) && !c.freeToPlayOnce) {
                if (Objects.equals(c.cardID, BendAndBreak.ID)) {
                    if (c.costForTurn > 0) {
                        c.setCostForTurn(c.costForTurn - 1);
                        RecklessTracker.recklessCostDecreased++;
                        callOnCostChanges(c, false);
                    }
                    c.flash(Color.GOLD.cpy());
                } else {
                    cardsToProcess.add(c);
                }
            }
        }

        callOnRecklessCardPlayed();
        RecklessTracker.recklessCardsPlayedCombat++;
        RecklessTracker.recklessCardsPlayedTurn++;
    }

    @Override
    public void update() {
        if (cardsToProcess.size == 0) {
            callOnRecklessEnd();
            this.isDone = true;
            return;
        }

        AbstractCard card = alternatelyRandomCard();
        if (card.cost >= 0) {
            randomizeCost(card);
        }

        if (cardsToProcess.size != 0) {
            addToTop(new RecklessAction(cardsToProcess, sourceCard, cardsIncreasedCost));
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
            }
        } else {
            callOnRecklessEnd();
        }

        this.isDone = true;
    }

    private AbstractCard alternatelyRandomCard() {
        return cardsToProcess.removeIndex(AbstractDungeon.cardRandomRng.random(cardsToProcess.size - 1));
    }

    private void randomizeCost(AbstractCard card) {
        boolean decreaseCost;

        // decreaseCost = trulyRandomCost(card);
        decreaseCost = sequencedChangedCost(card);

        if (decreaseCost) {
            if (card.costForTurn > 0) {
                card.setCostForTurn(card.costForTurn - 1);
                RecklessTracker.recklessCostDecreased++;
                callOnCostChanges(card, false);
            }
            card.flash(Color.GOLD.cpy());
        } else {
            card.setCostForTurn(card.costForTurn + 1);
            card.flash(Color.RED.cpy());
            RecklessTracker.recklessCostIncreased++;
            cardsIncreasedCost.add(card);
            callOnCostChanges(card, true);
        }
    }

    private boolean sequencedChangedCost(AbstractCard card) {
        boolean decreaseCost = RecklessTracker.recklessDecreaseCost;

        RecklessTracker.recklessDecreaseCost = !RecklessTracker.recklessDecreaseCost;

        return decreaseCost;
    }

    private boolean trulyRandomCost(AbstractCard card) {
        boolean decreaseCost;

        if (Objects.equals(card.cardID, BendAndBreak.ID)) {
            decreaseCost = true;
        } else {
            decreaseCost = AbstractDungeon.cardRandomRng.randomBoolean();
        }

        return decreaseCost;
    }

    private void callOnRecklessCardPlayed() {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) {
                ((BaseRelic) relic).onRecklessCardPlayed();
            }
        }

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) {
                ((BasePower) power).onRecklessCardPlayed();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onRecklessCardPlayed();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onRecklessCardPlayed();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onRecklessCardPlayed();
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onRecklessCardPlayed();
            }
        }
    }

    private void callOnCostChanges(AbstractCard card, boolean costIncreased) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) {
                ((BaseRelic) relic).onRecklessCostChange(card, card.costForTurn, costIncreased);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onRecklessCostChange(card, card.costForTurn, costIncreased);
            }
        }

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) {
                ((BasePower) power).onRecklessCostChange(card, card.costForTurn, costIncreased);
            }
        }

        if (costIncreased && card instanceof RecklessCostListener) {
            ((RecklessCostListener)card).onRecklessCostIncreasedThisTurn();
        }

        if (card != null && Objects.equals(card.cardID, Unwavering.ID)) {
            addToBot(new GainEnergyAction(card.magicNumber));
            addToBot(new DiscardSpecificCardAction(card));
        }

        if (costIncreased && this.sourceCard != null && (Objects.equals(sourceCard.cardID, HackAndSlash.ID) || Objects.equals(sourceCard.cardID, Onslaught.ID))) {
            RecklessTracker.recklessIncreasedAttack++;
        }
    }

    private void callOnRecklessEnd() {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) {
                ((BaseRelic) relic).onRecklessEnd(cardsIncreasedCost);
            }
        }

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) {
                ((BasePower) power).onRecklessEnd(cardsIncreasedCost);
            }
        }

        RecklessTracker.recklessDecreaseCost = false;

        if (this.sourceCard != null && Objects.equals(sourceCard.cardID, Subterfuge.ID)) {
            handleSubterfuge(cardsIncreasedCost);
        }

        /*if (this.sourceCard != null && Objects.equals(sourceCard.cardID, RiskyManeuver.ID)) {
            if (cardsIncreasedCost.size > 0) {
                addToTop(new GainEnergyAction(cardsIncreasedCost.size));
            }
        }*/
    }

    private void handleSubterfuge(Array<AbstractCard> cardsIncreasedCost) {
        final Array<AbstractCard> eligible = new Array<>();
        for (AbstractCard c : p.hand.group) {
            if (cardsIncreasedCost != null && cardsIncreasedCost.contains(c, true)) {
                eligible.add(c);
            }
        }

        if (eligible.size == 1) {
            AbstractCard c = eligible.first();
            c.setCostForTurn(0);
            c.flash(Color.GOLD.cpy());
            p.hand.refreshHandLayout();
            p.hand.applyPowers();
        }
        else if (eligible.size > 1) {
            addToBot(new SelectCardsInHandAction(
                    1,
                    TEXT[0],
                    false,
                    false,
                    c -> eligible.contains(c, true),
                    selected -> {
                        if (selected.isEmpty()) return;

                        AbstractCard c = selected.get(0);
                        c.setCostForTurn(0);
                        p.hand.refreshHandLayout();
                        p.hand.applyPowers();
                    }
            ));
        }
    }

    private RecklessAction(Array<AbstractCard> remainingCards, AbstractCard sourceCard, Array<AbstractCard> accumulator) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.sourceCard = sourceCard;

        this.cardsIncreasedCost = accumulator;
        this.cardsToProcess.addAll(remainingCards);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("RecklessAction"));
        TEXT = uiStrings.TEXT;
    }
}
