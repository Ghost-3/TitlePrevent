package ghost.titleprevent.mixin.client;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    private Text title;
    @Shadow
    private int titleRemainTicks;
    @Unique
    private final String[] titleBlacklist =
            {"UNCOMMON DROP!", "RARE DROP!", "CRAZY RARE DROP!", "PRAY TO RNGESUS DROP!"};

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V", at = @At("HEAD"))
    private void preRenderHud(CallbackInfo ci) {

        if (title != null) {
            Log.info(LogCategory.LOG, title.getString());

            for (String item : titleBlacklist) {
                if (title != null && title.getString().equals(item)) {
                    title = null;
                    titleRemainTicks = 0;
                }
            }
        }
    }
}