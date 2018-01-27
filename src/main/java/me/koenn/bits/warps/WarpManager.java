package me.koenn.bits.warps;

import me.koenn.bits.Bits;
import me.koenn.bits.Ref;
import me.koenn.bits.util.FileManager;
import mkremins.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WarpManager {

    public static WarpManager INSTANCE;
    private final FileManager file;
    private List<Warp> warps;

    private WarpManager() {
        this.file = new FileManager("warps.yml");
        this.file.write("version", Bits.getInstance().getDescription().getVersion());
        fetchFile();
    }

    public static void load() {
        INSTANCE = new WarpManager();
    }

    private void fetchFile() {
        warps = new ArrayList<>();
        if (file.getKeys("warps") == null) {
            return;
        }

        for (String warp : this.file.getKeys("warps")) {
            int x = (int) this.file.read("warps." + warp + ".x");
            int y = (int) this.file.read("warps." + warp + ".y");
            int z = (int) this.file.read("warps." + warp + ".z");
            World world = Bukkit.getWorld((String) this.file.read("warps." + warp + ".world"));

            Location location = new Location(world, (double) x, (double) y, (double) z);

            if (this.file.has("warps." + warp + ".category")) {
                String category = (String) this.file.read("warps." + warp + ".category");
                this.warps.add(new Warp(warp, location, this.file, category));
            } else {
                this.warps.add(new Warp(warp, location, this.file));
            }

            sort();
        }
    }

    public void addWarp(String name, Location location) {
        Warp warp = new Warp(name, location, this.file);
        this.warps.add(warp);
        sort();
    }

    public void deleteWarp(Warp warp) {
        warp.delete();
        this.warps.remove(warp);
        sort();
    }

    public Warp getWarp(String name) {
        return this.warps.stream()
                .filter(warp -> warp.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public boolean isWarpInsideChunk(Chunk chunk) {
        for (Warp warp : warps) {
            if (warp.getLocation().getChunk().equals(chunk)) {
                return true;
            }
        }
        return false;
    }

    public void printWarps(Player player, String category) {
        if (category == null) {
            player.sendMessage(Ref.HEADER_CATEGORIES);
            player.sendMessage(Ref.NEUTRAL_CLICK_CATEGORY);
            List<String> categories = new ArrayList<>();
            this.warps.stream()
                    .filter(Warp::hasCategory)
                    .map(Warp::getCategory)
                    .filter(cat -> !categories.contains(cat))
                    .forEach(categories::add);
            categories.add("Other");
            categories.forEach(cat -> new FancyMessage(cat)
                    .formattedTooltip(new FancyMessage("Click to open the ")
                            .then(cat)
                            .style(ChatColor.BOLD)
                            .then(" category")
                    )
                    .command(String.format("/warps %s", cat))
                    .send(player));
        } else {
            if (category.equalsIgnoreCase("all")) {
                player.sendMessage(Ref.HEADER_WARPS
                        .replace("{category}", "All warps")
                );
                player.sendMessage(Ref.NEUTRAL_CLICK_WARP);
                this.printWarps(player, this.warps);
                return;
            }

            player.sendMessage(Ref.HEADER_WARPS
                    .replace("{category}", category)
            );
            player.sendMessage(Ref.NEUTRAL_CLICK_WARP);
            if (category.equalsIgnoreCase("other")) {
                this.printWarps(player, this.warps.stream()
                        .filter(warp -> !warp.hasCategory())
                        .collect(Collectors.toList())
                );
            } else {
                this.printWarps(player, this.warps.stream()
                        .filter(Warp::hasCategory)
                        .filter(warp -> warp.getCategory().equalsIgnoreCase(category))
                        .collect(Collectors.toList())
                );

            }
        }
    }

    private void printWarps(Player player, List<Warp> warps) {
        warps.stream()
                .map(Warp::getName)
                .forEach(warp -> new FancyMessage(warp)
                        .formattedTooltip(new FancyMessage("Click to warp to ")
                                .then(warp)
                                .style(ChatColor.BOLD)
                        )
                        .command(String.format("/warp %s", warp))
                        .send(player));
    }

    private void sort() {
        warps.sort(Comparator.comparing(Warp::getName));
    }
}
