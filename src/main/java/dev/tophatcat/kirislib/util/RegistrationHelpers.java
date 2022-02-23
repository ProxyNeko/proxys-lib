/*
 * Spooky Biomes - https://tophatcat.dev/spooky-biomes
 * Copyright (C) 2016-2022 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package dev.tophatcat.kirislib.util;

import com.mojang.datafixers.util.Pair;
import dev.tophatcat.kirislib.common.items.BurnableBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistrationHelpers {

    public static <BLOCK extends Block, ITEM extends BlockItem>
    Pair<RegistryObject<BLOCK>, RegistryObject<ITEM>> registerBlockAndItem(
        DeferredRegister<Block> blocks,
        DeferredRegister<Item> items,
        String name,
        Supplier<BLOCK> blockFactory,
        Function<? super BLOCK, ITEM> itemFactory) {
        RegistryObject<BLOCK> block = blocks.register(name, blockFactory);
        RegistryObject<ITEM> item = items.register(name, () -> itemFactory.apply(block.get()));
        return Pair.of(block, item);
    }

    public static <BLOCK extends Block> RegistryObject<BLOCK> registerBlockAndStandardItem(
        DeferredRegister<Block> blocks,
        DeferredRegister<Item> items,
        String name,
        Supplier<BLOCK> blockFactory,
        CreativeModeTab tab) {
        return registerBlockAndItem(blocks, items, name, blockFactory, block
            -> new BlockItem(block, new Item.Properties().tab(tab))).getFirst();
    }

    public static <BLOCK extends Block> RegistryObject<BLOCK> registerBlockAndBurnableItem(
        DeferredRegister<Block> blocks,
        DeferredRegister<Item> items,
        String name,
        Supplier<BLOCK> blockFactory,
        int burnTime,
        CreativeModeTab tab) {
        return registerBlockAndItem(blocks, items, name, blockFactory, block
            -> new BurnableBlockItem(block, burnTime, new Item.Properties().tab(tab))).getFirst();
    }
}
