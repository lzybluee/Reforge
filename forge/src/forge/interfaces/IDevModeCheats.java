package forge.interfaces;

public interface IDevModeCheats {

    void setCanPlayUnlimitedLands(boolean canPlayUnlimitedLands0);

    void setViewAllCards(boolean canViewAll);

    void generateMana(boolean empty);

    void dumpGameState();

    void setupGameState();

    void tutorForCard(boolean sideboard);

    void addCountersToPermanent();

    void tapPermanents(boolean all);

    void untapPermanents(boolean all);

    void setPlayerLife(boolean maxlife);

    void winGame(boolean lose);

    void addCardToHand(boolean lastAdded);

    void addCardToBattlefield(boolean lastAdded);

    void riggedPlanarRoll();

    void planeswalkTo();

    /**
     * Implementation of {@link IDevModeCheats} that disallows cheating by
     * performing no action whatsoever when any of its methods is called.
     */
    public static final IDevModeCheats NO_CHEAT = new IDevModeCheats() {
        @Override
        public void winGame(boolean lose) {
        }
        @Override
        public void untapPermanents(boolean all) {
        }
        @Override
        public void tutorForCard(boolean sideboard) {
        }
        @Override
        public void tapPermanents(boolean all) {
        }
        @Override
        public void setupGameState() {
        }
        @Override
        public void setViewAllCards(final boolean canViewAll) {
        }
        @Override
        public void setPlayerLife(boolean maxlife) {
        }
        @Override
        public void setCanPlayUnlimitedLands(final boolean canPlayUnlimitedLands0) {
        }
        @Override
        public void riggedPlanarRoll() {
        }
        @Override
        public void planeswalkTo() {
        }
        @Override
        public void generateMana(boolean empty) {
        }
        @Override
        public void dumpGameState() {
        }
        @Override
        public void addCountersToPermanent() {
        }
        @Override
        public void addCardToHand(boolean lastAdded) {
        }
        @Override
        public void addCardToBattlefield(boolean lastAdded) {
        }
    };

}