package at.technikum.models;

import at.technikum.models.card.ACard;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;

import java.util.ArrayList;

public class Package extends AModel {
    private static final IPackageRepository packageRepository = new PackageRepository();
    public Package(int id) {
        this.id = id;
    }

    public ArrayList<ACard> getCards() {
        return packageRepository.getCards(this.id);
    }

    protected ArrayList<ACard> cards;
}
