package flag.dev.picpay.controller;

import flag.dev.picpay.controller.dto.CreateWalletDto;
import flag.dev.picpay.entity.Wallet;
import flag.dev.picpay.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDto dto) {
       var wallet = walletService.createWallet(dto);
       return ResponseEntity.ok(wallet);
    }

    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getWallets() {
        var wallets = walletService.getWallters();
        return  ResponseEntity.ok(wallets);
    }

}
