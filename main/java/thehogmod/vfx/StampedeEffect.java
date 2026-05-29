package thehogmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveParticle;

public class StampedeEffect extends AbstractGameEffect {
    private float waveTimer = 0.0F;
    private float x;
    private float y;
    private float cX;
    private float cY;
    private static final float WAVE_INTERVAL = 0.03F;

    public StampedeEffect(float x, float y, float cX, float cY) {
        this.x = x + 120.0F * Settings.scale;
        this.y = y - 20.0F * Settings.scale;
        this.cX = cX;
        this.cY = cY;
    }

    public void update() {
        this.waveTimer -= Gdx.graphics.getDeltaTime();
        if (this.waveTimer < 0.0F) {
            this.waveTimer = 0.03F;
            this.x += 160.0F * Settings.scale;
            this.y -= 15.0F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new IronWaveParticle(this.x, this.y));
            if (this.x > this.cX) {
                this.isDone = true;
                CardCrawlGame.sound.playA("BLUNT_HEAVY", -0.3F);
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
