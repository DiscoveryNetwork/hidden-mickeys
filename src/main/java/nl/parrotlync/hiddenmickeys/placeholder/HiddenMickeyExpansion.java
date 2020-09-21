package nl.parrotlync.hiddenmickeys.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import nl.parrotlync.hiddenmickeys.HiddenMickeys;
import org.bukkit.OfflinePlayer;

public class HiddenMickeyExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "ParrotLync";
    }

    @Override
    public String getIdentifier() {
        return "hmickeys";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {

        // %hmickeys_found%
        if (identifier.equals("found")) {
            return HiddenMickeys.getInstance().getPlayerManager().getSize(player.getPlayer()).toString();
        }

        // %hmickeys_left%
        if (identifier.equals("left")) {
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
