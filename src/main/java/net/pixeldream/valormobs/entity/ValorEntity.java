package net.pixeldream.valormobs.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animatable.instance.SingletonAnimatableInstanceCache;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.pixeldream.valormobs.entity.tierhard.ExecutionerEntity;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;

import java.util.List;

public abstract class ValorEntity extends PathfinderMob implements GeoEntity, Enemy, SmartBrainOwner<ValorEntity> {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(ValorEntity.class, EntityDataSerializers.INT);


    protected ValorEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public int getAttckingState() {
        return entityData.get(STATE);
    }

    public void setAttackingState(int time) {
        entityData.set(STATE, time);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STATE, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setAttackingState(compound.getInt("state"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("state", getAttckingState());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public BrainActivityGroup<ValorEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<ExecutionerEntity>(
                        new TargetOrRetaliate<>().attackablePredicate(entity -> entity.isAlive() && !(entity instanceof ValorEntity) && (!(entity instanceof Player player) || !player.isCreative())),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().speedModifier(0.25f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 100))));
    }

    @Override
    public List<ExtendedSensor<ValorEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<ValorEntity>().setPredicate(
                        (target, entity) -> target.isAlive() && entity.hasLineOfSight(
                                target) && !(target instanceof ValorEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<ValorEntity> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),   // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>());  // Walk towards the current walk target
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    protected void produceParticles(ParticleOptions parameters) {
        for(int i = 0; i < 5; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.level().addParticle(parameters, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        produceParticles(ParticleTypes.POOF);
        super.die(damageSource);
    }
}