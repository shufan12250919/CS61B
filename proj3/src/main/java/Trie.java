import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    class TrieNode {
        ArrayList<String> words;
        boolean isWord = false;
        HashMap<Character, TrieNode> next;

        TrieNode() {
            words = new ArrayList<>();
            next = new HashMap<>();
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null || word.equals("")) {
            return;
        }
        String lowerword = word.toLowerCase();
        TrieNode ite = root;
        for (int i = 0; i < word.length(); ++i) {
            char c = lowerword.charAt(i);
            if (!ite.next.containsKey(c)) {
                ite.next.put(c, new TrieNode());
            }
            ite = ite.next.get(c);
        }
        ite.isWord = true;
        for (String w : ite.words) {
            if (w.equals(word)) {
                return;
            }
        }
        ite.words.add(word);
    }

    public boolean search(String word) {
        TrieNode ite = root;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (!ite.next.containsKey(c)) {
                return false;
            }
            ite = ite.next.get(c);
        }
        return ite.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode ite = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char c = prefix.charAt(i);
            if (!ite.next.containsKey(c)) {
                return false;
            }
            ite = ite.next.get(c);
        }
        return true;
    }

    private TrieNode getNode(String prefix) {
        TrieNode ite = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char c = prefix.charAt(i);
            if (!ite.next.containsKey(c)) {
                return null;
            }
            ite = ite.next.get(c);
        }
        return ite;
    }

    private void getWords(List<String> list, TrieNode node) {
        if (node == null) {
            return;
        }
        if (node.isWord) {
            for (String w : node.words) {
                list.add(w);
            }
        }
        HashMap<Character, TrieNode> possible = node.next;
        for (Character c : possible.keySet()) {
            getWords(list, possible.get(c));
        }
    }


    public List<String> wordsStartWith(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode possible = getNode(prefix);
        getWords(results, possible);
        return results;

    }

    public static void main(String[] args) {
        /* Test creation of a Trie. */
        Trie trie = new Trie();
        trie.insert("ana");
        trie.insert("anana");
        trie.insert("ananas");
        trie.insert("banana");
        trie.insert("bananas");
        /* Test get words that start with a prefix. */
        /* [ana, anana, ananas] */
        System.out.println(trie.wordsStartWith("a").toString());
        System.out.println(trie.wordsStartWith("an").toString());
        System.out.println(trie.wordsStartWith("ana").toString());
        /* [anana, ananas] */
        System.out.println(trie.wordsStartWith("anan").toString());
        /* [banana, bananas] */
        System.out.println(trie.wordsStartWith("b").toString());
        /* [bananas] */
        System.out.println(trie.wordsStartWith("bananas").toString());
    }

}
