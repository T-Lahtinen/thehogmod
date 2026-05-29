package thehogmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.CustomPotionBounceEffect;
import thehogmod.actions.IgniteAction;

import static thehogmod.TheHogMod.makeID;

public class CauterizePower extends BasePower {
    public static final String POWER_ID = makeID("Cauterize");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public CauterizePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onDeath() {
        flashWithoutSound();
        AbstractDungeon.player.heal(amount);
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDeadOrEscaped()) {
                    addToBot(new VFXAction(new CustomPotionBounceEffect(owner.hb.cX, owner.hb.cY + 50.0F, monster.hb.cX, monster.hb.cY, Color.FIREBRICK), 0.4F));
                }
            }

            addToBot(new IgniteAction(true));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}