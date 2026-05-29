package thehogmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.RecklessAction;
import thehogmod.actions.RecklessAttackAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;
import thehogmod.util.CustomTags;

public class HackAndSlash extends BaseCard {
    public static final String ID = makeID(HackAndSlash.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    public HackAndSlash() {
        super(ID, info);

        setBlock(5, 2);
        setDamage(5, 2);

        tags.add(CustomTags.RECKLESS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RecklessAction(this));
        addToBot(new RecklessAttackAction(m, new DamageInfo(p, damage), this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new HackAndSlash();
    }
}
