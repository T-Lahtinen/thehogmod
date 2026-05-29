package thehogmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import thehogmod.actions.RecklessAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class BoneCrusher extends BaseCard {
    public static final String ID = makeID(BoneCrusher.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public BoneCrusher() {
        super(ID, info);

        setDamage(28, 7);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }

        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY, (damageDealt) -> {
            boolean enemyDead = (m.isDying || m.currentHealth <= 0) && !m.halfDead;

            if (enemyDead) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (mo != null && !mo.isDeadOrEscaped()) {
                        addToBot(new StunMonsterAction(mo, p));
                    }
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }));

        addToBot(new RecklessAction(this));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BoneCrusher();
    }
}
