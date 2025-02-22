package dev.tr7zw.skinlayers;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.ConfigGuiHandler.ConfigGuiFactory;

@Mod("skinlayers3d")
public class EntityCullingMod extends SkinLayersModBase {

    //Forge only
    private boolean onServer = false;
    
    public EntityCullingMod() {
        try {
            Class clientClass = net.minecraft.client.Minecraft.class;
        }catch(Throwable ex) {
            System.out.println("3dSkinLayers Mod installed on a Server. Going to sleep.");
            onServer = true;
            return;
        }
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiFactory.class, () -> new ConfigGuiFactory((mc, screen) -> {
            return createConfigScreen(screen);
        }));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        if(onServer)return;
        onInitialize();
    }
    
}
