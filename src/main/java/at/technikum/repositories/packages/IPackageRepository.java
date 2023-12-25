package at.technikum.repositories.packages;

import at.technikum.models.Package;
import at.technikum.models.card.ACard;

import java.util.ArrayList;

public interface IPackageRepository {
    Package create();

    ArrayList<ACard> getCards(int packageId);
}
