package thehogmod.relics.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;
import thehogmod.util.CustomTags;

import static thehogmod.TheHogMod.makeID;

public class RedFlag extends BaseRelic {
    private static final String NAME = "RedFlag"; // The name for the relic.
    public static final String ID = makeID(NAME); // Adds the mod's prefix to the relic ID.
    private static final RelicTier RARITY = RelicTier.RARE; // Relic rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; // Relic sound.

    public RedFlag() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.hasTag(CustomTags.RECKLESS)) {
            flash();
            AbstractDungeon.player.increaseMaxHp(3, true);
        }
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
