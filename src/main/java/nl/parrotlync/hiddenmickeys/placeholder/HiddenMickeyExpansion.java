package nl.parrotlync.hiddenmickeys.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class HiddenMickeyExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "ParrotLync";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hmickeys";
    }

    @Override
    public @NotNull String getVersion() {
        return HiddenMickeys.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {

        // %hmickeys_found%
        if (identifier.equals("found")) {
            assert player.getPlayer() != null;
            return HiddenMickeys.getInstance().getPlayerManager().getSize(player.getPlayer()).toString();
        }

        // %hmickeys_left%
        if (identifier.equals("left")) {
            assert player.getPlayer() != null;
            Integer found = HiddenMickeys.getInstance().getPlayerManager().getSize(player.getPlayer());
            Integer total = HiddenMickeys.getInstance().getMickeyManager().getSize();
            return String.valueOf(total - found);
        }

        // %hmickeys_total%
        if (identifier.equals("total")) {
            return HiddenMickeys.getInstance().getMickeyManager().getSize().toString();
        }

        return null;
    }
}
