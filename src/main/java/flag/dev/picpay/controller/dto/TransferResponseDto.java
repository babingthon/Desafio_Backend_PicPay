package flag.dev.picpay.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferResponseDto(UUID id, BigDecimal value, Long receiver_id, Long sender_id) {
}
