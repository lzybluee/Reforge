package forge.player;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import forge.game.event.EventValueChangeType;
import forge.game.player.PlayerView;
import forge.game.zone.ZoneType;

public class PlayerZoneUpdate implements Serializable {
    private static final long serialVersionUID = -7666875897455073969L;

    private final PlayerView player;
    private final Set<ZoneType> zones;
    private final EventValueChangeType type;

    public PlayerZoneUpdate(final PlayerView player, final ZoneType zone, final EventValueChangeType type) {
        if (player == null || zone == null) {
            throw new NullPointerException();
        }
        this.player = player;
        this.zones = EnumSet.of(zone);
        this.type = type;
    }

    public PlayerView getPlayer() {
        return player;
    }
    public Set<ZoneType> getZones() {
        return zones;
    }
    public EventValueChangeType getType() {
        return type;
    }

    void addZone(final ZoneType zone) {
        if (zone == null) {
            throw new NullPointerException();
        }
        zones.add(zone);
    }
    void add(final PlayerZoneUpdate other) {
        if (other == null) {
            throw new NullPointerException();
        }
        zones.addAll(other.getZones());
    }
}
