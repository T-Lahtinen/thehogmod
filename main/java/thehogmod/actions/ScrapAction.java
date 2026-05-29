package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thehogmod.cards.BaseCard;
import thehogmod.cards.Fortitude;
import thehogmod.cards.Retool;
import thehogmod.powers.BasePower;
import thehogmod.powers.ScrapArmorPower;
import thehogmod.relics.BaseRelic;
import thehogmod.relics.shop.Broom;
import thehogmod.util.ScrapTracker;

import java.util.Iterator;
import java.util.Objects;

import static thehogmod.TheHogMod.makeID;

public class ScrapAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;
    private AbstractCard sourceCard = null;
    private int cleanseAmount = 1;
    private int energyGainAmount = 0;
    private boolean scrappingMany = false;
    private boolean isCleanse = false;
    private boolean isMangle = false;
    private int additionalScrapArmor = 0;
    private boolean gainEnergyIfSkip = false;
    private boolean optional = false;
    private AbstractMonster m;
    private boolean isPulverize = false;
    private DamageInfo info;
    private boolean scrapSpecificCard = false;
    private AbstractCard specificCard;

    public ScrapAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public ScrapAction(int amount, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.optional = optional;
    }

    public ScrapAction(int amount, AbstractCard sourceCard) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.sourceCard = sourceCard;
    }

    public ScrapAction(int amount, boolean optional, int additionalScrapArmor) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.optional = optional;
        this.additionalScrapArmor = additionalScrapArmor;
    }

    public ScrapAction(int amount, boolean optional, boolean gainEnergyIfSkip, int energyGainAmount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.gainEnergyIfSkip = gainEnergyIfSkip;
        this.energyGainAmount = energyGainAmount;
        this.optional = optional;
    }

    public ScrapAction(int amount, boolean optional, boolean gainEnergyIfSkip, boolean isCleanse, int cleanseAmount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.gainEnergyIfSkip = gainEnergyIfSkip;
        this.isCleanse = isCleanse;
        this.cleanseAmount = cleanseAmount;
        this.optional = optional;
    }

    public ScrapAction(int amount, AbstractMonster m, DamageInfo info, boolean isPulverize) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.info = info;
        this.isPulverize = isPulverize;
        this.m = m;
    }

    public ScrapAction(AbstractCard specificCard, boolean scrapSpecificCard) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.specificCard = specificCard;
        this.scrapSpecificCard = scrapSpecificCard;
    }

    public ScrapAction(AbstractCard specificCard, boolean scrapSpecificCard, boolean scrappingMany, float duration) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = duration;
        this.scrappingMany = scrappingMany;
        this.specificCard = specificCard;
        this.scrapSpecificCard = scrapSpecificCard;
    }

    public ScrapAction(AbstractCard specificCard, boolean scrapSpecificCard, int additionalScrapArmor) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.specificCard = specificCard;
        this.scrapSpecificCard = scrapSpecificCard;
        this.additionalScrapArmor = additionalScrapArmor;
    }

    public ScrapAction(int amount, AbstractMonster m, DamageInfo info, boolean isPulverize, boolean isMangle) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
        this.info = info;
        this.isPulverize = isPulverize;
        this.isMangle = isMangle;
        this.m = m;
    }

    public ScrapAction(AbstractCard sourceCard, AbstractCard specificCard, boolean scrapSpecificCard, boolean scrappingMany, float duration) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = duration;
        this.scrappingMany = scrappingMany;
        this.specificCard = specificCard;
        this.scrapSpecificCard = scrapSpecificCard;
        this.sourceCard = sourceCard;
    }

    public void update() {
        if (this.scrapSpecificCard) {
            gainScrapArmor(this.specificCard, additionalScrapArmor);
            exhaustCard(this.specificCard);
            this.isDone = true;
            this.tickDuration();
        } else {
            if (this.duration == this.startDuration) {
                if (this.p.hand.isEmpty()) {
                    this.isDone = true;
                } else if (this.p.hand.size() == 1 && !optional) {
                    AbstractCard c = this.p.hand.getBottomCard();

                    gainScrapArmor(c, additionalScrapArmor);
                    exhaustCard(c);
                    this.tickDuration();
                } else {
                    if (!optional) {
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false);
                    } else {
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, true, false, false, true);
                    }
                    this.tickDuration();
                }
            } else {
                if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    int count = 0;
                    AbstractCard c;
                    for (Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); var1.hasNext(); exhaustCard(c)) {
                        count++;
                        c = var1.next();

                        gainScrapArmor(c, additionalScrapArmor);
                    }

                    if (count == 0 && gainEnergyIfSkip) {
                        addToTop(new GainEnergyAction(this.energyGainAmount));
                    }

                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                }

                this.tickDuration();
            }
        }
    }

    private void gainScrapArmor(AbstractCard c, int additionalScrapArmor) {
        if (!c.freeToPlayOnce) {
            int cost = c.costForTurn == -1 ? EnergyPanel.getCurrentEnergy() : Math.max(c.costForTurn, 0);
            if (additionalScrapArmor > 0) {
                cost += additionalScrapArmor;
            }

            if (p.hasRelic(Broom.ID)) {
                p.getRelic(Broom.ID).flash();
                addToTop(new RelicAboveCreatureAction(p, p.getRelic(Broom.ID)));
                cost += 2;
            }

            if (cost > 0) {
                addToTop(new ApplyPowerAction(p, p, new ScrapArmorPower(p, cost), cost));
            }
        }
    }

    private void exhaustCard(AbstractCard c) {
        ScrapTracker.scrappedThisTurn++;
        ScrapTracker.scrappedThisCombat++;
        handleSpecials(c);
        if (AbstractDungeon.player.drawPile.contains(c)) {
            this.p.drawPile.moveToExhaustPile(c);
        } else if (AbstractDungeon.player.discardPile.contains(c)) {
            this.p.discardPile.moveToExhaustPile(c);
        } else {
            this.p.hand.moveToExhaustPile(c);
        }
    }

    private void handleSpecials(AbstractCard card) {
        callOnScrap(card);

        if (sourceCard != null && Objects.equals(sourceCard.cardID, Retool.ID)) {
            AbstractCard tmp = card.makeStatEquivalentCopy();
            tmp.upgrade();
            addToTop(new MakeTempCardInHandAction(tmp, sourceCard.magicNumber));
        }

        if (sourceCard != null && Objects.equals(sourceCard.cardID, Fortitude.ID) && card.type == AbstractCard.CardType.STATUS) {
            sourceCard.applyPowers();
            addToTop(new GainBlockAction(AbstractDungeon.player, sourceCard.block));
        }

        if (this.isPulverize) {
            if (!card.freeToPlayOnce) {
                int cost = card.costForTurn == -1 ? EnergyPanel.getCurrentEnergy() : Math.max(card.costForTurn, 0);
                for (int i = 0; i < cost; i++) {
                    addToTop(new DamageAction(m, this.info, AttackEffect.BLUNT_LIGHT));
                }
            }
        }

        if (isCleanse) {
            if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
                addToTop(new GainEnergyAction(1));
            }
        }
    }

    private void callOnScrap(AbstractCard card) {
        for (AbstractRelic relic : AbstractDungeon.player.relics) {
            if (relic instanceof BaseRelic) {
                ((BaseRelic) relic).onScrap(card);
            }
        }

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof BasePower) {
                ((BasePower) power).onScrap(card);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onScrap(card);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onScrap(card);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onScrap(card);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof BaseCard) {
                ((BaseCard) c).onScrap(card);
            }
        }

        if (card instanceof BaseCard) {
            ((BaseCard) card).triggerOnManualScrap();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ScrapAction"));
        TEXT = uiStrings.TEXT;
    }
}
