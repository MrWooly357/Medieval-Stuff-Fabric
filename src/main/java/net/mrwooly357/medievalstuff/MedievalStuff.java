package net.mrwooly357.medievalstuff;

import net.fabricmc.api.ModInitializer;

import net.mrwooly357.medievalstuff.attachment.MSAttachmentTypes;
import net.mrwooly357.medievalstuff.block.MSBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.MSBlocks;
import net.mrwooly357.medievalstuff.command.MSCommands;
import net.mrwooly357.medievalstuff.enchantment.effects.custom.entity_damage.MSEnchantmentEntityDamageEffects;
import net.mrwooly357.medievalstuff.entity.spawn.condition.SpawnConditionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.context.SpawnContextTypes;
import net.mrwooly357.medievalstuff.entity.spawn.entry.SpawnEntryTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.SpawnFunctionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.function.condition.SpawnFunctionConditionTypes;
import net.mrwooly357.medievalstuff.entity.spawn.pos_finder.SpawnPosFinderTypes;
import net.mrwooly357.medievalstuff.entity.spawn.rule.SpawnRuleTypes;
import net.mrwooly357.medievalstuff.entity.spawn.selector.SpawnSelectorTypes;
import net.mrwooly357.medievalstuff.item.MSItems;
import net.mrwooly357.medievalstuff.item.component.MSEnchantmentEffectComponentTypes;
import net.mrwooly357.medievalstuff.registry.MSRegistries;
import net.mrwooly357.medievalstuff.world.mark.WorldMarkTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MedievalStuff implements ModInitializer {

	public static final String MOD_ID = "medievalstuff";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		MSRegistries.initialize();
		MSItems.initialize();
		MSEnchantmentEntityDamageEffects.initialize();
		MSEnchantmentEffectComponentTypes.initialize();
		MSBlocks.initialize();
		MSBlockEntityTypes.initialize();
		SpawnContextTypes.initialize();
		SpawnSelectorTypes.initialize();
		SpawnConditionTypes.initialize();
		SpawnPosFinderTypes.initialize();
		SpawnRuleTypes.initialize();
		SpawnFunctionConditionTypes.initialize();
		SpawnFunctionTypes.initialize();
		SpawnEntryTypes.initialize();
		WorldMarkTypes.initialize();
		MSAttachmentTypes.initialize();
		MSCommands.initializeServer();
	}

	public static void logInitialization(String id)  {
        LOGGER.info("Initializing " + MOD_ID + " {}.", id);
	}
}
