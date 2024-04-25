package committee.nova.barrierfree.mixin;

import committee.nova.barrierfree.api.ExtendedEntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = EntityEntry.class, remap = false)
public abstract class MixinEntityEntry implements ExtendedEntityEntry {
    @Unique
    private boolean barrierfree$canClimb = false;

    @Unique
    private boolean barrierfree$canOpenDoor = false;

    @Override
    public boolean barrierfree$canClimb() {
        return this.barrierfree$canClimb;
    }

    @Override
    public void barrierfree$markCanClimb(boolean canClimb) {
        this.barrierfree$canClimb = canClimb;
    }

    @Override
    public boolean barrierfree$canOpenDoor() {
        return this.barrierfree$canOpenDoor;
    }

    @Override
    public void barrierfree$markCanOpenDoor(boolean canOpenDoor) {
        this.barrierfree$canOpenDoor = canOpenDoor;
    }
}
