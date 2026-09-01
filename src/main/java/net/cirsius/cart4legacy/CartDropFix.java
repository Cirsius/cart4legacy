package net.cirsius.cart4legacy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.inventory.ItemStack;

final class CartDropFix implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (!(event.getVehicle() instanceof ExplosiveMinecart)) return;

        event.setCancelled(true);
        Location location = event.getVehicle().getLocation();
        event.getVehicle().remove();
        location.getWorld().dropItemNaturally(location, new ItemStack(Material.TNT_MINECART));
    }
}
