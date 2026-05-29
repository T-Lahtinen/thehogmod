package thehogmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.relics.boss.TornManifesto;
import thehogmod.relics.shop.Broom;
import thehogmod.vfx.ScrapArmorDamageEffect;

import static thehogmod.TheHogMod.makeID;

public class ScrapArmorPower extends BasePower {
    public static final String POWER_ID = makeID("ScrapArmor");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ScrapArmorPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_PLATED", 0.05F);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            flash();
            damageAmount = damageAmount - this.amount;

            if (damageAmount < 0) {
                damageAmount = 0;
            }

            int repeatTimes = 1;

            if (AbstractDungeon.player.hasPower(RazorhidePower.POWER_ID)) {
                AbstractDungeon.player.getPower(RazorhidePower.POWER_ID).flash();
                repeatTimes += AbstractDungeon.player.getPower(RazorhidePower.POWER_ID).amount;
            }

            int scrapDamageAmount = this.amount;
            if (AbstractDungeon.player.hasRelic(Broom.ID)) {
                AbstractDungeon.player.getRelic(Broom.ID).flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, AbstractDungeon.player.getRelic(Broom.ID)));
                scrapDamageAmount = scrapDamageAmount / 2;
            }

            for (int i = 0; i < repeatTimes; i++) {
                if (AbstractDungeon.player.hasRelic(TornManifesto.ID)) {
                    addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(scrapDamageAmount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                    AbstractDungeon.player.getRelic(TornManifesto.ID).flash();
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (!mo.isDead) {
                            addToTop(new RelicAboveCreatureAction(mo, AbstractDungeon.player.getRelic(TornManifesto.ID)));
                        }
                    }
                } else {
                    addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, scrapDamageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }

                addToTop(new VFXAction(new ScrapArmorDamageEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            }

            if (owner.hasPower(ThickSkinnedPower.POWER_ID)) {
                owner.getPower(ThickSkinnedPower.POWER_ID).flash();
                addToTop(new ReducePowerAction(owner, owner, owner.getPower(ThickSkinnedPower.POWER_ID), 1));
            } else {
                addToTop(new RemoveSpecificPowerAction(owner, owner, this));
                this.amount = 0;
            }
        }

        return damageAmount;
    }

    public void updateDescription() {
        int scrapDamageAmount = this.amount;
        if (AbstractDungeon.player.hasRelic(Broom.ID)) {
            scrapDamageAmount = scrapDamageAmount / 2;
        }

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + scrapDamageAmount + DESCRIPTIONS[2];
    }
}