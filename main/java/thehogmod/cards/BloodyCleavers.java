package thehogmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class BloodyCleavers extends BaseCard {
    public static final String ID = makeID(BloodyCleavers.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    public BloodyCleavers() {
        super(ID, info);

        setDamage(4);
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, (damageTaken) -> {
            if (damageTaken > 0) {
                addToTop(new ApplyLacerationAction(m, magicNumber));
            }
        }));
        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL, (damageTaken) -> {
            if (damageTaken > 0) {
                addToTop(new ApplyLacerationAction(m, magicNumber));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BloodyCleavers();
    }
}
