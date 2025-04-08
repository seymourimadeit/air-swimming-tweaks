package tallestegg.biome_tag_swim.mixins;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.biome_tag_swim.BiomeTagSwim;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends LivingEntity {

    protected LocalPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At(value = "RETURN"), method = "isUnderWater", cancellable = true)
    public void isUnderWater(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(level().getBiome(blockPosition()).is(BiomeTagSwim.CAN_SWIM));
    }
}
