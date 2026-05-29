package thehogmod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import thehogmod.util.TextureLoader;

import static thehogmod.TheHogMod.imagePath;

public class AttackEffectPatch {

    private static final Texture LacerationTexture = TextureLoader.getTexture(imagePath("vfx/LacerationEffect.png"), true);

    @SpirePatch(clz = FlashAtkImgEffect.class, method = "loadImage")
    public static class Vfx {
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> Prefix(FlashAtkImgEffect __instance, AbstractGameAction.AttackEffect ___effect) {
            if (___effect == EnumPatch.HOG_LACERATION) {
                return SpireReturn.Return(new TextureAtlas.AtlasRegion(
                        LacerationTexture,
                        0, 0,
                        LacerationTexture.getWidth(),
                        LacerationTexture.getHeight()
                ));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = FlashAtkImgEffect.class, method = "playSound")
    public static class Sfx {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(FlashAtkImgEffect __instance, AbstractGameAction.AttackEffect effect) {
            if (effect == EnumPatch.HOG_LACERATION) {
                // Pick any base-game sound, or your own registered sound
                CardCrawlGame.sound.playAV("ATTACK_POISON", MathUtils.random(-0.1F, -0.3F), 1.1F);

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
