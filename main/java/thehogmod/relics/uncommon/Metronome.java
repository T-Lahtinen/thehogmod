package thehogmod.relics.uncommon;

import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class Metronome extends BaseRelic {
    private static final String NAME = "Metronome"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Metronome() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
