package uvt.ngo.rest.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NGO {

    @Id
    private String id;

    private String description;
    private String location;
    private String address;
    private String founded;
    private String website;
    private String imageUrl;
    private Integer priority;
    private Boolean approved;

    private List<IssueArea> issueAreaList;

}
