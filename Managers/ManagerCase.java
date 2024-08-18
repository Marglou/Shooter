package Shooter.Managers;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Shooter.model.Case;
import Shooter.model.Enregistrement;


public class ManagerCase {

    public Case SOL_CASE,SOL_SALLE_BAIN, HERBE, MUR_HAUT ,MUR_BAS , MUR_GAUCHE,MUR_DROIT, MUR_COIN_DROIT,MUR_COIN_GAUCHE ,MUR_COIN_HAUT_DROIT,MUR_COIN_HAUT_GAUCHE, MUR_MILIEU , MUR_C_BAS , MUR_C_HAUT , MUR_C_GAUCHE , MUR_C_DROIT, CHAISE,CHAISE_BAS,CHAISE_GAUCHE,CHAISE_DROITE
    , EAU_BAS ,EAU_HAUT, EAU_GAUCHE , EAU_DROIT , EAU_COIN_DROITE_HAUT, EAU_COIN_GAUCHE_BAS ,EAU_COIN_GAUCHE_HAUT ,EAU_COIN_DROITE_BAS , EAU_MILIEU, TABLE_MILIEU,TABLE_BAS,TABLE_HAUT,COUSSIN,COUSSIN_MILIEU,
    PLAN_CUISINE,PLANCHE_DECOUPE,PLAQUE_CUISSON,GARDE_MANGER_VIDE,GARDE_MANGER_LEGUMES,GARDE_MANGER_CONSERVE,TROTOIRE,TROTOIRE_PASSAGE,PASSAGE,PORTE,PORTE2,BUISSON,TABLE,TABLE_BASSE_1,TABLE_BASSE,TABLE_BASSE_MILIEU,

    OBSTACLE_MANGER,OBSTACLE_JOUET,OBSTACLE_ENTRE_1,OBSTACLE_ENTRE_2,OBSTACLE_ENTRE_MILIEU,OBSTACLE_CHAMBRE,OBSTACLE_CUISINE,OBSTACLE_BAIN,OBSTACLE_BAIN_2,FLEUR_1,FLEUR_2,CHAUSSURE,LAMPE_1,LAMPE_2,LAMPE_3,BAC,BUREAU,BUREAU_DROITE,BUREAU_GAUCHE,TOILETTE,FRIGO,EVIER_CUISINE,POUBELLE,PLANCHE_DECOUPE_2,BEBE_4,BEBE_5,BEBE_6,BEBE_7,BEBE_8,BEBE_9,BEBE_10,BEBE_11,BEBE_12,BEBE_13,BEBE_14,BEBE_15
    ,BAIN_BAS,BAIN_DROITE,BAIN_GAUCHE,BAIN_HAUT,BAIN_MILIEU,BAIN_MILIEU_P,DOUCHE,DOUCHE_MILIEU,MEUBLE_BAIN,DRAP_J,DRAP_G,DRAP_V,DRAP_V_BAS, DRAP_J_BAS,DRAP_G_BAS,DRAP_G_DROITE,DRAP_V_DROITE,DRAP_J_DROITE,DRAP_G_HAUT,DRAP_J_HAUT,DRAP_V_HAUT,DRAP_G_GAUCHE,DRAP_J_GAUCHE,DRAP_V_GAUCHE,TRAIN,BAC_A_JOUET,PIANO_1,PIANO_2,PIANO_3,PIANO_4,PIANO_5,PIANO_6,PIANO_7,PIANO_8,
    BURREAU_MARRON_1,BURREAU_MARRON_2,BURREAU_MARRON_3,BURREAU_MARRON_4,BURREAU_MARRON_5,BURREAU_MARRON_6,BURREAU_GRIS_1,BURREAU_GRIS_2,BURREAU_GRIS_3,BURREAU_GRIS_4,BURREAU_GRIS_5,BURREAU_GRIS_6,TABLE_DE_CHEVET_MARRON,TABLE_DE_CHEVET_GRIS,CHAISE_MARRON,CHAISE_GRISE,PORTE_NORMAL,PORTE_BAIN,PORTE_BAIN_2,MEUBLE_BAIN_D,MEUBLE_BAIN_G,EVIER_BAIN,EAU_BAS_J,EAU_DROIT_J,EAU_GAUCHE_J,EAU_HAUT_J,EAU_COIN_DROITE_BAS_J,EAU_COIN_DROITE_HAUT_J,EAU_COIN_GAUCHE_BAS_J,EAU_COIN_GAUCHE_HAUT_J,
    PAVE,PAVE_D,HAIE_BORD,HAIE_MILIEU,ARBRE,CANAPE_D,CANAPE_M,CANAPE_G,TELE_D,TELE_M,TELE_G,POSE_SAVON,EVIER_1,VERRE_BRISE,SAVON,CACTUS,BANANE,POUBELLE_N_VIDE,POUBELLE_N_PLEINE,MACHINIE_CAFE,BOL_FRUIT,BOL,GRILLE_PAIN_ETEINT,GRILLE_PAIN_ALLUME,GRILLE_PAIN_MARCHE,GATEAU_PETIT,GATEAU_GRAND,PREPARATION,PREPARATION_FINI,BANC_DROIT,BANC_MILIEU,BANC_GAUCHE,BALANCOIRE,BALANCOIRE_2, HAIE_R,BANC_DROIT_R,BANC_MILIEU_R,BANC_GAUCHER,
    CANAPE_D_R,CANAPE_M_R,CANAPE_G_R,TELE_D_R,TELE_M_R,TELE_G_R;
    

