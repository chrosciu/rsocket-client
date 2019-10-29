package pl.com.sages.rsocketclient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookQueryDto {
    private String title;
}
