package thehogmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehogmod.actions.DrawOnEnemyHitAction;
import thehogmod.actions.GainGoldOnEnemyHitEndAction;
import thehogmod.character.TheHog;
import thehogmod.util.CardStats;

public class Ransack extends BaseCard {
    public static final String ID = makeID(Ransack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheHog.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );

    public Ransack() {
        super(ID, info);

        setDamage(5, 1);
        setMagic(5, 2);
        setExhaust(true);

        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < 3; i++) {
            int attackEffectNum = AbstractDungeon.cardRandomRng.random(0, 2);
            AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.NONE;

            switch (attackEffectNum) {
                case 0:
                    effect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
                    break;
                case 1:
                    effect = AbstractGameAction.AttackEffect.SLASH_VERTICAL;
                    break;
                case 2:
                    effect = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
                    break;
            }
            addToBot(new DrawOnEnemyHitAction(this, effect));
        }

        addToBot(new GainGoldOnEnemyHitEndAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ransack();
    }
}

