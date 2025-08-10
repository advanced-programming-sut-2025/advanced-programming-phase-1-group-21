package models.tool;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import models.Item.Item;
import models.Item.ItemType;
import models.Saver;
import models.game.Game;
import models.game.Player;
import models.map.Coord;
import models.map.Direction;
import models.result.Result;
import org.w3c.dom.Text;


public abstract class Tool extends Item implements Saver {
    private final ToolType toolType;
	public ToolMaterialType toolMaterialType;
	private transient Texture texture;
	private transient Sprite sprite;
	public float spriteX;
	public float spriteY;
	public float animationTime;
	public int animationDirection;
	
	public Tool(ToolType toolType) {
		this.toolType = toolType;
		if (toolType == ToolType.SCYTHE) {
			toolMaterialType = ToolMaterialType.STEEL;
		}
		else {
			toolMaterialType = ToolMaterialType.PRIMITIVE;
		}
		loadTexture();
	}

	@Override
	public void save() {
		spriteX = sprite.getX();
		spriteY = sprite.getY();
	}

	@Override
	public void load() {
		loadTexture();
	}

	public void animation(float delta){
		animationTime += delta;
		sprite.rotate((float) (3.14 + animationTime * 0.01 * animationDirection * MathUtils.radiansToDegrees));
		if(animationTime > 1)
			animationTime = 0;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

	public void setAnimationDirection(int animationDirection) {
		this.animationDirection = animationDirection;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void loadTexture(){
		String type = this.getToolMaterialType().toString();
		String name = this.getToolType().toString();
		String result = type.substring(0, 1).toUpperCase() + type.substring(1).toLowerCase() + "_" + name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		texture = SharedAssetManager.getToolTexture(result);
		if(sprite == null)
			sprite = new Sprite(texture);
		sprite.setTexture(texture);
		sprite.setX(spriteX);
		sprite.setY(spriteY);
	}

	public void handleRotation(int x , int y){
		float angle = (float) Math.atan2(y - spriteY, x - spriteX);
		sprite.setRotation((float) (3.14 + angle * MathUtils.radiansToDegrees));
	}

	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}

	abstract public Result<Item> use(Coord coord, Game game, Player player);

	public String getName() {
		return toolType.getName();
	}

	public ItemType getItemType() {
		return ItemType.TOOL;
	}

	public int getAmount() {
		return 1;
	}

	public void setAmount(int amount) {
		throw new RuntimeException("HEY. You are not supposed to call this method. Because this is a tool: " + toolType.getName() + " and you can't set new amount for it.");
	}

	public void setToolMaterialType(ToolMaterialType toolMaterialType) {
		this.toolMaterialType = toolMaterialType;
	}

	public void changeAmount(int change) {
		throw new RuntimeException("HEY. You are not supposed to call this method. Because this is a tool: " + toolType.getName() + " and you can't change it's amount.");
	}

	public boolean isSalable() {
		return false;
	}

	public int getPrice() {
		return 0;
	}

	@Override
	public String toString() {
		return String.format(
				"%s{toolType=%s, toolMaterialType=%s}",
				getClass().getSimpleName(),
				toolType,
				toolMaterialType
		);
	}
}
