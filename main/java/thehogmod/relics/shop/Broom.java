package thehogmod.relics.shop;

import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class Broom extends BaseRelic {
    private static final String NAME = "Broom"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public Broom() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
