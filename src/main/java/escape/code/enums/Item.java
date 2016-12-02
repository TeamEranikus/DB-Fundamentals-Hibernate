package escape.code.enums;

/**
 * Item types in the game
 */
public enum Item {

    KEY(true),
    BOOK(true),
    NONE(false);

    private boolean hasItem;

    /**
     * Sets item capacity status
     *
     * @param hasItem whether an item is present
     */
    Item(boolean hasItem) {
        this.hasItem = hasItem;
    }

    /**
     * Gets item capacity status
     *
     * @return true if the player has corresponding item or false if hasn't
     */
    public boolean hasItem() {
        return this.hasItem;
    }
}
