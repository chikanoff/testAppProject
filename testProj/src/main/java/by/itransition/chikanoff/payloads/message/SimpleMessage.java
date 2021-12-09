package by.itransition.chikanoff.payloads.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SimpleMessage {
    private String name;
}
