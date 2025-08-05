package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import models.game.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardScreen implements Screen {
    private final SpriteBatch batch;
    private final Stage stage;
    private final BitmapFont font;
    private final Skin skin;
    private final List<Player> players;
    private final Table table;
    private SortCriteria currentSort = SortCriteria.NAME;

    public ScoreboardScreen() {
        this.players = App.getInstance().currentPlayerViewController.game.getPlayers();
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        font.getData().setScale(1.5f);

        skin = Assets.getSkin();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        createUI();

        UIUtil.createBack(() -> {
            Main.getInstance().setScreen(new PauseMenu());
        }, stage);

    }

    private void createUI() {
        table.clear();

        // Title
        Table titleTable = new Table();
        TextButton.TextButtonStyle titleStyle = new TextButton.TextButtonStyle();
        titleStyle.font = font;
        titleStyle.fontColor = Color.GOLD;

        TextButton title = new TextButton("SCOREBOARD", titleStyle);
        titleTable.add(title).padBottom(20);
        table.add(titleTable).colspan(4).row();

        // Sorting buttons
        TextButton sortNameBtn = createSortButton("Name", SortCriteria.NAME);
        TextButton sortSkillBtn = createSortButton("Skill", SortCriteria.SKILL);
        TextButton sortQuestsBtn = createSortButton("Quests", SortCriteria.QUESTS);
        TextButton sortCoinsBtn = createSortButton("Coins", SortCriteria.COINS);

        table.add(sortNameBtn).pad(5).width(200);
        table.add(sortSkillBtn).pad(5).width(150);
        table.add(sortQuestsBtn).pad(5).width(150);
        table.add(sortCoinsBtn).pad(5).width(150);
        table.row();

        // Column headers
        table.add(createHeaderCell("Player Name")).width(200);
        table.add(createHeaderCell("Total Skill")).width(150);
        table.add(createHeaderCell("Quests")).width(150);
        table.add(createHeaderCell("Coins")).width(150);
        table.row();

        // Player rows with alternating colors
        boolean alternate = false;
        for (Player player : players) {
            Color rowColor = alternate ? new Color(0.2f, 0.2f, 0.2f, 1) : new Color(0.3f, 0.3f, 0.3f, 1);
            alternate = !alternate;

            table.add(createPlayerCell(player.getUser().getUsername(), rowColor)).width(200);
            table.add(createPlayerCell(String.valueOf(player.getSkill()), rowColor)).width(150);
            table.add(createPlayerCell(String.valueOf(player.getQuest()), rowColor)).width(150);
            table.add(createPlayerCell(String.valueOf(player.getInventory().getAmount("coin")), rowColor)).width(150);
            table.row();
        }
    }

    private TextButton createSortButton(String text, SortCriteria criteria) {
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sortPlayers(criteria);
            }
        });
        return button;
    }

    private TextButton createHeaderCell(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.font.getData().setScale(1.2f);
        style.fontColor = Color.YELLOW;

        TextButton button = new TextButton(text, style);
        button.getLabel().setAlignment(Align.center);
        return button;
    }

    private TextButton createPlayerCell(String text, Color backgroundColor) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.font.getData().setScale(1.2f);
        style.fontColor = Color.WHITE;

        TextButton button = new TextButton(text, style);
        button.getLabel().setAlignment(Align.center);
        button.getColor().set(backgroundColor);
        return button;
    }

    private void sortPlayers(SortCriteria criteria) {
        currentSort = criteria;
        switch (criteria) {
            case NAME:
                players.sort(Comparator.comparing(p -> p.getUser().getUsername()));
                break;
            case SKILL:
                players.sort((p1, p2) -> Integer.compare(
                        p2.getSkill(),
                        p1.getSkill()
                ));
                break;
            case QUESTS:
                players.sort((p1, p2) -> Integer.compare(p2.getQuest(), p1.getQuest()));
                break;
            case COINS:
                players.sort((p1, p2) -> Integer.compare(
                        p2.getInventory().getAmount("coin"),
                        p1.getInventory().getAmount("coin")
                ));
                break;
        }
        createUI();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        font.dispose();
        skin.dispose();
    }

    private enum SortCriteria {
        NAME, SKILL, QUESTS, COINS
    }
}