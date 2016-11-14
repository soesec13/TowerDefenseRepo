package map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebi on 004 04/11/2016.
 */
public class Path {
    private List<Tile> path;
    private TileGrid map;

    public Path(TileGrid map) {
        path = new ArrayList<>();
        this.map = map;
        Tile start = map.getStartTile();
        Tile end = map.getEndTile();
        Tile next, current = start, prev = start;
        while (true) {
            next = findNext(prev, current);
            path.add(next);
            if (next.equals(end) || next.equals(current))
                break;
            prev = current;
            current = next;
            next = null;
        }
        path.add(next);
    }

    private Tile findNext(Tile prev, Tile current) {
        Tile[] neighbors = map.getNeighbours(current);
        for (int j = 0; j < neighbors.length; j++) {
            Tile t = neighbors[j];
            if (t == null)
                continue;
            if (t.equals(prev))
                continue;
            if (t.getType().isWalkable())
                return t;
        }
        return current;
    }

    public int indexOf(Tile t) {
        return path.indexOf(t);
    }

    public Tile getNext(Tile current) {
        return path.get(path.indexOf(current) + 1);
    }

    public int length() {
        return path.size();
    }
}
