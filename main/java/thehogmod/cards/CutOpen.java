package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ApplyLacerationAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.vfx.BigSlashEffect;

public class CutOpen extends BaseCard {
    public static final String ID = makeID(CutOpen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public CutOpen() {
        super(ID, info);

        setDamage(14, 4);
        setMagic(4, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BigSlashEffect(m.hb.cX * Settings.scale, m.hb.cY, true, Color.RED, Color.WHITE)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyLacerationAction(true, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CutOpen();
    }
}
