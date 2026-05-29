package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.vfx.BigSlashEffect;

import java.util.HashSet;

public class StrikeDown extends BaseCard {
    public static final String ID = makeID(StrikeDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public StrikeDown() {
        super(ID, info);

        setDamage(6, 2);

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        HashSet<String> debuffSet = new HashSet<>();

        for (AbstractPower power : m.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")) {
                debuffSet.add(power.ID);
            }
        }

        for (int i = 0; i < debuffSet.size(); i++) {
            addToBot(new VFXAction(new BigSlashEffect(m.hb.cX - MathUtils.random(-45.0F, -75.0F) * Settings.scale, m.hb.cY, true, Color.RED, Color.GOLD)));
            addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrikeDown();
    }
}
