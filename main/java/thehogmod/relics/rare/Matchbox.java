package thehogmod.relics.rare;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class Matchbox extends BaseRelic {
    private static final String NAME = "Matchbox"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public Matchbox() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onIgnite() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
