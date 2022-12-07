package uvt.ngo.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IssueArea {
	NGO("NGO"),
	HEALTH("HEALTH"), // ALL THESE ENUMS GIVE ERROR, FOR NO CONSTRUCTOR
	FINANCE("FINANCE"),
	ECONOMIC_DEVELOPMENT("ECONOMIC_DEVELOPMENT"),
	ENTREPRENEURSHIP("ENTREPRENEURSHIP"),
	AGRICULTURE("AGRICULTURE"),
	COMMUNITY_DEVELOPMENT("COMMUNITY_DEVELOPMENT"),
	EDUCATION("EDUCATION"),
	JOBS("JOBS");

	@Getter private String value;
}
