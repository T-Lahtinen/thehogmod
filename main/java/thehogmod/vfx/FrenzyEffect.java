package thehogmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WobblyLineEffect;
public class FrenzyEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.0F;
    private float x;
    private float y;
    private float vfxTimer;
    private static final float VFX_INTERVAL = 0.016F;

    public FrenzyEffect(float newX, float newY) {
        this.duration = 1.0F;
        this.x = newX;
        this.y = newY;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            this.vfxTimer = 0.016F;
            AbstractDungeon.effectsQueue.add(new WobblyLineEffect(this.x, this.y, randomColor()));
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    private Color randomColor() {
        int randomNum = AbstractDungeon.cardRandomRng.random(1, 6);
        Color color = Color.WHITE;

        switch (randomNum) {
            case 1:
                color = Color.MAROON.cpy();
                break;
            case 2:
                color = Color.ORANGE.cpy();
                break;
            case 3:
                color = Color.PURPLE.cpy();
                break;
            case 4:
                color = Color.SCARLET.cpy();
                break;
            case 5:
                color = Color.PINK.cpy();
                break;
            case 6:
                color = Color.FIREBRICK.cpy();
                break;
        }

        return color;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}