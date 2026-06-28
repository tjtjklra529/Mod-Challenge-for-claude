package com.echogemmod.command;

import com.echogemmod.registry.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;

/**
 * /echogems give <player> <item_id>  — admin command to give any echo gems item.
 * /echogems list                     — lists all registered mod items.
 * /echogems reload                   — placeholder for config reload.
 *
 * Requires operator level 2.
 */
public class EchoGemsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess access,
                                CommandManager.RegistrationEnvironment env) {
        dispatcher.register(
            CommandManager.literal("echogems")
                .requires(src -> src.hasPermissionLevel(2))

                .then(CommandManager.literal("list")
                    .executes(ctx -> {
                        StringBuilder sb = new StringBuilder("§b[EchoGems] §fItems: ");
                        Registries.ITEM.getIds().stream()
                            .filter(id -> id.getNamespace().equals("echogems"))
                            .forEach(id -> sb.append("§e").append(id.getPath()).append("§f, "));
                        ctx.getSource().sendFeedback(() -> Text.literal(sb.toString()), false);
                        return 1;
                    }))

                .then(CommandManager.literal("give")
                    .then(CommandManager.argument("targets", EntityArgumentType.players())
                        .then(CommandManager.argument("item", StringArgumentType.word())
                            .executes(ctx -> {
                                String itemName = StringArgumentType.getString(ctx, "item");
                                Identifier id = new Identifier("echogems", itemName);
                                Item item = Registries.ITEM.get(id);

                                if (item == Registries.ITEM.get(new Identifier("minecraft:air"))) {
                                    ctx.getSource().sendError(
                                        Text.literal("§c[EchoGems] Unknown item: " + itemName));
                                    return 0;
                                }

                                Collection<ServerPlayerEntity> players =
                                    EntityArgumentType.getPlayers(ctx, "targets");
                                for (ServerPlayerEntity player : players) {
                                    ItemStack stack = new ItemStack(item);
                                    boolean added = player.getInventory().insertStack(stack);
                                    if (!added) player.dropItem(stack, false);
                                    ctx.getSource().sendFeedback(() ->
                                        Text.literal("§b[EchoGems] §fGave §e" + itemName
                                            + "§f to §e" + player.getName().getString()), true);
                                }
                                return players.size();
                            }))))

                .then(CommandManager.literal("reload")
                    .executes(ctx -> {
                        ctx.getSource().sendFeedback(
                            () -> Text.literal("§b[EchoGems] §fConfig reloaded."), false);
                        return 1;
                    }))
        );
    }
}
