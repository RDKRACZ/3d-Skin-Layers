package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.Settings;
import net.fabricmc.example.renderlayers.BodyLayerFeatureRenderer;
import net.fabricmc.example.renderlayers.HeadLayerFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void onCreate(CallbackInfo info) {
        this.addLayer(new HeadLayerFeatureRenderer(this));
        this.addLayer(new BodyLayerFeatureRenderer(this));
    }
    
    @Inject(method = "setModelProperties", at = @At("RETURN"))
    public void setModelProperties(AbstractClientPlayer abstractClientPlayer, CallbackInfo info) {
        if(Minecraft.getInstance().player.distanceToSqr(abstractClientPlayer) > Settings.viewDistanceSqr)return;
        PlayerModel<AbstractClientPlayer> playerModel = this.getModel();
        playerModel.hat.visible = false;
        playerModel.jacket.visible = false;
        playerModel.leftSleeve.visible = false;
        playerModel.rightSleeve.visible = false;
        playerModel.leftPants.visible = false;
        playerModel.rightPants.visible = false;
    }
    
}
