package data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.items.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
	public static void load() {
		ConsumableData.setData(loadEach(ConsumableData.getDataURL(), new TypeToken<List<ConsumableData>>(){}.getType()));
		AllItemsData.setData(loadEach(AllItemsData.getDataURL(), new TypeToken<List<AllItemsData>>(){}.getType()));
		PlaceableData.setData(loadEach(PlaceableData.getDataURL(), new TypeToken<List<PlaceableData>>(){}.getType()));
		SalableData.setData(loadEach(SalableData.getDataURL(), new TypeToken<List<SalableData>>(){}.getType()));
		SeedData.setData(loadEach(SeedData.getDataURL(), new TypeToken<List<SeedData>>(){}.getType()));
		TreeData.setData(loadEach(TreeData.getDataURL(), new TypeToken<List<TreeData>>(){}.getType()));

		RecipeData.setCookingData(loadEach(RecipeData.getCookingDataURL(), new TypeToken<List<RecipeData>>(){}.getType()));
		RecipeData.setCraftingData(loadEach(RecipeData.getCraftingDataURL(), new TypeToken<List<RecipeData>>(){}.getType()));

		ArtisanGoodsData.setData(loadEach(ArtisanGoodsData.getDataURL(), new TypeToken<List<ArtisanGoodsData>>(){}.getType()));

		ScarecrowData.setData(loadEach(ScarecrowData.getDataURL(), new TypeToken<List<ScarecrowData>>(){}.getType()));
		SprinklerData.setData(loadEach(SprinklerData.getDataURL(), new TypeToken<List<SprinklerData>>(){}.getType()));

		ShopData.setData(loadEach(ShopData.getDataURL(), new TypeToken<List<ShopData>>(){}.getType()));

		AnimalData.setData(loadEach(AnimalData.getDataURL(), new TypeToken<List<AnimalData>>(){}.getType()));
		AnimalHouseData.setData(loadEach(AnimalHouseData.getDataURL(), new TypeToken<List<AnimalHouseData>>(){}.getType()));

		FishData.setData(loadEach(FishData.getDataURL(), new TypeToken<List<FishData>>(){}.getType()));
		ForagingCropData.setData(loadEach(ForagingCropData.getDataURL(), new TypeToken<List<ForagingCropData>>(){}.getType()));
		ForagingSeedData.setData(loadEach(ForagingSeedData.getDataURL(), new TypeToken<List<ForagingSeedData>>(){}.getType()));
		ForagingTreeData.setData(loadEach(ForagingTreeData.getDataURL(), new TypeToken<List<ForagingTreeData>>(){}.getType()));
		MineralData.setData(loadEach(MineralData.getDataURL(), new TypeToken<List<MineralData>>(){}.getType()));
		MixedSeedData.setData(loadEach(MixedSeedData.getDataURL(), new TypeToken<List<MixedSeedData>>(){}.getType()));
		TreeData.setData(loadEach(TreeData.getDataURL(), new TypeToken<List<TreeData>>(){}.getType()));

		VillagerData.setData(loadEach(VillagerData.getDataURL(), new TypeToken<List<VillagerData>>(){}.getType()));
	}

	public static <T extends Data> ArrayList<T> loadEach(String URL, Type typeOfT) {
		Gson gson = new Gson();
		ArrayList<T> data = null;

        try {
            FileHandle file = Gdx.files.internal(URL);
			data = gson.fromJson(file.readString(), typeOfT);
			for (T a : data) {
				a.fullConstruct();
			}
		} catch (Exception e) {
			System.out.println("Can't open " + URL + ": " + e.getMessage());
			e.printStackTrace();
		}

		return data;
	}
}
