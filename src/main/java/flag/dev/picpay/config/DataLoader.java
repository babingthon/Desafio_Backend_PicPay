package flag.dev.picpay.config;

import flag.dev.picpay.entity.WalletType;
import flag.dev.picpay.repository.WalletTypeRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final WalletTypeRepository walletTypeRepository;

    public DataLoader(WalletTypeRepository walletTypeRepository) {
        this.walletTypeRepository = walletTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(WalletType.EnumWalletType.values())
                .forEach(walletType -> walletTypeRepository.save(walletType.get()));
    }
}
