package thehogmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import thehogmod.cards.MolotovCocktail;
import thehogmod.character.TheHog;

import static thehogmod.TheHogMod.makeID;

public class MolotovPotion extends BasePotion {
    public static final String ID = makeID(MolotovPotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 140, 0);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 69, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 215, 0);

    public MolotovPotion() {
        super(ID, 2, PotionRarity.UNCOMMON, PotionSize.JAR, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
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
            MolotovCocktail card = new MolotovCocktail();
            card.freeToPlayOnce = true;
            addToBot(new MakeTempCardInHandAction(card, potency));
        }
    }
}
