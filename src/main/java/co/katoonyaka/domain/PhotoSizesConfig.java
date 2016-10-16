package co.katoonyaka.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class PhotoSizesConfig {

    @Getter
    private final String nameSeparator;

    @Getter
    private final SortedSet<PhotoSize> sizes;

    public PhotoSizesConfig(String nameSeparator, Collection<PhotoSize> sizes) {
        this.nameSeparator = nameSeparator;

        SortedSet<PhotoSize> sortedSizes = new TreeSet<>(
                (o1, o2) -> Integer.compare(o1.getWidthInPx(), o2.getWidthInPx()));
        sortedSizes.addAll(sizes);

        this.sizes = Collections.unmodifiableSortedSet(sortedSizes);
    }

    public String findSizeName(int width) {
        PhotoSize result = sizes.first();
        for (PhotoSize size : sizes) {
            result = size;
            if (width < size.getWidthInPx()) {
                break;
            }
        }
        return result.getName();
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class PhotoSize {
        private final String name;
        private final int widthInPx;
    }

}