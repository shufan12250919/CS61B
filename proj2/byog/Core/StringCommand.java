package byog.Core;

public class StringCommand {
    private boolean load;
    private StringBuilder action;
    private long seed;
    private int actionIndex;
    private boolean store = false;

    public StringCommand(String s) {
        checkLoadOrNew(s);
        getSeed(s);
        setAction(s);
    }

    public boolean getLoad() {
        return load;
    }

    public long getSeed() {
        return seed;
    }

    public String getAction() {
        return action.toString();
    }

    public boolean getStore() {
        return store;
    }

    //check for Start new Game or Load game
    private void checkLoadOrNew(String s) {
        char first = s.charAt(0);
        if (first == 'N' || first == 'n') {
            load = false;
        } else {
            load = true;
        }

    }

    //get seed and get where the action command will start
    private void getSeed(String s) {
        if (!load) {
            long tempSeed = 0L;
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == 'S' || c == 's') {
                    break;
                }
                tempSeed = tempSeed * 10 + Long.parseLong(s.substring(i, i + 1));
                actionIndex = i + 1;
            }
            seed = tempSeed;
        }
    }

    private void setAction(String s) {
        action = new StringBuilder();
        for (int i = actionIndex; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ':') {
                store = true;
                return;
            }
            action.append(c);
        }
    }

}
