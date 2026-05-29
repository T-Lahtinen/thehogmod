package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Dread extends BaseCard {
    public static final String ID = makeID(Dread.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );

    public Dread() {
        super(ID, info);

        setMagic(3, 2);
        setCustomVar("e", 2, 1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("INTIMIDATE", -0.3F));
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.NAVY)));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.NAVY, p.hb.cX, p.hb.cY), 0.0F));
        addToBot(new ShakeScreenAction(0.0F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.HIGH));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(p, new VerticalAuraEffect(Color.TEAL, mo.hb.cX, mo.hb.cY), 0.0F));
            }
        }
        addToBot(new ApplyFrightAction(true, magicNumber));
        addToBot(new AllEnemyApplyPowerAction(p, -customVar("e"), (AbstractMonster mo) -> new StrengthPower(mo, -customVar("e"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Dread();
    }
}
