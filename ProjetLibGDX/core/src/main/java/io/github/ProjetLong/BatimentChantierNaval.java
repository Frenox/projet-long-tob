package io.github.ProjetLong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class BatimentChantierNaval implements Batiment {

    // Pour l'affichage et l'interaction
    private boolean isOpened;
    private Stage stage;
    private Vector3 mouse;

    // Textures et sprites pour le bâtiment et l’interface (overlay)
    private Texture chantierTexture;
    private Sprite chantierSprite;

    private Texture interfaceOverlayTexture;
    private Sprite interfaceOverlaySprite;

    // Boutons graphiques (pour garder la cohérence visuelle)
    private Texture buttonValidateTexture;
    private Sprite buttonValidateSprite;
    private Texture buttonCancelTexture;
    private Sprite buttonCancelSprite;
    private Texture nom;

    // Texture de fond pour les "cartes" d'option
    private Texture equipmentCardTexture;

    // Gestion de la file d'attente pour les ordres de construction
    private final Queue<ConstructionOrder> buildQueue;
    private final int maxQueueLength = 5;

    // Niveau du chantier (chaque niveau réduit les temps de construction)
    private int level;

    // Ensemble des équipements débloqués
    private final Set<EquipementType> unlockedEquipments;

    // Coordonnées et dimensions pour l'affichage des cartes d'options
    private final int cardStartX = 136;
    private final int cardStartY = 197;
    private final int cardWidth = 100;
    private final int cardHeight = 25;

    public BatimentChantierNaval() {
        // Initialisation de l'interface et du stage
        this.isOpened = false;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.mouse = new Vector3(0, 0, 0);

        // Chargement des textures. Mettre les bonnes textures.
        this.nom = new Texture("nom_chantier.png");
        this.chantierTexture = new Texture("bat1.png"); // image du bâtiment chantier naval
        this.interfaceOverlayTexture = new Texture("overlayMarche.png"); // overlay de l'interface
        this.buttonValidateTexture = new Texture("valider.png"); // bouton de validation (affichage)
        this.buttonCancelTexture = new Texture("refuser.png"); // bouton d'annulation (affichage)
        this.equipmentCardTexture = new Texture("hover_alpha.png"); // fond pour une carte d'option

        // Création des sprites
        this.chantierSprite = new Sprite(chantierTexture);
        this.interfaceOverlaySprite = new Sprite(interfaceOverlayTexture);
        this.buttonValidateSprite = new Sprite(buttonValidateTexture);
        this.buttonCancelSprite = new Sprite(buttonCancelTexture);

        // Positionnement inspiré du marché / capitainerie
        this.chantierSprite.setPosition(0, 90);
        this.interfaceOverlaySprite.setPosition(130, 50);
        this.buttonValidateSprite.setPosition(280, 90);
        this.buttonCancelSprite.setPosition(335, 90);

        // Initialisation du chantier
        this.level = 1;
        this.buildQueue = new LinkedList<>();
        this.unlockedEquipments = new HashSet<>();
        // this.unlockedEquipments.add();
        // Par défaut, seul le Bateau est débloqué
        this.unlockedEquipments.add(EquipementType.BOAT);
    }

    @Override
    public void input(VilleScreen screen) {
        // Utilisation de la touche W (au lieu de la barre espace) pour afficher/cacher
        // l'interface
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            isOpened = !isOpened;
        }
        // Si l'interface est ouverte, on gère la détection de clic sur une option
        if (isOpened) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                screen.jeu.viewport.getCamera().unproject(mouse);

                // Chaque équipement débloqué est affiché sous forme de "carte"
                int index = 0;
                for (EquipementType eq : EquipementType.values()) {
                    if (unlockedEquipments.contains(eq)) {
                        int cardX = cardStartX;
                        int cardY = cardStartY - index * cardHeight;
                        // Vérifie si le clic se situe dans la zone de la carte
                        if (mouse.x >= cardX && mouse.x <= (cardX + cardWidth) &&
                                mouse.y >= cardY && mouse.y <= (cardY + cardHeight)) {
                            // On ajoute l'équipement à la file si celle-ci n'est pas pleine
                            if (buildQueue.size() < maxQueueLength) {
                                int effectiveTime = getEffectiveBuildTime(eq);
                                buildQueue.add(new ConstructionOrder(eq, effectiveTime));
                                System.out.println(eq.getName() + " ajouté à la file d'attente (temps effectif : "
                                        + effectiveTime + " sec)");
                            } else {
                                System.out.println("La file d'attente est pleine !");
                            }
                            break; // Un seul clic traité par frame
                        }
                        index++;
                    }
                }
            }
        } else {
            // Si l'interface est fermée, on réinitialise la position de la souris
            mouse.set(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {
        // Mise à jour de la file : décrémenter le temps de construction du premier
        // ordre
        if (!buildQueue.isEmpty()) {
            ConstructionOrder currentOrder = buildQueue.peek();
            currentOrder.decrementTime();
            if (currentOrder.isCompleted()) {
                System.out.println(currentOrder.getType().getName() + " construit !");
                buildQueue.poll();
            }
        }

        // Pour tester le système, le niveau peut être augmenté avec la touche L (le
        // temps sera réduit)
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            level++;
            System.out.println("Niveau du chantier naval : " + level);
        }
    }

    @Override
    public void draw(VilleScreen screen, int position) {
        // Toujours afficher le bâtiment chantier
        chantierSprite.draw(screen.jeu.batch);
    }

    // Méthode d'affichage de l'interface (inspirée du code de la capitainerie)
    public void affichageInterface(VilleScreen screen) {
        if (isOpened) {
            // Affichage de l'overlay
            interfaceOverlaySprite.draw(screen.jeu.batch);
            screen.jeu.batch.draw(this.nom, 130, 231);
            // Titre et niveau (position et style inspirés de la capitainerie)
            screen.jeu.HebertBold.draw(screen.jeu.batch, "Niveau : " + level, 243, 229);

            // Affichage des options de construction sous forme de "cartes"
            int index = 0;
            for (EquipementType eq : EquipementType.values()) {
                if (unlockedEquipments.contains(eq)) {
                    int cardX = cardStartX;
                    int cardY = cardStartY - index * cardHeight;
                    // Affichage de la carte (le fond)
                    screen.jeu.batch.draw(equipmentCardTexture, cardX, cardY);
                    // Affichage des informations : nom, temps effectif et coût
                    String text = eq.getName() + " (T:" + getEffectiveBuildTime(eq)
                            + "s, C:" + eq.getBaseCost() + ")";
                    screen.jeu.HebertBold.draw(screen.jeu.batch, text, cardX + 6, cardY + cardHeight - 4);
                    index++;
                }
            }

            // Affichage de la file d'attente (les constructions en cours)
            int yQueue = cardStartY - index * cardHeight - 10;
            screen.jeu.HebertBold.draw(screen.jeu.batch, "EN CONSTRUCTION :", cardStartX, yQueue);
            yQueue -= 15;
            for (ConstructionOrder order : buildQueue) {
                screen.jeu.HebertBold.draw(screen.jeu.batch, order.toString(), cardStartX, yQueue);
                yQueue -= 20;
            }

            // Affichage des boutons de validation/annulation pour la cohérence visuelle
            buttonValidateSprite.draw(screen.jeu.batch);
            buttonCancelSprite.draw(screen.jeu.batch);
        }
    }

    // Calcule le multiplicateur de temps : chaque niveau au-delà du 1 réduit de 10
    // % le temps de construction,
    // avec un plancher à 50 % du temps de base.
    private double getBuildTimeMultiplier() {
        double multiplier = 1.0 - 0.1 * (level - 1);
        return multiplier < 0.5 ? 0.5 : multiplier;
    }

    // Renvoie le temps effectif de construction pour un équipement donné
    public int getEffectiveBuildTime(EquipementType equipment) {
        return (int) Math.ceil(equipment.getBaseTime() * getBuildTimeMultiplier());
    }

}
