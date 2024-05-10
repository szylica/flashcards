import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizGame {

    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private QuizCard currentCard;
    private int currentCardIndex;
    private JFrame frame;
    private JButton nextCardButton;
    private boolean isAnswerShown;

    public static void main(String[] args) {
        QuizGame game = new QuizGame();
        game.setup();
    }

    public void setup(){
        frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        question = new JTextArea(10, 20);
        question.setFont(bigFont);
        question.setLineWrap(true);
        question.setEditable(false);

        JScrollPane scrollQuestion = new JScrollPane(question);
        scrollQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollQuestion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        nextCardButton = new JButton("Show question");
        mainPanel.add(scrollQuestion);
        mainPanel.add(nextCardButton);
        nextCardButton.addActionListener(new NextCardListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem optionOpen = new JMenuItem("Open card collection");
        optionOpen.addActionListener(new OpenMenuListener());
        menuFile.add(optionOpen);
        menuBar.add(menuFile);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(640, 500);
        frame.setVisible(true);
    }

    public class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(isAnswerShown){
                question.setText(currentCard.getAnswer());
                nextCardButton.setText("Next card");
                isAnswerShown = false;
            }
            else {
                if (currentCardIndex < cardList.size()){
                    showNextCard();
                }
                else {
                    question.setText("That was last card");
                    nextCardButton.setEnabled(false);
                }
            }

        }
    }

    public class OpenMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            JFileChooser dialogFile = new JFileChooser();
            dialogFile.showOpenDialog(frame);
            loadFile(dialogFile.getSelectedFile());

        }
    }

    private void loadFile(File file){
        cardList =  new ArrayList<QuizCard>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String row = null;
            while((row = reader.readLine()) != null){
                createCard(row);
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println("Cant read a file with cards");
            ex.printStackTrace();
        }
        showNextCard();
    }
    private void createCard(String dataRow){
        String[] results = dataRow.split("/");
        QuizCard card = new QuizCard(results[0], results[1]);
        cardList.add(card);
        System.out.println("Card has been created");
    }

    private void showNextCard(){
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex++;
        question.setText(currentCard.getQuestion());
        nextCardButton.setText("Show answer");
        isAnswerShown = true;
    }

}
