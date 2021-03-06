/*
 * Copyright (c) 2012.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.edoxile.bettermechanics.utils;

import net.edoxile.bettermechanics.models.blockbags.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public class BlockBagHandler {
    public static BlockBagHandler locate(Sign sign) throws BlockBagException {
        BlockBag source = null, sink = null;
        ArrayList<Block> signList = searchBlockType(sign.getBlock(), 2, Material.WALL_SIGN, Material.SIGN_POST);
        ChestBag chestBag = new ChestBag((Chest) searchSingleBlockType(sign.getBlock(), 2, Material.CHEST).getState());
        if (signList.isEmpty() && chestBag.isEmpty()) {
            throw new BlockBagException(BlockBagException.Type.NO_BAG_FOUND);
        } else {
            if (!signList.isEmpty()) {
                for (Block b : signList) {
                    Sign s = (Sign) b.getState();
                    String id = s.getLine(1);
                    if (sink == null && id.equals("BlackHole")) {
                        sink = new BlackHole();
                    } else if (source == null && id.equals("BlockSource")) {
                        source = new BlockSource();
                    }
                }
            }
            if (!chestBag.isEmpty()) {
                if (source == null) {
                    source = chestBag;
                }
                if (sink == null) {
                    sink = chestBag;
                }
            }
        }
        if (sink == null || source == null) {
            throw new BlockBagException(BlockBagException.Type.NO_BAG_FOUND);
        } else {
            return new BlockBagHandler(source, sink);
        }
    }

    private BlockBag source = null, sink = null;

    private BlockBagHandler(BlockBag both) {
        source = both;
    }

    private BlockBagHandler(BlockBag sourceBag, BlockBag sinkBag) {
        source = sourceBag;
        sink = sinkBag;
    }

    public boolean removeItems(int id, byte data, int amount) {
        try {
            sink.removeItems(id, data, amount);
            return true;
        } catch (BlockBagException e) {
            return false;
        }
    }

    public boolean storeItems(int id, byte data, int amount) {
        try {
            source.storeItems(id, data, amount);
            return true;
        } catch (BlockBagException e) {
            return false;
        }
    }

    private static ArrayList<Block> searchBlockType(Block block, int distance, Material... materials) {
        ArrayList<Block> blockList = new ArrayList<Block>();
        List<Material> materialList = Arrays.asList(materials);
        for (int dy = -distance; dy < 0; dy++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (dx == 0 && dz == 0 && dy == 0) {
                        continue;
                    }
                    Block toCheck = block.getRelative(dx, dy, dz);
                    if (materialList.contains(toCheck.getType())) {
                        blockList.add(toCheck);
                    }
                }
            }
        }
        for (int dy = 0; dy <= distance; dy++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (dx == 0 && dz == 0 && dy == 0) {
                        continue;
                    }
                    Block toCheck = block.getRelative(dx, dy, dz);
                    if (materialList.contains(toCheck.getType())) {
                        blockList.add(toCheck);
                    }
                }
            }
        }
        return blockList;
    }

    private static Block searchSingleBlockType(Block block, int distance, Material material) {
        for (int dy = -distance; dy < 0; dy++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (dx == 0 && dz == 0 && dy == 0) {
                        continue;
                    }
                    Block toCheck = block.getRelative(dx, dy, dz);
                    if (toCheck.getTypeId() == material.getId()) {
                        return toCheck;
                    }
                }
            }
        }
        for (int dy = 0; dy <= distance; dy++) {
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dz = -distance; dz <= distance; dz++) {
                    if (dx == 0 && dz == 0 && dy == 0) {
                        continue;
                    }
                    Block toCheck = block.getRelative(dx, dy, dz);
                    if (toCheck.getTypeId() == material.getId()) {
                        return toCheck;
                    }
                }
            }
        }
        return null;
    }
}
