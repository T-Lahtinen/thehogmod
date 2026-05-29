package thehogmod.relics.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.character.TheHog;
import thehogmod.powers.IntensityPower;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class Bellows extends BaseRelic {
    private static final String NAME = "Bellows"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    private boolean triggeredThisTurn = false;

    public Bellows() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayerGainedHeat(int amount) {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntensityPower(AbstractDungeon.player, 1), 1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            stopPulse();
        }
    }

    @Override
    public void atTurnStart() {
        triggeredThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
