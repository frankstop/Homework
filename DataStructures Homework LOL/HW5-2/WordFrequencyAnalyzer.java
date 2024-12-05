import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a word and its frequency.
 */
class WordFrequency {
    String word;
    int frequency;

    public WordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return word + ": " + frequency;
    }
}

/**
 * Represents a node in the Binary Search Tree (BST).
 */
class BSTNode {
    String word;
    int frequency;
    BSTNode left, right;

    public BSTNode(String word) {
        this.word = word;
        this.frequency = 1;
        this.left = null;
        this.right = null;
    }
}

/**
 * Implements a Binary Search Tree (BST) to store words and their frequencies.
 */
class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Inserts a word into the BST. If the word already exists, increments its frequency.
     *
     * @param word The word to insert.
     */
    public void insert(String word) {
        root = insertRec(root, word);
    }

    private BSTNode insertRec(BSTNode node, String word) {
        if (node == null) {
            return new BSTNode(word);
        }

        int cmp = word.compareTo(node.word);
        if (cmp < 0) {
            node.left = insertRec(node.left, word);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, word);
        } else {
            node.frequency++;
        }
        return node;
    }

    /**
     * Performs an in-order traversal of the BST and returns a list of words with their frequencies.
     *
     * @return A list of WordFrequency objects in sorted order.
     */
    public List<WordFrequency> inOrderTraversal() {
        List<WordFrequency> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(BSTNode node, List<WordFrequency> result) {
        if (node != null) {
            inOrderRec(node.left, result);
            result.add(new WordFrequency(node.word, node.frequency));
            inOrderRec(node.right, result);
        }
    }

    /**
     * Finds the word with the highest frequency in the BST.
     *
     * @return The WordFrequency object with the highest frequency, or null if the BST is empty.
     */
    public WordFrequency findMostFrequent() {
        if (root == null) return null;
        return findMostFrequentRec(root, new WordFrequency("", 0));
    }

    private WordFrequency findMostFrequentRec(BSTNode node, WordFrequency max) {
        if (node != null) {
            if (node.frequency > max.frequency) {
                max = new WordFrequency(node.word, node.frequency);
            }
            max = findMostFrequentRec(node.left, max);
            max = findMostFrequentRec(node.right, max);
        }
        return max.frequency > 0 ? max : null;
    }

    /**
     * Finds the word with the lowest frequency in the BST.
     *
     * @return The WordFrequency object with the lowest frequency, or null if the BST is empty.
     */
    public WordFrequency findLeastFrequent() {
        if (root == null) return null;
        return findLeastFrequentRec(root, new WordFrequency("", Integer.MAX_VALUE));
    }

    private WordFrequency findLeastFrequentRec(BSTNode node, WordFrequency min) {
        if (node != null) {
            if (node.frequency < min.frequency) {
                min = new WordFrequency(node.word, node.frequency);
            }
            min = findLeastFrequentRec(node.left, min);
            min = findLeastFrequentRec(node.right, min);
        }
        return min.frequency < Integer.MAX_VALUE ? min : null;
    }
}

/**
 * Analyzes word frequencies grouped by their starting letters.
 */
public class WordFrequencyAnalyzer {
    private Map<Character, BinarySearchTree> wordGroups;

    /**
     * Initializes the analyzer with an empty hash table.
     */
    public WordFrequencyAnalyzer() {
        wordGroups = new HashMap<>();
        // Initialize the map with keys a-z
        for (char c = 'a'; c <= 'z'; c++) {
            wordGroups.put(c, new BinarySearchTree());
        }
    }

    /**
     * Processes the input file to group words and count their frequencies.
     *
     * @param filename The path to the input text file.
     */
    public void processFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Regular expression to extract words (alphabetic characters only)
            String regex = "\\b[a-zA-Z]+\\b";

            Pattern pattern = Pattern.compile(regex);

