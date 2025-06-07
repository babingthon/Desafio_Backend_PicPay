package flag.dev.picpay.controller;

import flag.dev.picpay.controller.dto.TransferDto;
import flag.dev.picpay.controller.dto.TransferResponseDto;
import flag.dev.picpay.entity.Transfer;
import flag.dev.picpay.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDto dto) {
       var resp = transferService.transfer(dto);
       return ResponseEntity.ok(resp);
    }

    @GetMapping("/transfer")
    public ResponseEntity<List<TransferResponseDto>> getTransfers() {
        var transfers = transferService.getTransfers();
        return ResponseEntity.ok(transfers);
    }
}
