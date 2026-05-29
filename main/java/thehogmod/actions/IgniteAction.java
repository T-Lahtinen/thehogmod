package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import thehogmod.powers.*;
import thehogmod.relics.BaseRelic;
import thehogmod.util.IgnitionTracker;

public class IgniteAction extends AbstractGameAction {
    private int repeatTimes = 1;
    private boolean skipHeatLoss = false;
    private boolean isMulti;
    private final AbstractPlayer p = AbstractDungeon.player;

    public IgniteAction(AbstractCreature target) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.target = target;
    }

    public IgniteAction(AbstractCreature target, boolean skipHeatLoss) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.skipHeatLoss = skipHeatLoss;
    }

    public IgniteAction(boolean multi) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.isMulti = multi;
    }

    public IgniteAction(AbstractCreature target, int repeatTimes) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.target = target;
        this.repeatTimes = repeatTimes;
    }

    public IgniteAction(boolean multi, boolean skipHeatLoss) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.isMulti = multi;
        this.skipHeatLoss = skipHeatLoss;
    }

    public IgniteAction(boolean multi, int repeatTimes) {
        this.duration = 0.0F;
        this.actionType = ActionType.DAMAGE;
        this.isMulti = multi;
        this.repeatTimes = repeatTimes;
    }

    public void update() {
        if (p.hasPower(HeatPower.POWER_ID)) {
            AbstractPower heatPower = p.getPower(HeatPower.POWER_ID);
            int primerAmount = heatPower.amount;
            if (primerAmount > 0) {
                int thisIgnitedAmount = 0;

                if (p.hasPower(IntensityPower.POWER_ID)) {
                    primerAmount += p.getPower(IntensityPower.POWER_ID).amount;
                }

                if (isMulti) {
                    for (int i = 0; i < this.repeatTimes; i++) {
                        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                            int igniteAmount = primerAmount;
                            if (!monster.isDeadOrEscaped()) {
                                if (monster.hasPower(OiledPower.POWER_ID)) {
                                    int addAmount = primerAmount / 2;
                                    igniteAmount += addAmount;
                                }

                                addToTop(new IgniteDamageAction(monster, new DamageInfo(p, igniteAmount, DamageInfo.DamageType.THORNS)));
                                IgnitionTracker.enemiesIgnitedThisCombat++;
                                IgnitionTracker.enemiesIgnitedThisTurn++;
                                thisIgnitedAmount++;

                                for (AbstractRelic relic : AbstractDungeon.player.relics) {
                                    if (relic instanceof BaseRelic) {
                                        ((BaseRelic) relic).onIgnite();
                                    }
                                }

                                for (AbstractPower power : AbstractDungeon.player.powers) {
                                    if (power instanceof BasePower) {
                                        ((BasePower) power).onIgnite(monster);
                                    }
                                }

                                for (AbstractPower power : monster.powers) {
                                    if (power instanceof BasePower) {
                                        ((BasePower) power).onIgnite(monster);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < this.repeatTimes; i++) {
                        if (!target.isDeadOrEscaped()) {
                            if (target.hasPower(OiledPower.POWER_ID)) {
                                int addAmount = primerAmount / 2;
                                primerAmount += addAmount;
                            }

                            addToTop(new IgniteDamageAction(target, new DamageInfo(p, primerAmount, DamageInfo.DamageType.THORNS)));
                            thisIgnitedAmount++;
                            IgnitionTracker.enemiesIgnitedThisCombat++;
                            IgnitionTracker.enemiesIgnitedThisTurn++;

                            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                                if (relic instanceof BaseRelic) {
                                    ((BaseRelic) relic).onIgnite();
                                }
                            }

                            for (AbstractPower power : AbstractDungeon.player.powers) {
                                if (power instanceof BasePower) {
                                    ((BasePower) power).onIgnite(target);
                                }
                            }

                            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                                if (mo.isDeadOrEscaped()) continue;

                                for (AbstractPower power : mo.powers) {
                                    if (power instanceof BasePower) {
                                        ((BasePower) power).onIgnite(target);
                                    }
                                }
                            }
                        }
                    }
                }

                if (!skipHeatLoss) {
                    addToTop(new ReducePowerAction(p, p, heatPower, thisIgnitedAmount));
                }
            }
        }

        this.isDone = true;
    }
}

