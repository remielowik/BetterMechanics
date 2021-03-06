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

package net.edoxile.bettermechanics.mechanics.interfaces;

import net.edoxile.bettermechanics.BetterMechanics;
import net.edoxile.bettermechanics.models.BlockMap;
import net.edoxile.bettermechanics.models.BlockMapException;
import net.edoxile.bettermechanics.models.SignMechanicEventData;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Edoxile
 */
public abstract class SignMechanic extends BlockMechanic {

    public void onSignPowerOn(SignMechanicEventData data) {
    }

    public void onSignPowerOff(SignMechanicEventData data) {
    }

    public void onPlayerRightClickSign(Player player, SignMechanicEventData data) {
    }

    public void onPlayerLeftClickSign(Player player, SignMechanicEventData data) {
    }

    public abstract boolean hasBlockMapper();

    public abstract boolean hasBlockBag();

    public BlockMap mapBlocks(Sign s) throws BlockMapException{
        BetterMechanics.log("BlockMapper called but not implemented in this mechanic.", Level.WARNING);
        return null;
    }

    public abstract List<String> getIdentifier();

    public abstract List<Material> getMechanicActivator();

    public List<Material> getMechanicTarget() {
        return Arrays.asList(Material.WALL_SIGN, Material.SIGN_POST);
    }
}
