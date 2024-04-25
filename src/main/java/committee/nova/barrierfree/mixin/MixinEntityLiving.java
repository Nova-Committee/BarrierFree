package committee.nova.barrierfree.mixin;

import committee.nova.barrierfree.api.ExtendedEntityEntry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLiving.class)
public abstract class MixinEntityLiving extends EntityLivingBase {
    @Shadow
    @Final
    public EntityAITasks tasks;

    @Shadow
    public abstract PathNavigate getNavigator();

    @Shadow
    protected PathNavigate navigator;

    public MixinEntityLiving(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void inject$onInitialSpawn(World worldIn, CallbackInfo ci) {
        final ExtendedEntityEntry entry = (ExtendedEntityEntry) EntityRegistry.getEntry(this.getClass());
        if (entry == null) return;
        if (entry.barrierfree$canClimb()) {
            this.navigator = new PathNavigateClimber((EntityLiving) (Object) this, world);
        }
        if (entry.barrierfree$canOpenDoor()) {
            final NodeProcessor node = this.getNavigator().getNodeProcessor();
            node.setCanOpenDoors(true);
            node.setCanEnterDoors(true);
            this.tasks.addTask(1, new EntityAIOpenDoor((EntityLiving) (Object) this, true));
        }
    }
}
