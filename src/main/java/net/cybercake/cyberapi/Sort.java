package net.cybercake.cyberapi;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Sort {

    public static <K, V extends Comparable<V> > Map<K, V> sort(final Map<K, V> map) {
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k1).compareTo(
                    map.get(k2));
            if (comp == 0)
                return 1;
            else
                return comp;
        };

        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);

        sorted.putAll(map);

        return sorted;
    }

}
