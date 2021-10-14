package view;

import control.DictionaryCommandLine;
import control.DictionaryManagement;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Display for the app.
 */
public class DictionaryView {
    //Word search field
    private final JTextField wordSearchField;
    //Word explain area
    private final JTextArea wordExplainArea;
    //Word list
    private final JList<Word> wordTargetList;
    DefaultListModel<Word> wordListModel = new DefaultListModel<>();
    ListSelectionModel selectionModel;


    public DictionaryView(Dictionary dictionary) {
        //build a frame GUI
        JFrame mainFrame;
        Container actionContainer;
        {
            mainFrame = new JFrame("dictionary");
            mainFrame.setBounds(400, 70, 600, 640);
            mainFrame.setLayout(null);
            //mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(mainFrame,
                            "Bạn có chắc chắn muốn thoát?", "Thoát?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        DictionaryManagement.dictionaryExportToFile(dictionary);
                        System.exit(0);
                    }
                }
            });

            actionContainer = mainFrame.getContentPane();
            actionContainer.setLayout(null);

        }

        //search field & button.
        {
            //Text field
            wordSearchField = new JTextField();
            wordSearchField.setSize(225, 25);
            wordSearchField.setLocation(0, 2);
            actionContainer.add(wordSearchField);
            //Search button
            JButton wordSearchButton = new JButton("Tìm");
            wordSearchButton.setSize(75, 25);
            wordSearchButton.setLocation(225, 2);
            wordSearchButton.addActionListener(e -> {
                String wordKey = wordSearchField.getText();
                Dictionary searchDictionary = DictionaryCommandLine.dictionaryAdvanced(
                        DictionaryCommandLine.SEARCH_KEY_WORD,
                        dictionary,
                        new Word(wordKey, ""));
                assert searchDictionary != null;
                updateWordList(searchDictionary);
            });
            actionContainer.add(wordSearchButton);
        }

        //Buttons
        {
            JButton addWord = new JButton("Thêm");
            addWord.setBounds(340, 2, 80, 25);
            addWord.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String wordTarget = JOptionPane.showInputDialog(null, "Từ để thêm:");
                    String word_explain = JOptionPane.showInputDialog(null, "Giải thích:");
                    DictionaryCommandLine.dictionaryAdvanced(
                            DictionaryCommandLine.ADD_WORD,
                            dictionary,
                            new Word(wordTarget, word_explain));
                    updateWordList(dictionary);
                }
            });
            JButton removeWord = new JButton("Xóa");
            removeWord.setBounds(420, 2, 80, 25);
            removeWord.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String wordTarget = JOptionPane.showInputDialog(null, "Từ cần xóa:");
                    DictionaryCommandLine.dictionaryAdvanced(
                            DictionaryCommandLine.REMOVE_WORD,
                            dictionary, new Word(wordTarget, ""));
                    updateWordList(dictionary);
                }
            });
            JButton fixWord = new JButton("Sửa");
            fixWord.setBounds(500, 2, 80, 25);
            fixWord.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String wordTarget = JOptionPane.showInputDialog(null, "Từ cần sửa:");
                    String word_explain = JOptionPane.showInputDialog(null, "Giải thích mới:");
                    DictionaryCommandLine.dictionaryAdvanced(
                            "fix word",
                            dictionary,
                            new Word(wordTarget, word_explain));
                    updateWordList(dictionary);
                }
            });
            actionContainer.add(addWord);
            actionContainer.add(removeWord);
            actionContainer.add(fixWord);
        }

        //labels
        {
            JLabel wordTargetListLabel = new JLabel("Từ vựng");
            wordTargetListLabel.setBounds(0, 30, 300, 30);
            wordTargetListLabel.setFont(new Font("Arial", Font.BOLD, 25));
            actionContainer.add(wordTargetListLabel);

            JLabel wordExplainLabel = new JLabel("Ý nghĩa");
            wordExplainLabel.setBounds(300, 30, 300, 30);
            wordExplainLabel.setFont(new Font("Arial", Font.BOLD, 25));
            actionContainer.add(wordExplainLabel);
        }

        //wordTarget list
        {
            wordTargetList = new JList<>();
            wordTargetList.setBounds(0, 60, 299, 540);
            wordTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTargetList.setLayoutOrientation(JList.VERTICAL);
            wordTargetList.setVisibleRowCount(-1);
            updateWordList(dictionary);
            selectionModel = this.wordTargetList.getSelectionModel();
            selectionModel.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateWordExplain();
                }
            });
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(wordTargetList);
            scrollPane.setBounds(0, 60, 299, 540);
            actionContainer.add(scrollPane);
        }

        //word explain area
        {
            wordExplainArea = new JTextArea();
            wordExplainArea.setBounds(301, 60, 299, 540);
            actionContainer.add(wordExplainArea);
        }

        mainFrame.setVisible(true);

    }

    /**
     * Update wordTargetList when you search for new words.
     *
     * @param dictionary dictionary to display
     */
    public void updateWordList(Dictionary dictionary) {
        this.wordListModel.removeAllElements();
        this.wordListModel.addAll(dictionary.getWordList());
        this.wordTargetList.setModel(this.wordListModel);
    }

    /**
     * Update wordExplainArea when you pick a word.
     */
    private void updateWordExplain() {
        int index = this.wordTargetList.getSelectedIndex();
        if (index >= 0) {
            //String newWord = this.wordListModel.getElementAt(index).getWordTarget();
            String newExplain = this.wordListModel.getElementAt(index).getWordExplain();
            wordExplainArea.setText(newExplain);
        }
    }
}
