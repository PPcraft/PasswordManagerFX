package ppcraft.operations;

import ppcraft.crypto.Crypto;
import ppcraft.objects.Site;

import java.util.List;

import static ppcraft.main.Main.resurs;

public class PrepareData {
    private String address;
    private String login;
    private String password;

    public void writeSites(List<Site> siteList){
        String passData = Crypto.encrypt(Crypto.md5Crypto()) + "\n";
        WriteFile.write(passData);
        for (int i = 0; i < siteList.size(); i++){
            Site siteThis = siteList.get(i);
            writeOneSite(siteThis);
        }
    }

    public void writeOneSite(Site site){
        this.address = Crypto.encrypt(site.getAddress());
        this.login = Crypto.encrypt(site.getLogin());
        this.password = Crypto.encrypt(site.getPassword());
        String data = address + "\n" + login + "\n" + password + "\n";
        WriteFile.writeAdd(data);
    }

    public Site readSite(int j){
        Site result = new Site();
            if (resurs[j] != ""){
                this.address = Crypto.decrypt(resurs[j]);
                this.login = Crypto.decrypt(resurs[j+1]);
                this.password = Crypto.decrypt(resurs[j+2]);
                result.setAddress(this.address);
                result.setLogin(this.login);
                result.setPassword(this.password);
            }
        return result;
    }
}
