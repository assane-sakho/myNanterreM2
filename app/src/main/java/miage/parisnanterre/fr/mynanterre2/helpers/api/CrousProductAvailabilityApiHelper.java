package miage.parisnanterre.fr.mynanterre2.helpers.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import miage.parisnanterre.fr.mynanterre2.api.crous.CrousProduct;
import miage.parisnanterre.fr.mynanterre2.api.crous.ProductAvailability;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CrousProductAvailabilityApiHelper extends ApiHelper<ProductAvailability, ProductAvailability> {
    private static CrousProductAvailabilityApiHelper instance;
    private static String baseEndPoint = "crous_product_availabilities";

    private CrousProductAvailabilityApiHelper() {
        super(baseEndPoint, false);
    }

    public static CrousProductAvailabilityApiHelper getInstance() {
        if (instance == null)
            instance = new CrousProductAvailabilityApiHelper();
        return instance;
    }

    @Override
    List<ProductAvailability> convertToList(JsonArray jsonArray) {
        return Arrays.asList(gson.fromJson(jsonArray, ProductAvailability[].class));
    }

    @Override
    List<ProductAvailability> convertToList(String jsonString) {
        return Arrays.asList(gson.fromJson(jsonString, ProductAvailability[].class));
    }

    @Override
    ProductAvailability convertToComplete(String jsonString) {
        return gson.fromJson(jsonString, ProductAvailability.class);
    }

    public void createAvailability(boolean isAvailable, CrousProduct crousProduct) throws IOException {
        ProductAvailability productAvailability = new ProductAvailability(isAvailable, crousProduct);
        crousProduct.addProductAvailabilities(productAvailability);
        String jsonString = gson.toJson(productAvailability).replace("{\"id\":0,", "{"); //id is not used for insertion
        sendData(jsonString, ApiRequestMethod.POST);
    }
}
