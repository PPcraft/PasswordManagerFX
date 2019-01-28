package ppcraft.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ppcraft.interfaces.SitesDirectory;
import ppcraft.objects.Site;

public class CollectionSitesDirectory implements SitesDirectory {

    private ObservableList<Site> siteList = FXCollections.observableArrayList();

    public void add(Site site) {
        getSiteList().add(site);
    }

    public void update(Site site) {
    }

    public void delete(Site site) {
        getSiteList().remove(site);
    }

    public ObservableList<Site> getSiteList(){
        return siteList;
    }
}