    public static final int SOL = 0;
    public static final int MUR = 1;
    public static final int CASSANT = 2;
    public static final int MUR_CASSANT = 3;
    public static final int OBSTACLE = 4;
    public static final int BLOQUE = 5;//faire ensorte temporairement bloquer ne peut plus bougé
    public static final int ACCELERATION =6;
    public static final int TOURNIE =7;
    public static final int RALENTIE =8;
    public static final int PERTE=9;
    protected static final int tailleCase = 40;
    

    private BufferedImage atlas;
    public static ArrayList<Case> cases = new ArrayList<>();

    public ArrayList<Case> mur = new ArrayList<>();
    public ArrayList<Case> obstacle = new ArrayList<>();
    public ArrayList<Case> sol = new ArrayList<>();
    public ArrayList<Case> decor = new ArrayList<>();

    public ManagerCase() {
        loadAtlas();
        createCases();
    }

    private void createCases() {
        int id=0;
        sol.add(SOL_CASE= new Case(getSprite(0, 0),id++,SOL));//0    
        sol.add(SOL_SALLE_BAIN= new Case(getSprite(1, 0),id++,SOL));//0
        sol.add(HERBE=new Case(getSprite(2, 0),id++,SOL));//1

        sol.add(TROTOIRE= new Case(getSprite(3, 0),id++,SOL));//2
        sol.add(TROTOIRE_PASSAGE= new Case(getSprite(4, 0),id++,SOL));//3
        sol.add(PASSAGE= new Case(getSprite(5, 0),id++,SOL));//4
        sol.add(PAVE = new Case(getSprite(8,11),id++,SOL));
        sol.add(PAVE_D= new Case(getRotImg(getSprite(8,11 ),90),id++, SOL));

        mur.add(MUR_HAUT= new Case(getSprite(8, 4),id++,MUR));//7
        mur.add(MUR_BAS= new Case(getRotImg(getSprite(8, 4), 180),id++,MUR));//8
        mur.add(MUR_GAUCHE= new Case(getRotImg(getSprite(8, 4), 270),id++,MUR));//9
        mur.add(MUR_DROIT= new Case(getRotImg(getSprite(8, 4), 90),id++,MUR));//10

        mur.add(MUR_COIN_GAUCHE= new Case(getSprite(0, 5),id++,MUR));//11
        mur.add(MUR_COIN_DROIT= new Case(getRotImg(getSprite(0, 5), 90),id++,MUR));//12
        mur.add(MUR_COIN_HAUT_DROIT= new Case(getRotImg(getSprite(0, 5), 270),id++,MUR));//13
        mur.add(MUR_COIN_HAUT_GAUCHE= new Case(getRotImg(getSprite(0, 5), 180),id++,MUR));//14
        mur.add(MUR_MILIEU= new Case(getSprite(1, 5),id++,1));//15
        
        mur.add(MUR_C_BAS= new Case(getSprite(3, 2),id++,CASSANT));//16
        mur.add(MUR_C_HAUT= new Case(getRotImg(getSprite(3, 2), 180),id++,CASSANT));//17
        mur.add(MUR_C_GAUCHE=new Case(getRotImg(getSprite(3, 2), 90),id++,CASSANT));//18
        mur.add(MUR_C_DROIT= new Case(getRotImg(getSprite(3, 2), 270),id++,CASSANT));//19

        obstacle.add(EAU_BAS= new Case(getSprite(6, 0),id++,TOURNIE));//20
        obstacle.add(EAU_HAUT= new Case(getRotImg(getSprite(6, 0), 180),id++,TOURNIE));//21
        obstacle.add(EAU_GAUCHE= new Case(getRotImg(getSprite(6, 0), 90),id++,TOURNIE));//22
        obstacle.add(EAU_DROIT= new Case(getRotImg(getSprite(6, 0), 270),id++,TOURNIE));//23

        obstacle.add(EAU_COIN_DROITE_HAUT= new Case(getSprite(7, 0),id++,TOURNIE));//24
        obstacle.add(EAU_COIN_GAUCHE_BAS= new Case(getRotImg(getSprite(7, 0), 180),id++,TOURNIE));//25
        obstacle.add(EAU_COIN_GAUCHE_HAUT= new Case(getRotImg(getSprite(7, 0), 270),id++,TOURNIE));//26
        obstacle.add(EAU_COIN_DROITE_BAS= new Case(getRotImg(getSprite(7, 0), 90),id++,TOURNIE));//27

        obstacle.add(EAU_MILIEU= new Case(getSprite(8, 0),id++,TOURNIE));//28

        obstacle.add(EAU_BAS_J= new Case(getSprite(0, 1),id++,TOURNIE));//20
        obstacle.add(EAU_HAUT_J= new Case(getRotImg(getSprite(0, 1), 180),id++,TOURNIE));//21
        obstacle.add(EAU_GAUCHE_J= new Case(getRotImg(getSprite(0, 1), 90),id++,TOURNIE));//22
        obstacle.add(EAU_DROIT_J= new Case(getRotImg(getSprite(0, 1), 270),id++,TOURNIE));//23

        obstacle.add(EAU_COIN_DROITE_HAUT_J= new Case(getSprite(1, 1),id++,TOURNIE));//24
        obstacle.add(EAU_COIN_GAUCHE_BAS_J= new Case(getRotImg(getSprite(1, 1), 180),id++,TOURNIE));//25
        obstacle.add(EAU_COIN_GAUCHE_HAUT_J= new Case(getRotImg(getSprite(1, 1), 270),id++,TOURNIE));//26
        obstacle.add(EAU_COIN_DROITE_BAS_J= new Case(getRotImg(getSprite(1, 1), 90),id++,TOURNIE));//27


        obstacle.add(OBSTACLE_MANGER= new Case(getSprite(2, 1),id++,TOURNIE));//20
        obstacle.add(OBSTACLE_JOUET= new Case(getSprite(3, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_ENTRE_1= new Case(getSprite(4, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_ENTRE_2= new Case(getRotImg(getSprite(4, 1),180),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_ENTRE_MILIEU= new Case(getSprite(5, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_CHAMBRE= new Case(getSprite(6, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_CUISINE= new Case(getSprite(7, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_BAIN= new Case(getSprite(8, 1),id++,RALENTIE));//20
        obstacle.add(OBSTACLE_BAIN_2= new Case(getSprite(0, 2),id++,RALENTIE));//20

        obstacle.add(FLEUR_1= new Case(getSprite(1, 2),id++,BLOQUE));//5
        obstacle.add(FLEUR_2= new Case(getSprite(2, 2),id++,OBSTACLE));//5
       
        obstacle.add(PORTE= new Case(getSprite(4, 2),id++,CASSANT));//5
        obstacle.add(PORTE2= new Case(getSprite(5, 2),id++,CASSANT));//5

        obstacle.add(PORTE_NORMAL= new Case(getSprite(0, 3),id++,CASSANT));//5
        obstacle.add(PORTE_BAIN= new Case(getSprite(1, 3),id++,CASSANT));//5
        obstacle.add(PORTE_BAIN_2= new Case(getRotImg(getSprite(1, 3), 180),id++,CASSANT));//5
        obstacle.add(TRAIN= new Case(getSprite(7, 8),id++,OBSTACLE));//56
        
        obstacle.add(PIANO_1= new Case(getSprite(8, 8),id++,CASSANT));//56
        obstacle.add(PIANO_2= new Case(getSprite(0, 9),id++,CASSANT));//56
        obstacle.add(PIANO_3= new Case(getSprite(1, 9),id++,CASSANT));//56
        obstacle.add(PIANO_4= new Case(getSprite(2, 9),id++,CASSANT));//56
        obstacle.add(PIANO_5= new Case(getSprite(3, 9),id++,CASSANT));//56
        obstacle.add(PIANO_6= new Case(getSprite(4, 9),id++,CASSANT));//56
        obstacle.add(PIANO_7= new Case(getSprite(5, 9),id++,CASSANT));//56
        obstacle.add(PIANO_8= new Case(getSprite(6, 9),id++,CASSANT));//56

        obstacle.add(VERRE_BRISE= new Case(getSprite(5, 13),id++,PERTE));
        obstacle.add(SAVON= new Case(getSprite(2, 15),id++,TOURNIE));
        obstacle.add(CACTUS= new Case(getSprite(6, 13),id++,PERTE));
        obstacle.add(BANANE= new Case(getSprite(7, 13),id++,PERTE));

        decor.add(TABLE= new Case(getSprite(6, 2),id++,BLOQUE));//5
        decor.add(TABLE_BAS= new Case(getSprite(7, 2),id++,BLOQUE));//5
        decor.add(TABLE_HAUT= new Case(getRotImg(getSprite(7, 2), 180),id++,BLOQUE));//31
        decor.add(CHAISE= new Case(getSprite(8, 2),id++,BLOQUE));//32
        decor.add(CHAISE_GAUCHE= new Case(getRotImg(getSprite(8, 2), 90),id++,BLOQUE));//33
        decor.add(CHAISE_BAS= new Case(getRotImg(getSprite(8, 2), 180),id++,BLOQUE));//33
        decor.add(CHAISE_DROITE= new Case(getRotImg(getSprite(8, 2), 270),id++,BLOQUE));//33


        decor.add(TABLE_BASSE= new Case(getSprite(2, 3),id++,BLOQUE));//5
        decor.add(TABLE_BASSE_1= new Case(getRotImg(getSprite(2, 3), 180),id++,BLOQUE));//5
        decor.add(TABLE_BASSE_MILIEU= new Case(getSprite(3, 3),id++,BLOQUE));//5

        decor.add(CHAUSSURE= new Case(getSprite(4, 3),id++,BLOQUE));//5

        decor.add(LAMPE_1= new Case(getSprite(5, 3),id++,BLOQUE));//5
        decor.add(LAMPE_2= new Case(getSprite(6, 3),id++,BLOQUE));//5
        decor.add(LAMPE_3= new Case(getSprite(7, 3),id++,BLOQUE));//5
        decor.add(GARDE_MANGER_LEGUMES= new Case(getSprite(8, 3),id++,BLOQUE));//5
        decor.add(GARDE_MANGER_VIDE= new Case(getSprite(0, 4),id++,BLOQUE));//5
        decor.add(GARDE_MANGER_CONSERVE= new Case(getSprite(1, 4),id++,BLOQUE));//5
        
        decor.add(BAC= new Case(getSprite(2, 4),id++,BLOQUE));//5
        decor.add(BUREAU_GAUCHE= new Case(getSprite(3, 4),id++,BLOQUE));//5
        decor.add(BUREAU= new Case(getSprite(4, 4),id++,BLOQUE));//5
        decor.add(BUREAU_DROITE= new Case(getSprite(5, 4),id++,BLOQUE));//5

        decor.add(TOILETTE= new Case(getSprite(6, 4),id++,BLOQUE));//5
        
        decor.add(BUISSON= new Case(getSprite(7, 4),id++,BLOQUE));//29
        decor.add(FRIGO= new Case(getSprite(2, 5),id++,BLOQUE));//29
        
        decor.add(PLAN_CUISINE= new Case(getSprite(3, 5),id++,BLOQUE));//34
        decor.add(EVIER_CUISINE= new Case(getSprite(4, 5),id++,BLOQUE));//35
        decor.add(PLAQUE_CUISSON= new Case(getSprite(5, 5),id++,BLOQUE));//35
        decor.add(PLANCHE_DECOUPE= new Case(getSprite(6, 5),id++,BLOQUE));//36
        decor.add(PLANCHE_DECOUPE_2= new Case(getSprite(7, 5),id++,BLOQUE));//36
        decor.add(POUBELLE= new Case(getSprite(8, 5),id++,BLOQUE));//36
    

        decor.add(COUSSIN= new Case(getSprite(0, 6),id++,BLOQUE));//57
        decor.add(COUSSIN_MILIEU=new Case(getSprite(1, 6),id++,BLOQUE));//58
        decor.add(DRAP_J_HAUT= new Case(getSprite(2, 6),id++,BLOQUE));//52
        decor.add(DRAP_J_BAS= new Case(getRotImg(getSprite(2, 6), 180),id++,BLOQUE));//53
        decor.add(DRAP_J_GAUCHE= new Case(getRotImg(getSprite(2, 6), 270),id++,BLOQUE));//54
        decor.add(DRAP_J_DROITE= new Case(getRotImg(getSprite(2, 6), 90),id++,BLOQUE));//55

        decor.add(DRAP_J= new Case(getSprite(3, 6),id++,BLOQUE));//56

        decor.add(DRAP_G_HAUT= new Case(getSprite(4, 6),id++,BLOQUE));//52
        decor.add(DRAP_G_BAS= new Case(getRotImg(getSprite(4, 6), 180),id++,BLOQUE));//53
        decor.add(DRAP_G_GAUCHE= new Case(getRotImg(getSprite(4, 6), 270),id++,BLOQUE));//54
        decor.add(DRAP_G_DROITE= new Case(getRotImg(getSprite(4, 6), 90),id++,BLOQUE));//55

        decor.add(DRAP_G= new Case(getSprite(5, 6),id++,BLOQUE));//56

        decor.add(DRAP_V_HAUT= new Case(getSprite(6, 6),id++,BLOQUE));//52
        decor.add(DRAP_V_BAS= new Case(getRotImg(getSprite(6, 6), 180),id++,BLOQUE));//53
        decor.add(DRAP_V_GAUCHE= new Case(getRotImg(getSprite(6, 6), 270),id++,BLOQUE));//54
        decor.add(DRAP_V_DROITE= new Case(getRotImg(getSprite(6, 6), 90),id++,BLOQUE));//55

        decor.add(DRAP_V= new Case(getSprite(7, 6),id++,BLOQUE));//56

        
        decor.add(BEBE_4= new Case(getSprite(8, 6),id++,BLOQUE));//56
        decor.add(BEBE_5= new Case(getVerticalFlipImg(getSprite(8, 6)),id++,BLOQUE));//56
        
        decor.add(BEBE_6= new Case(getSprite(0, 7),id++,BLOQUE));//56
        
        decor.add(BEBE_7= new Case(getSprite(1, 7),id++,BLOQUE));//56
        decor.add(BEBE_8= new Case(getRotImg(getSprite(1, 7), 180),id++,BLOQUE));//56
        
        decor.add(BEBE_9= new Case(getSprite(2, 7),id++,BLOQUE));//56
        decor.add(BEBE_10= new Case(getSprite(3, 7),id++,BLOQUE));//56
        decor.add(BEBE_11= new Case(getSprite(4, 7),id++,BLOQUE));//56
        decor.add(BEBE_12= new Case(getSprite(5, 7),id++,BLOQUE));//56
        decor.add(BEBE_13= new Case(getSprite(6,7),id++,BLOQUE));//56
        
        decor.add(BEBE_14= new Case(getSprite(7, 7),id++,BLOQUE));//56
        decor.add(BEBE_15= new Case(getSprite(8, 7),id++,BLOQUE));//56

        
        decor.add(BAIN_HAUT= new Case(getSprite(0, 8),id++,BLOQUE));//56
        decor.add(BAIN_DROITE= new Case(getRotImg(getSprite(0, 8), 90),id++,BLOQUE));//53
        decor.add(BAIN_BAS= new Case(getRotImg(getSprite(0, 8), 180),id++,BLOQUE));//53
        decor.add(BAIN_GAUCHE= new Case(getRotImg(getSprite(0, 8), 270),id++,BLOQUE));//53
        decor.add(BAIN_MILIEU= new Case(getSprite(1, 8),id++,BLOQUE));//56
        decor.add(BAIN_MILIEU_P= new Case(getRotImg(getSprite(1, 8), 90),id++,BLOQUE));//53
        decor.add(DOUCHE_MILIEU= new Case(getSprite(2, 8),id++,BLOQUE));//56
        decor.add(DOUCHE= new Case(getSprite(3, 8),id++,BLOQUE));//56
        decor.add(MEUBLE_BAIN_D= new Case(getSprite(4, 8),id++,BLOQUE));//56
        decor.add(MEUBLE_BAIN_G= new Case(getRotImg(getSprite(4, 8), 180),id++,BLOQUE));//53
        decor.add(EVIER_BAIN= new Case(getSprite(5, 8),id++,BLOQUE));//56
        decor.add(BAC_A_JOUET= new Case(getSprite(6, 8),id++,BLOQUE));//56

        decor.add(BURREAU_MARRON_1= new Case(getSprite(8, 9),id++,BLOQUE));//56
        decor.add(BURREAU_MARRON_2= new Case(getSprite(0, 10),id++,BLOQUE));//56
        decor.add(BURREAU_MARRON_3= new Case(getSprite(1, 10),id++,BLOQUE));//56
        decor.add(BURREAU_MARRON_4= new Case(getSprite(2, 10),id++,BLOQUE));//56
        decor.add(BURREAU_MARRON_5= new Case(getSprite(3, 10),id++,BLOQUE));//56
        decor.add(BURREAU_MARRON_6= new Case(getSprite(4, 10),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_1= new Case(getSprite(6, 10),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_2= new Case(getSprite(7, 10),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_3= new Case(getSprite(8, 10),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_4= new Case(getSprite(0, 11),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_5= new Case(getSprite(1, 11),id++,BLOQUE));//56
        decor.add(BURREAU_GRIS_6= new Case(getSprite(2, 11),id++,BLOQUE));//56

        decor.add(TABLE_DE_CHEVET_MARRON= new Case(getSprite(7, 9),id++,BLOQUE));//56
        decor.add(TABLE_DE_CHEVET_GRIS= new Case(getSprite(5, 10),id++,BLOQUE));//56

        decor.add(CANAPE_D= new Case(getSprite(4,12),id++,BLOQUE));
        decor.add(CANAPE_M= new Case(getSprite(5,12),id++,BLOQUE));
        decor.add(CANAPE_G= new Case(getSprite(6,12),id++,BLOQUE));

        decor.add(CANAPE_D_R= new Case(getRotImg(getSprite(4,12),180),id++,BLOQUE));
        decor.add(CANAPE_M_R= new Case(getRotImg(getSprite(5,12),180),id++,BLOQUE));
        decor.add(CANAPE_G_R= new Case(getRotImg(getSprite(6,12),180),id++,BLOQUE));

        decor.add(TELE_D= new Case(getSprite(7,12),id++,BLOQUE));
        decor.add(TELE_M= new Case(getSprite(8,12),id++,BLOQUE));
        decor.add(TELE_G= new Case(getSprite(0,13),id++,BLOQUE));

        decor.add(TELE_D_R= new Case(getRotImg(getSprite(7,12),180),id++,BLOQUE));
        decor.add(TELE_M_R= new Case(getRotImg(getSprite(8,12),180),id++,BLOQUE));
        decor.add(TELE_G_R= new Case(getRotImg(getSprite(0,12),180),id++,BLOQUE));

        decor.add(POSE_SAVON= new Case(getSprite(3,15),id++,BLOQUE));
        decor.add(EVIER_1= new Case(getSprite(4,15),id++,BLOQUE));

        decor.add(POUBELLE_N_VIDE= new Case(getSprite(8,13),id++,BLOQUE));
        decor.add(POUBELLE_N_PLEINE= new Case(getSprite(0,14),id++,BLOQUE));        

        decor.add(CHAISE_MARRON= new Case(getSprite(3, 11),id++,BLOQUE));//56
        decor.add(CHAISE_GRISE= new Case(getSprite(4, 11),id++,BLOQUE));//56

        decor.add(HAIE_BORD=new Case(getSprite(5,11),id++,BLOQUE));
        decor.add(HAIE_MILIEU=new Case(getSprite(6,11),id++,BLOQUE));
        decor.add(HAIE_R= new Case(getRotImg(getSprite(6,11),90),id++,BLOQUE));
        decor.add(ARBRE= new Case(getSprite(7,11),id++,BLOQUE));

        decor.add(BANC_DROIT=new Case(getSprite(0,12),id++,BLOQUE));
        decor.add(BANC_MILIEU=new Case(getSprite(1,12),id++,BLOQUE));
        decor.add(BANC_GAUCHE=new Case(getSprite(2,12),id++,BLOQUE));

        
        decor.add(BANC_DROIT_R=new Case(getRotImg(getSprite(0,12),-90),id++,BLOQUE));
        decor.add(BANC_MILIEU_R=new Case(getRotImg(getSprite(1,12),-90),id++,BLOQUE));
        decor.add(BANC_GAUCHER=new Case(getRotImg(getSprite(2,12),-90),id++,BLOQUE));


        decor.add(BALANCOIRE= new Case(getSprite(3,12),id++,BLOQUE));
        decor.add(BALANCOIRE_2= new Case(getRotImg(getSprite(3,12),180),id++,BLOQUE));

        decor.add(GRILLE_PAIN_ETEINT= new Case(getSprite(4,14),id++,BLOQUE));
        decor.add(GRILLE_PAIN_ALLUME= new Case(getSprite(5,14),id++,BLOQUE));
        decor.add(GRILLE_PAIN_MARCHE= new Case(getSprite(6,14),id++,BLOQUE));

        decor.add(MACHINIE_CAFE= new Case(getSprite(1,14),id++,BLOQUE));
        decor.add(BOL_FRUIT= new Case(getSprite(2,14),id++,BLOQUE));
        decor.add(BOL= new Case(getSprite(3,14),id++,BLOQUE));

        decor.add(GATEAU_PETIT= new Case(getRotImg(getSprite(7,14),180),id++,BLOQUE));
        decor.add(GATEAU_GRAND= new Case(getSprite(1,15),id++,BLOQUE));

        decor.add(PREPARATION= new Case(getSprite(8,14),id++,BLOQUE));
        decor.add(PREPARATION_FINI= new Case(getSprite(0,15),id++,BLOQUE));

        
        cases.addAll(sol);
        cases.addAll(mur);
        cases.addAll(obstacle);
        cases.addAll(decor);
    }

    private void loadAtlas() {
        atlas = Enregistrement.getSpriteAtlas();
    }

    public static BufferedImage getRotImg(BufferedImage input, int rotAngle) {
        int width = input.getWidth();
        int height = input.getHeight();
        
        BufferedImage nouveauImg = new BufferedImage(width, height, input.getType());
        Graphics2D g2d = nouveauImg.createGraphics();
        
        g2d.rotate(Math.toRadians(rotAngle),width/2,height/2);
        g2d.drawImage(input, 0, 0, null);
        g2d.dispose();
        
        return nouveauImg;
    }

    public static BufferedImage getVerticalFlipImg(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        
        BufferedImage nouveauImg = new BufferedImage(width, height, input.getType());
        Graphics2D g2d = nouveauImg.createGraphics();
        
        // Dessiner l'image d'entrée à l'envers verticalement
        g2d.drawImage(input, 0, 0, width, height, 0, height, width, 0, null);
        g2d.dispose();
        
        return nouveauImg;
    }

    public BufferedImage getSprite(int id) {
        return cases.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        int subImageWidth = 40; // Largeur de la sous-image
        int subImageHeight = 40; // Hauteur de la sous-image
        
        // Calculer la position en x en prenant en compte les deux lignes d'images
        int xPosition = xCord * subImageWidth;
        
        // Calculer la position en y en prenant en compte la ligne d'image
        int yPosition = yCord * subImageHeight;
        
        // Vérifier les limites pour éviter une exception RasterFormatException
        if (xPosition + subImageWidth > atlas.getWidth() || yPosition + subImageHeight > atlas.getHeight()) {
            // Gérer cette situation d'une manière appropriée, par exemple, renvoyer une image par défaut ou lancer une exception
            System.err.println("Erreur : Coordonnées en dehors des limites de l'image atlas.");
            System.out.println(xCord+","+yCord);
            return getDefaultSprite(); // Remplacez ceci par la logique appropriée
        }
        
        return atlas.getSubimage(xPosition, yPosition, subImageWidth, subImageHeight);
    }
    
    private BufferedImage getDefaultSprite() {
        // Remplacez ceci par la logique appropriée pour renvoyer une image par défaut
        return null;
    }

    public int getTailleCase() {
        return tailleCase;
    }
    
    public static int getCaseType(int id){
        for (Case c : cases) {
            if (c.getId() == id) {
                return c.getType();
            }
        }
        // Si aucun identifiant correspondant n'est trouvé, vous pouvez choisir de renvoyer une valeur par défaut ou lancer une exception selon vos besoins.
        System.err.println("Erreur : Aucune case trouvée avec l'identifiant " + id);
        return -1; // Remplacez ceci par la valeur par défaut ou la logique appropriée.
    }

    public Case getMUR() {
        return MUR_HAUT;
    }

}


