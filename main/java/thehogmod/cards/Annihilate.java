package thehogmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Annihilate extends BaseCard {
    public static final String ID = makeID(Annihilate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );


    public Annihilate() {
        super(ID, info);

        setDamage(7, 3);
        setMagic(3);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int attackSize = damage;
        for (int i = 0; i < magicNumber; i++) {
            attackSize += damage;
            addToBot(new VFXAction(new WallopEffect(attackSize, m.hb.cX, m.hb.cY)));
            addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY, (damageTaken) -> {
                if (damageTaken > 0) {
                    addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, -1), -1));
                }
            }));
            if (Settings.FAST_MODE) {
                addToBot(new WaitAction(Settings.ACTION_DUR_XFAST));
            } else {
                addToBot(new WaitAction(Settings.ACTION_DUR_FAST));
            }

        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Annihilate();
    }
}
