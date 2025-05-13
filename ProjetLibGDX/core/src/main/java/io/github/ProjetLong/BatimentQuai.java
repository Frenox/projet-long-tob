package io.github.ProjetLong;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.ProjetLong.DataManager.DataManager;
import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentQuai implements Batiment {
    final int CAPACITE_MAX_MENU = 4;

    private Texture batQuaiTexture;
    private BitmapFont HebertBold;

    private int maxPage;

    private Texture fishInv;

    private boolean isOpened;

    // à supprimer quand implémenté
    private DataManager data;

    private Vector3 mouse;

    private ScrollPane scrollpane_poissons;
    private ScrollPane scrollpane_stockage;

    Stage stage;
    Table mtable;

    Table caracteristiques;
    int page;

    Label nom_bateau;
    Label modele;
    Label taille_stockage;
    Label page_label;
    Table poissons_table;
    Table stockage_table;
    Image Equipement_Texture;

    boolean poissonDessin;
    Texture poisson_texture_hover;

    Texture pixmaptex;
    private Texture NomBat;

    private int width;
    private int height;

    public BatimentQuai(Viewport viewport, DataManager datan) {
        poissonDessin = false;
        this.data = datan;
        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);
        this.batQuaiTexture = new Texture("bat1.png");
        this.fishInv = new Texture("fish_tab_fish.png");

        this.mouse = new Vector3(0, 0, 0);
        this.isOpened = false;

        this.page = 0;

        this.maxPage = this.data.getBateaux().size() / CAPACITE_MAX_MENU;

        height = this.fishInv.getHeight() * 7 + 6 * 2 + 70;
        width = 370;

        pixmaptex = new Texture("overlayQuai.png");
        NomBat = new Texture("nom_quais.png");

        // ***************************************************** */

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        mtable = new Table();
        mtable.setVisible(false);

        Skin skin = new Skin(Gdx.files.internal("skin.json"));
        skin.add("HebertBold", HebertBold);
        ButtonStyle ButtonStylePoisson = new ButtonStyle(skin.getDrawable("fish_tab_fish"),
                skin.getDrawable("fish_tab_fish"), skin.getDrawable("fish_tab_fish"));

        Table root = new Table();
        stage.addActor(root);

        caracteristiques = new Table();
        caracteristiques.setSkin(skin);

        nom_bateau = new Label("Bateau :", skin, "HebertBold", Color.WHITE);
        modele = new Label("Modele :", skin, "HebertBold", Color.WHITE);
        taille_stockage = new Label("Taille Stockage :", skin, "HebertBold", Color.WHITE);
        Equipement_Texture = new Image();

        Table infos_bateaux = new Table();
        infos_bateaux.setSkin(skin);
        infos_bateaux.add(nom_bateau).pad(2).row();
        infos_bateaux.add(modele).pad(2).row();
        infos_bateaux.add(taille_stockage).pad(2).row();
        // Ajout des caractéristiques (infos bateau) en haut
        Table haut = new Table();
        haut.add(infos_bateaux).left().pad(5);
        mtable.add(haut).row();

        Table droite = new Table();
        poissons_table = new Table();
        stockage_table = new Table();

        scrollpane_poissons = new ScrollPane(poissons_table);
        scrollpane_stockage = new ScrollPane(stockage_table);

        droite.setSkin(skin);

        droite.add("Equipement disponible :", "HebertBold", Color.WHITE).row();
        droite.add(scrollpane_stockage).center().bottom().expand().height(this.fishInv.getWidth() * 1.618f * 0.3f)
                .pad(5).padBottom(5).row();
        droite.add("Poissons", "HebertBold", Color.WHITE).padBottom(0).row();
        droite.add(scrollpane_poissons).right().bottom().expand().height(this.fishInv.getWidth() * 1.618f * 0.3f).pad(5)
                .padTop(5);
        caracteristiques.add(new Label("Equipement : ", skin, "HebertBold", Color.WHITE)).top().left().expand().pad(2);
        caracteristiques.add(Equipement_Texture).center().pad(2).expand();
        caracteristiques.add(droite).right().expand();

        for (int i = 0; i < CAPACITE_MAX_MENU; i++) {
            mtable.add(new ButtonBateau(ButtonStylePoisson, i)).pad(2);
            mtable.row();
        }

        mtable.setSkin(skin);
        skin.add("Arrow_left", new Texture("minigame2_arrow_left_fishing.png"));
        skin.add("Arrow_right", new Texture("minigame2_arrow_right_fishing.png"));

        Table changer = new Table();

        ButtonStyle styleChangerPageGauche = new ButtonStyle(skin.getDrawable("Arrow_left"),
                skin.getDrawable("Arrow_left"), skin.getDrawable("Arrow_left"));
        ButtonStyle styleChangerPageDroite = new ButtonStyle(skin.getDrawable("Arrow_right"),
                skin.getDrawable("Arrow_right"), skin.getDrawable("Arrow_right"));

        changer.add(new ButtonChangerPage(styleChangerPageGauche, -1)).pad(3);

        page_label = new Label((page + 1) + " / " + (maxPage + 1), skin, "HebertBold", Color.WHITE);
        changer.add(page_label);

        changer.add(new ButtonChangerPage(styleChangerPageDroite, 1)).pad(3);

        mtable.add(changer);

        root.add(mtable);
        root.add(caracteristiques).fill().width((this.fishInv.getWidth() + 2) * 1.618f).pad(5);

        root.setFillParent(true);

        caracteristiques.setVisible(false);
        support.firePropertyChange("page", -1, page);
    }

    @Override
    public void input(VilleScreen screen) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            if (this.isOpened) {
                isOpened = false;
                mtable.setVisible(false);
                caracteristiques.setVisible(false);
            } else {
                mtable.setVisible(true);
                isOpened = true;
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            this.mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);
        } else {
            this.mouse = new Vector3(0, 0, 0);
        }
    }

    @Override
    public void logic(VilleScreen screen) {
        this.data = screen.jeu.data;
        this.maxPage = this.data.getBateaux().size() / CAPACITE_MAX_MENU;
    }

    @Override
    public void draw(VilleScreen screen, int position) {
        screen.jeu.batch.draw(this.batQuaiTexture, 0f + position * this.batQuaiTexture.getWidth(), 90);

    }

    private void afficherCaracteristiques(int i) {
        caracteristiques.setVisible(true);

        Bateau bateau = data.getBateaux().get(i);

        nom_bateau.setText("Nom : " + bateau.getName());
        modele.setText("Modele : " + bateau.getModeleName());
        taille_stockage.setText("Taille de stockage : " + bateau.getTailleStockage());
        Skin skin = new Skin(Gdx.files.internal("skin.json"));
        skin.add("CanneApeche", new Texture("fishing_rod_1_lvl1.png"));
        Equipement_Texture.setDrawable(skin.getDrawable("CanneApeche"));

        poissons_table.clear();
        stockage_table.clear();

        List<Poisson> poissons = bateau.getContenu();
        List<CanneAPeche> CannesAPeche = bateau.getCannes();

        ButtonStyle ButtonStylePoisson = new ButtonStyle(skin.getDrawable("fish_tab_fish"),
                skin.getDrawable("fish_tab_fish"), skin.getDrawable("fish_tab_fish"));

        for (int j = 0; j < 3; j++) {
            poissons_table.add(new ButtonPoisson(ButtonStylePoisson, new Poisson(j, j)));
            poissons_table.row();
        }

        for (ModuleBateau module : this.data.getModules()) {
            stockage_table.add(new Image(module.getTexture()));
            stockage_table.row();
        }
    }

    Event evenX = new Event();

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public class ButtonBateau extends Button {
        private int i;
        private TextButton textButton;

        public ButtonBateau(ButtonStyle buttonStyle, int i) {
            super(buttonStyle);

            this.i = i;
            int width1 = 20;
            int height1 = 10;

            Pixmap pixmap = new Pixmap(width1, height1, Format.RGBA8888);

            pixmap.setColor(30 / 255, 11 / 255, 2 / 255, 1f);

            pixmap.fillRectangle(0, 0, width1, height1);

            Texture pixmaptex1 = new Texture(pixmap);
            Skin skin1 = new Skin();
            skin1.add("Rectangle", pixmaptex1);

            TextButtonStyle style = new TextButtonStyle(skin1.getDrawable("Rectangle"), skin1.getDrawable("Rectangle"),
                    skin1.getDrawable("Rectangle"), HebertBold);

            this.textButton = new TextButton("Supprimer", style);
            this.textButton.setPosition(this.getX() + 20, this.getY() + 12);
            this.textButton.setVisible(false);
            stage.addActor(this.textButton);

            support.addPropertyChangeListener("page", new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent arg0) {
                    if (i + page * CAPACITE_MAX_MENU < data.getBateaux().size()) {
                        setVisible(true);
                    } else {
                        setVisible(false);
                        textButton.setVisible(false);
                    }
                }

            });

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    afficherCaracteristiques(i + page * CAPACITE_MAX_MENU);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    textButton.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    textButton.setVisible(false);
                }
            });

            this.textButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    data.getBateaux().remove(i + page * CAPACITE_MAX_MENU);
                    if (maxPage > (data.getBateaux().size() - 1) / CAPACITE_MAX_MENU) {
                        if (page == maxPage) {
                            page--;
                        }

                        page_label.setText((page + 1) + " / " + (maxPage + 1));
                    }
                    support.firePropertyChange("page", 0, 1);

                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    textButton.setVisible(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    textButton.setVisible(false);
                }
            });

        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            this.textButton.setPosition(this.getX() + 75, this.getY() + 2);

            HebertBold.getData().setScale(0.15f);
            if (i + page * CAPACITE_MAX_MENU < data.getBateaux().size()) {
                Bateau bateau = data.getBateaux().get(i + page * CAPACITE_MAX_MENU);
                HebertBold.draw(batch, bateau.getName(), this.getX() + 4, this.getY() + 12);
            }
        }
    }

    public class ButtonPoisson extends Button {
        private Poisson poisson;

        Batch batch;

        public ButtonPoisson(ButtonStyle buttonStyle, Poisson poisson) {
            super(buttonStyle);

            this.poisson = poisson;

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);

                    poissonDessin = true;
                    poisson_texture_hover = poisson.getFishText();
                }

                public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    poissonDessin = false;
                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            HebertBold.getData().setScale(0.15f);
            HebertBold.draw(batch, poisson.getNom(), this.getX() + 4, this.getY() + 12);

            this.batch = batch;
        }
    }

    public class ButtonCanneAPeche extends Button {
        private CanneAPeche CanneAPeche;

        public ButtonCanneAPeche(ButtonStyle buttonStyle, CanneAPeche CanneAPeche) {
            super(buttonStyle);

            this.CanneAPeche = CanneAPeche;

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                }
            });
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            HebertBold.getData().setScale(0.15f);
            HebertBold.draw(batch, CanneAPeche.getNiveau() + "", this.getX() + 4, this.getY() + 12);
        }
    }

    public class ButtonChangerPage extends Button {

        public ButtonChangerPage(ButtonStyle buttonStyle, int pas) {
            super(buttonStyle);

            this.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (page + pas >= 0 && page + pas <= maxPage) {
                        page += pas;
                        page_label.setText((page + 1) + " / " + (maxPage + 1));

                        support.firePropertyChange("page", page - pas, page);
                    }
                }
            });
        }
    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (this.isOpened) {
            int epsilon = 8;
            screen.jeu.batch.draw(this.pixmaptex, screen.jeu.viewport.getWorldWidth() / 2 - width / 2,
                    screen.jeu.viewport.getWorldHeight() / 2 - height / 2 + epsilon);
            screen.jeu.batch.draw(this.NomBat, 71, 233);
        }

        screen.jeu.batch.end();

        stage.getViewport().apply();
        stage.act();
        stage.draw();

        screen.jeu.batch.begin();

        if (poissonDessin) {
            Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            screen.jeu.viewport.getCamera().unproject(mouse);

            screen.jeu.batch.draw(poisson_texture_hover, mouse.x, mouse.y, 70, 70 / 1.6f);
        }
    }
}
