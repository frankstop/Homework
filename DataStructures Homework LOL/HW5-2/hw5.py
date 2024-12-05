import string
import re

class BSTNode:
    """
    A node in the Binary Search Tree.
    """
    def __init__(self, word):
        self.word = word
        self.frequency = 1
        self.left = None
        self.right = None

class BST:
    """
    Binary Search Tree implementation for storing words and their frequencies.
    """
    def __init__(self):
        self.root = None

    def insert(self, word):
        """
        Inserts a word into the BST or updates its frequency if it already exists.
        """
        if self.root is None:
            self.root = BSTNode(word)
            return

        current = self.root
        while True:
            if word == current.word:
                current.frequency += 1
                return
            elif word < current.word:
                if current.left is None:
                    current.left = BSTNode(word)
                    return
                current = current.left
            else:
                if current.right is None:
                    current.right = BSTNode(word)
                    return
                current = current.right

    def in_order_traversal(self):
        """
        Returns a list of tuples containing words and their frequencies in sorted order.
        """
        result = []
        def traverse(node):
            if node:
                traverse(node.left)
                result.append((node.word, node.frequency))
                traverse(node.right)
        traverse(self.root)
        return result

    def find_most_frequent(self):
        """
        Finds and returns the most frequent word and its frequency in the BST.
        """
        most_frequent = None
        max_freq = -1
        def traverse(node):
            nonlocal most_frequent, max_freq
            if node:
                traverse(node.left)
                if node.frequency > max_freq:
                    max_freq = node.frequency
                    most_frequent = node.word
                traverse(node.right)
        traverse(self.root)
        return most_frequent, max_freq

    def find_least_frequent(self):
        """
        Finds and returns the least frequent word and its frequency in the BST.
        """
        least_frequent = None
        min_freq = float('inf')
        def traverse(node):
            nonlocal least_frequent, min_freq
            if node:
                traverse(node.left)
                if node.frequency < min_freq:
                    min_freq = node.frequency
                    least_frequent = node.word
                traverse(node.right)
        traverse(self.root)
        return least_frequent, min_freq

class WordGrouping:
    """
    Manages the grouping of words by their starting letter using a hash table of BSTs.
    """
    def __init__(self):
        # Initialize hash table with keys as lowercase letters and values as BST instances
        self.groups = {letter: BST() for letter in string.ascii_lowercase}

    def insert_word(self, word):
        """
        Inserts a word into the appropriate BST based on its starting letter.
        """
        if not word:
            return
        first_char = word[0]
        if first_char in self.groups:
            self.groups[first_char].insert(word)
        # Words with non-alphabetic starting characters are ignored

    def get_sorted_words(self, letter):
        """
        Retrieves all words starting with the given letter in sorted order along with their frequencies.
        """
        letter = letter.lower()
        if letter in self.groups:
            return self.groups[letter].in_order_traversal()
        else:
            return []

    def get_most_frequent_word(self, letter):
        """
        Finds the most frequent word starting with the given letter.
        """
        letter = letter.lower()
        if letter in self.groups:
            return self.groups[letter].find_most_frequent()
        else:
            return (None, 0)

    def get_least_frequent_word(self, letter):
        """
        Finds the least frequent word starting with the given letter.
        """
        letter = letter.lower()
        if letter in self.groups:
            return self.groups[letter].find_least_frequent()
        else:
            return (None, 0)

def process_file(file_path):
    """
    Processes the input text file and builds the word grouping data structure.
    """
    word_grouping = WordGrouping()
    word_pattern = re.compile(r'\b[a-z]+\b')  # Matches words containing only alphabets

    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            for line in file:
                # Normalize text to lowercase
                line = line.lower()
                # Extract words using regular expressions
                words = word_pattern.findall(line)
                for word in words:
                    word_grouping.insert_word(word)
        return word_grouping
    except FileNotFoundError:
        print(f"Error: File '{file_path}' not found.")
        return None
    except IOError:
        print(f"Error: An I/O error occurred while reading '{file_path}'.")
        return None

def query_sorted_words(word_grouping, letter):
    """
    Handles Query 1: Retrieve sorted words starting with a specified letter along with their frequencies.
    """
    sorted_words = word_grouping.get_sorted_words(letter)
    if sorted_words:
        print(f"Words starting with '{letter.lower()}':")
        for word, freq in sorted_words:
            print(f"{word}: {freq}")
    else:
        print(f"No words found starting with '{letter.lower()}'.")

def query_most_frequent(word_grouping, letter):
    """
    Handles Query 2: Find the most frequent word starting with a specified letter.
    """
    word, freq = word_grouping.get_most_frequent_word(letter)
    if word:
        print(f"Most frequent word starting with '{letter.lower()}':")
        print(f"{word}: {freq}")
    else:
        print(f"No words found starting with '{letter.lower()}'.")

def query_least_frequent(word_grouping, letter):
    """
    Handles the Additional Challenge: Find the least frequent word starting with a specified letter.
    """
    word, freq = word_grouping.get_least_frequent_word(letter)
    if word:
        print(f"Least frequent word starting with '{letter.lower()}':")
        print(f"{word}: {freq}")
    else:
        print(f"No words found starting with '{letter.lower()}'.")

def main():
    import sys

    # Check if file path is provided
    if len(sys.argv) < 2:
        print("Usage: python word_analysis.py <file_path>")
        return

    file_path = sys.argv[1]
    word_grouping = process_file(file_path)

    if not word_grouping:
        return

    while True:
        print("\nChoose an option:")
        print("1. Retrieve all words starting with a given letter in sorted order along with their frequencies.")
        print("2. Find the most frequent word in a group.")
        print("3. Find the least frequent word in a group. (Additional Challenge)")
        print("4. Exit.")

        choice = input("Enter your choice (1-4): ")

        if choice == '1':
            letter = input("Enter the starting letter: ").strip().lower()
            if len(letter) != 1 or letter not in string.ascii_lowercase:
                print("Please enter a single valid alphabet letter.")
                continue
            query_sorted_words(word_grouping, letter)
        elif choice == '2':
            letter = input("Enter the starting letter: ").strip().lower()
            if len(letter) != 1 or letter not in string.ascii_lowercase:
                print("Please enter a single valid alphabet letter.")
                continue
            query_most_frequent(word_grouping, letter)
        elif choice == '3':
            letter = input("Enter the starting letter: ").strip().lower()
            if len(letter) != 1 or letter not in string.ascii_lowercase:
                print("Please enter a single valid alphabet letter.")
                continue
            query_least_frequent(word_grouping, letter)
        elif choice == '4':
            print("Exiting the program.")
            break
        else:
            print("Invalid choice. Please select a valid option.")

if __name__ == "__main__":
    main()