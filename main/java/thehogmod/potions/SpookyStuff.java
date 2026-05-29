package thehogmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thehogmod.TheHogMod;
import thehogmod.actions.ApplyFrightAction;
import thehogmod.character.TheHog;

import static thehogmod.TheHogMod.makeID;

public class SpookyStuff extends BasePotion {
    public static final String ID = makeID(SpookyStuff.class.getSimpleName());

    private static final Color LIQUID_COLOR = Color.PURPLE;
    private static final Color HYBRID_COLOR = Color.TEAL;
    private static final Color SPOTS_COLOR = Color.SLATE;

    public SpookyStuff() {
        super(ID, 5, PotionRarity.COMMON, PotionSize.BOTTLE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        this.playerClass = TheHog.Meta.THE_HOG;
        labOutlineColor = Color.PINK;
        isThrown = true;
        targetRequired = true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyFrightAction(abstractCreature, potency));
        }
    }

    @Override
    public void addAdditionalTips() {
        tips.add(new PowerTip(TheHogMod.keywords.get("fright").PROPER_NAME, TheHogMod.keywords.get("fright").DESCRIPTION));
    }
}
