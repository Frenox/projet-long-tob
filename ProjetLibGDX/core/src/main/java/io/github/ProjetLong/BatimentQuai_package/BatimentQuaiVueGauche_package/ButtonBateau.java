package io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVueGauche_package;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;

import io.github.ProjetLong.Bateaux.Bateau;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiVue;
import io.github.ProjetLong.batiments.BatimentQuai;
import io.github.ProjetLong.BatimentQuai_package.BatimentQuaiModele;

public class ButtonBateau extends Button {
    private int i;
    private TextButton textButton;
    private BatimentQuaiModele batimentQuaiModele;

    public ButtonBateau(BatimentQuai batimentQuai, BatimentQuaiModele batimentQuaiModele, ButtonStyle buttonStyle,
            int i) {
        super(buttonStyle);

        this.i = i;
        this.batimentQuaiModele = batimentQuaiModele;

        int width1 = 20;
        int height1 = 10;

        Pixmap pixmap = new Pixmap(width1, height1, Format.RGBA8888);
        pixmap.setColor(30 / 255, 11 / 255, 2 / 255, 1f);
        pixmap.fillRectangle(0, 0, width1, height1);

        Texture pixmaptex1 = new Texture(pixmap);
        Skin skin1 = new Skin();
        skin1.add("Rectangle", pixmaptex1);

        TextButtonStyle style = new TextButtonStyle(skin1.getDrawable("Rectangle"), skin1.getDrawable("Rectangle"),
                skin1.getDrawable("Rectangle"), BatimentQuaiVue.skin.getFont("HebertSansBold"));

        this.textButton = new TextButton("Supprimer", style);
        this.textButton.setPosition(this.getX() + 20, this.getY() + 12);
        this.textButton.setVisible(false);
        this.textButton.setZIndex(1);
        batimentQuai.addActor(this.textButton);

        this.afficher();

        batimentQuaiModele.addPropertyChangeListener("Nombre de bateaux différent", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                afficher();
            }

        });

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                batimentQuaiModele.setBateauCourant(i);
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
                batimentQuaiModele.retirer_element(i);
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

        if (this.batimentQuaiModele.element_affichable(i)) {
            Bateau bateau = this.batimentQuaiModele.bateau_i(i);
            BatimentQuaiVue.skin.getFont("HebertSansBold").draw(batch, bateau.getName(), this.getX() + 4,
                    this.getY() + 12);
        }
    }

    public void afficher() {
        if (batimentQuaiModele.element_affichable(i)) {
            setVisible(true);
        } else {
            setVisible(false);
            textButton.setVisible(false);
        }
    }
}
