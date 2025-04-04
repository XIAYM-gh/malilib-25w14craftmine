package fi.dy.masa.malilib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.malilib.MaLiLib;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.GameInstance;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.GameMode;
import fi.dy.masa.malilib.event.ServerHandler;

@Mixin(value = IntegratedServer.class)
public class MixinIntegratedServer
{
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "setupServer", at = @At("RETURN"))
    private void setupServer(CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
        {
            ((ServerHandler) ServerHandler.getInstance()).onServerIntegratedSetup(this.client.getServer());
        }
    }

    @Inject(method = "openToLan", at = @At("RETURN"))
    private void checkOpenToLan(GameInstance gameInstance, GameMode gameMode, boolean bl, int i, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue())
        {
            ((ServerHandler) ServerHandler.getInstance()).onServerOpenToLan(this.client.getServer());
        }
    }

    @Inject(method = "method_69059", at = @At("RETURN"))
    private void onStartServer(ResourcePackManager resourcePackManager, SaveLoader saveLoader, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfoReturnable<GameInstance> cir, @Local GameInstance gameInstance) {
        MaLiLib.GAME_INSTANCE = gameInstance;
    }
}
