package ppcraft.interfaces;

import ppcraft.objects.Site;

public interface SitesDirectory {
    void add(Site site);

    void update(Site site);

    void delete(Site site);
}
