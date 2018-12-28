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

public class Editor {

	private JFrame frmEditorDeTextos;
	private JFileChooser chooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.frmEditorDeTextos.setVisible(true);
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEditorDeTextos = new JFrame();
		frmEditorDeTextos.setForeground(Color.CYAN);
		frmEditorDeTextos.setBackground(Color.RED);
		frmEditorDeTextos.setFont(new Font("Courier New", Font.PLAIN, 12));
		frmEditorDeTextos.setTitle("Editor de Textos");
		frmEditorDeTextos.setBounds(100, 100, 500, 300);
		frmEditorDeTextos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditorDeTextos.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	
		JTextArea text = new JTextArea();
		text.setFont(new Font("Dialog", Font.PLAIN, 18));
		text.setColumns(28);
		text.setRows(10);
		frmEditorDeTextos.getContentPane().add(text);

		JScrollPane scrollPane = new JScrollPane(text);
		frmEditorDeTextos.getContentPane().add(scrollPane);
		
		JMenuBar menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		JMenu mnAbrir = new JMenu("Arquivo");
		menuBar.add(mnAbrir);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
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
		
		JMenuItem mntmNovo = new JMenuItem("Novo");
		mnAbrir.add(mntmNovo);
		mnAbrir.add(mntmAbrir);
		
		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mntmSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser = new JFileChooser();
				File file = null;
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = chooser.getSelectedFile();
				}
				byte[] bytes = text.getText().getBytes();
				try {
					Files.write(file.toPath(), bytes);
					JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Erro de entrada/saída");
				}
			}
		});
		mnAbrir.add(mntmSalvar);
		
		JMenuItem mntmFechar = new JMenuItem("Fechar");
		mntmFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setText("");
			}
		});
		mnAbrir.add(mntmFechar);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnAbrir.add(mntmSair);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenu mnFormatar = new JMenu("Formatar");
		menuBar.add(mnFormatar);
		
		JMenuItem mntmFonte = new JMenuItem("Fonte");
		mnFormatar.add(mntmFonte);
		
		JMenu mnVer = new JMenu("Ver");
		menuBar.add(mnVer);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmComoUsar = new JMenuItem("Como usar");
		mntmComoUsar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"Esse editor é muito simples. É só digitar o texto usando a setas, backspace e Enter\n" +
						"como em qualquer outro editor. Use os menus para abrir novo arquivo e salvar o que foi\n" +
						"digitado. O outro menu é o de Ajuda (muito simples de usar).");
			}
		});
		mnAjuda.add(mntmComoUsar);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"Essa é uma mensagem que mostra informações a respeito do Editorde Textos.\n" +
						"É um editor muito simples, escrito como exercício de programação de infterfaces\n" +
						"em Java.");
			}
		});
		mnAjuda.add(mntmSobre);
	}

}
