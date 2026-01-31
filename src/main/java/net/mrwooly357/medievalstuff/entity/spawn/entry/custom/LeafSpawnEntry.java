package net.mrwooly357.medievalstuff.entity.spawn.entry.custom;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnCondition;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContext;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntry;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunction;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinder;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRule;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelector;
import net.mrwooly357.medievalstuff.util.TriPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class LeafSpawnEntry extends SpawnEntry {

    protected static TriPredicate<Vec3d, Entity, SpawnContext> DEFAULT_COMBINED_RULE = (vec3d, entity, context) -> true;
    protected static BiFunction<Entity, SpawnContext, Entity> DEFAULT_COMBINED_FUNCTION = (entity, context) -> entity;

    protected final SpawnPosFinder posFinder;
    protected final boolean ignoreVanillaRules;
    protected final List<SpawnRule> rules;
    protected TriPredicate<Vec3d, Entity, SpawnContext> combinedRule = null;
    protected final List<SpawnFunction> functions;
    protected BiFunction<Entity, SpawnContext, Entity> combinedFunction = null;

    protected LeafSpawnEntry(SpawnSelector.Data selectorData, List<SpawnCondition> conditions, SpawnPosFinder posFinder, boolean ignoreVanillaRules, List<SpawnRule> rules, List<SpawnFunction> functions) {
        super(selectorData, conditions);

        this.posFinder = posFinder;
        this.ignoreVanillaRules = ignoreVanillaRules;
        this.rules = rules;
        this.functions = functions;
    }


    protected static <E extends LeafSpawnEntry> Products.P6<RecordCodecBuilder.Mu<E>, SpawnSelector.Data, List<SpawnCondition>,
            SpawnPosFinder, Boolean, List<SpawnRule>, List<SpawnFunction>> addLeafFields(RecordCodecBuilder.Instance<E> instance) {
        return addDefaultFields(instance)
                .and(instance.group(
                        SpawnPosFinder.CODEC.fieldOf("pos_finder").forGetter(entry -> entry.posFinder),
                        Codec.BOOL.optionalFieldOf("ignore_vanilla_rules", false).forGetter(entry -> entry.ignoreVanillaRules),
                        SpawnRule.CODEC.listOf().optionalFieldOf("rules", List.of()).forGetter(entry -> entry.rules),
                        SpawnFunction.CODEC.listOf().optionalFieldOf("functions", List.of()).forGetter(entry -> entry.functions)
                ));
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
    public List<Entity> tryGenerateEntities(SpawnContext context) {
        rules.forEach(context::check);
        functions.forEach(context::check);
        List<Entity> entities = new ArrayList<>();
        createEntities(context).forEach(entity -> {
            List<Vec3d> banned = new ArrayList<>();
            Vec3d pos = posFinder.nextPos(context, banned);
            int attempt = 1;
            int maxAttempts = posFinder.getMaxAttempts();

            while (!combinedRule.test(pos, entity, context) && attempt < maxAttempts) {
                banned.add(pos);
                pos = posFinder.nextPos(context, banned);
                attempt++;
            }

            if (combinedRule.test(pos, entity, context)) {
                entity.refreshPositionAndAngles(pos, 0.0F, 0.0F);

                if (ignoreVanillaRules || (entity instanceof MobEntity mob && mob.canSpawn(context.getWorld())))
                    entities.add(entity);
            }
        });
        List<Entity> processed = new ArrayList<>();
        entities.forEach(entity -> processed.add(combinedFunction.apply(entity, context)));

        return List.copyOf(processed);
    }

    protected abstract List<Entity> createEntities(SpawnContext context);
}
