package at.technikum.server.controller;

import at.technikum.models.Transaction;
import at.technikum.models.User;
import at.technikum.repositories.packages.IPackageRepository;
import at.technikum.repositories.packages.PackageRepository;
import at.technikum.repositories.transaction.ITransactionRepository;
import at.technikum.repositories.transaction.TransactionRepository;
import at.technikum.server.EContentType;
import at.technikum.server.HttpStatus;
import at.technikum.server.Response;
import at.technikum.server.mappers.TransactionMapper;

import java.util.ArrayList;

public class TransactionController {
    private static final ITransactionRepository transactionRepository = new TransactionRepository();
    private static final IPackageRepository packageRepository = new PackageRepository();
    public Response store(User user) {
        int availablePackageId = packageRepository.getAvailable();
        if(availablePackageId == -1)
            return new Response(HttpStatus.NOT_FOUND, EContentType.JSON, "{\"message\":\"Currently no packages available.\"}");

        Transaction transaction = transactionRepository.create(user.getId(), availablePackageId);
        return new Response(HttpStatus.CREATED, EContentType.JSON, TransactionMapper.transactionToJson(transaction));
    }
}
