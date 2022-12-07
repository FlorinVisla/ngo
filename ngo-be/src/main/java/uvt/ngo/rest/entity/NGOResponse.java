package uvt.ngo.rest.entity;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NGOResponse {

    private String message;
    private List<NGO> ngos;

}
