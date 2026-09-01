package net.cirsius.cart4legacy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class CrossbowFix implements Listener {
    private final Set<AbstractArrow> arrows = new HashSet<>();

    CrossbowFix(JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 1, 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onShoot(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();
        if (bow != null && bow.getType() == Material.CROSSBOW
                && event.getProjectile() instanceof AbstractArrow) {
            AbstractArrow arrow = (AbstractArrow) event.getProjectile();
            if (!ignite(arrow)) arrows.add(arrow);
        }
    }

    private void tick() {
        Iterator<AbstractArrow> iterator = arrows.iterator();
        while (iterator.hasNext()) {
            AbstractArrow arrow = iterator.next();
            if (!arrow.isValid() || arrow.isInBlock() || arrow.getFireTicks() > 0 || ignite(arrow)) {
                iterator.remove();
            }
        }
    }

    private boolean ignite(AbstractArrow arrow) {
        Location location = arrow.getLocation();
        Vector start = location.toVector();
        Vector velocity = arrow.getVelocity();
        double distance = velocity.length();
        if (distance == 0) return false;

        BlockIterator blocks = new BlockIterator(location.getWorld(), start, velocity, 0,
                (int) Math.ceil(distance));
        while (blocks.hasNext()) {
            Block block = blocks.next();
            if (isFire(block) && BoundingBox.of(block).rayTrace(start, velocity, distance) != null) {
                arrow.setFireTicks(100);
                return true;
            }
        }
        return false;
    }

    private boolean isFire(Block block) {
        Material type = block.getType();
        return type == Material.FIRE || type == Material.LAVA || type.name().equals("SOUL_FIRE");
    }
}
