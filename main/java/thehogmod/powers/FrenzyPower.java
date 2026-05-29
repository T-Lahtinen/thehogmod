package thehogmod.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.actions.RecklessAction;
import thehogmod.util.CustomTags;

import static thehogmod.TheHogMod.makeID;

public class FrenzyPower extends BasePower {
    public static final String POWER_ID = makeID("Frenzy");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FrenzyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            if (!card.hasTag(CustomTags.RECKLESS)) {
                flashWithoutSound();
                addToTop(new RecklessAction(card));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}