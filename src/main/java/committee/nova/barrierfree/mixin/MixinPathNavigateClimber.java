package committee.nova.barrierfree.mixin;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PathNavigateClimber.class)
public abstract class MixinPathNavigateClimber extends PathNavigateGround {
    @Shadow
    private BlockPos targetPosition;

    public MixinPathNavigateClimber(EntityLiving entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    @Inject(method = "onUpdateNavigation", at = @At("HEAD"), cancellable = true)
    private void inject$onUpdateNavigation(CallbackInfo ci) {
        ci.cancel();
        if (!this.noPath()) {
            super.onUpdateNavigation();
        } else {
            if (this.targetPosition != null) {
                double d0 = Math.max(this.entity.width * this.entity.width, 1.0);

                if (this.entity.getDistanceSqToCenter(this.targetPosition) >= d0 && (this.entity.posY <= (double) this.targetPosition.getY() || this.entity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor(this.entity.posY), this.targetPosition.getZ())) >= d0)) {
                    this.entity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
                } else {
                    this.targetPosition = null;
                }
            }
        }
    }
}
