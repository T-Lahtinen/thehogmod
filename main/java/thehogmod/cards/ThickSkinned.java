package thehogmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.character.TheHog;
import thehogmod.powers.ScrapArmorPower;
import thehogmod.powers.ThickSkinnedPower;
import thehogmod.util.CardStats;

public class ThickSkinned extends BaseCard {
    public static final String ID = makeID(ThickSkinned.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ThickSkinned() {
        super(ID, info);

        setCustomVar("e", 5, 3);
        setMagic(1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ScrapArmorPower(p, customVar("e")), customVar("e")));
        addToBot(new ApplyPowerAction(p, p, new ThickSkinnedPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThickSkinned();
    }
}
