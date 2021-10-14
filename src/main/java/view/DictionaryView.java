package view;

import control.DictionaryCommandLine;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    //Position value


    public DictionaryView(Dictionary dictionary) {
        //build a frame GUI
        JFrame mainFrame;
        Container actionContainer;
        {
            mainFrame = new JFrame("dictionary");
            mainFrame.setBounds(450, 90, 450, 600);
            mainFrame.setLayout(null);
            mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

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
            JButton wordSearchButton = new JButton("search");
            wordSearchButton.setSize(100, 25);
            wordSearchButton.setLocation(225, 2);
            wordSearchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String wordKey = wordSearchField.getText();
                    Dictionary searchDictionary = DictionaryCommandLine.dictionaryAdvanced(
                            DictionaryCommandLine.SEARCH_KEY_WORD,
                            dictionary,
                            new Word(wordKey,""));
                    assert searchDictionary != null;
                    updateWordList(searchDictionary);
                }
            });
            actionContainer.add(wordSearchButton);
        }


        //settings: add word, remove word, fix word.
        {
            JComboBox<String> settings = new JComboBox<>(new String[]{"settings", "add word", "fix word", "remove word"});
            settings.setBounds(330, 2, 95, 25);
            settings.setSelectedItem(settings);
            settings.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String item = e.getItem().toString();
                        String wordTarget;
                        String word_explain;
                        switch (item) {
                            case "add word":
                                wordTarget = JOptionPane.showInputDialog(null, "từ thêm:");
                                word_explain = JOptionPane.showInputDialog(null, "Y nghĩa");
                                DictionaryCommandLine.dictionaryAdvanced(
                                        DictionaryCommandLine.ADD_WORD,
                                        dictionary,
                                        new Word(wordTarget, word_explain));
                                updateWordList(dictionary);
                                break;
                            case "remove word":
                                wordTarget = JOptionPane.showInputDialog(null, "chon từ xóa:");
                                DictionaryCommandLine.dictionaryAdvanced(
                                        DictionaryCommandLine.REMOVE_WORD,
                                        dictionary, new Word(wordTarget, ""));
                                updateWordList(dictionary);
                                break;
                            case "fix word":
                                wordTarget = JOptionPane.showInputDialog(null, "chon từ sửa:");
                                word_explain = JOptionPane.showInputDialog(null, "Y nghĩa mới:");
                                DictionaryCommandLine.dictionaryAdvanced(
                                        "fix word",
                                        dictionary,
                                        new Word(wordTarget, word_explain));
                                updateWordList(dictionary);
                                break;
                            case "settings":
                                DictionaryCommandLine.dictionaryAdvanced("show all word", dictionary);
                                break;
                            default:
                                break;
                        }
                    }

                }
            });
            actionContainer.add(settings);
        }

        //labels
        {
            JLabel wordTargetListLabel = new JLabel("Từ vựng");
            wordTargetListLabel.setBounds(0, 30, 225, 30);
            wordTargetListLabel.setFont(new Font("Arial", Font.BOLD, 25));
            actionContainer.add(wordTargetListLabel);

            JLabel wordExplainLabel = new JLabel("Y nghĩa");
            wordExplainLabel.setBounds(225, 30, 225, 30);
            wordExplainLabel.setFont(new Font("Arial", Font.BOLD, 25));
            actionContainer.add(wordExplainLabel);
        }

        //wordTarget list
        {
            wordTargetList = new JList<>();
            wordTargetList.setBounds(0, 60, 220, 540);
            wordTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTargetList.setLayoutOrientation(JList.VERTICAL);
            wordTargetList.setVisibleRowCount(-1);
            updateWordList(dictionary);
            selectionModel = this.wordTargetList.getSelectionModel();
            selectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        updateWordExplain();
                    }
                }
            });
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(wordTargetList);
            scrollPane.setBounds(0, 60, 220, 540);
            actionContainer.add(scrollPane);
        }

        //word explain area
        {
            wordExplainArea = new JTextArea();
            wordExplainArea.setBounds(230, 60, 220, 540);
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

