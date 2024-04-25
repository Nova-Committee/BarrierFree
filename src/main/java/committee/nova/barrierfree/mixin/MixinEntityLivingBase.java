package committee.nova.barrierfree.mixin;

import committee.nova.barrierfree.api.ExtendedEntityEntry;
import committee.nova.barrierfree.api.IMayClimb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity implements IMayClimb {
    @Unique
    private EntityEntry barrierfree$entryCache = null;

    @Unique
    private boolean barrierfree$isClimbing = false;

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }


    @Inject(method = "isOnLadder", at = @At("HEAD"), cancellable = true)
    private void inject$isOnLadder(CallbackInfoReturnable<Boolean> cir) {
        if (barrierfree$canClimb() && barrierfree$isClimbing()) cir.setReturnValue(true);
    }

    @Inject(method = "onUpdate", at = @At("TAIL"))
    private void inject$onUpdate(CallbackInfo ci) {
        if (!barrierfree$canClimb()) return;
        barrierfree$setClimbing(this.collidedHorizontally);
    }

    @Unique
    private boolean barrierfree$canClimb() {
        if (this.barrierfree$entryCache == null) this.barrierfree$entryCache = EntityRegistry.getEntry(this.getClass());
        if (this.barrierfree$entryCache == null) return false;
        return ((ExtendedEntityEntry) this.barrierfree$entryCache).barrierfree$canClimb();
    }

    @Override
    public boolean barrierfree$isClimbing() {
        return this.barrierfree$isClimbing;
    }

    @Override
    public void barrierfree$setClimbing(boolean climbing) {
        this.barrierfree$isClimbing = climbing;
    }
}
