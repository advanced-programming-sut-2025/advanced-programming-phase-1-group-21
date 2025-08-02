package io.github.StardewValley.views.menu.GUI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TagNotification {
	private final Stage stage;
	private final Label messageLabel;
	private final Table container;
	private boolean isVisible = false;
	private final int WIDTH = 300;
	private final int HEIGHT = 100;
	private final int MARGIN = 20;

	public TagNotification(Skin skin) {
		stage = new Stage(new ScreenViewport());

		BitmapFont font = generateFont(24);

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;

		messageLabel = new Label("", labelStyle);
		messageLabel.setWrap(true);
		messageLabel.setAlignment(Align.center);

		container = new Table();
		container.top().right();
		container.setBackground(new Image(new TextureRegionDrawable(new TextureRegion(createRoundedBackground(1, 1, 20)))).getDrawable()); // اندازه موقع ساخت مهم نیست
		container.add(messageLabel).width(400).pad(20);
		container.setVisible(false);
		stage.addActor(container);
	}

	public void show(String text) {
		isVisible = true;

		messageLabel.setText(text);

		// یک بار Stage رو act کن تا اندازه واقعی متن محاسبه شه
		stage.act(Gdx.graphics.getDeltaTime());

		container.pack(); // حالا اندازه واقعی محتوا مشخصه

		float width = container.getWidth();
		float height = container.getHeight();

		float x = Gdx.graphics.getWidth() - width - MARGIN;
		float y = Gdx.graphics.getHeight() - height - MARGIN;
		container.setPosition(x, y);

		// ساخت پس‌زمینه جدید با اندازه درست و گوشه‌های گرد
		container.setBackground(new Image(new TextureRegionDrawable(new TextureRegion(
				createRoundedBackground((int) width, (int) height, 20)
		))).getDrawable());

		container.setVisible(true);
		container.getColor().a = 1f;
		container.clearActions();

		container.addAction(Actions.sequence(
				Actions.delay(2f),
				Actions.fadeOut(1f),
				Actions.run(() -> {
					container.setVisible(false);
					isVisible = false;
				})
		));
	}

	public void draw() {
		if (isVisible) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public Stage getStage() {
		return stage;
	}

	public void dispose() {
		stage.dispose();
	}

	private Texture createRoundedBackground(int width, int height, int radius) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
		Color bgColor = new Color(0.3f, 0.3f, 0.3f, 0.8f); // خاکستری نیمه شفاف
		pixmap.setColor(bgColor);

		// رسم مستطیل با لبه گرد
		pixmap.fillCircle(radius, radius, radius);
		pixmap.fillCircle(width - radius - 1, radius, radius);
		pixmap.fillCircle(radius, height - radius - 1, radius);
		pixmap.fillCircle(width - radius - 1, height - radius - 1, radius);
		pixmap.fillRectangle(radius, 0, width - 2 * radius, height); // مرکز افقی
		pixmap.fillRectangle(0, radius, width, height - 2 * radius); // مرکز عمودی

		Texture texture = new Texture(pixmap);
		pixmap.dispose();
		return texture;
	}

	private BitmapFont generateFont(int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Blomberg-8MKKZ.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		parameter.color = Color.WHITE;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}
}
