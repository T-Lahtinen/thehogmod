package thehogmod.relics.boss;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehogmod.character.TheHog;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class DeadlyRose extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "DeadlyRose"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public DeadlyRose() {
        super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        if (this.counter == 4) {
            this.beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled") && source == AbstractDungeon.player && target != source && !target.hasPower("Artifact")) {
            ++this.counter;
            if (this.counter == 5) {
                this.counter = 0;
                this.flash();
                this.pulse = false;
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToBot(new DrawCardAction(1));
            } else if (this.counter == 4) {
                this.beginPulse();
                this.pulse = true;
            }
        }

        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
