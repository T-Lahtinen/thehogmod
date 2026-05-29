package thehogmod.relics.boss;

import com.badlogic.gdx.utils.Array;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class LargeAcorn extends BaseRelic {
    private static final String NAME = "LargeAcorn"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.
    private boolean triggeredThisTurn = false;

    public LargeAcorn() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        triggeredThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onRecklessEnd(Array<AbstractCard> cardsIncreasedCost) {
        if (!triggeredThisTurn) {
            flash();
            stopPulse();
            triggeredThisTurn = true;
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return  DESCRIPTIONS[0];
    }
}
