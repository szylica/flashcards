import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardEditor {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        QuizCardEditor editor = new QuizCardEditor();
        editor.setup();
    }

    public void setup(){
        frame = new JFrame("Quiz card editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);
        
        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next card");

        cardList = new ArrayList<QuizCard>();

        JLabel questionLabel = new JLabel("Question: ");
        JLabel answerLabel = new JLabel("Answer: ");

        mainPanel.add(questionLabel);
        mainPanel.add(questionScroll);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScroll);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());
        JMenuBar menu = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem newOption = new JMenuItem("New");
        JMenuItem saveOption = new JMenuItem("Save");
        
        newOption.addActionListener(new NewMenuListener());
        saveOption.addActionListener(new saveMenuListener());
        menuFile.add(newOption);
        menuFile.add(saveOption);
        menu.add(menuFile);
        frame.setJMenuBar(menu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500,600);
        frame.setVisible(true);
    }

    public class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }
    
    public class saveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);

            JFileChooser dataFile = new JFileChooser();
            dataFile.showSaveDialog(frame);
            saveFile(dataFile.getSelectedFile());
        }
    }

    public class NewMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            cardList.clear();
            clearCard();
        }
    }

    private void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Cant save card's file");
            ex.printStackTrace();
        }
    }


}