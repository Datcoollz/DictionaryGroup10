package view;

import control.*;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class DictionaryView {
    private JPanel mainPanel;
    private JPanel main;
    private JPanel googleAPI;
    private JSplitPane mainDictionary;
    private JTextField wordSearchField;
    private JButton wordSearchButton;
    private JButton addButton;
    private JEditorPane wordExplainArea;
    private JScrollPane searchScrollPane;
    private JList<Word> wordTargetList;
    private JPanel searchSide;
    private JPanel explainSide;
    private JScrollPane explainScrollPane;
    private JButton voice;
    private JButton search;
    private JButton API;
    private JPanel menu;
    private JButton arrow;
    private JPanel source;
    private JPanel target;
    private JTextPane targetText;
    private JLabel sourceLang;
    private JLabel targetLang;
    private JTextArea sourceText;
    private JScrollPane sourceScrollPane;
    private JScrollPane targetScrollPane;
    private JButton swap;
    private JLabel home;
    DefaultListModel<Word> wordListModel = new DefaultListModel<>();
    ListSelectionModel selectionModel;
    private final CardLayout card = (CardLayout) main.getLayout();

    public DictionaryView(Dictionary dictionary) {
        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(mainPanel);
        frame.setBounds(200, 30, 700, 400);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //menu
        home.setIcon(new ImageIcon("src\\main\\java\\image\\home.png"));
        ImageIcon Default = new ImageIcon("src\\main\\java\\image\\default.png");
        ImageIcon hover = new ImageIcon("src\\main\\java\\image\\hover.png");
        search.setBorderPainted(false);
        search.setFocusPainted(false);
        search.setContentAreaFilled(false);
        search.setOpaque(false);
        search.setIcon(Default);
        search.setRolloverIcon(hover);
        search.addActionListener(e -> card.show(main, "mainDictionary"));

        API.setBorderPainted(false);
        API.setFocusPainted(false);
        API.setContentAreaFilled(false);
        API.setOpaque(false);
        API.setIcon(Default);
        API.setRolloverIcon(hover);
        API.addActionListener(e -> card.show(main, "googleAPI"));

        //search field & button.
        {
            //Search button-> get from wordSearchField
            wordSearchButton.setFocusPainted(false);
            wordSearchButton.setContentAreaFilled(false);
            wordSearchButton.setIcon(new ImageIcon("src\\main\\java\\image\\search.png"));
            wordSearchButton.addActionListener(e -> {
                String wordKey = wordSearchField.getText();
                if(!wordKey.equals("")) {
                    Dictionary searchDictionary = DictionaryCommandLine.dictionaryAdvanced(
                            DictionaryCommandLine.SEARCH_KEY_WORD, dictionary,
                            new Word(wordKey, ""));
                    assert searchDictionary != null;
                    updateWordList(searchDictionary);
                }
                wordExplainArea.setText("this help to clear a single dot");
                wordExplainArea.setText("");
            });
        }

        //wordTarget list
        {
            wordTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTargetList.setVisibleRowCount(-1);
            updateWordList(dictionary);
            selectionModel = this.wordTargetList.getSelectionModel();
            selectionModel.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateWordExplain();
                }
            });
        }

        //popupMenu for fix & remove
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("fix")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = wordTargetList.getSelectedIndex();
                DictionaryCommandLine.dictionaryAdvanced("fix word",
                        dictionary, new Word(wordListModel.getElementAt(index).getWordTarget(),
                                wordExplainArea.getText()));
                updateWordList(dictionary);

            }
        });
        popupMenu.add(new JMenuItem("remove")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = wordTargetList.getSelectedIndex();
                DictionaryCommandLine.dictionaryAdvanced("remove word",
                        dictionary, wordListModel.getElementAt(index));
                updateWordList(dictionary);
            }
        });
        wordTargetList.setComponentPopupMenu(popupMenu);


        //add buttons
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setIcon(new ImageIcon("src\\main\\java\\image\\add.png"));
        addButton.addActionListener(e -> {
            //implement later
        });

        //voice
        voice.setBorderPainted(false);
        voice.setFocusPainted(false);
        voice.setIcon(new ImageIcon("src\\main\\java\\image\\sound.png"));
        voice.addActionListener(e -> {
            int index = wordTargetList.getSelectedIndex();
            try {
                new TextToSpeech(wordListModel.getElementAt(index).getWordTarget());
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "chưa chọn từ");
            }
        });

        //word explain area
        {
            wordExplainArea.setContentType("text/html");
            wordExplainArea.setEditable(true);
            explainScrollPane.setViewportView(wordExplainArea);
        }



        //google
        sourceLang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        targetLang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sourceText.setLineWrap(true);
        sourceText.setWrapStyleWord(true);

        //buttons
        arrow.setIcon(new ImageIcon("src\\main\\java\\image\\arrowRight.png"));
        arrow.setFocusPainted(false);
        arrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    targetText.setText(Translator.translate("en", "vi", sourceText.getText()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        swap.setIcon(new ImageIcon("src\\main\\java\\image\\swap.png"));
        swap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        frame.setVisible(true);
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
            String newExplain = "<html>" + this.wordListModel.getElementAt(index).getWordExplain() + "</html";
            wordExplainArea.setText(newExplain);
        }
    }

}