            while ((line = br.readLine()) != null) {
                // Normalize to lowercase
                line = line.toLowerCase();
                // Extract words using regex
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group();
                    char firstChar = word.charAt(0);
                    if (firstChar >= 'a' && firstChar <= 'z') {
                        BinarySearchTree bst = wordGroups.get(firstChar);
                        bst.insert(word);
                    }
                    // Ignore words that do not start with a-z
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file '" + filename + "': " + e.getMessage());
            System.exit(1); // Exit the program if file reading fails
        }
    }

    /**
     * Retrieves all words starting with the specified letter in sorted order along with their frequencies.
     *
     * @param letter The starting letter.
     * @return A list of WordFrequency objects.
     */
    public List<WordFrequency> queryWordsByLetter(char letter) {
        letter = Character.toLowerCase(letter);
        if (wordGroups.containsKey(letter)) {
            return wordGroups.get(letter).inOrderTraversal();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Finds the most frequent word starting with the specified letter.
     *
     * @param letter The starting letter.
     * @return The most frequent WordFrequency object, or null if no words are found.
     */
    public WordFrequency queryMostFrequentWord(char letter) {
        letter = Character.toLowerCase(letter);
        if (wordGroups.containsKey(letter)) {
            return wordGroups.get(letter).findMostFrequent();
        } else {
            return null;
        }
    }

    /**
     * Finds the least frequent word starting with the specified letter.
     *
     * @param letter The starting letter.
     * @return The least frequent WordFrequency object, or null if no words are found.
     */
    public WordFrequency queryLeastFrequentWord(char letter) {
        letter = Character.toLowerCase(letter);
        if (wordGroups.containsKey(letter)) {
            return wordGroups.get(letter).findLeastFrequent();
        } else {
            return null;
        }
    }

    /**
     * Main method for demonstration and testing.
     *
     * @param args Command-line arguments. Expects a single argument: the input filename.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java WordFrequencyAnalyzer <filename>");
            System.exit(1);
        }

        String filename = args[0];
        WordFrequencyAnalyzer analyzer = new WordFrequencyAnalyzer();

        // Process the input file
        analyzer.processFile(filename);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Word Frequency Analyzer");
        System.out.println("========================");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Retrieve all words starting with a given letter (sorted order with frequencies)");
            System.out.println("2. Find the most frequent word starting with a given letter");
            System.out.println("3. Find the least frequent word starting with a given letter");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Enter the starting letter: ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Invalid input. Please enter a single alphabetic character.");
                    continue;
                }
                char letter = input.charAt(0);
                List<WordFrequency> words = analyzer.queryWordsByLetter(letter);
                if (words.isEmpty()) {
                    System.out.println("No words found starting with '" + letter + "'.");
                } else {
                    System.out.println("Words starting with '" + letter + "':");
                    for (WordFrequency wf : words) {
                        System.out.println(wf);
                    }
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter the starting letter: ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Invalid input. Please enter a single alphabetic character.");
                    continue;
                }
                char letter = input.charAt(0);
                WordFrequency mostFrequent = analyzer.queryMostFrequentWord(letter);
                if (mostFrequent != null) {
                    System.out.println("Most frequent word starting with '" + letter + "':");
                    System.out.println(mostFrequent);
                } else {
                    System.out.println("No words found starting with '" + letter + "'.");
                }
            } else if (choice.equals("3")) {
                System.out.print("Enter the starting letter: ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                    System.out.println("Invalid input. Please enter a single alphabetic character.");
                    continue;
                }
                char letter = input.charAt(0);
                WordFrequency leastFrequent = analyzer.queryLeastFrequentWord(letter);
                if (leastFrequent != null) {
                    System.out.println("Least frequent word starting with '" + letter + "':");
                    System.out.println(leastFrequent);
                } else {
                    System.out.println("No words found starting with '" + letter + "'.");
                }
            } else if (choice.equals("4")) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please select an option between 1 and 4.");
            }
        }

        scanner.close();
    }
}