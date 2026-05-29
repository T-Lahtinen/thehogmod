package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.IncreaseCostAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class DoubleDown extends BaseCard {
    public static final String ID = makeID(DoubleDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    public DoubleDown() {
        super(ID, info);

        setDamage(6, 1);
        setMagic(6, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (damage > 20) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        addToBot(new ModifyDamageAction(this.uuid, magicNumber));
        addToBot(new IncreaseCostAction(this.uuid, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DoubleDown();
    }
}
