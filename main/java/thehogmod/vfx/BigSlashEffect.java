package thehogmod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class BigSlashEffect extends AbstractGameEffect {
    private final Color color2;
    private float x;
    private float y;
    private boolean isVertical;

    public BigSlashEffect(float x, float y, boolean isVertical, Color color) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.isVertical = isVertical;
        this.color = color;
        this.color2 = color;
    }

    public BigSlashEffect(float x, float y, boolean isVertical, Color color, Color color2) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.isVertical = isVertical;
        this.color = color;
        this.color2 = color2;
    }

    public void update() {
        if (this.isVertical) {
            CardCrawlGame.sound.playA("ATTACK_IRON_3", -0.4F);
            CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.4F);
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, 0.0F, -500.0F, 180.0F, 5.0F, this.color, this.color2));
        } else {
            CardCrawlGame.sound.playA("ATTACK_IRON_1", -0.4F);
            CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.4F);
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, -500.0F, -500.0F, 135.0F, 4.0F, this.color, this.color2));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}