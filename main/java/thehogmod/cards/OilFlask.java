package thehogmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thehogmod.actions.CustomPotionBounceEffect;
import thehogmod.character.TheHog;
import thehogmod.powers.OiledPower;
import thehogmod.util.CardStats;

public class OilFlask extends BaseCard {
    public static final String ID = makeID(OilFlask.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    public OilFlask() {
        super(ID, info);

        setMagic(2, 1);
        setExhaust(true);


        //TheHog.loadBetaArt(this, OilFlask.class.getSimpleName() + ".png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDeadOrEscaped()) {
                addToBot(new VFXAction(new CustomPotionBounceEffect(p.hb.cX, p.hb.cY + 50.0F, monster.hb.cX, this.hb.cY, Color.BLACK), 0.4F));
                addToBot(new ApplyPowerAction(monster, p, new OiledPower(monster, magicNumber), magicNumber));
                addToBot(new ApplyPowerAction(monster, p, new VulnerablePower(monster, magicNumber, false), magicNumber));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new OilFlask();
    }
}
