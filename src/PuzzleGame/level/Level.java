package PuzzleGame.level;

import PuzzleGame.map.Map;
import PuzzleGame.objects.Door;
import PuzzleGame.objects.Key;
import PuzzleGame.player.Player;
import PuzzleGame.teleport.TeleportManager;

public class Level
{
    private Map map;
    private Player player;
    private Key key;
    private Door door;
    private TeleportManager teleportManager;

    public Level(
        Map map,
        Player player,
        Key key,
        Door door,
        TeleportManager teleportManager
    )
    {
        this.map = map;
        this.player = player;
        this.key = key;
        this.door = door;
        this.teleportManager = teleportManager;
    }

    public Map getMap()
    {
        return map;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Key getKey()
    {
        return key;
    }

    public Door getDoor()
    {
        return door;
    }

    public TeleportManager getTeleportManager()
    {
        return teleportManager;
    }
}