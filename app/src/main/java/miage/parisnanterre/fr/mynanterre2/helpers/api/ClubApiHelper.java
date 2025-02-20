package miage.parisnanterre.fr.mynanterre2.helpers.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import miage.parisnanterre.fr.mynanterre2.api.club.Club;
import miage.parisnanterre.fr.mynanterre2.api.club.Publication;
import miage.parisnanterre.fr.mynanterre2.api.club.SimpleClub;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ClubApiHelper extends ApiHelper<SimpleClub, Club> {

    private static ClubApiHelper instance;
    private static final String baseFinalEndPoint = "clubs";

    private ClubPublicationApiHelper clubPublicationApiHelper;

    private ClubApiHelper() {
        super(baseFinalEndPoint, true);
        clubPublicationApiHelper = ClubPublicationApiHelper.getInstance();
    }

    public static ClubApiHelper getInstance()
    {
        if(instance == null)
            instance = new ClubApiHelper();
        return instance;
    }

    @Override
    List<SimpleClub> convertToList(JsonArray jsonArray) {
        return Arrays.asList(gson.fromJson(jsonArray, Club[].class));
    }

    @Override
    List<SimpleClub> convertToList(String jsonString) {
        return Arrays.asList(gson.fromJson(jsonString, Club[].class));
    }

    @Override
    Club convertToComplete(String jsonString) {
        return gson.fromJson(jsonString, Club.class);
    }

    public SimpleClub getSimpleClub(int id)
    {
        return getSimpleElement(id);
    }

    public List<Publication> getPublications(int clubId) throws ExecutionException, InterruptedException {
        return clubPublicationApiHelper.getAllPublications("" + clubId);
    }

    public List<SimpleClub> getMoreSimpleClubs()
    {
        return getMoreSimpleElements();
    }

    public List<SimpleClub> getCreatedClubs()
    {
        int creatorId = UserApiHelper.getInstance().getUserConnected().getId();
        resetPaginationIndex();
        baseEndpointUrl = baseFinalEndPoint + "?creator=" + creatorId;
        parametersCompleter = '&';
        List<SimpleClub> result =  getMoreSimpleElements();
        baseEndpointUrl = baseFinalEndPoint;
        parametersCompleter = '?';
        return result;
    }
    public Club createClub(Club club) throws IOException {
        String jsonString = gson.toJson(club).replace("{\"id\":0,", "{"); //id is not used for insertion
        return convertToComplete(sendData(jsonString, ApiRequestMethod.POST));
    }

    public Club updateClub(Club club) throws IOException {
        String jsonString = gson.toJson(club);
        return convertToComplete(sendData(jsonString, ApiRequestMethod.PUT, Optional.of(club.getId())));
    }

    public boolean deleteClub(Club club) throws IOException {
        String jsonString = gson.toJson(club);
        sendData(jsonString, ApiRequestMethod.DELETE, Optional.of(club.getId()));
        return true;
    }
}
