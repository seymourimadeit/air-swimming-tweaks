package tallestegg.biome_tag_swim.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.biome_tag_swim.BiomeTagSwim;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow
    public Abilities abilities;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isUnderWater() {
        return this.isInWater() || super.isUnderWater();
    }

    @Override
    public boolean isInWater() {
        return level().getBiome(blockPosition()).is(BiomeTagSwim.CAN_SWIM) || super.isInWater();
    }

    @Override
    public boolean canStartSwimming() {
        return level().getBiome(blockPosition()).is(BiomeTagSwim.CAN_SWIM) || super.canStartSwimming();
    }

    @Inject(at = @At(value = "TAIL"), method = "tick", cancellable = true)
    public void jump(CallbackInfo ci) {
        if (level().getBiome(blockPosition()).is(BiomeTagSwim.CAN_SWIM) && jumping)
            this.jumpInFluid(net.neoforged.neoforge.common.NeoForgeMod.WATER_TYPE.value());
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        super.checkFallDamage(y, onGround, state, pos);
        if (level().getBiome(blockPosition()).is(BiomeTagSwim.CAN_SWIM))
            this.resetFallDistance();
    }
}
