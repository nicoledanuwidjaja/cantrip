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
                break;
            case HOTEL:
                enumString = "hotel";
                break;
            case LANDMARK:
                enumString = "landmark";
                break;
            default:
                break;
        }
        return enumString;
    }
}
