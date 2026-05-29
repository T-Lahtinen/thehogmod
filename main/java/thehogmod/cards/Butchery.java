package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.ButcheryAction;
import thehogmod.actions.RecklessAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class Butchery extends BaseCard {
    public static final String ID = makeID(Butchery.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public Butchery() {
        super(ID, info);

        setDamage(3);
        setCustomVar("e", 2);
        setMagic(3, 1);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ButcheryAction(new DamageInfo(p, damage), customVar("e")));
        }

        addToBot(new RecklessAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Butchery();
    }
}
