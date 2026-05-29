package thehogmod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;

public class FirePunchEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private int effectSize;

    public FirePunchEffect(float x, float y, int effectSize) {
        this.x = x;
        this.y = y;
        this.effectSize = effectSize;
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, true);
    }

    public void update() {
        CardCrawlGame.sound.playA("ATTACK_FIRE", 0.3F);
        CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.5F);
        float dst = 180.0F + (float)this.effectSize * 3.0F;
        AbstractDungeon.effectsQueue.add(new RedFireballEffect(this.x - dst * Settings.scale, this.y, this.x + dst * Settings.scale, this.y - 50.0F * Settings.scale, this.effectSize));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
