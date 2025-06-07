package flag.dev.picpay.service;

import flag.dev.picpay.controller.dto.CreateWalletDto;
import flag.dev.picpay.entity.Wallet;
import flag.dev.picpay.exception.InvalidCpfCnpjException;
import flag.dev.picpay.exception.InvalidEmailException;
import flag.dev.picpay.exception.WalletDataAlreadyExistsExeption;
import flag.dev.picpay.repository.WalletRepository;
import flag.dev.picpay.util.CpfCnpjValidator;
import flag.dev.picpay.util.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {
        if (!CpfCnpjValidator.isValidCpfCnpj(dto.cpfCnpj())) {
            throw new InvalidCpfCnpjException("Provided CPF or CNPJ is invalid: " + dto.cpfCnpj());
        }

        if (!EmailValidator.isValidEmail(dto.email())) {
            throw new InvalidEmailException("Provided Email is invalid: " + dto.email());
        }

        var walletDb = walletRepository.findByCpfCnpjOrEmail(dto.cpfCnpj(), dto.email());
       if (walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsExeption("CpfCnpj already exists");
       }

      return walletRepository.save(dto.toWallet());
    }

    public List<Wallet> getWallters() {
        var wallets = walletRepository.findAll();
        return wallets;
    }
}
