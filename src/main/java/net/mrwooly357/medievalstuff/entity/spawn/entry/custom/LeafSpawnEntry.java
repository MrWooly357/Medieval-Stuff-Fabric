package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.util.TetraPredicate;
import net.mrwooly357.medievalstuff.util.TriFunction;

import java.util.ArrayList;
import java.util.List;

public abstract class LeafSpawnEntry extends SpawnEntry {

    protected static TetraPredicate<Vec3d, Entity, SpawnContext, SpawnReason> DEFAULT_COMBINED_RULE = (vec3d, entity, context, reason) -> true;
    protected static TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> DEFAULT_COMBINED_FUNCTION = (entity, context, reason) -> Pair.of(entity, reason);

    protected final SpawnPosFinder posFinder;
    protected final boolean ignoreVanillaRules;
    protected final List<SpawnRule> rules;
    protected TetraPredicate<Vec3d, Entity, SpawnContext, SpawnReason> combinedRule = null;
    protected final List<SpawnFunction> functions;
    protected TriFunction<Entity, SpawnContext, SpawnReason, Pair<Entity, SpawnReason>> combinedFunction = null;

    protected LeafSpawnEntry(
            SpawnSelector.Data selectorData,
            List<SpawnCondition> conditions,
            SpawnReason reason,
            SpawnPosFinder posFinder,
            boolean ignoreVanillaRules,
            List<SpawnRule> rules,
            List<SpawnFunction> functions
    ) {
        super(selectorData, conditions, reason);

        this.posFinder = posFinder;
        this.ignoreVanillaRules = ignoreVanillaRules;
        this.rules = rules;
        this.functions = functions;
    }


    @Override
    public void setCombinedRule(List<SpawnRule> poolRules, List<SpawnRule> tableRules) {
        List<SpawnRule> rules1 = new ArrayList<>();

        if (!poolRules.isEmpty())
            rules1.addAll(poolRules);

        if (!tableRules.isEmpty())
            rules1.addAll(tableRules);

        if (!rules.isEmpty())
            rules1.addAll(rules);

        if (!rules1.isEmpty())
            combinedRule = SpawnRule.combine(rules1);
        else
            combinedRule = DEFAULT_COMBINED_RULE;
    }

    @Override
    public void setCombinedFunction(List<SpawnFunction> poolFunctions, List<SpawnFunction> tableFunctions) {
        List<SpawnFunction> functions1 = new ArrayList<>();

        if (!poolFunctions.isEmpty())
            functions1.addAll(poolFunctions);

        if (!tableFunctions.isEmpty())
            functions1.addAll(tableFunctions);

        if (!functions.isEmpty())
            functions1.addAll(functions);

        if (!functions1.isEmpty())
            combinedFunction = SpawnFunction.combine(functions1);
        else
            combinedFunction = DEFAULT_COMBINED_FUNCTION;
    }

    @Override
    public List<Pair<Entity, SpawnReason>> tryGenerateEntities(SpawnContext context) {
        rules.forEach(context::check);
        functions.forEach(context::check);
        List<Pair<Entity, SpawnReason>> entities = new ArrayList<>();
        createEntities(context).forEach(pair -> {
            Entity entity = pair.getFirst();
            SpawnReason reason1 = pair.getSecond();
            ServerWorld world = context.getWorld();
            List<Vec3d> banned = new ArrayList<>();
            Vec3d pos = posFinder.nextPos(context, banned);
            int attempt = 1;
            int maxAttempts = posFinder.getMaxAttempts();

            while (!combinedRule.test(pos, entity, context, reason) && attempt < maxAttempts) {
                banned.add(pos);
                pos = posFinder.nextPos(context, banned);
                attempt++;
            }

            if (combinedRule.test(pos, entity, context, reason) && (!ignoreVanillaRules ||
                    (SpawnRestriction.canSpawn(entity.getType(), world, reason1, BlockPos.ofFloored(pos), world.getRandom()) && (!(entity instanceof MobEntity mob) || mob.canSpawn(world))))) {
                entity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
                entities.add(pair);
            }
        });
        List<Pair<Entity, SpawnReason>> processed = new ArrayList<>();
        entities.forEach(pair -> processed.add(combinedFunction.apply(pair.getFirst(), context, reason)));

        return List.copyOf(processed);
    }

    protected abstract List<Pair<Entity, SpawnReason>> createEntities(SpawnContext context);


    protected abstract static class Builder<B extends Builder<B>> extends SpawnEntry.Builder<B> {

        protected boolean ignoreVanillaRules = false;
        protected final List<SpawnRule> rules = new ArrayList<>();
        protected final List<SpawnFunction> functions = new ArrayList<>();


        public B ignoreVanillaSpawnRules() {
            if (!ignoreVanillaRules) {
                ignoreVanillaRules = true;

                return getThisBuilder();
            } else
                throw new UnsupportedOperationException("Ignore vanilla rules is already set to true!");
        }

        public B rule(SpawnRule rule) {
            if (!rules.contains(rule)) {
                rules.add(rule);

                return getThisBuilder();
            } else
                throw new IllegalArgumentException("Duplicate spawn rule! Duplicate: " + rule + ".");
        }

        public B function(SpawnFunction function) {
            if (!functions.contains(function)) {
                functions.add(function);

                return getThisBuilder();
            } else
                throw new IllegalArgumentException("Duplicate spawn function! Duplicate: " + function + ".");
        }
    }
}
