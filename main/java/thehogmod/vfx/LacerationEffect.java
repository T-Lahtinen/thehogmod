package thehogmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

public class LacerationEffect extends AbstractGameEffect {
    private final AbstractCreature target;
    private int count = 0;
    private float timer = 0.0F;

    public LacerationEffect(AbstractCreature target) {
        this.target = target;
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += 0.3F;
            switch (this.count) {
                case 0:
                    CardCrawlGame.sound.playA("DEBUFF_1", -0.25F);
                    AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX, target.hb.cY + 50.0F * Settings.scale));
                    break;
                case 1:
                    AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX + 50.0F * Settings.scale, target.hb.cY - 8.0F * Settings.scale));
                    break;
                case 2:
                    AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX - 20.0F * Settings.scale, target.hb.cY + 5.0F * Settings.scale));
                    break;
            }

            ++this.count;
            if (this.count == 6) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
