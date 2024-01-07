package at.technikum.server.controller;

import at.technikum.models.Transaction;
import at.technikum.models.User;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.repositories.transaction.ITransactionRepository;
import at.technikum.repositories.transaction.TransactionRepository;
import at.technikum.repositories.user.IUserRepository;
import at.technikum.repositories.user.UserRepository;
import at.technikum.enums.EContentType;
import at.technikum.enums.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.TransactionMapper;

public class TransactionController {
    private static final ITransactionRepository transactionRepository = new TransactionRepository();
    private static final IPackageRepository packageRepository = new PackageRepository();
    private static final IUserRepository userRepository = new UserRepository();
    public Response store(User user) {
        int availablePackageId = packageRepository.getAvailable();
        if(availablePackageId == -1)
            return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.JSON, "{\"message\":\"Currently no packages available.\"}");

        if (user.getCoins() < 5)
            return new Response(HttpStatus.UNPROCESSABLE_ENTITY, EContentType.JSON, "{\"message\":\"Not enough money.\"}");

        Transaction transaction = transactionRepository.create(user.getId(), availablePackageId);
        userRepository.edit(user.getUsername(), user.getName(), user.getBio(), user.getImage(), user.getCoins()-5);

        return new Response(HttpStatus.CREATED, EContentType.JSON, TransactionMapper.transactionToJson(transaction));
    }
}
