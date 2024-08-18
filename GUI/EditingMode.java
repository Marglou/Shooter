package Shooter.GUI;

import Shooter.Managers.ManagerCase;
import Shooter.factory.*;
import Shooter.model.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class EditingMode extends GameScene {

	private ManagerCase managerCase;
	private int levelselected; // numéro du niveau selectionné par l'utilisateur (si 0, c'est un nouveau niveau
								// sinon c'est un niveau existant)
	private int[][] level; // niveau sélectionné par l'utilisateur
	private ArrayList<String> ListeEnnemis;
	private boolean levelpersonnalisé = true; // MODE PERSONNALISÉ OU CAMPAGNE
	private int clicked; // bouton cliquée

	private JPanel Plateau;
	private JPanel Colonne;
	private JPanel barre = new JPanel();
	private JPanel barreNord = new JPanel();

	public EditingMode(Game game) {
		super(game);
		levelselected = 0;
		this.managerCase = new ManagerCase();
		this.ListeEnnemis = new ArrayList<>();
		this.level = creeLevelsimple();
		setLayout(new BorderLayout());
		Plateau = new Plateau(level, ListeEnnemis);
		add(Plateau, BorderLayout.CENTER);
		createBarreBas();
		add(barre, BorderLayout.SOUTH);
		this.Colonne = new JPanel();
		creeColonne();
		add(Colonne, BorderLayout.WEST);
		ColonneGauche();
		barrehaut();

	}

	// Crée level basique.
	public int[][] creeLevelsimple() {
		int[][] l = new int[21][37];
		for (int i = 0; i < l.length; i++) {
			for (int j = 0; j < l[i].length; j++) {
				if (i == 0 || j == 0 || i == 20 || j == 36) {
					l[i][j] = 16;
				} else {
					l[i][j] = 0;
				}
			}
		}
		levelselected = 0;
		return l;
	}

	// --------------------------------BARRE HAUT----------------------------------

	public void barrehaut() {
		barreNord.removeAll();
		barreNord.setLayout(new FlowLayout()); 
		barreNord.setBackground(colourL1);
		JPanel boutonMenuPanel = new JPanel();
		boutonMenuPanel.setBackground(colourL1);
		boutonMenuPanel.add(createButton("Menu", "Menu"));

		String[] options = options();
		JComboBox<String> barrelevel = new JComboBox<>(options);
		barrelevel.setRenderer(new CustomComboBox());
		barrelevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				int n = cb.getSelectedIndex();
				levelselected = n;
				if (n != 0) {
					level = PlateauLevelLoader.loadPlayingBoard(cheminFichierLevel(), levelselected - 1);
					ListeEnnemis = EnemyLevelLoader.EnnemiDuNiveau(cheminFichierEnnemi(), levelselected);
				} else {
					level = creeLevelsimple();
					ListeEnnemis = new ArrayList<>();
				}

				updatePlateau();
				createBarreBas();

			}
		});

		barreNord.add(boutonMenuPanel);
		barreNord.add(barrelevel);
		barreNord.revalidate();
		barreNord.repaint();
		add(barreNord, BorderLayout.NORTH);
	}

	public String[] options() {
		int levelmax;
		if (levelpersonnalisé) {
			if (fichierExiste("Shooter/factory/creativeTmp.txt")) {
				levelmax = PlateauLevelLoader.levelmax("Shooter/factory/creativeTmp.txt");
			} else {
				levelmax = 0;
			}
		} else {
			levelmax = PlateauLevelLoader.levelmax("Shooter/factory/PlateauLevels.txt");
		}
		String[] options = new String[levelmax + 1];
		options[0] = "Nouveau";
		for (int i = 0; i < levelmax; i++) {
			options[i + 1] = "Level " + (i + 1);
		}
		return options;
	}

	// -----------------------------CREER PLATEAU---------------------------

	public void updatePlateau() {

		remove(Plateau);
		this.Plateau = new Plateau(level, ListeEnnemis); // Créez un nouveau Plateau
		add(Plateau, BorderLayout.CENTER); // Ajoutez le nouveau Plateau au conteneur principal
		revalidate(); // Revalidez la disposition pour refléter les changements
		repaint();
	}

	public boolean removeEnnemie(int li, int col) {
		int i = isEnnemi(li, col);
		if (i !=-1) {
			ListeEnnemis.remove(i);
			return true;
		}
		return false;
	}

	public int isEnnemi(int x, int y) {
		for (int i = 0; i < ListeEnnemis.size(); i++) {
			String[] ennemi = ListeEnnemis.get(i).split(",");
			int posX = Integer.parseInt(ennemi[1].replaceAll("[^0-9]", "").trim());
			int posY = Integer.parseInt(ennemi[2].replaceAll("[^0-9]", "").trim());
			if (posX - 50 < x && posX + 50 > x && posY - 50 < y && posY + 50 > y) {
				return i;
			}
		}
		return -1;
	}

	// ----------------------------CREER BARRE DU BAS(LA HONTE,VRAIMENT)
	// (SAUVEGARDER/MODIFIER)---------------------------
	public void createBarreBas() {
		this.barre.removeAll();
		barre.setBackground(colourL1);
		barre.setLayout(new GridLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		JButtonStyled sauvegarde = new JButtonStyled("Sauvegarder", shooterFont);
		sauvegarde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ListeEnnemis.size() > 0) {
					sauvegardeAction();
					update();
				}
			}

		});

		barre.add(sauvegarde, gbc);
		gbc.gridx = 1;
		JButtonStyled modifier = new JButtonStyled("Modifier", shooterFont);
		modifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifierAction();
				update();

			}

		});

		if (levelselected != 0) {
			barre.add(modifier, gbc);
		}

		barre.revalidate();
		barre.repaint();
	}

	public void update() {
		level = creeLevelsimple();
		ListeEnnemis = new ArrayList<>();
		barrehaut();
		updatePlateau();
	}

	public void creeColonne() {
		Colonne.setLayout(new BorderLayout());
		JPanel barreHaut = new JPanel();
		barreHaut.setBackground(colourL1);
		JPanel b = new JPanel();
		b.setBackground(colourL1);
		JComboBox<String> TypeCase = new JComboBox<>(new String[] { "Mur", "Sol", "Décor", "Obstacle" });
		TypeCase.setRenderer(new CustomComboBox());
		updatecolonne(b, "Mur");
		TypeCase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) TypeCase.getSelectedItem();
				updatecolonne(b, s);
			}
		});
		JScrollPane scrollPane = new JScrollPane(b);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		Colonne.add(scrollPane, BorderLayout.CENTER);

		barreHaut.add(TypeCase);
		Colonne.add(barreHaut, BorderLayout.NORTH);
	}

	public void updatecolonne(JPanel j, String s) {
		j.removeAll();

		// Création des deux colonnes de boutons
		JPanel colonne1 = new JPanel();
		colonne1.setBackground(colourL1);
		colonne1.setLayout(new BoxLayout(colonne1, BoxLayout.Y_AXIS));
		JPanel colonne2 = new JPanel();
		colonne2.setBackground(colourL1);
		colonne2.setLayout(new BoxLayout(colonne2, BoxLayout.Y_AXIS));

		ArrayList<Case> l = Liste(s);
		for (int i = 0; i < l.size(); i++) {
			ImageIcon CaseImage = new ImageIcon(l.get(i).getSprite());
			JButtonStyled b = new JButtonStyled(l.get(i).getId(), CaseImage);
			b.setPreferredSize(new Dimension(33, 33));
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clicked = b.getId();

				}
			});

			// Ajout des boutons à chaque colonne alternativement
			if (i % 2 == 0) {
				colonne1.add(b);
			} else {
				colonne2.add(b);
			}
			if (i < l.size() - 1 && i % 2 == 0) {
				colonne1.add(Box.createVerticalStrut(10)); // Ajoute un espace vertical de 10 pixels
				colonne2.add(Box.createVerticalStrut(10)); // Ajoute un espace vertical de 10 pixels
			}
		}

		// Conteneur pour les deux colonnes de boutons
		JPanel colonnesContainer = new JPanel();
		colonnesContainer.setLayout(new BoxLayout(colonnesContainer, BoxLayout.X_AXIS));
		colonnesContainer.add(colonne1);
		JPanel colonnem = new JPanel();
		colonnem.setBackground(colourL1);
		colonnesContainer.add(colonnem);
		// colonnesContainer.add(Box.createHorizontalStrut(10));
		colonnesContainer.add(colonne2);

		// Ajout du conteneur de colonnes à votre conteneur principal
		j.add(colonnesContainer);

		// Revalidatez et repeignez le conteneur
		j.revalidate();
		j.repaint();
	}

	public void ColonneGauche() {
		JPanel Colonne = new JPanel();
		Colonne.setLayout(new BorderLayout());
		Colonne.setBackground(colourL1);
		JLabel label = new JLabel("Ennemis");
		// label.setFont(shooterFont);
		label.setForeground(colourL2);
		label.setBackground(colourD1);
		label.setPreferredSize(new Dimension(100, 100));
		Colonne.add(label, BorderLayout.NORTH);
		JPanel colonne1 = new JPanel();
		colonne1.setBackground(colourL1);
		colonne1.setLayout(new BoxLayout(colonne1, BoxLayout.Y_AXIS));

		ArrayList<String> l = ListeEnnemis();
		for (int i = 0; i < l.size(); i++) {
			JButtonStyled b = new JButtonStyled(i + 500, getSprite(l.get(i)));
			b.setPreferredSize(new Dimension(49, 49));
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clicked = b.getId();

				}
			});
			colonne1.add(b);
			colonne1.add(Box.createVerticalStrut(10));
		}
		ImageIcon CaseImage = new ImageIcon("Shooter/res/Supprimer.png");
		Image image = CaseImage.getImage().getScaledInstance(49, 49, java.awt.Image.SCALE_SMOOTH);
		JButtonStyled b = new JButtonStyled(-1, new ImageIcon(image));
		b.setPreferredSize(new Dimension(49, 49));
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clicked = b.getId();
			}
		});
		colonne1.add(b);
		Colonne.add(colonne1, BorderLayout.CENTER);
		add(Colonne, BorderLayout.EAST);
	}

	public ImageIcon getSprite(String s) {
		int id = 1;
		switch (s) {
			case "Enemy 1":
				id = new EnemyBasique(-1, -1).getId();
				break;
			case "Enemy 2":
				id = new EnemyMedium(-1, -1).getId();
				break;
			case "Enemy 3":
				 id= new EnemySniper(-1,-1).getId();
				break;
			case "Enemy 4":
				id = new EnemyIA(-1, -1).getId();
				break;
			case "Enemy 5":
				//id = new EnemyRebond(-1, -1).getId();
				id = new Enemy5(-1, -1).getId();

				break;
		}
		String filename = "../res/we_" + id + "_" + 0 + ".png";
		URL imageUrl = getClass().getResource(filename);

		ImageIcon img = new ImageIcon(imageUrl);
		Image image = img.getImage();
		Image newimg = image.getScaledInstance(49, 49, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	public ArrayList<String> ListeEnnemis() {
		ArrayList<String> l = new ArrayList<>();
		l.add("Enemy 1");// Basique
		l.add("Enemy 2");// Medium
		l.add("Enemy 3");// Guardien
		l.add("Enemy 4"); // IA
		l.add("Enemy 5"); // Sniper
		return l;
	}

	public ArrayList<Case> Liste(String s) {
		ArrayList<Case> l = new ArrayList<>();
		switch (s) {
			case "Sol":
				l = managerCase.sol;
				break;
			case "Mur":
				l = managerCase.mur;
				break;
			case "Obstacle":
				l = managerCase.obstacle;
				break;
			case "Décor":
				l = managerCase.decor;
				break;
		}
		return l;
	}

	// ---------------------------------------------------------------------------------------
	public String cheminFichierLevel() {
		if (levelpersonnalisé) {
			return "Shooter/factory/creativeTmp.txt";
		}
		return "Shooter/factory/PlateauLevels.txt";
	}

	public String cheminFichierLevel(int n) {
		if (levelpersonnalisé || n == 1) {
			return "Shooter/factory/creativeTmp.txt";

		}
		return "Shooter/factory/PlateauLevels.txt";
	}

	public String cheminFichierEnnemi() {
		if (levelpersonnalisé) {
			return "Shooter/factory/enemyTmp.txt";

		}
		return "Shooter/factory/EnemiesForLevels.txt";
	}

	public String cheminFichierEnnemi(int n) {
		if (levelpersonnalisé || n == 1) {
			return "Shooter/factory/enemyTmp.txt";
		}
		return "Shooter/factory/EnemiesForLevels.txt";
	}

	public String niveauExistant(int avant, int apres) {
		String s = "";
		for (int i = avant; i < apres; i++) {
			s += PlateauLevelLoader.getLevelString(cheminFichierLevel(), i);
		}
		return s;
	}

	public String StringEnnemie(int debut, int fin) {
		String s = "";
		for (int i = debut; i < fin; i++) {
			s += EnemyLevelLoader.EnnemiNiveau(cheminFichierEnnemi(), i);
		}
		return s;
	}

	public void modifierAction() {
		int levelmax = PlateauLevelLoader.levelmax(cheminFichierLevel());
		String avantLevel = niveauExistant(1, levelselected);
		String apresLevel = niveauExistant(levelselected + 1, levelmax + 1);
		String avantEnnemi = StringEnnemie(1, levelselected);
		String apresEnnemi = StringEnnemie(levelselected + 1, levelmax + 1);
		try {
			FileWriter writer = new FileWriter(cheminFichierLevel());
			writer.write(avantLevel);
			writer.write("Level " + levelselected + ":\n");
			writer.write(nouveauTableau());
			writer.write(apresLevel);
			writer.close();

			writer = new FileWriter(cheminFichierEnnemi());
			writer.write(avantEnnemi);
			writer.write(levelselected + ": ");
			writer.write(nouveauEnnemi());
			writer.write(apresEnnemi);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean fichierExiste(String cheminFichier) {
		File fichier = new File(cheminFichier);
		return fichier.exists();
	}

	public void sauvegardeAction() {
		String cheminFichierLevel = cheminFichierLevel(1);
		String cheminFichierEnnemi = cheminFichierEnnemi(1);
		try {
			if (!fichierExiste(cheminFichierLevel) && !fichierExiste(cheminFichierEnnemi)) {
				// S'il n'existe pas, crée le fichier
				File fichier = new File(cheminFichierLevel);
				fichier.createNewFile();
				fichier = new File(cheminFichierEnnemi);
				fichier.createNewFile();
				System.out.println("Fichiers créés avec succès : " + cheminFichierLevel + " " + cheminFichierEnnemi);
				//game.nbLevelCreatif++;
			}
			FileWriter writer = new FileWriter(cheminFichierLevel, true);
			// Écrire les données à sauvegarder dans le fichier
			// Par exemple, vous pouvez parcourir votre tableau de données et écrire chaque
			// élément dans le fichier
			writer.write("Level " + (PlateauLevelLoader.levelmax(cheminFichierLevel) + 1) + ":\n");
			writer.write(nouveauTableau());
			writer.close();

			writer = new FileWriter(cheminFichierEnnemi, true);
			writer.write((PlateauLevelLoader.levelmax(cheminFichierLevel)) + ": ");
			writer.write(nouveauEnnemi());
			writer.close();
			System.out.println("Données sauvegardées avec succès dans le fichier texte.");
			game.nbLevelCreatif++;
			
		} catch (IOException e) {
			
			System.out.println("Erreur lors de la sauvegarde des données dans le fichier texte : " + e.getMessage());
		}
	}

	public boolean is(String cases,int click){
		ArrayList<Case> l = Liste(cases);
		for (int i = 0; i < l.size(); i++) {
			if(l.get(i).getId() == click){
				
				return true;
			}
		}
		return false;
	}

	public String nouveauEnnemi() {
		String s = "";
		for (int i = 0; i < ListeEnnemis.size(); i++) {
			s += ListeEnnemis.get(i);

		}
		s += "\n";
		return s;
	}

	public String nouveauTableau() {
		String s = "";
		for (int i = 0; i < level.length; i++) {
			s += "{";
			for (int j = 0; j < level[i].length; j++) {
				s += level[i][j];
				if (j != level[i].length - 1) {
					s += ", ";
				}
			}
			s += "}\n";
		}
		return s;
	}
	

	private class Plateau extends JPanel {
		public Plateau(int[][] level, ArrayList<String> ListeEnnemis) {
			setPreferredSize(new Dimension(33 * level.length, 33 * level[0].length));
			setBackground(colourL1);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getY() / 33;
					int y = e.getX() / 33;
					if (clicked == -1) {
						removeEnnemie(y * 40, x * 40);
					} else if (clicked >= 500) {
						if (isEnnemi(x * 40, y * 40) != -1) {
							removeEnnemie(x * 40, y * 40);
						}
						if(is("Sol",level[x][y])&&!(x==10&& y==1)){
							String ennemi = ListeEnnemis().get(clicked - 500);
							ListeEnnemis.add(ennemi + "," + y * 40 + "," + x * 40 + ";");
						}

					} else if (x >= 0&& x < level.length && y >= 0 && y < level[0].length) {
						if(x==10&& y==1){
							if(is("Sol",clicked)){
								level[x][y] = clicked;
							}
						}else if (x == 0 || y == 0 || x == 20 || y == 36) {
							if(is("Mur",clicked)){
								level[x][y] = clicked;
							}
						}else{
							level[x][y] = clicked;
						}
						

					}

					revalidate();
					repaint();
				}


			});
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					int x = e.getY() / 33;
					int y = e.getX() / 33;

					if (clicked == -1) {
						removeEnnemie(y * 40, x * 40);
						revalidate();
						repaint();
					} else if (clicked >= 500) {
						if (isEnnemi(x * 40, y * 40) != -1) {
							removeEnnemie(x * 40, y * 40);
							revalidate();
							repaint();
						}
						if(is("Sol",level[x][y])&&(!(x==10&& y==1))){
							String ennemi = ListeEnnemis().get(clicked - 500);
							ListeEnnemis.add(ennemi + "," + y * 40 + "," + x * 40 + ";");
						}
						revalidate();
						repaint();

					} else if (x >= 0 && x < level.length && y >= 0 && y < level[0].length) {
						if(x==10&& y==1){
							if(is("Sol",clicked)){
								level[x][y] = clicked;
							}
						}else if (x == 0 || y == 0 || x == 20 || y == 36) {
							if(is("Mur",clicked)){
								level[x][y] = clicked;
							}
						}
						else {
							level[x][y] = clicked;
						}
						revalidate();
						repaint();
					}
				}
			});


		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < level.length; i++) {
				for (int j = 0; j < level[0].length; j++) {
					int x = j * 33;
					int y = i * 33;
					MyBufferedImage sprite = new MyBufferedImage(i, j);
					g.drawImage(sprite.imageIcon.getImage(), x, y, 33, 33, null);

				}
			}

			for (int i = 0; i < ListeEnnemis.size(); i++) {
				String[] ennemi = ListeEnnemis.get(i).split(",");
				int x = Integer.parseInt(ennemi[1].replaceAll("[^0-9]", "").trim());
				int y = Integer.parseInt(ennemi[2].replaceAll("[^0-9]", "").trim());
				x = x / 40 * 33;
				y = y / 40 * 33;
				MyBufferedImage sprite = new MyBufferedImage(0, 0);

				sprite.imageIcon = getSprite(ennemi[0]);
				g.drawImage(sprite.imageIcon.getImage(), x, y, 49, 49, null);
			}
		}

	}

	// ---------------------------------------------image et
	// bouton------------------------------------------------------
	private class MyBufferedImage extends BufferedImage {
		private ImageIcon imageIcon;

		public MyBufferedImage(int x, int y) {
			super(33, 33, BufferedImage.TYPE_INT_ARGB);
			imageIconOriginal(x, y);
		}

		public void imageIconOriginal(int x, int y) {
			BufferedImage sprite = managerCase.getSprite(level[x][y]);
			Image image = sprite.getScaledInstance(33, 33, java.awt.Image.SCALE_SMOOTH);
			this.imageIcon = new ImageIcon(image);
		}

		
	}
}
