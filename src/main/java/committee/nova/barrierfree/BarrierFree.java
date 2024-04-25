package committee.nova.barrierfree;

import committee.nova.barrierfree.config.BFConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = BarrierFree.MODID, useMetadata = true, guiFactory = "committee.nova.barrierfree.screen.GuiFactory")
public class BarrierFree {
    public static final String MODID = "barrierfree";

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ConfigManager.load(MODID, Config.Type.INSTANCE);
        BFConfig.refresh();
    }
}
