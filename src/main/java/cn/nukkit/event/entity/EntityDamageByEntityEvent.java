package cn.nukkit.event.entity;

import cn.nukkit.api.Since;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.potion.Effect;

import java.util.Map;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityDamageByEntityEvent extends EntityDamageEvent {

    private final Entity damager;

    private float knockBack;

    private Enchantment[] enchantments;

    public EntityDamageByEntityEvent(Entity damager, Entity entity, DamageCause cause, float damage) {
        this(damager, entity, cause, damage, 0.3f);
    }

    public EntityDamageByEntityEvent(Entity damager, Entity entity, DamageCause cause, Map<DamageModifier, Float> modifiers) {
        this(damager, entity, cause, modifiers, 0.3f);
    }

    public EntityDamageByEntityEvent(Entity damager, Entity entity, DamageCause cause, float damage, float knockBack) {
        super(entity, cause, damage);
        this.damager = damager;
        this.knockBack = knockBack;
        this.addAttackerModifiers(damager);
    }

    public EntityDamageByEntityEvent(Entity damager, Entity entity, DamageCause cause, Map<DamageModifier, Float> modifiers, float knockBack) {
        this(damager, entity, cause, modifiers, knockBack, Enchantment.EMPTY_ARRAY);
    }

    @Since("FUTURE")
    public EntityDamageByEntityEvent(Entity damager, Entity entity, DamageCause cause, Map<DamageModifier, Float> modifiers, float knockBack, Enchantment[] enchantments) {
        super(entity, cause, modifiers);
        this.damager = damager;
        this.knockBack = knockBack;
        this.enchantments = enchantments == null? Enchantment.EMPTY_ARRAY : enchantments.clone();
        this.addAttackerModifiers(damager);
    }

    protected void addAttackerModifiers(Entity damager) {
        if (damager.hasEffect(Effect.STRENGTH)) {
            this.setDamage((float) (this.getDamage(DamageModifier.BASE) * 0.3 * (damager.getEffect(Effect.STRENGTH).getAmplifier() + 1)), DamageModifier.STRENGTH);
        }

        if (damager.hasEffect(Effect.WEAKNESS)) {
            this.setDamage(-(float) (this.getDamage(DamageModifier.BASE) * 0.2 * (damager.getEffect(Effect.WEAKNESS).getAmplifier() + 1)), DamageModifier.WEAKNESS);
        }
    }

    public Entity getDamager() {
        return damager;
    }

    public float getKnockBack() {
        return knockBack;
    }

    public void setKnockBack(float knockBack) {
        this.knockBack = knockBack;
    }

    @Since("FUTURE")
    public Enchantment[] getWeaponEnchantments() {
        return enchantments.length > 0? enchantments.clone() : Enchantment.EMPTY_ARRAY;
    }
}
