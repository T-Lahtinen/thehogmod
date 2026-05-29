package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.RecklessCostListener;
import thehogmod.util.StartOfTurnListener;

public class Outrage extends BaseCard implements RecklessCostListener, StartOfTurnListener {
    public static final String ID = makeID(Outrage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MULT = 3;
    boolean increasedFromReckless = false;

    public Outrage() {
        super(ID, info);

        setDamage(7, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (increasedFromReckless) {
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (increasedFromReckless) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (increasedFromReckless) {
            this.damage *= MULT;
            this.isDamageModified = (this.damage != this.baseDamage);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (increasedFromReckless) {
            this.damage *= MULT;
        }
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void onRecklessCostIncreasedThisTurn() {
        increasedFromReckless = true;
    }

    @Override
    public void onStartOfTurn() {
        increasedFromReckless = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Outrage();
    }
}
