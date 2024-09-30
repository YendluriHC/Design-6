//TC: O(l log k)
//SC: O(n*l)
class AutocompleteSystem {
    class TrieNode {
        Map<String, Integer> sentenceCountMap;  // Stores sentences and their counts
        Map<Character, TrieNode> children;
        
        TrieNode() {
            sentenceCountMap = new HashMap<>();
            children = new HashMap<>();
        }
    }

    private TrieNode root;
    private StringBuilder currentInput;  // Tracks the current input string
    private TrieNode currentNode;  // Tracks the current trie node for optimization

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        currentInput = new StringBuilder();
        currentNode = root;

        // Initialize the trie with sentences and their times
        for (int i = 0; i < sentences.length; i++) {
            insertSentence(sentences[i], times[i]);
        }
    }
    
    // Inserts a sentence into the Trie and updates its hotness
    private void insertSentence(String sentence, int count) {
        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.sentenceCountMap.put(sentence, node.sentenceCountMap.getOrDefault(sentence, 0) + count);
        }
    }

    // Handles input character and returns top 3 sentences matching the current input prefix
    public List<String> input(char c) {
        if (c == '#') {
            // Store the sentence when '#' is encountered
            String sentence = currentInput.toString();
            insertSentence(sentence, 1);  // Add this sentence to the Trie with a count of 1
            currentInput = new StringBuilder();  // Reset for new input
            currentNode = root;
            return new ArrayList<>();  // Return empty list when input ends
        }

        currentInput.append(c);
        if (currentNode != null && currentNode.children.containsKey(c)) {
            currentNode = currentNode.children.get(c);  // Move down the Trie
            return getTop3Sentences(currentNode.sentenceCountMap);
        } else {
            currentNode = null;  // No matches for the prefix
            return new ArrayList<>();
        }
    }

    // Helper method to get the top 3 sentences from a map of sentences and their counts
    private List<String> getTop3Sentences(Map<String, Integer> sentenceCountMap) {
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            if (sentenceCountMap.get(a).equals(sentenceCountMap.get(b))) {
                return a.compareTo(b);  // Lexicographical order
            }
            return sentenceCountMap.get(b) - sentenceCountMap.get(a);  // Sort by hotness
        });

        // Add all sentences to the priority queue
        for (String sentence : sentenceCountMap.keySet()) {
            pq.offer(sentence);
        }

        // Extract top 3 sentences
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            result.add(pq.poll());
        }

        return result;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
