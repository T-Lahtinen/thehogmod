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

public class BorromeanRingEffect extends AbstractGameEffect {
    private static TextureAtlas.AtlasRegion img;

    private final float x;
    private final float y;
    private final Color[] ringColors = new Color[3];
    private final float[] rotationOffsets = new float[] { 0.0F, 120.0F, 240.0F };

    public BorromeanRingEffect(float x, float y, Color ring1, Color ring2, Color ring3) {
        if (img == null) {
            img = ImageMaster.WHITE_RING;
        }

        this.startingDuration = 1.0F;
        this.duration = 1.0F;
        this.scale = 3.0F * Settings.scale;
        this.rotation = MathUtils.random(0.0F, 360.0F);

        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;

        this.ringColors[0] = ring1.cpy();
        this.ringColors[1] = ring2.cpy();
        this.ringColors[2] = ring3.cpy();

        for (Color c : this.ringColors) {
            c.a = 0.0F;
        }
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.rotation -= Gdx.graphics.getDeltaTime() * 205.0F;

        float fadeIn = (this.duration > 0.5F)
                ? Interpolation.fade.apply(0.45F, 0.0F, (this.duration - 0.5F) * 2.0F)
                : Interpolation.fade.apply(0.0F, 0.45F, this.duration * 2.0F);

        for (Color c : ringColors) {
            c.a = fadeIn;
        }

        if (this.duration <= 0.5F) {
            this.scale = Interpolation.swingOut.apply(0.0F, 3.0F * Settings.scale, this.duration * 2.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);

        float ringSpacing = 80.0F * Settings.scale;

        float[][] offsets = new float[][] {
                { 0, ringSpacing },
                { -ringSpacing * 0.87F, -ringSpacing / 2.0F },
                {  ringSpacing * 0.87F, -ringSpacing / 2.0F }
        };

        for (int i = 0; i < 3; i++) {
            sb.setColor(ringColors[i]);
            float drawX = this.x + offsets[i][0];
            float drawY = this.y + offsets[i][1];

            sb.draw(img,
                    drawX, drawY,
                    img.packedWidth / 2.0F, img.packedHeight / 2.0F,
                    img.packedWidth, img.packedHeight,
                    this.scale + MathUtils.random(-0.05F, 0.05F),
                    this.scale + MathUtils.random(-0.05F, 0.05F),
                    this.rotation + rotationOffsets[i]);
        }

        sb.setBlendFunction(770, 771);
    }

    @Override
    public void dispose() {
    }
}
