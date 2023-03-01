// Tuan Le
// CS145
// Assignment 1 -- Word Search

import java.util.*;

public class WordSearch {
    private char[][] board;
    private boolean[][] solution;
    private String[] word;
    // start of main
    public static void main(String[] args) {
        startGame();
    } // end of main

    // start of startGame
    public static void startGame() {
        boolean generated = false;
        Scanner console = new Scanner(System.in);
        String choice;
        WordSearch search = new WordSearch();
        do {
            intro();
            choice = console.next();
            if(choice.equals("g")) {
                System.out.println("Enter Each Word Line by Line. When finished, enter \"q\"");
                String tok = console.next();
                ArrayList<String> wordsAR = new ArrayList<String>();
                do {
                    wordsAR.add(tok);
                    tok = console.next();
                }while(!tok.equals("q"));
                String[] word = new String[wordsAR.size()];
                wordsAR.toArray(word);
                search.generate(word);
                generated = true;
            } else if(choice.equals("p")) {
                if(generated){
                    print(search);
                }
            } else if(choice.equals("s")) {
                if(generated){
                    showSolution(search);
                }
            }
        } while(!choice.equals("q"));

    } // end of startGame

    // start of intro
    public static void intro(){
        System.out.println("Welcome to the Word Search Generator!");
        System.out.println("Please select an option:");
        System.out.println("G -- Generate a Word Search");
        System.out.println("P -- Print your Word Search");
        System.out.println("S -- Show the Solution");
        System.out.println("Q -- Quit the Program");
    } // end of intro

    // start of print
    public static void print(WordSearch ws){
        System.out.println(ws);
    }
    // start of showSolution
    public static void showSolution(WordSearch ws){
        System.out.println(ws.toSolution());
    } // end of showSolution
    
    // start of generate
    public void generate(String[] w){
        for(int i = 0 ; i < w.length ; i++){
            w[i] = w[i].toLowerCase();
        }
        this.word = w;
        char[][] wordChars = setup();
        for(int i = 0 ; i < wordChars.length ; i++){
            place(wordChars, i);
        }
    fill();
    } // end of generate

    // start of toString
    public String toString(){
        String result = "";
        for(int i = 0 ; i < board.length ; i++){
            for(int x = 0 ; x < board[i].length ; x++){
                result += " " + board[i][x] + " ";
            }
            result += "\r\n";
        }
        return result;
    } // end of toString
    
    // start of place
    private void place(char[][] wordChars, int iter){
        Random rand = new Random();
        int direction = rand.nextInt(3);
        int[] pos = {0,0};
        if(direction == 0){ //horizontal
            horizontal(wordChars, iter, rand, pos);
        }else if(direction == 1){ //vertical
            vertical(wordChars, iter, rand, pos);
        }else if(direction == 2){ //diagonal
            diagonal(wordChars, iter, rand, pos);
        }
    } // end of place

    // start of toSolution
    public String toSolution(){
        String result = "";
        for(int i = 0 ; i < board.length ; i++){
            for(int x = 0 ; x < board[i].length ; x++){
                if(solution[i][x]){
                    result += " " + board[i][x] + " ";
                }else{
                    result += " X ";
                }
            }
            result += "\r\n";
        }
        return result;
    } // end of toSolution

    // start of fill
    private void fill(){
        for(int i = 0 ; i < board.length ; i++){
            for(int x = 0 ; x < board[i].length ; x++){
                Random rand = new Random();
                if(board[i][x] == '\u0000'){
                    board[i][x] = (char)(rand.nextInt(26)+97);
                }
            }
        }
    } // end of fill
    
    // start of setup
    private char[][] setup(){
        char[][] wordChars = new char[word.length][];
        int longest = 8;
        for(int i = 0 ; i < word.length ; i++){
            wordChars[i] = word[i].toCharArray();
            if(wordChars[i].length > longest){
                longest = wordChars[i].length;
            }
        }
        if(word.length > longest){
            longest = word.length;
        }
        this.board = new char[longest + 4][longest + 4];
        this.solution = new boolean[longest + 4][longest + 4];
        return wordChars;
    } // end of setup

    // start of vertical
    private void vertical(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
        int attempts = 0;
        while(!placed && attempts < 100){
            pos[0] = rand.nextInt((board.length-1) - wordChars[iter].length);
            pos[1] = rand.nextInt((board.length-1) - wordChars[iter].length);
            placed = true;
            for(int u = 0 ; u < wordChars[iter].length ; u++){
                if(board[pos[0]][pos[1] + u] != '\u0000' && board[pos[0]][pos[1] + u] != wordChars[iter][u]){
                    placed = false;
                    break;
                }
            }
        attempts++;
        }
        if(placed){
            for(int x = 0 ; x < wordChars[iter].length ; x++){
                board[pos[0]][pos[1]] = wordChars[iter][x];
                solution[pos[0]][pos[1]] = true;
                pos[1]++;
            }
        }
    } // end of vertical

    // start of horizontal
    private void horizontal(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
            int attempts = 0;
            while(!placed && attempts < 100){
                pos[0] = rand.nextInt((board.length-1) - wordChars[iter].length);
                pos[1] = rand.nextInt((board.length-1) - wordChars[iter].length);
                placed = true;
                for(int u = 0 ; u < wordChars[iter].length ; u++){
                    if(board[pos[0] + u][pos[1]] != '\u0000' && board[pos[0] + u][pos[1]] != wordChars[iter][u]){
                        placed = false;
                        break;
                    }
                }
                attempts++;
            }
            if(placed){
                for(int x = 0 ; x < wordChars[iter].length ; x++){
                    board[pos[0]][pos[1]] = wordChars[iter][x];
                    solution[pos[0]][pos[1]] = true;
                    pos[0]++;
                }
            }
    } // end of horizontal

    // start of diagonal
    private void diagonal(char[][] wordChars, int iter, Random rand, int[] pos){
        boolean placed = false;
        int attempts = 0;
        while(!placed && attempts < 100){
            pos[0] = rand.nextInt((board.length-1) - wordChars[iter].length);
            pos[1] = rand.nextInt((board.length-1) - wordChars[iter].length);
            placed = true;
            for(int u = 0 ; u < wordChars[iter].length ; u++){
                if(board[pos[0] + u][pos[1] + u] != '\u0000' && board[pos[0] + u][pos[1] + u] != wordChars[iter][u]){
                    placed = false;
                    break;
                }
            }
            attempts++;
        }
        if(placed){
            for(int x = 0 ; x < wordChars[iter].length ; x++){
                board[pos[0]][pos[1]] = wordChars[iter][x];
                solution[pos[0]][pos[1]] = true;
                pos[1]++;
                pos[0]++;
            }
        }
    } // end of diagonal
} // end of class