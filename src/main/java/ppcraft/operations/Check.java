package ppcraft.operations;

import ppcraft.objects.Site;

import java.util.List;

public class Check {
    public static boolean checkFile(String lineZero){
        boolean foundFile = false;
        if (!lineZero.equals("")) {
            foundFile = true;
        }
        return foundFile;
    }

    public static int checkAddress(List<Site> siteList, String searchAddress){
      int index = siteList.size() + 1;
        for (int i = 0; i < siteList.size(); i++){
            Site selectSite = siteList.get(i);
            if (selectSite.getAddress().equals(searchAddress)){
               index = i;
            }
        }
      return index;
    }
}
