package committee.nova.barrierfree.config;

import committee.nova.barrierfree.BarrierFree;
import committee.nova.barrierfree.api.ExtendedEntityEntry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;

import java.util.Arrays;
import java.util.List;

@Config(modid = BarrierFree.MODID)
@Mod.EventBusSubscriber
public class BFConfig {
    public static String[] entitiesCanClimb = new String[]{
            "example:entity1",
            "example:entity2"
    };

    public static String[] entitiesCanOpenDoor = new String[]{
            "example:entity1",
            "example:entity2"
    };

    public static void refresh() {
        final List<String> canClimb = Arrays.asList(entitiesCanClimb);
        final List<String> canOpenDoor = Arrays.asList(entitiesCanOpenDoor);
        final ForgeRegistry<EntityEntry> entityReg = GameData.getEntityRegistry();
        entityReg.getKeys().forEach(rl -> {
            final ExtendedEntityEntry entry = (ExtendedEntityEntry) entityReg.getValue(rl);
            if (entry == null) return;
            entry.barrierfree$markCanClimb(canClimb.contains(rl.toString()));
            entry.barrierfree$markCanOpenDoor(canOpenDoor.contains(rl.toString()));
        });
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(BarrierFree.MODID)) {
            ConfigManager.sync(BarrierFree.MODID, Config.Type.INSTANCE);
            refresh();
        }
    }
}
