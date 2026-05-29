package thehogmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thehogmod.actions.LacerationStartOfTurnAction;
import thehogmod.actions.LoseHPFastAction;
import thehogmod.patches.EnumPatch;

import static thehogmod.TheHogMod.makeID;

public class LacerationPower extends BasePower implements HealthBarRenderPower {
    public static final String POWER_ID = makeID("Laceration");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public LacerationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        this.priority = 99;
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    @Override
    public void onInitialApplication() {
        int repeatTimes = 1;
        if (AbstractDungeon.player.hasPower(BloodlustPower.POWER_ID)) {
            repeatTimes += AbstractDungeon.player.getPower(BloodlustPower.POWER_ID).amount;
        }
        for (int i = 0; i < repeatTimes; i++) {
            if (i > 0) {
                AbstractDungeon.player.getPower(BloodlustPower.POWER_ID).flash();
            }
            addToTop(new LoseHPFastAction(owner, AbstractDungeon.player, amount, EnumPatch.HOG_LACERATION));
        }
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        int repeatTimes = 1;
        if (AbstractDungeon.player.hasPower(BloodlustPower.POWER_ID)) {
            repeatTimes += AbstractDungeon.player.getPower(BloodlustPower.POWER_ID).amount;
        }
        for (int i = 0; i < repeatTimes; i++) {
            if (i > 0) {
                AbstractDungeon.player.getPower(BloodlustPower.POWER_ID).flash();
            }
            addToTop(new LoseHPAction(owner, AbstractDungeon.player, stackAmount, EnumPatch.HOG_LACERATION));
        }
        updateDescription();

    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new LacerationStartOfTurnAction(owner, amount));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return Color.SALMON;
    }
}