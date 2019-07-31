package utils;

// Enum of types of Reservations that can be added by a user.
public enum Reservation {

    FLIGHT,
    HOTEL,
    LANDMARK;

    @Override
    public String toString() {
        String enumString = "";
        switch (this) {
            case FLIGHT:
                enumString = "flight";
            case HOTEL:
                enumString = "hotel";
            case LANDMARK:
                enumString = "landmark";
            default:
                break;
        }
        return enumString;
    }
}
