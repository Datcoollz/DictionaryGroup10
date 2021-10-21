package view;

import control.DictionaryCommandLine;
import control.TextToSpeech;
import control.Translator;
import model.Dictionary;
import model.Word;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryView {
    private JPanel mainPanel;
    private JPanel main;
    private JPanel googleAPI;
    private JSplitPane mainDictionary;
    private JTextField wordSearchField;
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
    private JLabel searchSign;
    DefaultListModel<Word> wordListModel = new DefaultListModel<>();
    ListSelectionModel selectionModel;
    private final CardLayout card = (CardLayout) main.getLayout();

    public DictionaryView(Dictionary dictionary) {
        JFrame frame = new JFrame("Dictionary");
        frame.setContentPane(mainPanel);
        frame.setBounds(200, 30, 700, 400);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame,
                        "Bạn có chắc chắn muốn thoát?", "Thoát?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                    DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.EXPORT_TO_FILE, dictionary);
                }
            }
        });

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

        //search field , 3 override như nhau.
        searchSign.setIcon(new ImageIcon("src\\main\\java\\image\\search.png"));
        wordSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearch(dictionary);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearch(dictionary);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearch(dictionary);
            }
        });

        //wordTarget list
        {
            wordTargetList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            wordTargetList.setVisibleRowCount(-1);
            updateWordList(dictionary.searchAll());
            selectionModel = this.wordTargetList.getSelectionModel();
            selectionModel.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateWordExplain();
                }
            });
        }

        //popupMenu for fix & remove
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("fix")).addActionListener(e -> {
            int index = wordTargetList.getSelectedIndex();
            DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.FIX_WORD,
                    dictionary, new Word(wordListModel.getElementAt(index).getWordTarget(),
                            wordExplainArea.getText()));
            updateSearch(dictionary);
        });
        popupMenu.add(new JMenuItem("remove")).addActionListener(e -> {
            int index = wordTargetList.getSelectedIndex();
            DictionaryCommandLine.dictionaryAdvanced(DictionaryCommandLine.REMOVE_WORD,
                    dictionary, wordListModel.getElementAt(index));
            updateSearch(dictionary);
        });
        wordTargetList.setComponentPopupMenu(popupMenu);


        //add buttons (phần dài nhất)
        {
            addButton.setFocusPainted(false);
            addButton.setBorderPainted(false);
            addButton.setIcon(new ImageIcon("src\\main\\java\\image\\add.png"));
            addButton.addActionListener(e -> {
                String wordInput = JOptionPane.showInputDialog("chọn từ để thêm:");
                System.out.println(dictionary.search(wordInput));
                if (dictionary.search(wordInput) != null) {
                    JOptionPane.showMessageDialog(null, "Từ đã có sẵn");
                } else if (wordInput.equals("")) {
                    JOptionPane.showMessageDialog(null, "Từ chưa được nhập");
                } else {
                    String wordTarget = wordInput.trim().replaceAll(" +", " ");

                    final String[] last = {"<i>" + wordTarget};
                    JDialog dialog = new JDialog(frame, "add box");
                    dialog.setLayout(null);
                    dialog.setBounds(300, 130, 440, 440);

                    JLabel jLabel1 = new JLabel("phát âm:");
                    jLabel1.setBounds(11, 11, 50, 18);
                    JTextField jTextField1 = new JTextField();
                    jTextField1.setBounds(61, 10, 150, 20);
                    dialog.add(jLabel1);
                    dialog.add(jTextField1);

                    JLabel jLabel2 = new JLabel("dạng từ:");
                    jLabel2.setBounds(215, 11, 50, 18);
                    JTextField jTextField2 = new JTextField();
                    jTextField2.setBounds(265, 10, 150, 20);
                    dialog.add(jLabel2);
                    dialog.add(jTextField2);

                    JLabel jLabel3 = new JLabel("Ý nghĩa:");
                    jLabel3.setBounds(11, 31, 50, 18);
                    JTextArea jTextArea3 = new JTextArea();
                    jTextArea3.setBounds(11, 49, 200, 300);
                    jTextArea3.setBorder(new LineBorder(Color.black, 1));
                    dialog.add(jLabel3);
                    dialog.add(jTextArea3);

                    JLabel jLabel4 = new JLabel("Ví dụ:");
                    jLabel4.setBounds(215, 31, 50, 18);
                    JTextArea jTextArea4 = new JTextArea();
                    jTextArea4.setBounds(215, 49, 200, 300);
                    jTextArea4.setBorder(new LineBorder(Color.black, 1));
                    dialog.add(jLabel4);
                    dialog.add(jTextArea4);

                    JButton add = new JButton("add");
                    add.setBounds(185, 350, 70, 30);
                    add.addActionListener(e1 -> {
                        if (!jTextField1.getText().equals("")) {
                            last[0] += " /" + jTextField1.getText() + "/";
                        }
                        last[0] += "</i><br/>";
                        last[0] += jTextField1.getText() + "</i><br/>";
                        if (!jTextField2.getText().equals("")) {
                            last[0] += "<ul><li><b><i>" + jTextField2.getText() + "</i></b>";
                        }
                        if (!jTextArea3.getText().equals("")) {
                            for (String line : jTextArea3.getText().split("\\n")) {
                                last[0] += "<ul><li><font color='#cc0000'><b>" + line + "</b></font></ul></li>";
                            }
                        }
                        if (!jTextArea4.getText().equals("")) {
                            for (String line : jTextArea4.getText().split("\\n")) {
                                last[0] += "<ul><li>" + line + "</ul></li>";
                            }
                        }
                        if (!jTextField2.getText().equals("")) { // lặp lại vì đuôi của tag html
                            last[0] += "</ul></li>";
                        }
                        DictionaryCommandLine.dictionaryAdvanced("add word",
                                dictionary, new Word(wordTarget, last[0]));
                        dialog.dispose();

                        //Reset search field
                        if (wordSearchField.getText().isEmpty()) {
                            //If field is already empty, just update the list
                            updateWordList(dictionary.searchAll());
                        } else {
                            //Else reset the text and update automatically
                            wordSearchField.setText("");
                        }
                    });
                    dialog.add(add);
                    dialog.setVisible(true);
                }
            });
        }

        //voice
        voice.setBorderPainted(false);
        voice.setFocusPainted(false);
        voice.setIcon(new ImageIcon("src\\main\\java\\image\\sound.png"));
        voice.addActionListener(e -> {
            int index = wordTargetList.getSelectedIndex();
            try {
                new TextToSpeech(wordListModel.getElementAt(index).getWordTarget());
            } catch (Exception exception) {
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
        final String[] source = {"vi"};
        final String[] target = {"en"};
        sourceLang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        targetLang.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sourceText.setLineWrap(true);
        sourceText.setWrapStyleWord(true);

        //buttons
        arrow.setIcon(new ImageIcon("src\\main\\java\\image\\arrowRight.png"));
        arrow.setFocusPainted(false);
        arrow.addActionListener(e -> {
            try {
                targetText.setText(Translator.translate(source[0], target[0], sourceText.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        swap.setIcon(new ImageIcon("src\\main\\java\\image\\swap.png"));
        swap.addActionListener(e -> {
            String tmp;
            tmp = source[0];
            source[0] = target[0];
            target[0] = tmp;
            if (sourceLang.getText().equals("Vietnamese")) {
                sourceLang.setText("English");
                targetLang.setText("Vietnamese");
            } else {
                sourceLang.setText("Vietnamese");
                targetLang.setText("English");
            }
        });

        updateWordList(dictionary.searchAll());
        frame.setVisible(true);
    }

    /**
     * Update word list and explain area after a change in search bar
     *
     * @param dictionary input dictionary
     */
    private void updateSearch(Dictionary dictionary) {
        String wordKey = wordSearchField.getText();
        ArrayList<Word> list = DictionaryCommandLine.dictionaryAdvanced(
                DictionaryCommandLine.SEARCH_KEY_WORD, dictionary,
                new Word(wordKey));
        assert list != null;
        updateWordList(list);

        wordExplainArea.setText("this help to clear a single dot");
        wordExplainArea.setText("");
    }

    /**
     * Update wordTargetList when you search for new words.
     *
     * @param list list to display
     */
    public void updateWordList(ArrayList<Word> list) {
        this.wordListModel.removeAllElements();
        if (list != null) {
            if (!list.isEmpty()) {
                this.wordListModel.addAll(list);
            }
        }
        this.wordTargetList.setModel(this.wordListModel);
    }

    /**
     * Update wordExplainArea when you pick a word.
     */
    private void updateWordExplain() {
        int index = this.wordTargetList.getSelectedIndex();
        if (index >= 0) {
            String newExplain = "<html>" + this.wordListModel.getElementAt(index).getWordExplain() + "</html>";
            wordExplainArea.setText(newExplain);
        }
    }

}