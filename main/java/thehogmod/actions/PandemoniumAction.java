package thehogmod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PandemoniumAction extends AbstractGameAction {
    public PandemoniumAction() {
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            Color color = randomizeColor();
            if (target != null) {
                addToTop(new IgniteAction(target, true));
                addToTop(new VFXAction(new CustomPotionBounceEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + 50.0F, target.hb.cX, target.hb.cY, color), 0.4F));
            }
        }

        this.isDone = true;
    }

    private Color randomizeColor() {
        int randomNum = AbstractDungeon.cardRandomRng.random(1, 9);
        Color color = Color.WHITE;

        switch (randomNum) {
            case 1:
                color = Color.MAROON;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.ROYAL;
                break;
            case 4:
                color = Color.CYAN;
                break;
            case 5:
                color = Color.PINK;
                break;
            case 6:
                color = Color.ORANGE;
                break;
            case 7:
                color = Color.VIOLET;
                break;
            case 8:
                color = Color.GOLDENROD;
                break;
            case 9:
                color = Color.FOREST;
                break;
        }

        return color;
    }
}