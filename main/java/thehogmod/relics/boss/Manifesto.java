package thehogmod.relics.boss;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehogmod.character.TheHog;
import thehogmod.powers.ScrapArmorPower;
import thehogmod.relics.BaseRelic;

import static thehogmod.TheHogMod.makeID;

public class Manifesto extends BaseRelic {
    private static final String NAME = "Manifesto"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    /*private boolean damageDealt = false;*/

    public Manifesto() {super(ID, NAME, TheHog.Meta.CARD_COLOR, RARITY, SOUND);}

    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ScrapArmorPower(AbstractDungeon.player, 5), 5));
    }

    /*@Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!damageDealt && targetCard.type == AbstractCard.CardType.ATTACK) {
            flash();
            if (targetCard.target == AbstractCard.CardTarget.ALL || targetCard.target == AbstractCard.CardTarget.ALL_ENEMY) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDead) {
                        addToBot(new RelicAboveCreatureAction(mo, this));
                        addToBot(new ApplyLacerationAction(mo, 4, true));
                    }
                }
            } else {
                if (useCardAction.target != null) {
                    addToBot(new RelicAboveCreatureAction(useCardAction.target, this));
                    addToBot(new ApplyLacerationAction(useCardAction.target, 4, true));
                }
            }

            damageDealt = true;
            stopPulse();
        }
    }

    @Override
    public void atPreBattle() {
        beginLongPulse();
        damageDealt = false;
    }

    @Override
    public void onVictory() {
        stopPulse();
        damageDealt = false;
    }*/

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
