package flag.dev.picpay.service;

import flag.dev.picpay.controller.dto.TransferDto;
import flag.dev.picpay.controller.dto.TransferResponseDto;
import flag.dev.picpay.entity.Transfer;
import flag.dev.picpay.entity.Wallet;
import flag.dev.picpay.exception.InsufficientBalanceException;
import flag.dev.picpay.exception.TransferNotAllowedException;
import flag.dev.picpay.exception.TransferNotAuthorizedException;
import flag.dev.picpay.exception.WalletNotFoundException;
import flag.dev.picpay.repository.TransferRepository;
import flag.dev.picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(NotificationService notificationService,
                           AuthorizationService authorizationService,
                           TransferRepository transferRepository,
                           WalletRepository walletRepository) {
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto dto) {
        var sender = walletRepository.findById(dto.payer())
                .orElseThrow(() -> new WalletNotFoundException(dto.payer()));

        var receiver = walletRepository.findById(dto.payee())
                .orElseThrow(() -> new WalletNotFoundException(dto.payee()));

        validateTransfer(dto, sender);

        sender.debit(dto.value());
        receiver.credit(dto.value());

        var transfer = new Transfer(sender, receiver, dto.value());
        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;
    }

    private void validateTransfer(TransferDto dto, Wallet sender) {
        if (!sender.IsTransferAllowedforWalletType()){
            throw new TransferNotAllowedException();
        }

        if (!sender.IsBalangeEqualsOrGreaterThan(dto.value())){
            throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(dto)){
            throw new TransferNotAuthorizedException();
        }
    }

    public List<TransferResponseDto> getTransfers() {
        var transfers = transferRepository.findAll();
        return transfers.stream()
                .map(t -> new TransferResponseDto(
                        t.getId(),
                        t.getValue(),
                        t.getReceiver().getId(),
                        t.getSender().getId()
                ))
                .toList();
    }
}
