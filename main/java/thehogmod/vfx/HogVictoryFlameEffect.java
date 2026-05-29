package thehogmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HogVictoryFlameEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private float vY;
    private boolean flipX = MathUtils.randomBoolean();
    private TextureAtlas.AtlasRegion img;

    public HogVictoryFlameEffect() {
        this.duration = 1.0F;
        this.startingDuration = this.duration;
        this.renderBehind = false;

        // Randomly select one of the flame images
        switch (MathUtils.random(2)) {
            case 0:
                this.img = ImageMaster.FLAME_1;
                break;
            case 1:
                this.img = ImageMaster.FLAME_2;
                break;
            default:
                this.img = ImageMaster.FLAME_3;
        }

        // Full screen horizontal range
        this.x = MathUtils.random(0.0F, Settings.WIDTH) - this.img.packedWidth / 2.0F;
        this.y = -300.0F * Settings.scale - this.img.packedHeight / 2.0F;

        // Gentle horizontal drift
        this.vX = MathUtils.random(-50.0F, 50.0F) * Settings.xScale;

        // Strong upward velocity
        this.vY = MathUtils.random(600.0F, 900.0F) * Settings.scale;

        // Realistic fire colors: red/orange/yellow blend
        this.color = new Color(
                MathUtils.random(0.9F, 1.0F),   // Red
                MathUtils.random(0.5F, 0.8F),   // Green (orange/yellow)
                MathUtils.random(0.0F, 0.2F),   // Blue (low)
                0.8F                            // Alpha
        );

        // Scale and rotation for flame variation
        this.scale = MathUtils.random(6.0F, 10.0F) * Settings.scale;
        this.rotation = MathUtils.random(-10.0F, 10.0F);
    }

    @Override
    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.color.a = Interpolation.pow3Out.apply(0.0F, 0.8F, this.duration / this.startingDuration);
        this.duration -= Gdx.graphics.getDeltaTime();
        this.scale += Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        if (this.flipX && !this.img.isFlipX()) {
            this.img.flip(true, false);
        } else if (!this.flipX && this.img.isFlipX()) {
            this.img.flip(true, false);
        }

        sb.draw(
                this.img,
                this.x,
                this.y,
                (float)this.img.packedWidth / 2.0F,
                (float)this.img.packedHeight / 2.0F,
                (float)this.img.packedWidth,
                (float)this.img.packedHeight,
                this.scale,
                this.scale,
                this.rotation
        );
    }

    @Override
    public void dispose() {
    }
}
