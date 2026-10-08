package PuzzleGame;

import PuzzleGame.map.Map;
import PuzzleGame.map.Tile;
import PuzzleGame.objects.Box;
import PuzzleGame.objects.Switch;
import PuzzleGame.player.Direction;
import PuzzleGame.player.Player;

public class Main
{
    public static void main(String[] args)
    {
        Map map = new Map(5, 5);

        for (int y = 0; y < 5; y++)
        {
            for (int x = 0; x < 5; x++)
            {
                map.setTile(x, y, new Tile(true));
            }
        }

        Player player = new Player(1, 1);
        Box box = new Box(2, 1);
        Switch switchObject = new Switch(3, 1);

        System.out.println(switchObject.isActive());

        boolean pushed = box.push(player, Direction.RIGHT, map);

        switchObject.check(box);

        System.out.println(pushed);
        System.out.println(box.getX() + " " + box.getY());
        System.out.println(switchObject.isActive());
    }
}