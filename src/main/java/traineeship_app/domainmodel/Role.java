package traineeship_app.domainmodel;

public enum Role {
	USER_CO("Company"),
	USER_ST("Student"),
	USER_PR("Professor"),
	USER_COM("Committee");
    
    private final String value;

    private Role(String value) {
        this.value = value;
    }

        public String getValue() {
        return value;
    }
    
    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}