package thehogmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thehogmod.TheHogMod;
import thehogmod.actions.ScrapAction;
import thehogmod.actions.UnyieldingAction;
import thehogmod.character.TheHog;

import static thehogmod.TheHogMod.makeID;

public class EfficiencyPotion extends BasePotion {
    public static final String ID = makeID(EfficiencyPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = Color.CYAN;
    private static final Color HYBRID_COLOR = Color.SKY;
    private static final Color SPOTS_COLOR = Color.TEAL;

    public EfficiencyPotion() {
        super(ID, 5, PotionRarity.RARE, PotionSize.BOLT, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        this.playerClass = TheHog.Meta.THE_HOG;
        labOutlineColor = Color.PINK;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new UnyieldingAction(AbstractDungeon.player, 10));
            addToBot(new ScrapAction(potency, true));
        }
    }

    @Override
    public void addAdditionalTips() {
        tips.add(new PowerTip(TheHogMod.keywords.get("scrap").PROPER_NAME, TheHogMod.keywords.get("scrap").DESCRIPTION));
    }
}
