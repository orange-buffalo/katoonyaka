package co.katoonyaka.domain.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoUsageItem {
    private String type;
    private String name;
    private int count;
}
