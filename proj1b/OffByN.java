public class OffByN implements CharacterComparator {
    private int dif;

    public OffByN(int n) {
        dif = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == dif || x - y == -dif) {
            return true;
        }
        return false;
    }
}
