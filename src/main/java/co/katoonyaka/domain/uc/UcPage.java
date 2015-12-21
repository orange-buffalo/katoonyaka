package co.katoonyaka.domain.uc;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class UcPage<T> {
    private String next;
    private List<T> results = new ArrayList<>();
}
