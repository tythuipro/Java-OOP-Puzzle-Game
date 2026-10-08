package PuzzleGame.teleport;

import java.util.ArrayList;
import java.util.List;

import PuzzleGame.player.Player;

public class TeleportManager
{
    private List<Teleporter> teleporters;

    public TeleportManager()
    {
        teleporters = new ArrayList<>();
    }

    public void addTeleporter(Teleporter teleporter)
    {
        teleporters.add(teleporter);
    }

    public boolean checkTeleport(Player player)
    {
        for (Teleporter teleporter : teleporters)
        {
            if (teleporter.teleport(player))
            {
                return true;
            }
        }

        return false;
    }

    public List<Teleporter> getTeleporters()
    {
        return teleporters;
    }
}