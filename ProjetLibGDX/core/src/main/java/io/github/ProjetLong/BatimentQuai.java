package io.github.ProjetLong;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
<<<<<<< HEAD
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;
import io.github.ProjetLong.DataManager.DataManager;

public class BatimentQuai implements Batiment {
    public static final Texture batQuaiTexture = new Texture("bat1.png");
=======

import io.github.ProjetLong.ZonesPeche.Poisson;

public class BatimentQuai implements Batiment {
    final int CAPACITE_MAX_MENU = 7;

    private Texture batQuaiTexture;
>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
    private BitmapFont HebertBold;

    DataManager data;

    private boolean isOpened;

<<<<<<< HEAD
=======
    // à supprimer quand implémenté
    private ArrayList<Bateau> bateaux;

    private Vector3 mouse;

    private ScrollPane scrollpane_poissons;
    private ScrollPane scrollpane_stockage;

>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
    Stage stage;
    Table root;

    boolean poissonDessin;
    Texture poisson_texture_hover;

    Texture pixmaptex;
    private Texture NomBat;

    private int width;
    private int height;

<<<<<<< HEAD
    public BatimentQuai(Viewport viewport, DataManager data) {
=======
    public BatimentQuai(Viewport viewport) {
        poissonDessin = false;

>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
        HebertBold = new BitmapFont(Gdx.files.internal("HebertSansBold.fnt"));
        HebertBold.getData().setScale(0.15f);

        this.isOpened = false;
        this.data = data;

<<<<<<< HEAD
        height = BatimentQuaiVue.fishInv.getHeight() * 7 + 82;
=======
        this.page = 1;

        // à supprimer plus tard
        this.bateaux = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            this.bateaux.add(new Barque());
        }

        this.maxPage = this.bateaux.size() / CAPACITE_MAX_MENU;

        height = this.fishInv.getHeight() * 7 + 6 * 2 + 70;
>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
        width = 370;
        
        pixmaptex = new Texture("overlayQuai.png");
        NomBat = new Texture("nom_quais.png");
        

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        BatimentQuaiModele modele = new BatimentQuaiModele(this.data);
        root = new BatimentQuaiVue(this, modele);

        stage.addActor(root);

<<<<<<< HEAD
        root.setFillParent(true);
=======
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
        infos_bateaux.add(new Label("Equipement : ", skin, "HebertBold", Color.WHITE)).top().left().expand().pad(2)
                .row();
        infos_bateaux.add(Equipement_Texture).center().pad(2).expand();

        caracteristiques.add(infos_bateaux).top().left().expand();

        Table droite = new Table();

        poissons_table = new Table();
        stockage_table = new Table();

        scrollpane_poissons = new ScrollPane(poissons_table);
        scrollpane_stockage = new ScrollPane(stockage_table);

        droite.setSkin(skin);

        droite.add("Cannes a peche", "HebertBold", Color.WHITE).row();
        droite.add(scrollpane_stockage).center().bottom().expand().height(this.fishInv.getWidth() * 1.618f * 0.3f)
                .pad(5).padBottom(5).row();
        droite.add("Poissons", "HebertBold", Color.WHITE).padBottom(0).row();
        droite.add(scrollpane_poissons).right().bottom().expand().height(this.fishInv.getWidth() * 1.618f * 0.3f).pad(5)
                .padTop(5);

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

        page_label = new Label(page + " / " + maxPage, skin, "HebertBold", Color.WHITE);
        changer.add(page_label);

        changer.add(new ButtonChangerPage(styleChangerPageDroite, 1)).pad(3);

        mtable.add(changer);

        root.add(mtable);
        root.add(caracteristiques).fill().width((this.fishInv.getWidth() + 2) * 1.618f).pad(5);

        root.setFillParent(true);

        caracteristiques.setVisible(false);

>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
    }

    @Override
    public void input(VilleScreen screen) {
<<<<<<< HEAD
        
=======
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
>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
    }

    @Override
    public void logic(VilleScreen screen) {
<<<<<<< HEAD
        this.data = screen.jeu.data;
    }

    @Override
    public void draw(VilleScreen screen, int position, int offset) {
        screen.jeu.batch.draw(batQuaiTexture, 0f + position * batQuaiTexture.getWidth() + offset, 91);
        screen.jeu.HebertBold.draw(screen.jeu.batch, "Quai", 64 * position + offset, 180);

    }

    public void addActor(Actor actor) {
        this.stage.addActor(actor);;
=======

    }

    @Override
    public void draw(VilleScreen screen, int position) {
        screen.jeu.batch.draw(this.batQuaiTexture, 0f + position * this.batQuaiTexture.getWidth(), 90);

    }

    private void afficherCaracteristiques(int i) {
        caracteristiques.setVisible(true);

        Bateau bateau = bateaux.get(i);

        nom_bateau.setText("Nom : " + bateau.getName());
        modele.setText("Modele : " + bateau.getModeleName());
        taille_stockage.setText("Taille de stockage : " + bateau.getTailleDispo());
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

        for (int j = 0; j < 3; j++) {
            stockage_table.add(new Image(new Texture("fishing_rod_1_lvl1.png")));
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

                    if (i + page * CAPACITE_MAX_MENU < bateaux.size()) {
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
                    bateaux.remove(i + page * CAPACITE_MAX_MENU);
                    if (maxPage > (bateaux.size() - 1) / CAPACITE_MAX_MENU) {
                        if (page == maxPage) {
                            page--;
                        }

                        maxPage -= 1;
                        page_label.setText(page + " / " + maxPage);
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
            Bateau bateau = bateaux.get(i + page * CAPACITE_MAX_MENU);
            HebertBold.draw(batch, bateau.getName(), this.getX() + 4, this.getY() + 12);
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
                        page_label.setText(page + " / " + maxPage);

                        support.firePropertyChange("page", page - pas, page);
                    }
                }
            });
        }
>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
    }

    @Override
    public void affichageInterface(VilleScreen screen) {
        if (this.isOpened) {
            int epsilon = 8;
            screen.jeu.batch.draw(this.pixmaptex, screen.jeu.viewport.getWorldWidth() / 2 - width / 2,
                    screen.jeu.viewport.getWorldHeight() / 2 - height / 2 + epsilon);
            screen.jeu.batch.draw(this.NomBat,71,233);
        }

        screen.jeu.batch.end();

        Gdx.input.setInputProcessor(stage);

        stage.getViewport().apply();
        stage.act();
        stage.draw();

        screen.jeu.batch.begin();
    }
<<<<<<< HEAD

    public boolean getIsOpened() {
        return this.isOpened;
    }

    public void setIsOpened(boolean value) {
        this.isOpened = value;
    }

    public void close() {
        root.setVisible(false);
    }

    public void open() {
        root.setVisible(true);
    }
=======
>>>>>>> parent of 16df3c5 (Merge pull request #17 from Frenox/Coco)
}
