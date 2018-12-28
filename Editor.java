package Editor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Editor {

	private JFrame frmTextEditor;
	private JFileChooser chooser;
	private JTextArea text;
	private File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.frmTextEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Editor() {
		// initializing the editor
		initialize();
	}

	/**
	 * This code implements save for text file. It is used in the save functionality and in the close for saving vefore close
	 * (if the user wants to be this manner). The user can to go out without save, but is interesting to know before if is 
	 * exactly what he desires.
	 */
	private void salvar() {
		// the file chooser
		chooser = new JFileChooser();
		
		file = null;
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		
		// create a byte buffer with the file content
		byte[] bytes = text.getText().getBytes();
		
		// save
		try {
			Files.write(file.toPath(), bytes);
			JOptionPane.showMessageDialog(null, "File saved successfullyo");
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "I/O error");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// configure the frame text editor window
		frmTextEditor = new JFrame();
		frmTextEditor.setResizable(false);
		frmTextEditor.setForeground(Color.CYAN);
		frmTextEditor.setBackground(UIManager.getColor("InternalFrame.activeTitleForeground"));
		frmTextEditor.setFont(new Font("Courier New", Font.PLAIN, 12));
		frmTextEditor.setTitle("Editor de Textos");
		frmTextEditor.setBounds(100, 100, 900, 370);
		frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTextEditor.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// configure the editor text area
		text = new JTextArea();
		text.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		text.setForeground(UIManager.getColor("TextArea.foreground"));
		text.setBackground(UIManager.getColor("EditorPane.background"));
		text.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 18));
		text.setColumns(80);
		text.setRows(16);
		frmTextEditor.getContentPane().add(text);

		// configure scroll pane in the text area
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setBackground(Color.DARK_GRAY);
		frmTextEditor.getContentPane().add(scrollPane);

		// configure the menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("Menu.background"));
		scrollPane.setColumnHeaderView(menuBar);

		JMenu menuOpen = new JMenu("File");
		menuBar.add(menuOpen);

		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				Path path = Paths.get(file.getAbsolutePath());
				String conteudo = "";
				try {
					conteudo = new String(Files.readAllBytes(path));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro de entrada/saída");
				}
				text.setText(conteudo);
			}
		});

		JMenuItem menuItemNew = new JMenuItem("New");
		menuOpen.add(menuItemNew);
		menuOpen.add(menuItemOpen);

		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		menuOpen.add(menuItemSave);

		JMenuItem menuItemClose = new JMenuItem("Close");
		menuItemClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Você quer salvar o arquivo antes de fechar?", "Fechar", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					salvar();
					text.setText("");
				}
				if (result == JOptionPane.NO_OPTION)
					text.setText("");
			}
		});
		menuOpen.add(menuItemClose);

		JMenuItem menuItemQuit = new JMenuItem("Quit");
		menuItemQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuOpen.add(menuItemQuit);

		JMenu menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);

		JMenu menuFormat = new JMenu("Format");
		menuBar.add(menuFormat);

		JMenuItem mntmFonte = new JMenuItem("Fonte");
		menuFormat.add(mntmFonte);

		JMenu menuView = new JMenu("View");
		menuBar.add(menuView);

		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);

		JMenuItem mntmComoUsar = new JMenuItem("Como usar");
		mntmComoUsar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Esse editor é muito simples. É só digitar o texto usando a setas, backspace e Enter\n"
								+ "como em qualquer outro editor. Use os menus para abrir novo arquivo e salvar o que foi\n"
								+ "digitado. O outro menu é o de Ajuda (muito simples de usar).");
			}
		});
		menuHelp.add(mntmComoUsar);

		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Essa é uma mensagem que mostra informações a respeito do Editorde Textos.\n"
								+ "É um editor muito simples, escrito como exercício de programação de infterfaces\n"
								+ "em Java.");
			}
		});
		menuHelp.add(mntmSobre);
	}
}
