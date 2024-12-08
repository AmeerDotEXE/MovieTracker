package MovieCard;

public enum MovieStatus {
	Needs_Review,
	Might_Watch,
	Want_to_Watch,
	
	Want_to_Rewatch,
	
	Watched;

	public String toString() {
		if (this == Needs_Review) return "Review";
		return this.name().replaceAll("_", " ");
	}

	public static MovieStatus fromString(String name) {
		if (name.equals("Review")) return Needs_Review;
		return MovieStatus.valueOf(name.replaceAll(" ", "_"));
	}
}
