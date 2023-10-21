package ghost.titleprevent.mixin.client;

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
            {"§a§lUNCOMMON DROP!", "§9§lRARE DROP!", "§d§lCRAZY RARE DROP!", "§5§lPRAY TO RNGESUS DROP!",
                    "§f§lUNCOMMON DROP!", "§f§lRARE DROP!", "§f§lCRAZY RARE DROP!", "§f§lPRAY TO RNGESUS DROP!"};

    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V", at = @At("HEAD"))
    private void preRenderHud(CallbackInfo ci) {
        if (title != null) {
            for (String item : titleBlacklist) {
                if (title != null && title.getString().equals(item)) {
                    title = null;
                    titleRemainTicks = 0;
                }
            }
        }
    }
}